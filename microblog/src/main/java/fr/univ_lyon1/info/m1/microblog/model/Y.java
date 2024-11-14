package fr.univ_lyon1.info.m1.microblog.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Toplevel class for the Y microbloging application's model.
 */
public class Y extends Observable {
    private List<User> users = new ArrayList<>();
    private LinkedHashMap<User, LinkedHashMap<Message, MessageData>>
            usersMessages = new LinkedHashMap<>();

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


    }

    /** Get the users in the registry. */
    public Collection<User> getUsers() {
        return users;
    }


    /** Post a message. */ // on va le faire avec les observables
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

        // Notifier les observateurs que l'utilisateur a ajouté un message
        setChanged();
        notifyObservers(user);
    }

    /**
     *
     */
    public void sortMessages() {
        for (User u : this.getUsers()) {
            LinkedHashMap<Message, MessageData> messageList = getMessageList(u);
            new BookmarkScoring().computeScores(messageList);
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



}
