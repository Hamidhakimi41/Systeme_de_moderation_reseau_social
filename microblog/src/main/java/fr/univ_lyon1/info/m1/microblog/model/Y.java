package fr.univ_lyon1.info.m1.microblog.model;

//import java.security.Key;
import java.util.*;

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
         if (usersMessages.get(user) != null) {
             usersMessages.get(user).put(new Message(text), new MessageData());
         } else {
             LinkedHashMap<Message, MessageData> l = new LinkedHashMap<>();
            // users.add(user);
             l.put(new Message(text), new MessageData());
             usersMessages.put(user, l);

         }
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
     * @param idMessage
     */
    public void  ajouterMessageAtous(final String idMessage) {
         LinkedHashMap<Message, MessageData> l = new LinkedHashMap<>();
         l.put(new Message(idMessage), new MessageData());
         for (User u:users) {
             add(u, idMessage);
         }

    }
    // just for debugging

    /**
     * .
     */
    public  void affiche() {
         LinkedHashMap<Message, MessageData> l;
         for (User u:users) {
             l = usersMessages.get(u);

                 if (l != null) {  // Vérifier si l est null
                     for (Message m : l.keySet()) {
                         System.out.println(m.getContent());
                     }
                 } else {
                     // Optionnel : gérer le cas où l'utilisateur n'a pas de messages
                     System.out.println("Pas de messages pour l'utilisateur : " + u.getId());
                 }

             }

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
