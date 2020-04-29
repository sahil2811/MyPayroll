package sahil.mittal.mypayroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TotalEmployee extends AppCompatActivity {
    ListView listViewEmployee;
    DatabaseReference reff;
    List<Employees> employeesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_employee);
        listViewEmployee=findViewById(R.id.listViewEmployee);
        reff= FirebaseDatabase.getInstance().getReference("Employees");
        employeesList =new ArrayList<>();

    }
    @Override
    protected void onStart() {
        super.onStart();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeesList.clear();
                for(DataSnapshot employeeSnapshot:dataSnapshot.getChildren()){
                     Employees employees=employeeSnapshot.getValue(Employees.class);
                     employeesList.add(employees);

                 }
                EmployeeList adapter=new EmployeeList(TotalEmployee.this,employeesList);
                listViewEmployee.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
