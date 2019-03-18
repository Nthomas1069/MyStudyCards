package edu.regis.thomas.mystudycards.service;

import edu.regis.thomas.mystudycards.domain.Card;
import java.util.List;

/**
 * Interface for implementing CRUD operations on Card objects.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public interface ICardSvc {
    Card createCard(Card card);
    List<Card> retrieveAllCards();
    Card updateCard(Card card);
    Card deleteCard(Card card);

}
