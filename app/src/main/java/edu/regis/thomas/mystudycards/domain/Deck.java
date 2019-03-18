package edu.regis.thomas.mystudycards.domain;

import java.io.Serializable;

/**
 * Class defines serializable Deck object, used to create main list of study cards
 * groups by similar subjects.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class Deck implements Serializable {
    private int id;
    private String deckSubject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeckSubject() {
        return deckSubject;
    }

    public void setDeckSubject(String deckSubject) {
        this.deckSubject = deckSubject;
    }

    @Override
    public String toString() {
        return deckSubject;
    }
}
