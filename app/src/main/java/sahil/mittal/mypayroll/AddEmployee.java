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
    long empployeeId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        inputName=findViewById(R.id.input_name);
        inputEmail=findViewById(R.id.input_email);
        inputContact=findViewById(R.id.input_contact);
        inputBirth=findViewById(R.id.input_birth);
        inputDepartment=findViewById(R.id.input_department);
        inputAddress=findViewById(R.id.input_address);
        inputAge=findViewById(R.id.input_age);
        inputPost=findViewById(R.id.input_post);
        buttonAdd=findViewById(R.id.button_add);
        final Employees employees=new Employees();
        reff= FirebaseDatabase.getInstance().getReference().child("Employees");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    empployeeId=(dataSnapshot.getChildrenCount());

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

               employees.setContact(inputContact.getEditText().getText().toString().trim());
               employees.setAge(inputAge.getEditText().getText().toString().trim());
//                Integer Age=Integer.parseInt(inputAge.getEditText().getText().toString());

                employees.setName(inputName.getEditText().getText().toString().trim());
                employees.setEmail(inputEmail.getEditText().getText().toString().trim());
                employees.setBirthDate(inputBirth.getEditText().getText().toString().trim());
                employees.setDepartment(inputDepartment.getEditText().getText().toString().trim());
                employees.setAddress(inputAddress.getEditText().getText().toString().trim());
                employees.setPost(inputPost.getEditText().getText().toString().trim());
//                employees.setContact(Contact);
//                employees.setAge(Age);
                reff.child(String.valueOf(empployeeId+1)).setValue(employees);
                Toast.makeText(AddEmployee.this, "Employee Added", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
