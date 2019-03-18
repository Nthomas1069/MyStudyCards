package edu.regis.thomas.mystudycards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Class displays Contact Activity message and submit button.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class ContactUsActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView contactTextFld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        String contactText = "Problems? Suggestions? We want to hear from you!";

        contactTextFld = findViewById(R.id.contactTextBody);
        contactTextFld.setText(contactText);
    }
}
