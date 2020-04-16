package sahil.mittal.mypayroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class AddEmployee extends AppCompatActivity {

    private TextInputLayout inputName,inputEmail,inputContact,inputBirth,inputDepartment,inputPost,inputAddress,inputAge;
    private Button buttonAdd,buttonClear;
    private DatabaseReference reff;
    EditText a;
    long employeeId=100;
    Employees employees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        a=(EditText)findViewById(R.id.input_a);
        inputName=findViewById(R.id.input_name);
        inputEmail=findViewById(R.id.input_email);
        inputContact=(TextInputLayout)findViewById(R.id.input_contact);
        inputBirth=findViewById(R.id.input_birth);
        inputDepartment=findViewById(R.id.input_department);
        inputAddress=findViewById(R.id.input_address);
        inputAge=(TextInputLayout) findViewById(R.id.input_age);
        inputPost=findViewById(R.id.input_post);
        buttonAdd=findViewById(R.id.button_add);
        buttonClear=findViewById(R.id.button_clear);
        employees=new Employees();
        reff= FirebaseDatabase.getInstance().getReference().child("Employees");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    employeeId=(dataSnapshot.getChildrenCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Add employye button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age=Integer.parseInt(inputAge.getEditText().getText().toString().trim());
                employees.setAge(age);
                employees.setContact(inputContact.getEditText().getText().toString().trim());
                employees.setName(inputName.getEditText().getText().toString().trim());
                employees.setEmail(inputEmail.getEditText().getText().toString().trim());
                employees.setBirthDate(inputBirth.getEditText().getText().toString().trim());
                employees.setDepartment(inputDepartment.getEditText().getText().toString().trim());
                employees.setAddress(inputAddress.getEditText().getText().toString().trim());
                employees.setPost(inputPost.getEditText().getText().toString().trim());

                reff.child(String.valueOf(employeeId+2)).setValue(employees);
                Toast.makeText(AddEmployee.this, "Employee Added", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
