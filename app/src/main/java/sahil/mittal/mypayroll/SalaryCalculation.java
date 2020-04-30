package sahil.mittal.mypayroll;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SalaryCalculation extends AppCompatActivity {
    Spinner inputMonth;
    EditText Searchid, getid, getname, getpost, getsalary, gethra, getda, getmedical, getbonus, getallowance, getdeduction, getdays;
    Button saveButton, searchButton;
    String id, date, name, post,email, salary,da,hra,bonus,medical,allowance,leaves,deduction,department;
    DatabaseReference employeeRef,postRef, allowanceRef, deductionRef,salaryRef;
    private static final String TAG = "SalaryCalculation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_calculation);

        inputMonth = findViewById(R.id.input_month);
        Searchid = findViewById(R.id.input_search);
        searchButton = findViewById(R.id.search_button);

        getid = findViewById(R.id.get_id);
        getname = findViewById(R.id.get_name);
        getpost = findViewById(R.id.get_post);
        getsalary = findViewById(R.id.get_salary);

        getda = findViewById(R.id.get_da);
        gethra = findViewById(R.id.get_hra);
        getbonus = findViewById(R.id.get_bonus);
        getmedical = findViewById(R.id.get_medical);
        getallowance = findViewById(R.id.get_allowance);

        getdays = findViewById(R.id.get_leavedays);
        getdeduction = findViewById(R.id.get_deduction);


        saveButton=findViewById(R.id.save_button);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = Searchid.getText().toString();
                date = inputMonth.getSelectedItem().toString().trim();
                if (!TextUtils.isEmpty(id)) {
                    employeeRef= FirebaseDatabase.getInstance().getReference().child("Employees").child(id);
                    employeeRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                name = dataSnapshot.child("name").getValue().toString();
                                post=dataSnapshot.child("post").getValue().toString();
                                salary = dataSnapshot.child("salary").getValue().toString();
                                email=dataSnapshot.child("email").getValue().toString();
                                department=dataSnapshot.child("department").getValue().toString();

                                int monthlySalary = Integer.parseInt(salary);
                                monthlySalary = monthlySalary / 12;
                                String finalSalary = String.valueOf(monthlySalary);
                                getid.setText(id);
                                getname.setText(name);
                                getpost.setText(post);
                                getsalary.setText(finalSalary);


                                postRef = FirebaseDatabase.getInstance().getReference("Post");
                                postRef.child(post).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        da=dataSnapshot.child("da").getValue().toString();
                                        hra=dataSnapshot.child("hra").getValue().toString();
                                        bonus=dataSnapshot.child("bonus").getValue().toString();
                                        medical=dataSnapshot.child("medical").getValue().toString();
                                        getda.setText(da);
                                        gethra.setText(hra);
                                        getbonus.setText(bonus);
                                        getmedical.setText(medical);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                allowanceRef=FirebaseDatabase.getInstance().getReference().child("Allowances");
                                allowanceRef.child(date).child(id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        allowance=dataSnapshot.child("allowance").getValue().toString();

                                        getallowance.setText(allowance);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                deductionRef=FirebaseDatabase.getInstance().getReference().child("Deduction");
                                deductionRef.child(date).child(id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        leaves=dataSnapshot.child("leaves").getValue().toString();
                                        deduction=dataSnapshot.child("deduction").getValue().toString();

                                        getdeduction.setText(deduction);
                                        getdays.setText(leaves);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            } else {
                                Toast.makeText(SalaryCalculation.this, "Employee not exists", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(SalaryCalculation.this, "Please input ID", Toast.LENGTH_SHORT).show();
                }
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salaryRef=FirebaseDatabase.getInstance().getReference().child("Salary").child(date);
                salaryRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        name=getname.getText().toString().trim();
                        post=getpost.getText().toString().trim();
                        salary=getsalary.getText().toString().trim();
                        da=getda.getText().toString().trim();
                        hra=gethra.getText().toString().trim();
                        bonus=getbonus.getText().toString().trim();
                        medical=getmedical.getText().toString().trim();
                        allowance=getallowance.getText().toString().trim();
                        leaves=getdays.getText().toString().trim();
                        deduction=getdeduction.getText().toString().trim();

                        saveSalary save_salary=new saveSalary(id,department,name,post,email,salary,da,hra,bonus,medical,allowance,leaves,deduction);
                        salaryRef.child(id).setValue(save_salary);
                        Toast.makeText(SalaryCalculation.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}