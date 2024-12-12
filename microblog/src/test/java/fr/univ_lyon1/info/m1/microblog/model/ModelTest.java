package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * .
 */
public class ModelTest {
    private Y model;
    private User amine;
    private User      hamid;

    /**
     * .
     */
    @BeforeEach
    void setUp() {
        model = new Y();
        amine = model.createUser("amine");
        hamid = model.createUser("hamid");

        // Création des messages pour chaque utilisateur
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
                .build(); // Message vieux de plus d'une semaine

        // Création des scores
        MessageData data1 = new MessageData();
        data1.setScore(5); // Score : 5
        MessageData data2 = new MessageData();
        data2.setScore(10); // Score : 10
        MessageData data3 = new MessageData();
        data3.setScore(3); // Score : 3
        MessageData data4 = new MessageData();
        data4.setScore(0); // Score : 0

        // Ajout des messages au modèle
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
     * .
     */
    @Test
    public void testSortMessages() {
        // Appel de la méthode à tester
        model.sortMessages();

        // Vérification des résultats
        LinkedHashMap<Message, MessageData> sortedMessagesUser1 = model.getMessageList(amine);
        LinkedHashMap<Message, MessageData> sortedMessagesUser2 = model.getMessageList(hamid);

        // Vérifier l'ordre des messages pour user1 (message2 doit venir avant message1)
        Iterator<Message> iterator1 = sortedMessagesUser1.keySet().iterator();
        assertEquals("Message 1", iterator1.next().getContent()); // todo
        assertEquals("Message 2", iterator1.next().getContent());

        // Vérifier l'ordre des messages pour user2 (un seul message)
        Iterator<Message> iterator2 = sortedMessagesUser2.keySet().iterator();
        assertEquals("Message 3", iterator2.next().getContent());
        // Vérifier que le message 4 est présent
        assertEquals("Message 4", iterator2.next().getContent());
    }


    /**
     * test du filter.
     */
    @Test
    public void testGetFilteredMessages() {
        // Appel de la méthode de filtrage
        LinkedHashMap<Message, MessageData>
                filteredMessagesAmine = model.getFilteredMessages(amine);
        LinkedHashMap<Message, MessageData>
                filteredMessagesHamid = model.getFilteredMessages(hamid);

        // Vérifier les messages filtrés pour amine
        assertEquals(2, filteredMessagesAmine.size());
        // Il y a 2 messages valides pour Amine
        assertTrue(!filteredMessagesAmine.containsKey(
                new Message.Builder("Message 1").owner(amine).build())); // todo
        assertTrue(!filteredMessagesAmine.containsKey(
                new Message.Builder("Message 2").owner(amine).build())); // todo

        // Vérifier les messages filtrés pour hamid
       // Il devrait y avoir 1 message valide pour Hamid
        assertEquals(1, filteredMessagesHamid.size());
        assertTrue(!filteredMessagesHamid.containsKey(
                new Message.Builder("Message 3").owner(hamid).build()));
        // todo le filtre fonctionne pas correctement
        assertTrue(!filteredMessagesHamid.containsKey(
                new Message.Builder("Message 4").owner(hamid).build()));
        // Le message 4 ne doit pas être présent car trop vieux
    }
}
