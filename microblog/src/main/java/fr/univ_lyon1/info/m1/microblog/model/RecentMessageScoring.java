package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class RecentMessageScoring extends BookmarkScoring {

    /**
     * Calcule les scores pour les messages donnés, en appliquant un bonus pour les messages récents.
     * Le bonus est ajouté au score calculé par la classe parente.
     *
     * @param messagesData une map de messages et leurs données associées
     */
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        super.computeScores(messagesData);

        messagesData.forEach((Message m, MessageData d) -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime messageTime = m.getTimestamp();
            long daysBetween = ChronoUnit.DAYS.between(messageTime, now);
            if (daysBetween < 1) {
                //System.out.println("Message de moins de 24 heures");
                d.setScore(d.getScore() + 2); // Bonus de 2 points pour les messages de moins de 24 heures
            } else if (daysBetween < 7) {
                //System.out.println("Message de moins de 7 jours");
                d.setScore(d.getScore() + 1); // Bonus de 1 point pour les messages de moins de 7 jours
            }
        });
    }

    /**
     * Retourne une représentation textuelle de la classe.
     *
     * @return une chaîne de caractères
     */
    @Override
    public String toString() {
        return " Messages pertinents récents";
    }
}