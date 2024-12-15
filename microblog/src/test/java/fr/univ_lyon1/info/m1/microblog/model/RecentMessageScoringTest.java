package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.RecentMessageScoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * classe de test de la classe RecentMessageScoring.
 */
public class RecentMessageScoringTest {
    private RecentMessageScoring recentMessageScoring;
    private   Map<Message, MessageData> messagesData;

    /**
     * avant chaque test.
     */
    @BeforeEach
    void setUp() {
        recentMessageScoring = new RecentMessageScoring();
        messagesData = new HashMap<>();
    }

    /**
     * Créer un message.
     * @param content
     * @param owner
     * @return
     */
    private Message createMessage(final String content, final User owner) {
        return new Message.Builder(content).owner(owner).build();
    }

    /**
     * Test de la méthode toString.
     */
    @Test
    public void testToString() {
        assertEquals(" Messages pertinents récents", recentMessageScoring.toString());
    }

    /**
     * Test de la méthode computeScores.
     */
    @Test
    public void testComputeScores() {
        User user = new User("hamid");

        // Message de moins de 24 heures
        Message message1 = createMessage("Hello world", user);
        message1.setTimestamp(LocalDateTime.now().minusHours(1));
        MessageData data1 = new MessageData();
        data1.setBookmarked(true);
        messagesData.put(message1, data1);

        // Message de moins de 7 jours mais plus de 24 heures
        Message message2 = createMessage("world goo", user);
        message2.setTimestamp(LocalDateTime.now().minusDays(3));
        MessageData data2 = new MessageData();
        data2.setBookmarked(false);
        messagesData.put(message2, data2);

        // Message de plus de 7 jours
        Message message3 = createMessage("old message", user);
        message3.setTimestamp(LocalDateTime.now().minusDays(10));
        MessageData data3 = new MessageData();
        data3.setBookmarked(false);
        messagesData.put(message3, data3);

        recentMessageScoring.computeScores(messagesData);

        assertEquals(4, data1.getScore()); // +2 bookmarked +2 recent
        assertEquals(2, data2.getScore()); // +2 recent
        assertEquals(0, data3.getScore()); // pas de bonus
    }

}
