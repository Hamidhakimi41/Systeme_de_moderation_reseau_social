package fr.univ_lyon1.info.m1.microblog.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;

import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *  Test  BookmarkScoring manipulations.
 */
public class BookmarkScoringTest {
    private BookmarkScoring bookmarkScoring;
    private Map<Message, MessageData> messagesData;
    /**
     * avant chaque test.
     */
    @BeforeEach
    void setUp() {
        bookmarkScoring = new BookmarkScoring();
        messagesData = new HashMap<>();
    }

    /**
     * cree un message avec un contenu et un proprietaire.
     * @param content
     * @param owner
     * @return
     */
    private Message createMessage(final String content,
                                  final User owner) {
        return new Message.Builder(content).owner(owner).build();
    }
    /**
     * test de la methode computeScores avec un seul mot bookmarke.
     */
    @Test
    void testComputeScoresWithSingleBookmarkedWord() {
        User user = new User("hamid");
        Message message1 = createMessage("Hello world", user);
        MessageData data1 = new MessageData();
        data1.setBookmarked(true);

        messagesData.put(message1, data1);

        Message message2 = createMessage("world goo", user);
        MessageData data2 = new MessageData();
        data2.setBookmarked(false);

        messagesData.put(message2, data2);

        bookmarkScoring.computeScores(messagesData);

        assertEquals(2, data1.getScore()); // +2 bookmarded
        assertEquals(1, data2.getScore()); // +1 non marke et word en commun
    }
    /**
     * test de la methode computeScores avec plusieurs mots bookmarke.
     */
    @Test
    void testComputeScoresWithMultipleBookmarkedMessages() {
        User user = new User("amine");
        Message message1 = createMessage("t chaud", user);
        MessageData data1 = new MessageData();
        data1.setBookmarked(true);

        messagesData.put(message1, data1);

        Message message2 = createMessage("chaud est toi", user);
        MessageData data2 = new MessageData();
        data2.setBookmarked(true);

        messagesData.put(message2, data2);

        Message message3 = createMessage("il fait chaud", user);
        MessageData data3 = new MessageData();

        messagesData.put(message3, data3);

        bookmarkScoring.computeScores(messagesData);

        assertEquals(2, data1.getScore());
        assertEquals(3, data2.getScore());
        assertEquals(1, data3.getScore());
    }
    /**
     * test de la methode computeScores avec aucun mot bookmarke.
     */
    @Test
    void testComputeScoresWithNoBookmarkedWords() {
        User user = new User("Binomes");
        Message message1 = createMessage("pas bookmarke", user);
        MessageData data1 = new MessageData();

        messagesData.put(message1, data1);

        bookmarkScoring.computeScores(messagesData);

        assertEquals(0, data1.getScore());
    }

    /**
     * test de la methode computeScores avec un message vide.
     */
    @Test
    void testComputeScoresWithEmptyMessage() {
        User user = new User("empty");
        Message message = createMessage("", user);
        MessageData data = new MessageData();
        data.setBookmarked(true);

        messagesData.put(message, data);

        bookmarkScoring.computeScores(messagesData);

        assertEquals(0, data.getScore());
    }

    /**
     * test de la methode computeScores avec des caracteres speciaux.
     */
    @Test
    void testComputeScoresWithSpecialCharacters() {
        User user = new User("special");
        Message message = createMessage("Hello, world! 123", user);
        MessageData data = new MessageData();
        data.setBookmarked(true);

        messagesData.put(message, data);

        bookmarkScoring.computeScores(messagesData);

        assertEquals(2, data.getScore()); // "hello" et "world" sont les seuls mots alphabétiques
    }

    /**
     * test toString.
     */
    @Test
    void testToString() {
        assertEquals("Messages, ordre chronologique(pertinents)", bookmarkScoring.toString());
    }
}
