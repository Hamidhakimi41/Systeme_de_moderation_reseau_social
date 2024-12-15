package fr.univ_lyon1.info.m1.microblog.model;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for the Message class.
 */
public class MessageTest {

    /**
     * Test of Message creation.
     */
    @Test
    public void testMessageCreation() {
        User user = new User("testUser");
        Message message = new Message.Builder("testContent").owner(user).build();
        assertNotNull(message);
        assertEquals("testContent", message.getContent());
        assertEquals(user, message.getOwner());
        assertNotNull(message.getTimestamp());
    }

    /**
     * Test of Message creation with timestamp.
     */
    @Test
    public void testMessageCreationWithTimestamp() {
        User user = new User("testUser");
        LocalDateTime timestamp = LocalDateTime.now().minusDays(1);
        Message message = new Message.Builder("testContent").owner(user).setTime(timestamp).build();
        assertNotNull(message);
        assertEquals(timestamp, message.getTimestamp());
    }

    /**
     * Test of getTextFlow method.
     */
    @Test
    public void testGetTextFlow() {
        User user = new User("testUser");
        TextFlow textFlow = new TextFlow(new Text("testContent"));
        Message message = new Message.Builder("testContent").owner(user).build();
        assertNotNull(message.getTextFlow());
        assertEquals("testContent", ((Text) message.getTextFlow().getChildren().get(0)).getText());
    }
}
