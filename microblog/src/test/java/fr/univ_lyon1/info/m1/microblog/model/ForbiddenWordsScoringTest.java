package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.ForbiddenWordsScoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * .
 */
public class ForbiddenWordsScoringTest {
    private ForbiddenWordsScoring forbiddenWordsScoring;
    private Map<Message, MessageData> messagesData;

    /**
     * .
     */
    @BeforeEach
    void setUp() {
        forbiddenWordsScoring = new ForbiddenWordsScoring();
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
     * on compare à 2 parceque c'est les messages  récents.
     */
    @Test
    void testSansMotInterdits() {
        User user = new User("hamid");
        Message m1 = createMessage("le master", user);
        MessageData md1 = new MessageData();
        md1.setBookmarked(false);
        messagesData.put(m1, md1);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(2, md1.getScore());

    }

    /**
     * .
     */
    @Test
    void testAvecMotsInterdits() {
        User user = new User("amine");
        Message m1 = createMessage("le nana master", user);
        MessageData md1 = new MessageData();
        md1.setBookmarked(false);
        messagesData.put(m1, md1);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(-3, md1.getScore());



    }

    /**
     * pour verifier le malus s'additionne.
     */
   @Test

    void testAvecPlusieursMotsInterdits() {
        User user = new User("amine");
        Message m1 = createMessage("le nana master est bad", user);
        MessageData md1 = new MessageData();
        md1.setBookmarked(true);
        messagesData.put(m1, md1);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(-3, md1.getScore());



    }

    /**
     * .
     */
    @Test
    public  void  testToString() {
       assertEquals("Messages sans mots interdits", forbiddenWordsScoring.toString());
    }


}
