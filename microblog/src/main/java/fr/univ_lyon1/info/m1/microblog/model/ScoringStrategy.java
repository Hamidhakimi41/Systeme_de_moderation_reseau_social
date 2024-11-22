package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Map;

public interface ScoringStrategy {
    /**
     * @param messagesData a map of messages and their associated data
     */
    void computeScores(Map<Message, MessageData> messagesData);
}