package fr.univ_lyon1.info.m1.microblog.model.scoring;


/**
 * Factory for the forbidden words scoring strategy.
 */
public class ForbiddenWordsScoringFactory implements ScoringStrategyFactory {

    /**
     * Create a new ForbiddenWordsScoring strategy.
     *
     * @return the new ForbiddenWordsScoring strategy
     */
    @Override
    public ScoringStrategy createStrategy() {
        return new ForbiddenWordsScoring();
    }
}
