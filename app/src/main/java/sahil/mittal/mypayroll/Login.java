package sahil.mittal.mypayroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static maes.tech.intentanim.CustomIntent.customType;

public class Login extends AppCompatActivity {
    private EditText textEmail, textPassword;
    private Button btnLogin;
    private FirebaseAuth fireAuth;
    private ProgressBar processBar;
    private CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //intialising ids
        textEmail = findViewById(R.id.email_text);
        textPassword = findViewById(R.id.password_text);
        btnLogin = findViewById(R.id.singin_btn);
        processBar = findViewById(R.id.progress);
        remember=findViewById(R.id.checkBox);

        //Creating  instance of the firebase aunthetication.
        fireAuth=FirebaseAuth.getInstance();

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=preferences.getString("remember","");
        if(checkbox.equals("true")){
            processBar.setVisibility(View.VISIBLE);
            Intent intent=new Intent(Login.this,ManagementOptions.class);
            startActivity(intent);
        }else if(checkbox.equals("false")){
            Toast.makeText(this, "please login", Toast.LENGTH_SHORT).show();
        }




        //remeber check box
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(Login.this, "checked", Toast.LENGTH_SHORT).show();
                }else if(!compoundButton.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(Login.this, "unchecked", Toast.LENGTH_SHORT).show();
                }

            }
        });







        //click the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //getting the values from the fields
                final String email = textEmail.getText().toString().trim();
                final String password = textPassword.getText().toString().trim();


                //validating the user inputs
                if (TextUtils.isEmpty(email)) {
                    textEmail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    textPassword.setError("Password is required.");
                    return;
                }


                //progress bar visisblity on
                processBar.setVisibility(View.VISIBLE);


                //login by registered email id
                fireAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        processBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                            customType(Login.this,"left-to-right");
                        }else{
                            Toast.makeText(Login.this, "Login Failed, "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });








    }
        }