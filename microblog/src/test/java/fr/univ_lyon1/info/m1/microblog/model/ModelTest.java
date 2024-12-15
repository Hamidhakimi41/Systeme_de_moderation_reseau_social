package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.ForbiddenWordsScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Classe de test pour la classe Model Y.
 */
public class ModelTest {
    private Y model;
    private User amine;
    private User hamid;

    /**
     * Initialisation des données de test.
     */
    @BeforeEach
    void setUp() {

        // réinitialisation du modèle singleton
        Y.resetInstance();

        // Récupérer l'instance unique du modèle
        model = Y.getInstance();
        amine = model.createUser("amine");
        hamid = model.createUser("hamid");

        Message message1 = new Message.Builder("Message 1")
                .owner(amine)
                .setTime(LocalDateTime.now().minusMinutes(5))
                .build();
        Message message2 = new Message.Builder("Message 2")
                .owner(amine)
                .setTime(LocalDateTime.now().minusMinutes(2))
                .build();
        Message message3 = new Message.Builder("Message 3")
                .owner(hamid)
                .setTime(LocalDateTime.now().minusMinutes(10))
                .build();
        Message message4 = new Message.Builder("Message 4")
                .owner(hamid)
                .setTime(LocalDateTime.now().minusDays(8))
                .build();

        MessageData data1 = new MessageData();
        data1.setBookmarked(false);

        MessageData data2 = new MessageData();
        data2.setBookmarked(false);

        MessageData data3 = new MessageData();
        data3.setBookmarked(false);

        MessageData data4 = new MessageData();
        data4.setBookmarked(false);

        LinkedHashMap<Message, MessageData> messagesUser1 = new LinkedHashMap<>();
        messagesUser1.put(message1, data1);
        messagesUser1.put(message2, data2);

        LinkedHashMap<Message, MessageData> messagesUser2 = new LinkedHashMap<>();
        messagesUser2.put(message3, data3);
        messagesUser2.put(message4, data4);

        BookmarkScoring bs = new BookmarkScoring();
        model.setMessageList(messagesUser1, amine);
        model.setScoringStrategy(amine, bs);
        model.setMessageList(messagesUser2, hamid);
        model.setScoringStrategy(hamid, bs);

    }

    /**
     * Test de la méthode sortMessages.
     */
    @Test
    void createUser() {
    }


    /**
     * Test de la méthode getUsers.
     */
    @Test
    void testGetUsers() {
        assertEquals(2, model.getUsers().size());
        assertTrue(model.getUsers().contains(amine));
        assertTrue(model.getUsers().contains(hamid));
    }


    /**
     * Test de la méthode add avec un TextFlow.
     */
    @Test
    void testAddMessage() {
        // Créer un message
        model.add(amine, new TextFlow(new Text("Message de test")));

        // Vérifier que le message est bien ajouté pour tous les utilisateurs
        assertTrue(model.getMessageList(amine).keySet().stream().anyMatch(
                m -> m.getContent().equals("Message de test")));
        assertTrue(model.getMessageList(hamid).keySet().stream().anyMatch(
                m -> m.getContent().equals("Message de test")));
    }


    /**
     * Test de la méthode getMessagesList.
     */
    @Test
    void testGetMessageList() {
        LinkedHashMap<Message, MessageData> messages = model.getMessageList(amine);
        assertTrue(messages.keySet().stream().anyMatch(m -> m.getContent().equals("Message 1")));
        assertTrue(messages.keySet().stream().anyMatch(m -> m.getContent().equals("Message 2")));
    }


    /**
     * Test de la méthode setMessagesList.
     */
    @Test
    void testSetMessageList() {
        LinkedHashMap<Message, MessageData> messages = new LinkedHashMap<>();
        Message message = new Message.Builder("On test setMessageList")
                .owner(amine)
                .setTime(LocalDateTime.now())
                .build();
        MessageData data = new MessageData();
        data.setScore(5);
        data.setBookmarked(false);
        messages.put(message, data);

        model.setMessageList(messages, amine);

        assertEquals(messages, model.getMessageList(amine));
    }


    /**
     * Test de la méthode getUserById.
     */
    @Test
    void testGetUserById() {
        assertEquals(amine, model.getUserById(amine.getId()));
        assertEquals(hamid, model.getUserById(hamid.getId()));
        // Test avec un id inexistant
        assertNull(model.getUserById("test"));
    }


