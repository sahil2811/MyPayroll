package sahil.mittal.mypayroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Allowance extends AppCompatActivity {
    TextView searchID,inputPost,inputHra,inputId,inputName,inputSalary,inputBonus,inputDa,inputMedical
            ,inputTotalAllownce,inputDate;
    Button searchButton,addButton,saveButton;
    String finalAllowance,date;
    int month;
    String salaryMonth;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    DatabaseReference reff,postRef,allowanceRef;
    String id,post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowance);

        searchID = findViewById(R.id.input_search);
        inputId = findViewById(R.id.input_id);
        inputName = findViewById(R.id.input_name);
        inputSalary= findViewById(R.id.input_Salary);
        searchButton = findViewById(R.id.button_search);
        inputPost = findViewById(R.id.input_post);
        inputHra=findViewById(R.id.input_Hra);
        inputBonus=findViewById(R.id.input_Bonus);
        inputMedical=findViewById(R.id.input_Medical);
        inputDa=findViewById(R.id.input_Da);
        addButton=findViewById(R.id.button_allowanceCalculate);
        inputTotalAllownce=findViewById(R.id.input_calculate);
        inputDate=findViewById(R.id.input_allowanceDate);
        saveButton=findViewById(R.id.button_save);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id = searchID.getText().toString();
                reff = FirebaseDatabase.getInstance().getReference().child("Employees").child(id);
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String name = dataSnapshot.child("name").getValue().toString();
                            String post1 = dataSnapshot.child("post").getValue().toString();
                            String salary = dataSnapshot.child("salary").getValue().toString();

                            Integer monthlySalary = Integer.parseInt(salary);
                            monthlySalary = monthlySalary / 12;
                            String finalSalary = String.valueOf(monthlySalary);

                            inputId.setText(id);
                            inputName.setText(name);
                            inputPost.setText(post1);
                            inputSalary.setText(finalSalary);

                            postRef = FirebaseDatabase.getInstance().getReference("Post");
                            postRef.child(post1).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String hra = dataSnapshot.child("hra").getValue().toString();
                                    String bonus = dataSnapshot.child("bonus").getValue().toString();
                                    String da = dataSnapshot.child("da").getValue().toString();
                                    String medical = dataSnapshot.child("medical").getValue().toString();

                                    Integer hraVal = Integer.parseInt(hra);
                                    Integer daVal = Integer.parseInt(da);
                                    Integer bonusVal = Integer.parseInt(bonus);
                                    Integer medicalVal = Integer.parseInt(medical);


                                    Integer TotalAllowance = hraVal + daVal + medicalVal + bonusVal;
                                    finalAllowance = String.valueOf(TotalAllowance);

                                    inputHra.setText(hra);
                                    inputBonus.setText(bonus);
                                    inputDa.setText(da);
                                    inputMedical.setText(medical);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(Allowance.this, "Employee not exists", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });




        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTotalAllownce.setText(finalAllowance);
            }
        });



        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year =cal.get(Calendar.YEAR);
                 month =cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(Allowance.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                ,mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                salaryMonth=String.valueOf(month);
                date=month+"/"+day+"/"+year;
                inputDate.setText(date);

            }
        };





        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                allowanceRef=FirebaseDatabase.getInstance().getReference("Allowances").child(salaryMonth);
                String name=inputName.getText().toString().trim();
                String post=inputPost.getText().toString().trim();
                String salary=inputSalary.getText().toString().trim();
                String allowance=inputTotalAllownce.getText().toString().trim();
                String allowanceDate=inputDate.getText().toString().trim();
                saveAllowance save=new saveAllowance(id,allowanceDate,name,post,salary,allowance);
                allowanceRef.child(id).setValue(save);
                Toast.makeText(Allowance.this, "Employee Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
