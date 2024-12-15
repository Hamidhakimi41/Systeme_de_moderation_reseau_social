package fr.univ_lyon1.info.m1.microblog.controleur;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.model.User;
import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.decorator.MentionedMessageDecorator;
import fr.univ_lyon1.info.m1.microblog.model.decorator.TaggedMessageDecorator;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategyFactory;
import javafx.scene.text.TextFlow;

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
     * @param textFlow
     */
    public void publishMessage(final User user, final TextFlow textFlow) {
        if (user == null || textFlow == null) {
            throw new NullPointerException("User or TextFlow cannot be null");
        }
        model.add(user, textFlow);
        model.notifyObservers(user);
    }

    /**
     *
     * @param u
     * @param text
     * @param tags
     * @param mentions
     */
    public void publishMessageWithDecorators(
            final User u, final String text, final String tags, final String mentions) {
        if (u == null || text == null || tags == null || mentions == null) {
            throw new NullPointerException("User, text, tags, or mentions cannot be null");
        }
        Message message = new Message.Builder(text).owner(u).build();

        // Ajout des mentions en utilisant le décorateur
        if (!mentions.isEmpty()) {
            for (String mention : mentions.split(",")) {
                message = new MentionedMessageDecorator(message, mention.trim());
            }
        }

        // Ajout des tags en utilisant le décorateur
        if (!tags.isEmpty()) {
            for (String tag : tags.split(",")) {
                message = new TaggedMessageDecorator(message, tag.trim());
            }
        }

        // Création du TextFlow et publication du message
        TextFlow textFlow = message.getTextFlow();
        publishMessage(u, textFlow);
        sortMessages();
    }

    /**
     *
     * @param message
     * @param user
     */
    public void bookmarkMessage(final Message message, final User user) {
        if (message == null || user == null) {
            throw new NullPointerException("Message or User cannot be null");
        }
        model.bookmarkMessage(message, user);
    }

    /**
     *
     * @param user
     * @param factory
     */
    public void setScoringStrategy(final User user, final ScoringStrategyFactory factory) {
        if (user == null || factory == null) {
            throw new NullPointerException("User or ScoringStrategyFactory cannot be null");
        }
        ScoringStrategy strategy = factory.createStrategy();
        model.setScoringStrategy(user, strategy);
    }

    /**
     *
     */
    public void sortMessages() {
        model.sortMessages();
    }

    /**
     * Create an example set of users and messages, for testing.
     */
    public void createExampleMessages() {
        //model.createExampleMessages();
    }
    public Y getModel() {
        return model;
    }

}