    /**
     * Test de la méthode getMessages.
     */
    @Test
    void testGetMessages() {
        LinkedHashMap<User, LinkedHashMap<Message, MessageData>> messages = model.getMessages();

        assertNotNull(messages);
        assertEquals(2, messages.size());
        assertTrue(messages.containsKey(amine));
        assertTrue(messages.containsKey(hamid));

        LinkedHashMap<Message, MessageData> amineMessages = messages.get(amine);
        LinkedHashMap<Message, MessageData> hamidMessages = messages.get(hamid);

        assertNotNull(amineMessages);
        assertNotNull(hamidMessages);

        assertTrue(amineMessages.keySet().stream().anyMatch(
                m -> m.getContent().equals("Message 1")));
        assertTrue(amineMessages.keySet().stream().anyMatch(
                m -> m.getContent().equals("Message 2")));
        assertTrue(hamidMessages.keySet().stream().anyMatch(
                m -> m.getContent().equals("Message 3")));
        assertTrue(hamidMessages.keySet().stream().anyMatch(
                m -> m.getContent().equals("Message 4")));
    }


    /**
     * Test de la méthode getUserScoringStrategies.
     */
    @Test
    void testGetUserScoringStrategies() {
        // Vérifier que les stratégies de scoring des utilisateurs sont correctes
        Map<User, ScoringStrategy> strategies = model.getUserScoringStrategies();

        assertNotNull(strategies);
        assertEquals(2, strategies.size());
        assertTrue(strategies.containsKey(amine));
        assertTrue(strategies.containsKey(hamid));
        assertTrue(strategies.get(amine) instanceof BookmarkScoring);
        assertTrue(strategies.get(hamid) instanceof BookmarkScoring);
    }


    /**
     * Test de la méthode sortMessages.
     */
    @Test
    public void testSortMessages() {
        // vérifier les scores des messages avant le tri
        LinkedHashMap<Message, MessageData> messagesUser1 = model.getMessageList(amine);
        LinkedHashMap<Message, MessageData> messagesUser2 = model.getMessageList(hamid);

        // Appel de la méthode à tester
        model.sortMessages();


        messagesUser1.forEach((message, data) -> {
            model.getMessageList(amine).get(message).setScore(2);
        });

        messagesUser2.forEach((message, data) -> {
            model.getMessageList(hamid).get(message).setScore(2);
            data.setScore(2);
        });

        model.sortMessages();

        // Vérification des résultats
        LinkedHashMap<Message, MessageData> sortedMessagesUser1 = model.getMessageList(amine);
        LinkedHashMap<Message, MessageData> sortedMessagesUser2 = model.getMessageList(hamid);

        Iterator<Message> iterator1 = sortedMessagesUser1.keySet().iterator();

        assertEquals("Message 1", iterator1.next().getContent());
        assertEquals("Message 2", iterator1.next().getContent());

        // Vérifier l'ordre des messages pour user2 (message3 doit venir avant message4)
        Iterator<Message> iterator2 = sortedMessagesUser2.keySet().iterator();
        assertEquals("Message 3", iterator2.next().getContent());
        assertEquals("Message 4", iterator2.next().getContent());
    }


    /**
     * Test de la méthode getFilteredMessages.
     */
    @Test
    public void testGetFilteredMessages() {
        Message forbiddenMessage1 = new Message.Builder("This is a bad message")
                .owner(amine)
                .setTime(LocalDateTime.now().minusMinutes(1))
                .build();
        Message forbiddenMessage2 = new Message.Builder("Another twitter message")
                .owner(hamid)
                .setTime(LocalDateTime.now().minusMinutes(1))
                .build();

        MessageData forbiddenData1 = new MessageData();
        forbiddenData1.setScore(5);
        forbiddenData1.setBookmarked(false);
        MessageData forbiddenData2 = new MessageData();
        forbiddenData2.setScore(3);
        forbiddenData2.setBookmarked(false);

        model.getMessageList(amine).put(forbiddenMessage1, forbiddenData1);
        model.getMessageList(hamid).put(forbiddenMessage2, forbiddenData2);

        ForbiddenWordsScoring fws = new ForbiddenWordsScoring();
        model.setScoringStrategy(amine, fws);
        model.setScoringStrategy(hamid, fws);

        model.sortMessages();

        LinkedHashMap<Message, MessageData> filteredMessagesAmine =
                model.getFilteredMessages(amine);
        LinkedHashMap<Message, MessageData> filteredMessagesHamid =
                model.getFilteredMessages(hamid);

        assertTrue(filteredMessagesAmine.keySet().stream().noneMatch(
                m -> m.getContent().contains("bad")));
        assertTrue(filteredMessagesHamid.keySet().stream().noneMatch(
                m -> m.getContent().contains("twitter")));

        assertTrue(filteredMessagesAmine.keySet().stream().anyMatch(
                m -> m.getContent().contains("Message 1")));
        assertTrue(filteredMessagesAmine.keySet().stream().anyMatch(
                m -> m.getContent().contains("Message 2")));
        assertTrue(filteredMessagesHamid.keySet().stream().anyMatch(
                m -> m.getContent().contains("Message 3")));
    }


