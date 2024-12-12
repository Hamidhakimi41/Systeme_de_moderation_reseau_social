package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoringFactory;

import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * .
 */
public class BookmarkScoringFactoryTest {
    /**
     * .
     */
    @Test
    public  void testStrategieNoNull() {
        BookmarkScoringFactory rmsf = new BookmarkScoringFactory();
        ScoringStrategy st = rmsf.createStrategy();
        assertNotNull(st, "la strategie doit etre non null");





    }
    /**
     * .
     */
    @Test
    public  void testIntanceDeStrategieRecent() {
        BookmarkScoringFactory rmsf = new BookmarkScoringFactory();
        ScoringStrategy st = rmsf.createStrategy();
        assertTrue(st instanceof BookmarkScoring, "elle doit etre instance de "
                + "BookmarkScoring");
    }
}
