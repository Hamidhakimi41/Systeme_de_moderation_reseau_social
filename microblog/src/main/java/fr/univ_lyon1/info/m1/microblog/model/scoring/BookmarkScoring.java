package fr.univ_lyon1.info.m1.microblog.model.scoring;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Scoring of messages based on bookmarks.
 */
public class BookmarkScoring implements ScoringStrategy {

    /**
     * Calcule les scores pour les messages, en appliquant un bonus pour les messages récents.
     * Le bonus est ajouté au score calculé par la classe parente.
     *
     * @param messagesData une map de messages et leurs données associées
     */
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        Set<Message> messages = messagesData.keySet();
        Set<String> bookmarkedWords = new HashSet<>();

        messages.forEach((Message m) -> {
            MessageData d = messagesData.get(m);
            String[] w = m.getContent().toLowerCase().split("[^\\p{Alpha}]+");
            Set<String> words = new HashSet<>();
            words.addAll(Arrays.asList(w)); // For some reason, Set.of refuses duplicates
            d.setWords(words);
            if (d.isBookmarked()) {
                bookmarkedWords.addAll(words);
            }
        });

        // On calcule le score de chaque message en fonction du nombre de mots bookmarkés
        messages.forEach((Message m) -> {
            MessageData d = messagesData.get(m);
            int score = 0;
            for (String w : d.getWords()) {
                if (bookmarkedWords.contains(w)) {
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
