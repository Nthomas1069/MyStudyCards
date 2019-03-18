package edu.regis.thomas.mystudycards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Class sets "about" text display for layout view.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView aboutTextFld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String aboutText = "My Study Cards is a flashcard app that allows you to create your" +
                " own \"decks\" of custom cards for on-the-go studying and test prep.";

        aboutTextFld = findViewById(R.id.aboutBody);
        aboutTextFld.setText(aboutText);
    }
}
