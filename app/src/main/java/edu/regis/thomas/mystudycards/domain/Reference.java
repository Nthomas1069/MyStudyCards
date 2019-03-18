package edu.regis.thomas.mystudycards.domain;

import java.io.Serializable;

/**
 * Defines serializable Reference object used to track the title and ISBN number
 * of source references used to create study card content.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class Reference implements Serializable {

    private String uid;
    private String subject;
    private String details;
    private String imageReference;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageReference() { return imageReference; }

    public void setImageReference(String imageReference) {this.imageReference = imageReference; }

    public String toString() {
        return subject;
    }
}
