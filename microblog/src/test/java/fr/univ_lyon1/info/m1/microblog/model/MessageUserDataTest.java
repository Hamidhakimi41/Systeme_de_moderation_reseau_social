package fr.univ_lyon1.info.m1.microblog.model;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoring;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.Test;

/**
 *  Test message list manipulations.
 */
public class MessageUserDataTest {
    @Test
    void dummyTestMessageContent() {
        // Given
        Message m = new Message.Builder("Some content").build();

        // When
        String s = m.getContent();

        // Then

        assertThat(s, is("Some content"));
    }

    /**
     * Test list of messages sorting.
     */
    @Test
    void testSortMessages() {
        // Given
        Map<Message, MessageData> msgs = new HashMap<>();

        Message m1 = new Message.Builder("Hello, world!").build();
        add(msgs, m1);
        Message m2 = new Message.Builder("Hello, you!").build();
        add(msgs, m2);
        Message m3 = new Message.Builder("What is this message ?").build();
        add(msgs, m3);
        msgs.get(m1).setBookmarked(true);

        // When
        new BookmarkScoring().computeScores(msgs);

        // Then
        assertThat(msgs.get(m1).getScore(), is(2));
        assertThat(msgs.get(m2).getScore(), is(1));
        assertThat(msgs.get(m3).getScore(), is(0));

        List<Message> sorted = msgs.entrySet()
                .stream()
                .sorted(Collections.reverseOrder((Entry<Message, MessageData> left,
                        Entry<Message, MessageData> right) -> {
                    return left.getValue().compare(right.getValue());
                }))
                .map(Entry::getKey)
                .collect(Collectors.toList());

        assertThat(sorted, contains(m1, m2, m3));
    }

    /**
     * Test creation date of a message.
     */
    @Test
    void testGetDate() {
        // Given
        Message m = new Message.Builder("Some content").build();

        // When
        String date = m.getDate();

        // Then
        assertNotNull(date);
    }

    /**
     * Test timestamp of a message.
     */
    @Test
    void testGetTimestamp() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Message m = new Message.Builder("Some content").setTime(now).build();

        // When
        LocalDateTime timestamp = m.getTimestamp();

        // Then
        assertThat(timestamp, is(now));
    }

    /**
     * Test text flow of a message.
     */
    @Test
    void testGetTextFlow() {
        // Given
        Message m = new Message.Builder("Some content").build();

        // When
        TextFlow textFlow = m.getTextFlow();

        // Then
        assertNotNull(textFlow);
        assertThat(((Text) textFlow.getChildren().get(0)).getText(), is("Some content"));
    }

    /**
     * Test owner of a message.
     */
    @Test
    void testGetOwner() {
        // Given
        User user = new User("username");
        Message m = new Message.Builder("Some content").owner(user).build();

        // When
        User owner = m.getOwner();

        // Then
        assertThat(owner, is(user));
    }

    /**
     * Add a message to the list.
     *
     * @param msgs The list of messages.
     * @param m The message to add.
     */
    private void add(final Map<Message, MessageData> msgs, final Message m) {
        msgs.put(m, new MessageData());
    }
}
