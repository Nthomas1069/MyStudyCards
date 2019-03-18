package edu.regis.thomas.mystudycards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.regis.thomas.mystudycards.domain.Card;
import edu.regis.thomas.mystudycards.service.CardSvcSQLiteImpl;
import edu.regis.thomas.mystudycards.service.ICardSvc;

import static java.lang.String.valueOf;

/**
 * Class displays first card in deck, if not empty, and provides UI to delete current card,
 * reveal answer portion of current card, and iterate through deck selected in Main view.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class ViewCardActivity extends AppCompatActivity {

    private EditText deckSubjectFld;
    private EditText cardQuestionFld;
    private EditText cardAnswerFld;
    private EditText cardNumberFld;
    private EditText totalNumCardsFld;
    private static final String TAG = "MainActivity";

    private String deckName;
    private int cardNumber = 0;
    private int cardsInDeck = 0;
    private Card card;
    private ICardSvc cardSvc = null;
    List<Card> cardList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu within the action bar
        getMenuInflater().inflate(R.menu.viewcardmenu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        cardSvc = CardSvcSQLiteImpl.getInstance(getApplicationContext());

        deckSubjectFld = findViewById(R.id.deckName);
        cardQuestionFld = findViewById(R.id.cardQuestion);
        cardAnswerFld = findViewById(R.id.cardAnswer);
        cardNumberFld = findViewById(R.id.cardNumber);
        totalNumCardsFld = findViewById(R.id.numberInDeck);
        deckName = (String) getIntent().getSerializableExtra("Deck");
        deckSubjectFld.setText(deckName);
        cardList = ((CardSvcSQLiteImpl) cardSvc).retrieveBySubject(deckName);

        if (cardList.size() != 0) {
            card = cardList.get(cardNumber);
            cardsInDeck = cardList.size();
            cardQuestionFld.setText(card.getCardQuestion());
            cardNumberFld.setText(valueOf(cardNumber + 1));
            totalNumCardsFld.setText(valueOf(cardList.size()));
        } else {
            Toast.makeText(this, "This Deck is Empty!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_newcard:
                Log.i(TAG, "action NewCard clicked!");
                intent = new Intent(this, AddCardActivity.class);
                intent.putExtra("Deck", deckName);
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

    public void showAnswer(View view) {
        cardAnswerFld.setText(card.getCardAnswer());
    }

    /**
     * Method iterates forward through card list instantiated as having a shared card subject
     * corresponding with deck subject. Method will return user to first card if forward arrow
     * button is clicked while displaying last card in the list.
     *
     * @param view
     */
    public void viewNextCard(View view) {
        cardAnswerFld.setText("");
        cardNumber++;
        if (cardNumber < cardsInDeck) {
            card = cardList.get(cardNumber);
            cardQuestionFld.setText(card.getCardQuestion());
            cardNumberFld.setText(valueOf(cardNumber + 1));
        } else {
            cardNumber = 0;
            card = cardList.get(cardNumber);
            cardQuestionFld.setText(card.getCardQuestion());
            cardNumberFld.setText(valueOf(cardNumber + 1));
        }
    }

    /**
     * Method iterates backward through card list instantiated as having a shared card subject
     * corresponding with deck subject. Method will return user to the last care if backward arrow
     * button is clicked while displaying first card in the list.
     *
     * @param view
     */
    public void viewPreviousCard(View view) {
        cardAnswerFld.setText("");
        cardNumber--;
        if (cardNumber >= 0) {
            card = cardList.get(cardNumber);
            cardQuestionFld.setText(card.getCardQuestion());
            cardNumberFld.setText(valueOf(cardNumber + 1));
        } else {
            cardNumber = (cardList.size() - 1);
            card = cardList.get(cardNumber);
            cardQuestionFld.setText(card.getCardQuestion());
            cardNumberFld.setText(valueOf(cardNumber + 1));
        }
    }

    /**
     * Method deletes the card currently displayed.
     *
     * @param view
     */
    public void deleteCard(View view) {
        ICardSvc cardSvc = CardSvcSQLiteImpl.getInstance(getApplicationContext());

        if (cardsInDeck != 0) {
            cardSvc.deleteCard(card);
            Toast.makeText(this, "Card Deleted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "This Deck is Empty!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
