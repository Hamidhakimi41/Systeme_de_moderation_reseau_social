package fr.univ_lyon1.info.m1.microblog.model.decorator;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Class representing a message with a mention.
 */
public class MentionedMessageDecorator extends MessageDecorator {
    private final String mention;

    /**
     * Constructor.
     *
     * @param message the message to decorate
     * @param mention the mention to add
     */
    public MentionedMessageDecorator(final Message message, final String mention) {
        super(message);
        this.mention = mention;
    }

    /**
     * Get the text flow of the message.
     *
     * @return the text flow
     */
    @Override
    public TextFlow getTextFlow() {
        TextFlow textFlow = super.getTextFlow();
        textFlow.getChildren().add(new Text("\n @" + mention + " "));
        return textFlow;
    }
}
