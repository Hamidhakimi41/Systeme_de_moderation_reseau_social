package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.ForbiddenWordsScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ForbiddenWordsScoringFactory;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * .
 */
public class ForbiddenWordsScoringFactoryTest {
    /**
     * .
     */
    @Test
    public void testLaValeurNonNull() {
        ForbiddenWordsScoringFactory forbiddenWords = new ForbiddenWordsScoringFactory();
        ScoringStrategy scoringStrategy = forbiddenWords.createStrategy();
        assertNotNull(scoringStrategy, " scoringStrategy ne dois pas etre null");

    }


    /**
     * .
     */
    @Test
    public void testUneinstanceDeForbiddenWordsScoring() {
        ForbiddenWordsScoringFactory forbiddenWords = new ForbiddenWordsScoringFactory();
        ScoringStrategy scoringStrategy = forbiddenWords.createStrategy();
        assertInstanceOf(ForbiddenWordsScoring.class, scoringStrategy);

    }

}

