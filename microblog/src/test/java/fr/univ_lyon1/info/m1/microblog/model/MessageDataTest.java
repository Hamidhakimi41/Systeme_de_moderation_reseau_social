package fr.univ_lyon1.info.m1.microblog.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test class for the MessageData class.
 */
public class MessageDataTest {

    /**
     * Test of getScore and setScore methods.
     */
    @Test
    public void testGetSetScore() {
        MessageData data = new MessageData();
        data.setScore(10);
        assertEquals(10, data.getScore());
    }

    /**
     * Test of isBookmarked and setBookmarked methods.
     */
    @Test
    public void testIsSetBookmarked() {
        MessageData data = new MessageData();
        data.setBookmarked(true);
        assertTrue(data.isBookmarked());
        data.setBookmarked(false);
        assertFalse(data.isBookmarked());
    }

}
