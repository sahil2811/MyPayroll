package sahil.mittal.mypayroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Deduction extends AppCompatActivity {
    TextView searchID, inputId, inputName, inputEmail, input_days, input_Amount, inputDate;
    ;
    Button searchButton, calculateAmount, saveButton;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    DatabaseReference reff, deductionRef;
    String id, finalDeduction, date,salaryMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deduction);


        saveButton = findViewById(R.id.button_save);
        searchID = findViewById(R.id.input_search);
        inputId = findViewById(R.id.input_id);
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        searchButton = findViewById(R.id.button_search);
        input_days = findViewById(R.id.input_leaveDays);
        input_Amount = findViewById(R.id.input_Amount);
        inputDate = findViewById(R.id.input_allowanceDate);
        calculateAmount = findViewById(R.id.button_calculate);


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
                            String email = dataSnapshot.child("email").getValue().toString();

                            inputId.setText(id);
                            inputName.setText(name);
                            inputEmail.setText(email);

                        } else {
                            Toast.makeText(Deduction.this, "Employee not exists", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        calculateAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String daysString = input_days.getText().toString().trim();
                int days = Integer.parseInt(daysString);

                int totalAmount = 1500 * days;
                finalDeduction = String.valueOf(totalAmount);
                input_Amount.setText(finalDeduction);
            }
        });


        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Deduction.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                salaryMonth=String.valueOf(month);
                date = month + "/" + day + "/" + year;
                inputDate.setText(date);
            }
        };


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deductionRef = FirebaseDatabase.getInstance().getReference("Deduction").child(salaryMonth);
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String deduction = input_Amount.getText().toString().trim();
                String deductionDate = inputDate.getText().toString().trim();
                String leaves=input_days.getText().toString().trim();

                saveDeduction saveDeduction = new saveDeduction(id, name, email,leaves,deductionDate,deduction);
                deductionRef.child(id).setValue(saveDeduction);
                Toast.makeText(Deduction.this, "Deduction saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

