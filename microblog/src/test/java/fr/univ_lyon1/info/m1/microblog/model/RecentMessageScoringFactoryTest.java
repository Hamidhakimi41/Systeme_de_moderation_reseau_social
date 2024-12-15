package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.RecentMessageScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.RecentMessageScoringFactory;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/**
 * classe de test de la classe RecentMessageScoringFactory.
 */
public class RecentMessageScoringFactoryTest {

    /**
     * test de la creation de la strategie.
     */
    @Test
    public  void testStrategieNoNull() {
        RecentMessageScoringFactory rmsf = new RecentMessageScoringFactory();
        ScoringStrategy st = rmsf.createStrategy();
        assertNotNull(st, "la strategie doit etre non null");
    }

    /**
     * test de l'instance de la strategie.
     */
    @Test
    public  void testIntanceDeStrategieRecent() {
        RecentMessageScoringFactory rmsf = new RecentMessageScoringFactory();
        ScoringStrategy st = rmsf.createStrategy();
        assertTrue(st instanceof RecentMessageScoring, "elle doit etre instance de "
                + "RecentMessageScoringFactory");
    }

    /**
     * test de la creation de deux instances de strategie.
     */
    @Test
    public void testDeuxInstancesDifferentes() {
        RecentMessageScoringFactory rmsf = new RecentMessageScoringFactory();
        ScoringStrategy st1 = rmsf.createStrategy();
        ScoringStrategy st2 = rmsf.createStrategy();
        assertNotSame(st1, st2, "les deux instances doivent être différentes");
    }

    /**
     * test de la methode createStrategy pour verifier qu'elle ne leve pas d'exception.
     */
    @Test
    public void testCreateStrategyNoException() {
        RecentMessageScoringFactory rmsf = new RecentMessageScoringFactory();
        assertDoesNotThrow(() -> rmsf.createStrategy(),
                "la methode createStrategy ne doit pas lever d'exception");
    }

}
