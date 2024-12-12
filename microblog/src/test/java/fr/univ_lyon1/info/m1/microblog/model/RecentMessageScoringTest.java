package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.RecentMessageScoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * .
 */
public class RecentMessageScoringTest {
    private RecentMessageScoring recentMessageScoring;
    private   Map<Message, MessageData> messagesData;

    /**
     * .
     */
    @BeforeEach
    void setUp() {
        recentMessageScoring = new RecentMessageScoring();
        messagesData = new HashMap<>();
    }

    /**
     * .
     * @param content
     * @param owner
     * @return
     */
    private Message createMessage(final String content, final User owner) {
        return new Message.Builder(content).owner(owner).build();
    }

    /**
     * .
     */
    @Test
    public void testToString() {
        assertEquals(" Messages pertinents récents", recentMessageScoring.toString());
    }
    /**
     * .
     */
    @Test
    public  void testcomputeScores() {
        User user = new User("hamid");
        Message message1 = createMessage("Hello world", user);
        MessageData data1 = new MessageData();
        data1.setBookmarked(true);

        messagesData.put(message1, data1);

        Message message2 = createMessage("world goo", user);
        MessageData data2 = new MessageData();
        data2.setBookmarked(false);


        messagesData.put(message2, data2);

        recentMessageScoring.computeScores(messagesData);

        assertEquals(4, data1.getScore()); // +2 bookmarded+2recent
        assertEquals(3, data2.getScore()); // +1 non marke et +2 recent


    }
}
