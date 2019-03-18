package edu.regis.thomas.mystudycards;

import org.junit.Test;

import java.util.List;

import edu.regis.thomas.mystudycards.domain.Deck;
import edu.regis.thomas.mystudycards.service.DeckSvcCacheImpl;

import static org.junit.Assert.*;

public class DeckSvcCacheImplTest {

    @Test
    public void testCRUD() {

        DeckSvcCacheImpl svc = DeckSvcCacheImpl.getInstance();

        Deck deck = new Deck();
        deck.setId(1);
        deck.setDeckSubject("Test Subject");

        List<Deck> list = svc.retrieveAll();
        assertNotNull(deck);
        int initialSize = list.size();

        // test the create method
        deck = svc.create(deck);
        assertNotNull(deck);

        // test the retrieve method
        list = svc.retrieveAll();
        int size = list.size();
        assertEquals( initialSize + 1, size);

        // test the update method
        deck.setDeckSubject("Updated Subject");
        deck = svc.update(deck);
        assertNotNull(deck);
        assertEquals( "Updated Subject", deck.getDeckSubject());

        // test the delete method
        deck = svc.delete(deck);
        assertNotNull(deck);
        list = svc.retrieveAll();
        size = list.size();
        assertEquals(initialSize, size);
    }
}
