package edu.regis.thomas.mystudycards.service;

import edu.regis.thomas.mystudycards.domain.Deck;
import java.util.List;

/**
 * Interface for implementing CRUD operations on Deck object.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public interface IDeckSvc {

    Deck create(Deck deck);
    List<Deck> retrieveAll();
    Deck update(Deck deck);
    Deck delete(Deck deck);
}
