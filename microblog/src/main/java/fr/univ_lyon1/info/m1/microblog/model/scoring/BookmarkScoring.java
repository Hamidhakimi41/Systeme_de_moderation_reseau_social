package fr.univ_lyon1.info.m1.microblog.model.scoring;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Scoring of messages based on bookmarks.
 */
public class BookmarkScoring implements ScoringStrategy {

    /**
     * Calcule les scores pour les messages, en appliquant un bonus pour les messages bookmarkés.
     *
     * @param messagesData une map de messages et leurs données associées
     */
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        Set<String> bookmarkedWords = new HashSet<>();

        // Collect all words from bookmarked messages
        messagesData.forEach((Message m, MessageData d) -> {
            String[] wordsArray = m.getContent().toLowerCase().split("[^\\p{Alpha}]+");
            Set<String> words = new HashSet<>();
            for (String word : wordsArray) {
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
            d.setWords(words);
            if (d.isBookmarked()) {
                bookmarkedWords.addAll(words);
            }
        });

        // Calculate the score for each message based on the number of bookmarked words
        messagesData.forEach((Message m, MessageData d) -> {
            int score = 0;
            for (String word : d.getWords()) {
                if (bookmarkedWords.contains(word)) {
                    score++;
                }
            }
            d.setScore(score);

        });
    }

    /**
     * Retourne une représentation textuelle de la classe.
     *
     * @return une chaîne de caractères
     */
    @Override
    public String toString() {
        return "Messages, ordre chronologique(pertinents)";
    }

}
