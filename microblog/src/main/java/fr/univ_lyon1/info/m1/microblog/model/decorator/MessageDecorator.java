package fr.univ_lyon1.info.m1.microblog.model.decorator;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import javafx.scene.text.TextFlow;

/**
 * Class representing a message decorator.
 */
public abstract class MessageDecorator extends Message {
    private final Message decoratedMessage;

    /**
     * Constructor.
     *
     * @param decoratedMessage the message to decorate
     */
    public MessageDecorator(final Message decoratedMessage) {
        super(new Builder(decoratedMessage.getContent()));
        this.decoratedMessage = decoratedMessage;
    }

    /**
     * Get the content of the message.
     *
     * @return the content
     */
    @Override
    public String getContent() {
        return decoratedMessage.getContent();
    }

    /**
     * Get the date of the message.
     *
     * @return the date
     */
    @Override
    public TextFlow getTextFlow() {
        return decoratedMessage.getTextFlow();
    }

    /**
     * Get the decorated message.
     *
     * @return the decorated message
     */
    public Message getDecoratedMessage() {
        return decoratedMessage;
    }
}
