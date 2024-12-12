package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.RecentMessageScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.RecentMessageScoringFactory;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * .
 */
public class RecentMessageScoringFactoryTest {
    /**
     * .
     */
    @Test
    public  void testStrategieNoNull() {
        RecentMessageScoringFactory rmsf = new RecentMessageScoringFactory();
        ScoringStrategy st = rmsf.createStrategy();
        assertNotNull(st, "la strategie doit etre non null");





    }

    /**
     * .
     */
    @Test
    public  void testIntanceDeStrategieRecent() {
        RecentMessageScoringFactory rmsf = new RecentMessageScoringFactory();
        ScoringStrategy st = rmsf.createStrategy();
        assertTrue(st instanceof RecentMessageScoring, "elle doit etre instance de "
                + "RecentMessageScoringFactory");
    }


}
