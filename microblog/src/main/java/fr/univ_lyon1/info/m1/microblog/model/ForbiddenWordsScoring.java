package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Cette classe étend RecentMessageScoring pour ajouter une règle de notation qui
 * applique une pénalité pour les messages contenant des mots interdits. La pénalité
 * est soustraite du score calculé par la classe parente.
 */
public class ForbiddenWordsScoring extends RecentMessageScoring {

    private static final Set<String> FORBIDDEN_WORDS = new HashSet<>(Arrays.asList(
            "bad", "nana", "agh"));

    /**
     * Calcule les scores pour les messages donnés, en appliquant une pénalité pour les
     * messages contenant des mots interdits. La pénalité est soustraite du score calculé
     * par la classe parente.
     *
     * @param messagesData une map de messages et leurs données associées
     */
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        super.computeScores(messagesData);

        messagesData.forEach((Message m, MessageData d) -> {
            for (String word : d.getWords()) {
                for (String forbiddenWord : FORBIDDEN_WORDS) {
                    Pattern pattern = Pattern.compile(".*" + Pattern.quote(forbiddenWord)
                            + ".*", Pattern.CASE_INSENSITIVE);
                    if (pattern.matcher(word).matches()) {
                        System.out.println("Message contenant un mot interdit");
                        d.setScore(d.getScore() - 5); // Malus de 5 points pour les mots interdits
                        break;
                    }
                }
            }
        });
    }

    /**
     * Retourne une représentation textuelle de cette règle de notation.
     *
     * @return une chaîne de caractères représentant cette règle de notation
     */
    @Override
    public String toString() {
        return "Messages sans mots interdits";
    }
}
