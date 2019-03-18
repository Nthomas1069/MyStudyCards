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

import edu.regis.thomas.mystudycards.domain.Card;
import edu.regis.thomas.mystudycards.service.CardSvcSQLiteImpl;
import edu.regis.thomas.mystudycards.service.ICardSvc;

/**
 * Class provides methods to add study cards to deck selected from Main activity listView.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class AddCardActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Card card;
    private String cardSubject;
    private TextView deckSubjectFld;
    private EditText addQuestionFld;
    private EditText addAnswerFld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        String deckName = (String) getIntent().getSerializableExtra("Deck");

        deckSubjectFld = findViewById(R.id.deckToAddTo);
        addQuestionFld = findViewById(R.id.inputNewQ);
        addAnswerFld = findViewById(R.id.inputNewA);

        deckSubjectFld.setText(deckName);
        cardSubject = deckName;
    }

    public void addCard(View view) {

        if (!checkField()) return;

        ICardSvc cardSvc = CardSvcSQLiteImpl.getInstance(getApplicationContext());

        card = new Card();
        card.setCardSubject(cardSubject);
        card.setCardNum(1);               // Default value changes on retrieval
        card.setCardQuestion(addQuestionFld.getText().toString());
        card.setCardAnswer(addAnswerFld.getText().toString());
        cardSvc.createCard(card);
        Toast.makeText(this, "New Card Added!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean checkField(){
        if (addQuestionFld.getText().toString().equals("") ||
                (addAnswerFld.getText().toString().equals(""))) {
            Toast.makeText(this, "Both Q and A Required!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
}
