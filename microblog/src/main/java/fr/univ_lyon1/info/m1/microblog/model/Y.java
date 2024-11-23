package fr.univ_lyon1.info.m1.microblog.model;

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
     * Create an example set of users and messages, for testing.
     */
    public void createExampleMessages() {
        User foo = createUser("foo");
        User bar = createUser("bar");
        add(foo, "salut tout le monde");
        add(bar, "Hello les masters");
        add(foo, "GL c'est cool");
        add(bar, "les etudes sans dure");

        // Calculer les scores des messages après leur création
        sortMessages();
    }

    /** Get the users in the registry. */
    public Collection<User> getUsers() {
        return users;
    }


    /** Post a message, with observer notification. **/
    public void add(final User user, final String text) {
        // Créer une seule instance de Message
        Message message = new Message(text);

        // Ajouter le même message pour tous les  utilisateurs
        for (User u : users) {

                if (usersMessages.get(u) != null) {
                    usersMessages.get(u).put(message, new MessageData());
                } else {
                    LinkedHashMap<Message, MessageData> l = new LinkedHashMap<>();
                    l.put(message, new MessageData());
                    usersMessages.put(u, l);
                }
            }

        // Calculer les scores des messages après l'ajout d'un nouveau message
        sortMessages();

        // Notifier les observateurs que l'utilisateur a ajouté un message
        setChanged();
        notifyObservers(user);
    }

    /**
     * modifier la stratégie de scoring pour un utilisateur donné
     */
    public void setScoringStrategy(User user, ScoringStrategy selectedStrategy) {
        userScoringStrategies.put(user, selectedStrategy);
        System.out.println("Stratégie de scoring modifiée pour " + user.getId());
    }

    /**
     * Sort messages for all users using the current scoring strategy.
     * ForbiddenWordsScoring , RecentMessageScoring sont des classes qui héritent de BookmarkScoring
     * et qui surchargent la méthode computeScores pour calculer les scores des messages différemment
     */
    public void sortMessages() {
        for (User u : this.getUsers()) {
            LinkedHashMap<Message, MessageData> messageList = getMessageList(u);

            // On récupère la stratégie de scoring de l'utilisateur, ou on utilise la stratégie par défaut
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
        System.out.println("Messages triés");
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

    // filtrer les messages en fonction du score & de la date
    public LinkedHashMap<Message, MessageData> getFilteredMessages(User user) {
        System.out.println("ici getFilteredMessages, user = " + user.getId());
        LinkedHashMap<Message, MessageData> filteredMessages = new LinkedHashMap<>();
        LinkedHashMap<Message, MessageData> messages = getMessageList(user);
        //System.out.println("taille de messages = " + messages.size());
        Instant oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);

        // si le score est supérieur au seuil, et que le message est récent ou a un bonus, on l'ajoute
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

}
