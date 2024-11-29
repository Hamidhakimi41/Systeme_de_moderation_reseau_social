package fr.univ_lyon1.info.m1.microblog.model.scoring;


/**
 * Factory for the bookmark scoring strategy.
 */
public class BookmarkScoringFactory implements ScoringStrategyFactory {

    /**
     * Create a new bookmark scoring strategy.
     *
     * @return the new strategy
     */
    @Override
    public ScoringStrategy createStrategy() {
        return new BookmarkScoring();
    }
}
