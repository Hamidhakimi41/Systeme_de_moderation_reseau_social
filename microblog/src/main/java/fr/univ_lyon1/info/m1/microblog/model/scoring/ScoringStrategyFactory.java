package fr.univ_lyon1.info.m1.microblog.model.scoring;

/**
 * Interface for the scoring strategy factory.
 */
public interface ScoringStrategyFactory {
    /**
     * Create a scoring strategy.
     *
     * @return the scoring strategy
     */
    ScoringStrategy createStrategy();
}
