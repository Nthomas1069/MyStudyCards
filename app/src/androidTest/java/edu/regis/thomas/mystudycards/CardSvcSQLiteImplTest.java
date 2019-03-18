package edu.regis.thomas.mystudycards;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.regis.thomas.mystudycards.domain.Card;
import edu.regis.thomas.mystudycards.service.CardSvcSQLiteImpl;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CardSvcSQLiteImplTest {

    @Test
    public void testCRUD() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("edu.regis.thomas.mystudycards", appContext.getPackageName());

        CardSvcSQLiteImpl svc = CardSvcSQLiteImpl.getInstance(appContext);
        assertNotNull(svc);

        List<Card> list = svc.retrieveAllCards();
        assertNotNull(list);
        int initialSize = list.size();

        Card card = new Card();
        card.setCardNum(1);
        card.setCardSubject("Test");
        card.setCardQuestion("This is the test question?");
        card.setCardAnswer("This is the test answer.");
        card = svc.createCard(card);
        assertNotNull(card);

        int id = card.getId();
        assertNotEquals(0, id);

        list = svc.retrieveAllCards();
        assertEquals(initialSize + 1, list.size());

        card.setCardSubject("Updated Subject");
        card.setCardNum(2);
        card.setCardQuestion("Updated Question?");
        card.setCardAnswer("Updated Answer.");
        card = svc.updateCard(card);
        assertNotNull(card);

        card = svc.deleteCard(card);
        assertNotNull(card);

        list = svc.retrieveAllCards();
        assertEquals(initialSize, list.size());
    }
}
