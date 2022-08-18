package com.example.hackfest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Models.User;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    StorageReference sRefPImg;
    FirebaseUser firebaseUser;
    TextView Name,Age,Gender;
    ShapeableImageView Img;
    ProgressBar pb;
    Uri PImgUri;
    User user;
    private static final int PickCode  = 305;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();

        sRefPImg= FirebaseStorage.getInstance().getReference(getString(R.string.Profile_Image)+firebaseUser.getUid());
        Name=findViewById(R.id.tv_ProfileName);
        Age=findViewById(R.id.tv_ProfileAge);
        Gender=findViewById(R.id.tv_ProfileGender);
        Img=findViewById(R.id.img_ProfileImg);

        pb=findViewById(R.id.pb_upload);
        database.getReference("users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.getValue(User.class);
                assert user != null;
                Name.setText(user.getUserName());
                Age.setText(user.getAge());
                Gender.setText(user.getGender());
                Glide.with(ProfileActivity.this).load(user.getProfileUrl()).into(Img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PickCode);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PickCode && resultCode==RESULT_OK && data.getData()!=null){
            PImgUri=data.getData();
            StorageReference ref=sRefPImg.child(firebaseUser.getUid()+"."+getFileExtension(PImgUri));
                ref.putFile(PImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pb.setVisibility(View.GONE);
                        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                user.setProfileUrl(task.getResult().toString());
                                database.getReference("users").child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Glide.with(ProfileActivity.this).load(task.getResult()).into(Img);
                                    }
                                });

                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        pb.setVisibility(View.VISIBLE);
                        Double prg= (double) snapshot.getBytesTransferred();
                        Double total= (double) snapshot.getTotalByteCount();
                        pb.setProgress((int)(prg/total));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pb.setVisibility(View.GONE);

                    }
                });

        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap map=MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(cr.getType(uri));
    }
}