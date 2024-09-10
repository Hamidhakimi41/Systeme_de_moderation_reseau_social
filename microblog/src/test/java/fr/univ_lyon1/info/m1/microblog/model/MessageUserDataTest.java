package fr.univ_lyon1.info.m1.microblog.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 *  Test message list manipulations.
 */
public class MessageUserDataTest {
    @Test
    void dummyTestMessageContent() {
        // Given
        Message m = new Message("Some content");

        // When
        String s = m.getContent();

        // Then
        // TODO: this is obviously wrong, fix the expected value.
        assertThat(s, is("Some content"));
    }

    @Test
    void testSortMessages() {
        // Given
        Map<Message, MessageData> msgs = new HashMap<>();

        Message m1 = new Message("Hello, world!");
        add(msgs, m1);
        Message m2 = new Message("Hello, you!");
        add(msgs, m2);
        Message m3 = new Message("What is this message ?");
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

    private void add(final Map<Message, MessageData> msgs, final Message m) {
        msgs.put(m, new MessageData());
    }
}
