package sahil.mittal.mypayroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static maes.tech.intentanim.CustomIntent.customType;

public class ManagePayroll extends AppCompatActivity implements View.OnClickListener{
    private CardView deductionCard,allowanceCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_payroll);

        deductionCard=findViewById(R.id.deduction_card);
        allowanceCard=findViewById(R.id.allowance_card);

       allowanceCard.setOnClickListener(this);
       deductionCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent i;
        switch (v.getId()){
            case R.id.allowance_card: i=new Intent(this,Allowance.class);
                startActivity(i);
                customType(ManagePayroll.this,"left-to-right");
                break;

            case R.id.deduction_card: i=new Intent(this,Deduction.class);
                startActivity(i);
                customType(ManagePayroll.this,"left-to-right");break;

        }

    }
}
