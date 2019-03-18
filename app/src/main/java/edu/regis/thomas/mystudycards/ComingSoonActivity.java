package edu.regis.thomas.mystudycards;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class ComingSoonActivity extends AppCompatActivity {

    public static int imageNum = 1;
    private FirebaseAuth mAuth;
    private boolean login = false;
    final String TAG = "ComingSoonActivity";
    FirebaseStorage firebaseStorage = null;
    StorageReference gsReference = null;
    ImageView image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);

        initFirebase();
        image = (ImageView)findViewById(R.id.comingSoonImage);
        firebaseStorage = FirebaseStorage.getInstance();
        gsReference = firebaseStorage.getReferenceFromUrl(
                "gs://my-study-cards.appspot.com/comingsoon/comingsoonimage1.png");

        try {
            final File localFile = File.createTempFile ("images", "png");
            gsReference.getFile(localFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            try {
                                Bitmap bitmap =
                                        BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                image.setImageBitmap(bitmap);
                                image.setMinimumHeight(700);
                                image.setMinimumWidth(500);
                            } catch (Exception e1) {
                                Log.i(TAG, "*** onSuccess EXCEPTION: " + e1.getMessage());
                            }
                            Log.i(TAG, "*** onSuccess COMPLETED ***");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i(TAG, "*** onResume onFailure EXCEPTION: " +
                            exception.getMessage());
                }
            });
        } catch (Exception e ){
            Log.i(TAG,"*** onResume : "+e.getMessage());
        }
        Log.i(TAG, "*** onCreate FINISHED");
    }

    public void next(View view) {
        try {
            int nextImageNum = ++ComingSoonActivity.imageNum;
            if (ComingSoonActivity.imageNum > 5) {
                ComingSoonActivity.imageNum -= 5;
                nextImageNum = ComingSoonActivity.imageNum;
            }
            String url = "gs://my-study-cards.appspot.com/comingsoon/comingsoonimage"
                    + nextImageNum + ".png";
            gsReference = firebaseStorage.getReferenceFromUrl(url);
            final File localFile = File.createTempFile ("images", "png");
            gsReference.getFile(localFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            try {
                                Bitmap bitmap =
                                        BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                image.setImageBitmap(bitmap);
                                image.setMinimumHeight(700);
                                image.setMinimumWidth(500);
                            } catch (Exception e1) {
                                Log.i(TAG, "*** NEXT EXCEPTION: " + e1.getMessage());
                            }
                            Log.i(TAG, "*** NEXT COMPLETED ***");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i(TAG, "*** NEXT onFailure EXCEPTION: " +
                            exception.getMessage());
                }
            });
        } catch (Exception e ){
            Log.i(TAG,"*** NEXT : "+ e.getMessage());
        }
    }

    public void previous(View view) {
        try {
            int nextImageNum = --ComingSoonActivity.imageNum;
            if (ComingSoonActivity.imageNum < 1) {
                ComingSoonActivity.imageNum = 5;
                nextImageNum = ComingSoonActivity.imageNum;
            }
            String url = "gs://my-study-cards.appspot.com/comingsoon/comingsoonimage"
                    + nextImageNum + ".png";
            gsReference = firebaseStorage.getReferenceFromUrl(url);
            final File localFile = File.createTempFile ("images", "png");
            gsReference.getFile(localFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            try {
                                Bitmap bitmap =
                                        BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                image.setImageBitmap(bitmap);
                                image.setMinimumHeight(700);
                                image.setMinimumWidth(500);
                            } catch (Exception e1) {
                                Log.i(TAG, "*** PREVIOUS EXCEPTION: " + e1.getMessage());
                            }
                            Log.i(TAG, "*** PREVIOUS COMPLETED ***");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i(TAG, "*** PREVIOUS onFailure EXCEPTION: " +
                            exception.getMessage());
                }
            });
        } catch (Exception e ){
            Log.i(TAG,"*** PREVIOUS : "+ e.getMessage());
        }
    }

    private void initFirebase() {
        Log.i(TAG, "*** initFirebase started");
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "*** Login SUCCESSFUL");
                            login = true;
                        } else {
                            // If sign-in fails, display a message to user
                            login = false;
                            Log.i(TAG, "*** Login FAILED");
                        }
                    }
                });
        Log.i(TAG, "*** initFirebase completed");
    }
}
