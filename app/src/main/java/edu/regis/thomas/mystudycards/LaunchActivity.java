package edu.regis.thomas.mystudycards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class defines 3 second launch screen activity.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Thread.sleep( 3000);
        } catch (Exception e) {
        }

        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