    /**
     * Test de la méthode bookmarkMessage.
     */
    @Test
    public void testBookmarkMessage() {
        Message message = new Message.Builder("Test message")
                .owner(amine)
                .setTime(LocalDateTime.now())
                .build();
        MessageData data = new MessageData();
        data.setScore(5);
        data.setBookmarked(false);

        model.getMessageList(amine).put(message, data);

        model.bookmarkMessage(message, amine);
        assertTrue(model.getMessageList(amine).get(message).isBookmarked());

        model.bookmarkMessage(message, amine);
        assertFalse(model.getMessageList(amine).get(message).isBookmarked());
    }


    /**
     * Test de la méthode deleteMessage.
     */
    @Test
    public void testDeleteMessage() {

        Message message = new Message.Builder("Test message")
                .owner(amine)
                .setTime(LocalDateTime.now())
                .build();
        MessageData data = new MessageData();
        data.setScore(5);
        data.setBookmarked(false);

        model.getMessageList(amine).put(message, data);
        model.getMessageList(hamid).put(message, data);

        model.deleteMessage(message);
        assertFalse(model.getMessageList(amine).containsKey(message));
        assertFalse(model.getMessageList(hamid).containsKey(message));
    }


    /**
     * Test de la méthode setScoringStrategy.
     */
    @Test
    public void testChangeScoringStrategy() {
        Message message = new Message.Builder("Test message")
                .owner(amine)
                .setTime(LocalDateTime.now())
                .build();
        MessageData data = new MessageData();
        data.setBookmarked(false);

        model.getMessageList(amine).put(message, data);

        ForbiddenWordsScoring fws = new ForbiddenWordsScoring();
        model.setScoringStrategy(amine, fws);
        model.sortMessages();

        assertEquals(0, model.getMessageList(amine).get(message).getScore());
    }


    /**
     * Test la méthode getFilteredMessages avec un score minimum.
     */
    @Test
    public void testScoreThresholdFiltering() {
        Message lowScoreMessage = new Message.Builder("Low score message")
                .owner(amine)
                .setTime(LocalDateTime.now())
                .build();
        Message highScoreMessage = new Message.Builder("High score message")
                .owner(amine)
                .setTime(LocalDateTime.now())
                .build();
        MessageData lowScoreData = new MessageData();
        lowScoreData.setScore(-1);
        lowScoreData.setBookmarked(false);
        MessageData highScoreData = new MessageData();
        highScoreData.setScore(5);
        highScoreData.setBookmarked(false);

        model.getMessageList(amine).put(lowScoreMessage, lowScoreData);
        model.getMessageList(amine).put(highScoreMessage, highScoreData);

        LinkedHashMap<Message, MessageData> filteredMessages = model.getFilteredMessages(amine);

        assertFalse(filteredMessages.containsKey(lowScoreMessage));
        assertTrue(filteredMessages.containsKey(highScoreMessage));
    }


    /**
     * Test de levée d'exception pour les valeurs null.
     */
    @Test
    public void testNullValues() {
        assertThrows(NullPointerException.class, () -> model.add(null, null));
        assertThrows(NullPointerException.class, () -> model.bookmarkMessage(null, null));
        assertThrows(NullPointerException.class, () -> model.deleteMessage(null));
        assertThrows(NullPointerException.class, () -> model.getFilteredMessages(null));
        assertThrows(NullPointerException.class, () -> model.getMessageList(null));
        assertThrows(NullPointerException.class, () -> model.setScoringStrategy(null, null));
        assertThrows(NullPointerException.class, () -> model.setMessageList(null, null));
        assertThrows(NullPointerException.class, () -> model.createUser(null));
        assertThrows(NullPointerException.class, () -> model.getUserById(null));

    }


    /**
     * Test que les exceptions ne sont pas levées inutilement.
     */
    @Test
    public void testExceptionHandling() {
        try {
            model.add(amine, new TextFlow(new Text("Exception test message")));
            model.bookmarkMessage(new Message.Builder("Test message").owner(amine).build(), amine);
            model.deleteMessage(new Message.Builder("Test message").owner(amine).build());
            model.getFilteredMessages(amine);
            model.getMessageList(amine);
            model.setScoringStrategy(amine, new BookmarkScoring());
            model.setMessageList(new LinkedHashMap<>(), hamid);
            model.createUser("newUser");
            model.getUserById(amine.getId());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

}
