package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Message and its own data.
 */
public class Message {
    private String content;

    /**
     * Ajout des attributs pour récupérer la date.
     */
    private final Date date;
    private final SimpleDateFormat dateFormat;
    public String getContent() {
        return content;
    }

    /**
     * Fonction get pour récupérer la date.
     */

    public String getDate() {
        return dateFormat.format(date);
    }

    /**
     * Build a Message object from it's (String) content.
     */
    public Message(final String content) {
        this.content = content;
        this.date = new Date();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //On formate la date
    }

}
