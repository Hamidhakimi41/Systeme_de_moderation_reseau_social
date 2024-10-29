package fr.univ_lyon1.info.m1.microblog.controleur;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.model.User;
import fr.univ_lyon1.info.m1.microblog.model.Message;

/**
 * .
 */

public class Controleur {
    private final Y model;

    /**
     *
     * @param model
     */

    public Controleur(final Y model) {
        this.model = model;
    }

    /**
     *
     * @param user
     * @param content
     */

    public void publishMessage(final User user, final String content) {
        model.add(user, content);
    }

    /**
     *
     * @param message
     * @param user
     */
    public void bookmarkMessage(final Message message, final User user) {
        model.bookmarkMessage(message, user);
    }

    /**
     *
     */

    public void sortMessages() {
        model.sortMessages();
    }

    /**
     *
     */

    public void createExampleMessages() {
        model.createExampleMessages();
    }
    public Y getModel() {
        return model;
    }


}

