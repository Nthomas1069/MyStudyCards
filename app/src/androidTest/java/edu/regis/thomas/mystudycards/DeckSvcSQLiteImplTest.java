package edu.regis.thomas.mystudycards;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.regis.thomas.mystudycards.domain.Deck;
import edu.regis.thomas.mystudycards.service.DeckSvcSQLiteImpl;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DeckSvcSQLiteImplTest {

    @Test
    public void testCRUD() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("edu.regis.thomas.mystudycards", appContext.getPackageName());

        DeckSvcSQLiteImpl svc = DeckSvcSQLiteImpl.getInstance(appContext);
        assertNotNull(svc);

        List<Deck> list = svc.retrieveAll();
        assertNotNull(list);
        int initialSize = list.size();

        Deck deck = new Deck();
        deck.setDeckSubject("Test Deck");
        deck = svc.create(deck);
        assertNotNull(deck);

        int id = deck.getId();
        assertNotEquals(0, id);

        list = svc.retrieveAll();
        assertEquals(initialSize + 1, list.size());

        deck.setDeckSubject("Updated Deck Subject");
        deck = svc.update(deck);
        assertNotNull(deck);

        deck = svc.delete(deck);
        assertNotNull(deck);

        list = svc.retrieveAll();
        assertEquals(initialSize, list.size());
    }
}
