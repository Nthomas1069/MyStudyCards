package edu.regis.thomas.mystudycards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.regis.thomas.mystudycards.domain.Card;
import edu.regis.thomas.mystudycards.domain.Deck;
import edu.regis.thomas.mystudycards.service.CardSvcSQLiteImpl;
import edu.regis.thomas.mystudycards.service.DeckSvcSQLiteImpl;
import edu.regis.thomas.mystudycards.service.ICardSvc;
import edu.regis.thomas.mystudycards.service.IDeckSvc;

/**
 * Class allows user to perform update and delete operations on a Deck object or navigates to
 * View Cards activity for selected deck.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class ManageDeckActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView currentDeckNameFld;
    private EditText deckNameUpdateFld;
    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_deck);

        deck = (Deck) getIntent().getSerializableExtra("Deck");
        currentDeckNameFld = findViewById(R.id.currentDeckName);
        currentDeckNameFld.setText(deck.getDeckSubject());
        deckNameUpdateFld = findViewById(R.id.deckNameUpdate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu within the action bar
        getMenuInflater().inflate(R.menu.cancelmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
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

    /**
     * Method navigates to ViewCardActivity, passing deck subject as extra.
     *
     * @param view
     */
    public void viewCards(View view) {
        Intent intent = new Intent(this, ViewCardActivity.class);
        intent.putExtra("Deck", deck.getDeckSubject());
        startActivity(intent);
    }

    /**
     * Method allows the user to update an existing deck name.
     *
     * @param view
     */
    public void updateDeck(View view) {

        if (!updateCheckField()) return;

        IDeckSvc deckSvc = DeckSvcSQLiteImpl.getInstance(getApplicationContext());

        deck.setDeckSubject(deckNameUpdateFld.getText().toString());
        deckSvc.update(deck);
        Toast.makeText(this, "Deck Name Updated!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean updateCheckField(){
        if (deckNameUpdateFld.getText().toString().equals("")) {
            Toast.makeText(this, "Updated Deck Name Required!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Method deletes the selected deck and all of the stored cards associated with the
     * deck subject.
     *
     * @param view
     */
    public void deleteDeck(View view) {
        IDeckSvc deckSvc = DeckSvcSQLiteImpl.getInstance(getApplicationContext());
        ICardSvc cardSvc = CardSvcSQLiteImpl.getInstance(getApplicationContext());
        List<Card> cardList = ((CardSvcSQLiteImpl) cardSvc).retrieveBySubject(deck.getDeckSubject());
        Card targetCard;

        for (int i = 0; i < cardList.size(); i++) {
            targetCard = cardList.get(i);
            cardSvc.deleteCard(targetCard);
        }

        deckSvc.delete(deck);
        Toast.makeText(this, "Deck Deleted!", Toast.LENGTH_SHORT).show();

        finish();
    }
}
