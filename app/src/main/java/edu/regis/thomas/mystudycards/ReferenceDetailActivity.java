package edu.regis.thomas.mystudycards;

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

import edu.regis.thomas.mystudycards.domain.Reference;

/**
 * Class displays Reference subject and details and allows user to save/update or
 * cancel/delete a reference object.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class ReferenceDetailActivity extends AppCompatActivity {
    private final String TAG = "ReferenceDetailActivity";
    private Reference reference = null;
    private EditText subjectFld =null;
    private EditText detailsFld = null;
    private EditText refUrlFld = null;
    private ImageView cover = null;
    private Button saveBtn = null;
    private Button cancelBtn = null;
    private DatabaseReference firebaseDB = null;
    private FirebaseStorage firebaseStorage = null;
    private StorageReference gsReference = null;
    private FirebaseAuth mAuth;
    private boolean login = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_detail);

        initFirebase();
        firebaseDB = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = firebaseStorage.getInstance();
        subjectFld = findViewById(R.id.refSubjectInput);
        detailsFld = findViewById(R.id.refDetailsInput);
        refUrlFld =  findViewById(R.id.refUrlInput);
        saveBtn = findViewById(R.id.refSaveButton);
        cancelBtn = findViewById(R.id.refCancelButton);
        cover = (ImageView)findViewById(R.id.coverImage);
        Intent intent = getIntent();
        reference = (Reference)intent.getSerializableExtra("reference");

        if (reference != null) {
            Log.i(TAG, "Reference: " + reference.getUid());
            subjectFld.setText(reference.getSubject());
            detailsFld.setText(reference.getDetails());
            refUrlFld.setText(reference.getImageReference());
            saveBtn.setText("Update");
            cancelBtn.setText("Delete");
        }

        try {
            final File localFile = File.createTempFile ("images", "png");
            String localRef = reference.getImageReference();
            gsReference = firebaseStorage.getReferenceFromUrl(localRef);
            gsReference.getFile(localFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            try {
                                Bitmap bitmap =
                                        BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                cover.setImageBitmap(bitmap);
                                cover.setMinimumHeight(100);
                                cover.setMinimumWidth(100);
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
            Log.i(TAG,"*** onResume : "+ e.getMessage());
        }
        Log.i(TAG, "*** onCreate FINISHED");
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

    /**
     * Method "updates" reference if it already exists, and is saves a new reference with
     * user-defined values if reference is null.
     *
     * @param view view
     */
    public void saveUpdate(View view) {
        if (checkFields()) {
            if (reference == null) {
                // check for non-empty fields and save the new reference
                Reference reference = new Reference();
                reference.setUid(firebaseDB.child("references").push().getKey());
                reference.setSubject(subjectFld.getText().toString());
                reference.setDetails(detailsFld.getText().toString());
                reference.setImageReference(refUrlFld.getText().toString());
                firebaseDB.child("references").child(reference.getUid()).setValue(reference);
            } else {
                // update the existing reference
                reference.setSubject(subjectFld.getText().toString());
                reference.setDetails(detailsFld.getText().toString());
                reference.setImageReference(refUrlFld.getText().toString());
                firebaseDB.child("references").child(reference.getUid()).setValue(reference);
            }
            finish();
        }
    }

    /**
     *Method cancel the creation of a new reference or deletes the reference if not null.
     *
     * @param view
     */
    public void cancelDelete(View view) {
        if (reference == null) {
            // cancel and finish the activity
            finish();
        } else {
            // delete the existing reference
            firebaseDB.child("references").child(reference.getUid()).removeValue();
            finish();
        }
    }

    private boolean checkFields() {
        if (subjectFld.getText().toString().equals("") ||
                detailsFld.getText().toString().equals("")) {
            Toast.makeText(this, "** All fields must have a value **",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
