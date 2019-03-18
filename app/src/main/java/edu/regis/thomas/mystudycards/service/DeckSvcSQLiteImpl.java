package edu.regis.thomas.mystudycards.service;

import edu.regis.thomas.mystudycards.domain.Deck;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Deck service class uses SQLite database for persistence and CRUD operations on Deck objects.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class DeckSvcSQLiteImpl extends SQLiteOpenHelper implements IDeckSvc {

    private static final String TAG = "DeckSvcSQLiteImpl";
    private static final String DBNAME = "deckList.db";
    private static final int DBVERSION = 1;
    private final String CREATE_DECK_TABLE =
            "CREATE TABLE deck (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "deckSubject TEXT NOT NULL)";
    private final String DROP_DECK_TABLE = "DROP TABLE IF EXISTS deck";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        db.execSQL(CREATE_DECK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.i(TAG, "onUpgrade");
        db.execSQL(DROP_DECK_TABLE);
        onCreate(db);
    }

    private static DeckSvcSQLiteImpl instance = null;

    public static DeckSvcSQLiteImpl getInstance(Context context) {
        if (instance == null) {
            instance = new DeckSvcSQLiteImpl(context);
        }
        return instance;
    }

    public DeckSvcSQLiteImpl(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    public Deck create(Deck deck) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deckSubject", deck.getDeckSubject());
        db.insert( "deck", null, values);

        // get last insert id
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        deck.setId(id);
        cursor.close();
        db.close();
        return deck;
    }

    public List<Deck> retrieveAll() {
        List<Deck> deckList = new ArrayList<>();
        String columns [] = {"id", "deckSubject"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("deck", columns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Deck deck = getDeck(cursor);
            deckList.add(deck);
            cursor.moveToNext();
        }
        db.close();
        return deckList;
    }

    private Deck getDeck(Cursor cursor) {
        Deck deck = new Deck();
        deck.setId(cursor.getInt(0));
        deck.setDeckSubject(cursor.getString(1));
        return deck;
    }

    public Deck update(Deck deck) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deckSubject", deck.getDeckSubject());
        db.update("deck", values, "id=" + deck.getId(), null);
        db.close();
        return deck;
    }

    public Deck delete(Deck deck) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("deck", "id = " + deck.getId(), null);
        db.close();
        return deck;
    }
}
