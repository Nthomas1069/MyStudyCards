
package edu.regis.thomas.mystudycards.service;

import edu.regis.thomas.mystudycards.domain.Deck;
import java.util.LinkedList;
import java.util.List;

/**
 * Class used to test CRUD operations on Deck object. Does not provide data persistence.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class DeckSvcCacheImpl implements IDeckSvc {

    private int deckNumber = 0;
    private List<Deck> deckList = new LinkedList<Deck>();

    private DeckSvcCacheImpl() {
        initList();
    }

    private void initList() {
        Deck deck = new Deck();
        deck.setId(deckList.size() + 1);
        deck.setDeckSubject("New Subject");
        deckList.add(deck);
        //deck.create();
    }

    private static DeckSvcCacheImpl instance = new DeckSvcCacheImpl();

    public static DeckSvcCacheImpl getInstance() {
        return instance;
    }

    @Override
    public Deck create(Deck deck) {
        deck = new Deck();
        deck.setId(++deckNumber);
        deckList.add(deck);
        return deck;
    }

    @Override
    public List<Deck> retrieveAll() {
        return deckList;
    }

    @Override
    public Deck update(Deck deck) {
        int size = deckList.size();
        for (int i = 0; i < size; i++) {
            if (deckList.get(i).getId() == deck.getId()) {
                deckList.set(i, deck);
                break;
            }
        }
        return deck;
    }

    @Override
    public Deck delete(Deck deck) {
        int size = deckList.size();
        for (int i = 0; i < size; i++) {
            if (deckList.get(i).getId() == deck.getId()) {
                deckList.remove(i);
                break;
            }
        }
        return deck;
    }
}
