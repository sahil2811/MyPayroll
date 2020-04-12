package sahil.mittal.mypayroll;

import  androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends AppCompatActivity {


    //Variables
    private static int SPLASH_SCREEN=3000;
    Animation topAnim,bottomAnim;
    private FirebaseAuth fireAuth;
    private ImageView Iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);


        //Recieve id
        Iv=findViewById(R.id.logo_image);


        //set animation
        Iv.setAnimation(topAnim);


        //Start signup activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                customType(MainActivity.this,"left-to-right");
                finish();
            }
        },SPLASH_SCREEN);
    }
}
