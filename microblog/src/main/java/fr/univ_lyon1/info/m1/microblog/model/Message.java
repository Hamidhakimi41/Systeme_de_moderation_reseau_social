package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Class representing a message.
 */
public final class Message {
    private String content;
    private final Date date;
    private final SimpleDateFormat dateFormat;
    private final LocalDateTime timestamp;

    /**
     * Constructor.
     *
     * @param builder the builder to create the message
     */
    private Message(final Builder builder) {
        this.content = builder.content;
        this.date = builder.date;
        this.dateFormat = builder.dateFormat;
        this.timestamp = builder.timestamp;
    }


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
     * Builder class for the Message class.
     */
    public static class Builder {
        private String content;
        private Date date;
        private SimpleDateFormat dateFormat;
        private LocalDateTime timestamp;

        /**
         * Constructor.
         *
         * @param content the content of the message
         */
        public Builder(final String content) {
            this.content = content;
            this.date = new Date();
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.timestamp = LocalDateTime.now();
        }

        /**
         * Build the message.
         *
         * @return the message
         */
        public Message build() {
            return new Message(this);
        }
    }
}
