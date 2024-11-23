package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Message and its own data.
 */
public class Message {
    private String content;
    private final Date date;
    private final SimpleDateFormat dateFormat;
    private final LocalDateTime timestamp;

    public String getContent() {
        return content;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Build a Message object from its (String) content.
     */
    public Message(final String content) {
        this.content = content;
        this.date = new Date();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now();
    }
}
