package edu.regis.thomas.mystudycards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import edu.regis.thomas.mystudycards.domain.Reference;

/**
 * Class instantiates a ListView of Reference objects using Firebase real-time DB.
 * OnClick of ListView items navigates user to Reference Detail Activity.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class ReferenceListActivity extends AppCompatActivity {
    private final String TAG = "ReferenceListActivity";
    private ListView referenceListView;
    private DatabaseReference firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_list);

        referenceListView = findViewById(R.id.referenceListID);
        firebaseDB = FirebaseDatabase.getInstance().getReference();

        initFirebase();
    }

    private FirebaseAuth mAuth;
    private boolean login = false;

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

    @Override
    public void onResume() {
        super.onResume();

        firebaseDB.child("references").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<Reference> references = new ArrayList<>();
                for (DataSnapshot referenceDataSnapshot : dataSnapshot.getChildren()) {
                    Reference reference = referenceDataSnapshot.getValue(Reference.class);
                    references.add(reference);
                }

                ArrayAdapter adapter = new ArrayAdapter<Reference>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, references);
                referenceListView.setAdapter(adapter);
                referenceListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        Log.i(TAG, " *** onItemClickListener");
                        Reference reference = references.get(position);
                        Intent intent = new Intent(ReferenceListActivity.this,
                            ReferenceDetailActivity.class);
                        intent.putExtra("reference", references.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu within the action bar
        getMenuInflater().inflate(R.menu.referencesmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_new_reference:
                Log.i(TAG, "action New Reference clicked!");
                intent = new Intent(this, ReferenceDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.action_main:
                Log.i(TAG, "action Main clicked!");
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }
}
