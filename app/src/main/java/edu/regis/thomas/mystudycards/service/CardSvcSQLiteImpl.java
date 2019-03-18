package edu.regis.thomas.mystudycards.service;

import edu.regis.thomas.mystudycards.domain.Card;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Card service class uses SQLite database for persistence and CRUD operations on Card objects.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class CardSvcSQLiteImpl extends SQLiteOpenHelper implements ICardSvc {

    private static final String TAG = "CardSvcSQLiteImpl";
    private static final String DBNAME = "cards.db";
    private static final int DBVERSION = 1;
    private final String CREATE_CARD_TABLE =
            "CREATE TABLE card (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cardSubject TEXT NOT NULL, " +
                    "cardNum INTEGER NOT NULL, " +
                    "cardQuestion TEXT NOT NULL, " +
                    "cardAnswer TEXT NOT NULL)";

    private final String DROP_CARD_TABLE = "DROP TABLE IF EXISTS card";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        db.execSQL(CREATE_CARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.i(TAG, "onUpgrade");
        db.execSQL(DROP_CARD_TABLE);
        onCreate(db);
    }

    private static CardSvcSQLiteImpl instance = null;

    public static CardSvcSQLiteImpl getInstance(Context context) {
        if (instance == null) {
            instance = new CardSvcSQLiteImpl(context);
        }
        return instance;
    }

    public CardSvcSQLiteImpl(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    public Card createCard(Card card) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cardSubject", card.getCardSubject());
        values.put("cardNum", card.getCardNum());
        values.put("cardQuestion", card.getCardQuestion());
        values.put("cardAnswer", card.getCardAnswer());
        db.insert( "card", null, values);

        // get last insert id
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        card.setId(id);
        cursor.close();
        db.close();
        return card;
    }

    public List<Card> retrieveAllCards() {
        List<Card> cardList = new ArrayList<>();
        String columns [] = {"id", "cardSubject", "cardNum", "cardQuestion", "cardAnswer"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("card", columns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Card card = getCard(cursor);
            cardList.add(card);
            cursor.moveToNext();
        }
        db.close();
        return cardList;
    }

    public List<Card> retrieveBySubject(String deckSubject) {
        int deckCardNum = 0;
        List<Card> cardList = new ArrayList<>();
        String columns [] = {"id", "cardSubject", "cardNum", "cardQuestion", "cardAnswer"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("card", columns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Card card = getCard(cursor);
            if (card.getCardSubject().equals(deckSubject)) {
                card.setCardNum(++deckCardNum);
                cardList.add(card);
            }
            cursor.moveToNext();
        }
        db.close();
        return cardList;
    }

    private Card getCard(Cursor cursor) {
        Card card = new Card();
        card.setId(cursor.getInt(0));
        card.setCardSubject(cursor.getString(1));
        card.setCardNum(cursor.getInt(2));
        card.setCardQuestion(cursor.getString(3));
        card.setCardAnswer(cursor.getString(4));
        return card;
    }

    public Card updateCard(Card card) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cardSubject", card.getCardSubject());
        values.put("cardNum", card.getCardNum());
        values.put("cardQuestion", card.getCardQuestion());
        values.put("cardAnswer", card.getCardAnswer());
        db.update("card", values, "id = " + card.getId(), null);
        db.close();
        return card;
    }

    public Card deleteCard(Card card) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("card", "id = " + card.getId(), null);
        db.close();
        return card;
    }
}
