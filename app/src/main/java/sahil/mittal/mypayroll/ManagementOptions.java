package sahil.mittal.mypayroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import static maes.tech.intentanim.CustomIntent.customType;

public class ManagementOptions extends AppCompatActivity {
    private CardView manage_Card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_management_options);

        manage_Card=findViewById(R.id.manageCard);

        manage_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManagementOptions.this, Home.class);
                startActivity(intent);
                customType(ManagementOptions.this,"left-to-right");
            }
        });


    }


    }

