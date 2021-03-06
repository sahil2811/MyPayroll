package sahil.mittal.mypayroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static maes.tech.intentanim.CustomIntent.customType;

public class Home extends AppCompatActivity implements View.OnClickListener{
    private CardView addCard,deleteCard,updateCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //defining cards
        addCard=findViewById(R.id.add_card);
        deleteCard=findViewById(R.id.delete_card);
        updateCard=findViewById(R.id.update_card);
        //add click listeners to card view
        addCard.setOnClickListener(this);
        deleteCard.setOnClickListener(this);
        updateCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.add_card: i=new Intent(this,AddEmployee.class);
            startActivity(i);
            customType(Home.this,"left-to-right");
            break;

            case R.id.update_card: i=new Intent(this,UpdateEmployee.class);
            startActivity(i);
            customType(Home.this,"left-to-right");break;

            case R.id.delete_card: i=new Intent(this,DeleteEmployee.class);
            startActivity(i);
            customType(Home.this,"left-to-right");break;

        }

    }

    @Override
    public void finish() {
        super.finish();
        customType(Home.this,"right-to-left");

    }
}
