package fr.univ_lyon1.info.m1.microblog.model.scoring;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;

import java.util.Map;

/**
 * Interface for scoring strategies.
 */
public interface ScoringStrategy {
    /**
     * @param messagesData a map of messages and their associated data
     */
    void computeScores(Map<Message, MessageData> messagesData);
}
