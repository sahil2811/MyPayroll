package sahil.mittal.mypayroll;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SalarySlip extends AppCompatActivity {

    Spinner inputMonth;
    Button searchButton, generateButton;
    String id, date, name, post,department,email, salary,da,hra,bonus,medical,allowance,leaves,deduction,netSalary,grossPay,taxAmount;
    EditText searchId, getId, getName, getEmail, getDepartment, getPost, getSalary;
    int width = 1000;
    final static int taxRate=5;
    DatabaseReference salaryRef;
    //pdf
    Bitmap bmp, scaledbmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_slip);

        inputMonth = findViewById(R.id.input_month);
        searchButton = findViewById(R.id.search_button);
        generateButton = findViewById(R.id.generate_button);
        searchId = findViewById(R.id.input_id);
        getId = findViewById(R.id.get_id);
        getName = findViewById(R.id.get_name);
        getEmail = findViewById(R.id.get_email);
        getDepartment = findViewById(R.id.get_department);
        getPost = findViewById(R.id.get_post);
        getSalary = findViewById(R.id.get_salary);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 300, 300, false);


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSalaryData();
            }
        });

    }


    public void searchSalaryData(){


        id =searchId.getText().toString();
        date = inputMonth.getSelectedItem().toString().trim();
        if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(date))
        {

            salaryRef= FirebaseDatabase.getInstance().getReference().child("Salary");
            salaryRef.child(date).child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        name = dataSnapshot.child("name").getValue().toString();
                         post= dataSnapshot.child("post").getValue().toString();
                         email= dataSnapshot.child("email").getValue().toString();
                         department=dataSnapshot.child("department").getValue().toString();

                         hra= dataSnapshot.child("hra").getValue().toString();
                         da= dataSnapshot.child("da").getValue().toString();
                         medical= dataSnapshot.child("medical").getValue().toString();
                         bonus= dataSnapshot.child("bonus").getValue().toString();
                         allowance= dataSnapshot.child("allowance").getValue().toString();
                         salary= dataSnapshot.child("salary").getValue().toString();

                         leaves= dataSnapshot.child("leaves").getValue().toString();
                         deduction= dataSnapshot.child("deduction").getValue().toString();

                         int calAllowance=Integer.parseInt(allowance);
                         int calSalary=Integer.parseInt(salary);
                         int calTotalAllowance=calAllowance+calSalary;

                         int calDeduction=Integer.parseInt(deduction);
                         int calGrossPay=calTotalAllowance-calDeduction;

                         double calTaxAmount=(calGrossPay*5)/100;

                         taxAmount=String.valueOf(calTaxAmount);

                         double calNetSalary=calGrossPay-calTaxAmount;

                         netSalary=String.valueOf(calNetSalary);

                         grossPay=String.valueOf(calGrossPay);


                         getId.setText(id);
                         getName.setText(name);
                         getEmail.setText(email);
                         getDepartment.setText(department);
                         getPost.setText(post);
                         getSalary.setText(salary);

                    }
                    else{
                        Toast.makeText(SalarySlip.this, "Data is not present", Toast.LENGTH_SHORT).show();
                    }


                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else{
            Toast.makeText(this, "Please Enter Details", Toast.LENGTH_SHORT).show();
        }

    }



    public void createPDF() {
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint titlePaint = new Paint();
                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1000, 2200, 1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                Canvas c = myPage1.getCanvas();

                c.drawBitmap(scaledbmp, 5, 5, myPaint);

                titlePaint.setTextAlign(Paint.Align.RIGHT);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(50);
                c.drawText("COMTECH Ltd.", 950, 100, titlePaint);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                myPaint.setTextSize(30);
                c.drawText("1234,Court Road,Patran,147105,IN", 950, 140, myPaint);
                c.drawText("Email:Comtechlimited@gmail.com,Ph:142078", 950, 175, myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(60);
                c.drawText("PAYSLIP", width / 2, 355, titlePaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                c.drawText("---------------------------------------------------------------------", 20, 390, myPaint);

                titlePaint.setTextAlign(Paint.Align.LEFT);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(45);
                c.drawText("EMPLOYEE DETAILS", 20, 450, titlePaint);
                myPaint.setTextSize(40);
                c.drawText("Name of Employee: "+name, 20, 500, myPaint);
                c.drawText("Department: "+department, 20, 550, myPaint);
                c.drawText("Designation: "+post, 20, 600, myPaint);
                c.drawText("Email: "+email, 20, 655, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                c.drawText("------------------------------------------------------------------", 20, 700, myPaint);

                titlePaint.setStyle(Paint.Style.STROKE);
                titlePaint.setStrokeWidth(2);
                c.drawRect(18, 730, width - 18, 830, titlePaint);
                titlePaint.setTextAlign(Paint.Align.LEFT);
                titlePaint.setStyle(Paint.Style.FILL);
                c.drawText("EARNINGS", 40, 790, titlePaint);
                c.drawText("AMOUNT", 750, 790, titlePaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                c.drawRect(18, 830, width - 18, 920, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("House Rent Allowance", 40, 890, myPaint);
                c.drawText("Rs "+hra, width -250, 890, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                c.drawRect(18, 920, width - 18, 1010, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Dearness Allowance", 40, 980, myPaint);
                c.drawText("Rs "+da, width - 250, 980, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                c.drawRect(18, 1010, width - 18, 1100, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Medical Allowance", 40, 1070, myPaint);
                c.drawText("Rs "+medical, width - 250, 1070, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                c.drawRect(18, 1100, width - 18, 1190, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Bonus Allowance", 40, 1160, myPaint);
                c.drawText("Rs "+bonus, width - 250, 1160, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                c.drawRect(18, 1190, width - 18, 1280, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Total Allowance", 40, 1250, myPaint);
                c.drawText("Rs "+allowance, width - 250, 1250, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                c.drawRect(18, 1280, width - 18, 1370, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Monthly Salary", 40, 1340, myPaint);
                c.drawText("Rs "+salary, width - 250, 1340, myPaint);


                c.drawLine(500, 730, 500, 1370, myPaint);


                titlePaint.setStyle(Paint.Style.STROKE);
                titlePaint.setStrokeWidth(2);
                c.drawRect(18, 1400, width - 18, 1500, titlePaint);
                titlePaint.setTextAlign(Paint.Align.LEFT);
                titlePaint.setStyle(Paint.Style.FILL);
                c.drawText("DEDUCTIONS", 40, 1460, titlePaint);
                c.drawText("AMOUNT", 750, 1460, titlePaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                c.drawRect(18, 1500, width - 18, 1590, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Total Leaves", 40, 1560, myPaint);
                c.drawText(leaves, width - 250, 1560, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                c.drawRect(18, 1590, width - 18, 1680, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Leave Deduction", 40, 1650, myPaint);
                c.drawText("Rs "+deduction, width - 250, 1650, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                c.drawRect(18, 1680, width - 18, 1780, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("GROSS PAY", 40, 1750, myPaint);
                c.drawText("Rs "+grossPay, width - 250, 1750, myPaint);


                c.drawLine(500, 1400, 500, 1780, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                c.drawRect(400, 1780, width - 18, 1880, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Tax Rate", 410, 1850, myPaint);
                c.drawText("5%", width - 250, 1850, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                c.drawRect(400, 1880, width - 18, 1980, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("Tax Amount", 410, 1950, myPaint);
                c.drawText("Rs "+taxAmount, width - 250, 1950, myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                c.drawRect(400, 1980, width - 18, 2080, myPaint);
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                c.drawText("NET SALARY", 410, 2050, myPaint);
                c.drawText("Rs "+netSalary, width - 250, 2050, myPaint);


                myPdfDocument.finishPage(myPage1);
                File file = new File(Environment.getExternalStorageDirectory(), name + "Payslip.pdf");
                Toast.makeText(SalarySlip.this, "Salary Slip Generated", Toast.LENGTH_SHORT).show();

                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
