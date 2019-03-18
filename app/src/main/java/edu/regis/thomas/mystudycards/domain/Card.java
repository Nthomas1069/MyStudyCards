package edu.regis.thomas.mystudycards.domain;

import java.io.Serializable;

/**
 * Class defines serializable Card flashcard object for MyStudyCards app.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class Card implements Serializable {

    private int id;
    private int cardNum;
    private String cardSubject;
    private String cardQuestion;
    private String cardAnswer;


    public int getId() { return id; }

    public void setId(int id) { this.id = id;}

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardSubject() {
        return cardSubject;
    }

    public void setCardSubject(String subject) {
        this.cardSubject = subject;
    }

    public String getCardQuestion() {
        return cardQuestion;
    }

    public void setCardQuestion(String cardQuestion) {
        this.cardQuestion = cardQuestion;
    }

    public String getCardAnswer() {
        return cardAnswer;
    }

    public void setCardAnswer(String cardAnswer) {
        this.cardAnswer = cardAnswer;
    }
}
