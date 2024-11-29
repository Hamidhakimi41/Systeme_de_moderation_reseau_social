package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Observable;

/**
 * Toplevel class for the Y microbloging application's model.
 */
public class Y extends Observable {
    private List<User> users = new ArrayList<>();
    private LinkedHashMap<User, LinkedHashMap<Message, MessageData>>
            usersMessages = new LinkedHashMap<>();

    // Stratégie de scoring actuelle (par défaut : BookmarkScoring)
    private ScoringStrategy scoringStrategy = new BookmarkScoring();

    // Seuil de score pour afficher un message = 0
    private static final int SCORE_THRESHOLD = 0;

    // Map associant chaque utilisateur à sa stratégie de scoring
    private Map<User, ScoringStrategy> userScoringStrategies = new HashMap<>();

    /** Create a user and add it to the user's registry. */
    public User createUser(final String id) {
        User u = new User(id);
        users.add(u);
        return u;
    }

    /**
     * Create an example set of users and messages, for testing (not any longuer used) .
     */
   public void createExampleMessages() {
//        User foo = createUser("foo");
//        User bar = createUser("bar");
//        add(foo, "salut tout le monde");
//        add(bar, "Hello les masters");
//        add(foo, "GL c'est cool");
//        add(bar, "les etudes sans dure");
//
//        // Calculer les scores des messages après leur création
//        sortMessages();
    }

    /** Get the users in the registry. */
    public Collection<User> getUsers() {
        return users;
    }


    /** Post a message, with observer notification. **/
    public void add(final User user, final TextFlow textFlow) {
        // Convert TextFlow to a string representation if needed
        StringBuilder contentBuilder = new StringBuilder();
        textFlow.getChildren().forEach(node -> {
            if (node instanceof Text) {
                contentBuilder.append(((Text) node).getText());
            }
        });
        String content = contentBuilder.toString();

        // Create a single instance of Message for all users
        Message message = new Message.Builder(content).build();

        // Add the same message for all users
        for (User u : users) {
            if (usersMessages.get(u) != null) {
                usersMessages.get(u).put(message, new MessageData());
            } else {
                LinkedHashMap<Message, MessageData> l = new LinkedHashMap<>();
                l.put(message, new MessageData());
                usersMessages.put(u, l);
            }
        }

        // Calculate message scores after adding a new message
        sortMessages();

        // Notify observers that the user has added a message
        setChanged();
        notifyObservers(user);
    }
    /**
     * update the strategy of scoring for a user .
     */
    public void setScoringStrategy(final User user, final ScoringStrategy selectedStrategy) {
        userScoringStrategies.put(user, selectedStrategy);
        //System.out.println("Stratégie modifiée pour " + user.getId() + " : " + selectedStrategy);
    }

    /**
     * Sort messages for all users using the current scoring strategy.
     * ForbiddenWordsScoring , RecentMessageScoring sont des classes qui héritent de BookmarkScoring
     * et qui surchargent la méthode computeScores pour calculer les scores des messages diff
     */
    public void sortMessages() {
        for (User u : this.getUsers()) {
            LinkedHashMap<Message, MessageData> messageList = getMessageList(u);

            // On récupère la stratégie de scoring de l'utilisateur, sinon la stratégie par défaut
            ScoringStrategy strategy = userScoringStrategies.getOrDefault(u, new BookmarkScoring());
            strategy.computeScores(messageList);

            LinkedHashMap<Message, MessageData> sortedMessageList = new LinkedHashMap<>();
            messageList.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder((Map.Entry<Message, MessageData> left,
                                                      Map.Entry<Message, MessageData> right) -> {
                        return left.getValue().compare(right.getValue());
                    }))
                    .forEach((Map.Entry<Message, MessageData> e) -> {
                        sortedMessageList.put(e.getKey(), e.getValue());
                    });
            setMessageList(sortedMessageList, u);
        }
        // on marque que le model a changé et on notifie les vues
        setChanged();
        notifyObservers();
        //System.out.println("Messages triés");
    }

    /**
     *
     * @param u
     * @return
     */

    public LinkedHashMap<Message, MessageData> getMessageList(final User u) {
         return  usersMessages.get(u);
    }
    private void  setMessageList(final LinkedHashMap<Message, MessageData> messages, final User u) {
        usersMessages.put(u, messages);
    }

    /**
     *
     * @param m
     * @param user
     */
    public void bookmarkMessage(final Message m, final User user) {
        // Logique de bookmark
        LinkedHashMap<Message, MessageData> userMessages = usersMessages.get(user);
        if (userMessages != null && userMessages.containsKey(m)) {
            MessageData messageData = userMessages.get(m);
            // Inverser l'état du bookmark (si déjà bookmarké, on le retire, sinon on l'ajoute)
            messageData.setBookmarked(!messageData.isBookmarked());
        }
        setChanged();
        notifyObservers(user);
    }

    /**
     * Récupérer les messages filtrés pour un utilisateur donné.
     * @param user
     * @return
     */
    public LinkedHashMap<Message, MessageData> getFilteredMessages(final User user) {
        LinkedHashMap<Message, MessageData> filteredMessages = new LinkedHashMap<>();
        LinkedHashMap<Message, MessageData> messages = getMessageList(user);
        //System.out.println("taille de messages = " + messages.size());
        Instant oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);

        // si le score est > au seuil, && le message est récent ou a un bonus, on l'ajoute
        messages.forEach((Message m, MessageData d) -> {
            Instant messageInstant = m.getTimestamp().atZone(ZoneId.systemDefault()).toInstant();
            boolean isRecent = messageInstant.isAfter(oneWeekAgo);
            boolean hasBonus = d.getScore() > SCORE_THRESHOLD;
            if (d.getScore() >= SCORE_THRESHOLD && (isRecent || hasBonus)) {
                filteredMessages.put(m, d);
            }
        });
        //System.out.println("taille de filteredMessages = " + filteredMessages.size());

        return filteredMessages;
    }

    /**
     * Supprimer un message pour tous les utilisateurs.
     * @param message
     */
    public void deleteMessage(final Message message) {
        for (User user : users) {
            LinkedHashMap<Message, MessageData> userMessages = usersMessages.get(user);
            if (userMessages != null) {
                userMessages.remove(message);
            }
        }
        setChanged();
        notifyObservers();
    }

}
