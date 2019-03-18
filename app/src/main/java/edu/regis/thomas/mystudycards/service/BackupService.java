package edu.regis.thomas.mystudycards.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

public class BackupService extends IntentService {

    public BackupService() {
        super("BackupService");
    }

    private final String TAG = "BackupService";

    @Override
    public void onHandleIntent(Intent intent) {
        // entry point of service
        try {
            exportSqliteDatabase();
        } catch (Exception e) {
            Log.i(TAG, "EXCEPTION: " + e.getMessage());
        }
    }

    protected void exportSqliteDatabase() {
        // export the SQLite database
        Log.i(TAG, "*** entering exportSQLiteDatabase ***");
        File data = Environment.getDataDirectory();
        String dbPath = "/data/data/edu.regis.thomas.mystudycards/databases/cards.db";
        Uri file = Uri.fromFile(new File(dbPath));
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference ref = storageRef.child("database/" + UUID.randomUUID().toString());
        ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // *** BROADCAST a notification message
                Intent intent = new Intent();
                intent.setAction("edu.regis.thomas.mystudycards.EXPORT_DATABASE");
                intent.putExtra("message", "Database exported to Firebase");
                sendBroadcast(intent);
                Log.i(TAG, "onFailureListener");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "onFailureListener");
            }
        });
        Log.i(TAG, "exiting exportSQLiteDatabase");
    }
}
