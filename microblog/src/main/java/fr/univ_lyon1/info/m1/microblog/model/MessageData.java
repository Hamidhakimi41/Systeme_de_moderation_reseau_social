package fr.univ_lyon1.info.m1.microblog.model;

//import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Piece of data associated to a message for a particular user.
 */
public class MessageData {
    /**
     * Represents the data associated with a message.
     */
    private boolean isBookmarked = false;
    private int score = -1;
    private Set<String> words = new HashSet<>();



    /**
     * Returns the set of words associated with the message.
     *
     * @return the set of words
     */
    public Set<String> getWords() {
        return words;
    }

    /**
     * Sets the set of words associated with the message.
     *
     * @param words the set of words
     */
    public void setWords(final Set<String> words) {
        this.words = words;
    }

    /**
     * Returns the score of the message.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the message.
     *
     * @param score the score
     */
    public void setScore(final int score) {
        this.score = score;
    }



    /**
     * Returns whether the message is bookmarked or not.
     *
     * @return true if the message is bookmarked, false otherwise
     */
    public boolean isBookmarked() {
        return isBookmarked;
    }

    /**
     * Sets whether the message is bookmarked or not.
     *
     * @param bookmarked true if the message is bookmarked, false otherwise
     */
    public void setBookmarked(final boolean bookmarked) {
        this.isBookmarked = bookmarked;
    }

    /**
     * Compare two Messages metadata. Suitable for sorting.
     */
    public int compare(final MessageData rightData) {
        try {
            if (isBookmarked() || rightData.isBookmarked()) {
                if (!isBookmarked() && rightData.isBookmarked()) {
                    return -1;
                } else if (isBookmarked() && !isBookmarked()) {
                    return 1;

                } else {
                    return 0;
                }

            } else {
                int scoreLeft = getScore();
                int scoreRight = rightData.getScore();
                if (scoreLeft < scoreRight) {
                    return -1;
                } else if (scoreLeft == scoreRight) {
                    return 0;
                } else {
                    return 1;
                }
            }
        } catch (Exception e) {
            throw new  AssertionError("cas no couvert dans la comparaison");

        }
    }
}
