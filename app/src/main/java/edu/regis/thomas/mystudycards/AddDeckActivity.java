package edu.regis.thomas.mystudycards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.regis.thomas.mystudycards.domain.Deck;
import edu.regis.thomas.mystudycards.service.DeckSvcSQLiteImpl;
import edu.regis.thomas.mystudycards.service.IDeckSvc;

/**
 * Class provides methods for accepting String object as deck subject to instantiate a new deck
 * in the Main Activity ListView.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class AddDeckActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText newDeckFld;
    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deck);

        newDeckFld = findViewById(R.id.inputNewDeck);
    }

    public void add(View view) {

        if (!checkField()) return;

        //IDeckSvc svc = DeckSvcCacheImpl.getInstance();
        IDeckSvc deckSvc = DeckSvcSQLiteImpl.getInstance(getApplicationContext());

        deck = new Deck();
        deck.setDeckSubject(newDeckFld.getText().toString());
        deckSvc.create(deck);
        Toast.makeText(this, "New Deck Added!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean checkField(){
        if (newDeckFld.getText().toString().equals("")) {
            Toast.makeText(this, "New Deck Name Required!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
