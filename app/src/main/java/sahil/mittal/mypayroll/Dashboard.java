package sahil.mittal.mypayroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import static maes.tech.intentanim.CustomIntent.customType;

public class Dashboard extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    private ImageView adminImage;
    private TextView adminName;
    private CardView manage_Card;

    Uri uriProfileImage;
    DatabaseReference reff;
    private static final String USERS ="users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Intialize
        adminImage=findViewById(R.id.adminImage);
        adminName=findViewById(R.id.adminName);
        reff= FirebaseDatabase.getInstance().getReference().child(USERS);
        manage_Card=findViewById(R.id.manageCard);

        manage_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, Home.class);
                startActivity(intent);
                customType(Dashboard.this,"fadein-to-fadeout");
            }
        });


    }


    //GET THE IMAGE FROM GALLERY
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CHOOSE_IMAGE && resultCode== RESULT_OK && data!=null && data.getData() !=null){
            uriProfileImage=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                adminImage.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {

        StorageReference profileImageRef= FirebaseStorage.getInstance()
                .getReference("Adminpics/"+System.currentTimeMillis()+".jpg");
        if(uriProfileImage != null){
            profileImageRef.putFile(uriProfileImage);
        }
    }

    private void showImageChooser() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose Image"),CHOOSE_IMAGE);
    }

}
