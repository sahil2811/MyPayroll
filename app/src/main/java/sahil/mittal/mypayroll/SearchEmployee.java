package sahil.mittal.mypayroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchEmployee extends AppCompatActivity {

    TextView inputName,inputSalary,inputEmail, inputId, inputContact, inputDepartment, inputPost, inputAge, inputAddress, searchID;
    Button searchButton;
    DatabaseReference reff;
    String id;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_employee);

        searchID = findViewById(R.id.input_search);
        searchButton = findViewById(R.id.button_search);
        inputId = findViewById(R.id.input_id);
        inputName = findViewById(R.id.input_allowance);
        inputContact = findViewById(R.id.input_contact);
        inputEmail = findViewById(R.id.input_email);
        inputDepartment = findViewById(R.id.input_department);
        inputAge = findViewById(R.id.input_age);
        inputAddress = findViewById(R.id.input_address);
        inputPost = findViewById(R.id.input_post);
        inputSalary=findViewById(R.id.input_Salary);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intialize progress dialog
                mProgressDialog=new ProgressDialog(SearchEmployee.this);
                //show dialog
                mProgressDialog.show();
                //set content view
                mProgressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                mProgressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                id=searchID.getText().toString();
                reff = FirebaseDatabase.getInstance().getReference().child("Employees").child(id);
                Search();
            }
        });
    }


    public void Search(){
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String age = dataSnapshot.child("age").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String contact = dataSnapshot.child("contact").getValue().toString();
                    String department = dataSnapshot.child("department").getValue().toString();
                    String post1 = dataSnapshot.child("post").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();
                    String salary=dataSnapshot.child("salary").getValue().toString();

                    inputId.setText(id);
                    inputName.setText(name);
                    inputEmail.setText(email);
                    inputContact.setText(contact);
                    inputDepartment.setText(department);
                    inputAge.setText(age);
                    inputPost.setText(post1);
                    inputAddress.setText(address);
                    inputSalary.setText(salary);

                    mProgressDialog.dismiss();
                }else
                {
                    Toast.makeText(SearchEmployee.this, "Employee not exists", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
