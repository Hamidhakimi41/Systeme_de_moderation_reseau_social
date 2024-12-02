package fr.univ_lyon1.info.m1.microblog.model.decorator;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Class representing a tagged message decorator.
 */
public class TaggedMessageDecorator extends MessageDecorator {
    private final String tag;

    /**
     * Constructor.
     *
     * @param message the message to decorate
     * @param tag the tag to add
     */
    public TaggedMessageDecorator(final Message message, final String tag) {
        super(message);
        this.tag = tag;
    }

    /**
     * Get the text flow of the message.
     *
     * @return the text flow
     */
    @Override
    public TextFlow getTextFlow() {
        TextFlow textFlow = super.getTextFlow();
        textFlow.getChildren().add(new Text("\n #" + tag + " "));
        return textFlow;
    }
}
