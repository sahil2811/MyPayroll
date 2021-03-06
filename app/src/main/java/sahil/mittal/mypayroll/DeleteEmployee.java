package sahil.mittal.mypayroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteEmployee extends AppCompatActivity {

    TextView inputName, inputEmail, inputId, inputContact, inputDepartment, inputPost, inputAge, inputAddress, searchID;
    Button searchButton,deleteButton;
    DatabaseReference reff;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

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
        deleteButton=findViewById(R.id.button_delete);





        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=searchID.getText().toString();
                reff = FirebaseDatabase.getInstance().getReference().child("Employees").child(id);
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

                            inputId.setText(id);
                            inputName.setText(name);
                            inputEmail.setText(email);
                            inputContact.setText(contact);
                            inputDepartment.setText(department);
                            inputAge.setText(age);
                            inputPost.setText(post1);
                            inputAddress.setText(address);
                        }else
                        {
                            Toast.makeText(DeleteEmployee.this, "Employee not exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Employees")
                        .child(id)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DeleteEmployee.this, "Employee removed ", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}
