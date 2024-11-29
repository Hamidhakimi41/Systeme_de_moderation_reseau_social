package fr.univ_lyon1.info.m1.microblog.model.scoring;

/**
 * Factory for the recent message scoring strategy.
 */
public class RecentMessageScoringFactory implements ScoringStrategyFactory {

    /**
     * Create a new recent message scoring strategy.
     *
     * @return the new scoring strategy
     */
    @Override
    public ScoringStrategy createStrategy() {
        return new RecentMessageScoring();
    }
}
