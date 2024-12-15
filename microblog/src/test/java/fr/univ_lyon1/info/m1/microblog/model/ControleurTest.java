package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.controleur.Controleur;
import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests unitaires pour la classe Controleur.
 */

class ControleurTest {
    private Controleur controleur;
    private Y model;

    /**
     * inialisation .
     */
    @BeforeEach
    public void setUp() {
        Y.resetInstance(); // réinitialisation du modèle singleton
        model = Y.getInstance(); // initialisation du modèle singleton
        controleur = new Controleur(model);
    }

    /**
     * test de la publication d'un message.
     */
    @Test
    public void testPublishMessage() {
        User user = model.createUser("TestUser");
        TextFlow textFlow = new TextFlow();
        Text text = new Text("Hello world!");
        textFlow.getChildren().add(text);

        controleur.publishMessage(user, textFlow);

        // Vérification que le message a été ajouté dans le modèle
        LinkedHashMap<Message, MessageData> messages = model.getMessageList(user);
        assertNotNull(messages);
        assertEquals(1, messages.size());
        Message message = messages.keySet().iterator().next();
        assertEquals("Hello world!", message.getContent());
        assertEquals(user, message.getOwner());
    }

    /**
     * test de la publication d'un message avec décorateurs.
     */
    @Test
    public void testPublishMessageWithDecorators() {
        User user = model.createUser("TestUser");
        String text = "Hello, @mention!";
        String tags = "tag1,tag2";
        String mentions = "mention";

        controleur.publishMessageWithDecorators(user, text, tags, mentions);

        // Vérifiez que le message avec décorateurs a été ajouté
        LinkedHashMap<Message, MessageData> messages = model.getMessageList(user);
        assertNotNull(messages);
        assertEquals(1, messages.size());

        Message message = messages.keySet().iterator().next();
        assertTrue(message.getContent().contains("#tag1"));
        assertTrue(message.getContent().contains("@mention"));
        assertEquals(user, message.getOwner());
    }

    /**
     * test de la publication d'un message avec bookmarks.
     */
    @Test
    public void testBookmarkMessage() {
        User user = model.createUser("TestUser");
        TextFlow textFlow = new TextFlow(new Text("Bookmark this message."));
        controleur.publishMessage(user, textFlow);

        // Récupérer le message ajouté
        Message message = model.getMessageList(user).keySet().iterator().next();

        // Ajouter un bookmark
        controleur.bookmarkMessage(message, user);

        // Vérifiez que le message a été bookmarké
        MessageData data = model.getMessageList(user).get(message);
        assertTrue(data.isBookmarked());

        // Retirer le bookmark
        controleur.bookmarkMessage(message, user);
        assertFalse(data.isBookmarked());
    }

    /**
     * test de la mise à jour de la stratégie de scoring.
     */
    @Test
    public void testSetScoringStrategy() {
        User user = model.createUser("TestUser");
        ScoringStrategy strategy = new BookmarkScoring();

        controleur.setScoringStrategy(user, () -> strategy);

        // Vérifiez que la stratégie a été mise à jour
        ScoringStrategy assignedStrategy = model.getUserScoringStrategies().get(user);
        assertNotNull(assignedStrategy);
        assertEquals(strategy.getClass(), assignedStrategy.getClass());
        };


    /**
     * test de tri des messages.
     */
    @Test
    public void testSortMessages() {
        User user = model.createUser("TestUser");
        TextFlow textFlow1 = new TextFlow(new Text("Message 1"));
        TextFlow textFlow2 = new TextFlow(new Text("Message 2"));

        // Ajouter deux messages
        controleur.publishMessage(user, textFlow1);
        controleur.publishMessage(user, textFlow2);

        // Assurez-vous que les messages sont triés après publication
        controleur.sortMessages();

        LinkedHashMap<Message, MessageData> messages = model.getMessageList(user);
        assertNotNull(messages);

        // Vérifiez qu'il y a 2 messages
        assertEquals(2, messages.size());
    }

    // Tests de cas limites et erreurs

    /**
     * test de la publication d'un message avec un utilisateur null.
     */
    @Test
    public void testPublishMessageWithNullUser() {
        TextFlow textFlow = new TextFlow(new Text("Hello world!"));
        assertThrows(NullPointerException.class, () -> controleur.publishMessage(null, textFlow));
    }

    /**
     * test de la publication d'un message avec un TextFlow null.
     */
    @Test
    public void testPublishMessageWithNullTextFlow() {
        User user = model.createUser("TestUser");
        assertThrows(NullPointerException.class, () -> controleur.publishMessage(user, null));
    }

    /**
     * test de la publication d'un message sans tags ni mentions.
     */
    @Test
    public void testPublishMessageWithDecoratorsEmptyStrings() {
        User user = model.createUser("TestUser");
        String text = "Hello, mention!";
        String tags = "";
        String mentions = "";

        controleur.publishMessageWithDecorators(user, text, tags, mentions);

        LinkedHashMap<Message, MessageData> messages = model.getMessageList(user);
        assertNotNull(messages);
        assertEquals(1, messages.size());

        Message message = messages.keySet().iterator().next();
        assertFalse(message.getContent().contains("#"));
        assertFalse(message.getContent().contains("@"));
        assertEquals(user, message.getOwner());
    }

    /**
     * test de bookmark d'un message avec un message null.
     */
    @Test
    public void testBookmarkMessageWithNullMessage() {
        User user = model.createUser("TestUser");
        assertThrows(NullPointerException.class, () -> controleur.bookmarkMessage(null, user));
    }

    /**
     * test de scorage d'un message avec un utilisateur null.
     */
    @Test
    public void testSetScoringStrategyWithNullUser() {
        ScoringStrategy strategy = new BookmarkScoring();
        assertThrows(NullPointerException.class,
                () -> controleur.setScoringStrategy(null, () -> strategy));
    }

    /**
     * test de scorage d'un message avec une stratégie null.
     */
    @Test
    public void testSetScoringStrategyWithNullFactory() {
        User user = model.createUser("TestUser");
        assertThrows(NullPointerException.class, () -> controleur.setScoringStrategy(user, null));
    }
}
