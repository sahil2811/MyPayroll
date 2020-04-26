package sahil.mittal.mypayroll;

import android.app.ProgressDialog;
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

public class AddEmployee extends AppCompatActivity  {

    private EditText inputName, inputEmail, inputContact, inputBirth, inputPost, inputAddress, inputAge, inputSalary;
    private Spinner inputDepartment;
    private Button buttonAdd, buttonClear;
    private DatabaseReference reff;
    long employeeId = 0;
    Employees employees;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);



        //Connecting to attributes of AddEmployee xml file.
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputBirth = findViewById(R.id.input_birth);
        inputDepartment = findViewById(R.id.spinner1);
        inputPost = findViewById(R.id.input_post);
        inputAddress = findViewById(R.id.input_address);
        inputAge = findViewById(R.id.input_age);
        inputContact = findViewById(R.id.input_contact);
        inputSalary = findViewById(R.id.input_Salary);
        buttonAdd = findViewById(R.id.button_add);
        buttonClear = findViewById(R.id.button_clear);
        //Database refrence
        reff=FirebaseDatabase.getInstance().getReference("Employees");

        //Generate Custom unique EmployeeID
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    employeeId = (dataSnapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        //Add employee button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intialize progress dialog
                mProgressDialog=new ProgressDialog(AddEmployee.this);
                //show dialog
                mProgressDialog.show();
                //set content view
                mProgressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                mProgressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                AddEmployee();
            }
        });


        //Clear employees button
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearEmployees();
            }
        });
    }

    //Clear Button
    private void ClearEmployees(){
        inputName.setText("");
        inputEmail.setText("");
        inputContact.setText("");
        inputAge.setText("");
        inputAddress.setText("");
        inputPost.setText("");
        inputSalary.setText("");
        inputBirth.setText("");
    }

    private void AddEmployee(){
        String name=inputName.getText().toString().trim();
        String email=inputEmail.getText().toString().trim();
        String dob=inputBirth.getText().toString().trim();
        String department=inputDepartment.getSelectedItem().toString().trim();
        String post=inputPost.getText().toString().trim();
        String address=inputAddress.getText().toString().trim();
        String age=inputAge.getText().toString().trim();
        String contact=inputContact.getText().toString().trim();
        String salary=inputSalary.getText().toString().trim();

        //Validtion of Input field
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(dob)
                && !TextUtils.isEmpty(department) && !TextUtils.isEmpty(post) && !TextUtils.isEmpty(address)
                && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(contact) && !TextUtils.isEmpty(salary))
        {
            String id=reff.child(String.valueOf(employeeId + 1)).getKey();
            Employees employees=new Employees(id,name,email,dob,department,post,address,age,contact,salary);
            reff.child(String.valueOf(employeeId + 1)).setValue(employees);
            Toast.makeText(AddEmployee.this, "Employee Added", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();

        }else{
            Toast.makeText(this, "Please complete details", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }

    }
}
