package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.ForbiddenWordsScoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * classe de test pour la classe ForbiddenWordsScoring.
 */
public class ForbiddenWordsScoringTest {
    private ForbiddenWordsScoring forbiddenWordsScoring;
    private Map<Message, MessageData> messagesData;

    /**
     * avant chaque test on initialise les attributs.
     */
    @BeforeEach
    void setUp() {
        forbiddenWordsScoring = new ForbiddenWordsScoring();
        messagesData = new HashMap<>();
    }

    /**
     * creer un message.
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
        assertEquals(0, md1.getScore());

    }

    /**
     * pour verifier le malus.
     */
    @Test
    void testAvecMotsInterdits() {
        User user = new User("amine");
        Message m1 = createMessage("le nana master", user);
        MessageData md1 = new MessageData();
        md1.setBookmarked(false);
        messagesData.put(m1, md1);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(-50, md1.getScore());
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
        assertEquals(-95, md1.getScore());
    }

    /**
     * Test avec un message vide.
     */
    @Test
    void testMessageVide() {
        User user = new User("amine");
        Message m1 = createMessage("", user);
        MessageData md1 = new MessageData();
        md1.setBookmarked(false);
        messagesData.put(m1, md1);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(0, md1.getScore()); // aucun effet
    }

    /**
     * Test avec des mots interdits en majuscules/minuscules.
     */
    @Test
    void testMotsInterditsCasse() {
        User user = new User("amine");
        Message m1 = createMessage("Le NaNa master est BAD", user);
        MessageData md1 = new MessageData();
        md1.setBookmarked(false);
        messagesData.put(m1, md1);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(-100, md1.getScore());
    }

    /**
     * Test avec plusieurs messages.
     */
    @Test
    void testPlusieursMessages() {
        User user = new User("amine");
        Message m1 = createMessage("le master", user);
        Message m2 = createMessage("le nana master", user);
        MessageData md1 = new MessageData();
        MessageData md2 = new MessageData();
        md1.setBookmarked(false);
        md2.setBookmarked(false);
        messagesData.put(m1, md1);
        messagesData.put(m2, md2);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(0, md1.getScore());
        assertEquals(-50, md2.getScore());  // -50 car "nana" est interdit
    }

    /**
     * Test avec des mots partiellement interdits.
     */
    @Test
    void testMotsPartiellementInterdits() {
        User user = new User("amine");
        // "bad" est interdit, mais "badminton" ne l'est pas
        Message m1 = createMessage("le badminton est amusant", user);
        MessageData md1 = new MessageData();
        md1.setBookmarked(false);
        messagesData.put(m1, md1);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(0, md1.getScore());

        md1.setBookmarked(true);
        messagesData.put(m1, md1);
        forbiddenWordsScoring.computeScores(messagesData);
        assertEquals(4, md1.getScore());
    }

    /**
     * test pour verifier le toString.
     */
    @Test
    public  void  testToString() {
       assertEquals("Messages sans mots interdits", forbiddenWordsScoring.toString());
    }

}
