package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.model.scoring.ForbiddenWordsScoring;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ForbiddenWordsScoringFactory;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * classe de test de la classe ForbiddenWordsScoringFactory.
 */
public class ForbiddenWordsScoringFactoryTest {
    /**
     * test de la création d'une instance de ForbiddenWordsScoringFactory.
     */
    @Test
    public void testLaValeurNonNull() {
        ForbiddenWordsScoringFactory forbiddenWords = new ForbiddenWordsScoringFactory();
        ScoringStrategy scoringStrategy = forbiddenWords.createStrategy();
        assertNotNull(scoringStrategy, " scoringStrategy ne dois pas etre null");

    }


    /**
     * test que l'instance créée est bien de type ForbiddenWordsScoring.
     */
    @Test
    public void testUneinstanceDeForbiddenWordsScoring() {
        ForbiddenWordsScoringFactory forbiddenWords = new ForbiddenWordsScoringFactory();
        ScoringStrategy scoringStrategy = forbiddenWords.createStrategy();
        assertInstanceOf(ForbiddenWordsScoring.class, scoringStrategy);
    }

    /**
     * test que les instances créées sont différentes.
     */
    @Test
    public void testNouvelleInstance() {
        ForbiddenWordsScoringFactory forbiddenWords = new ForbiddenWordsScoringFactory();
        ScoringStrategy scoringStrategy1 = forbiddenWords.createStrategy();
        ScoringStrategy scoringStrategy2 = forbiddenWords.createStrategy();
        assertNotSame(scoringStrategy1, scoringStrategy2,
                "Les instances doivent être différentes");
    }

    /**
     * test le comportement en cas d'erreur.
     */
    @Test
    public void testComportementEnCasDErreur() {
        ForbiddenWordsScoringFactory forbiddenWords = new ForbiddenWordsScoringFactory();
        try {
            ScoringStrategy scoringStrategy = forbiddenWords.createStrategy();
            assertNotNull(scoringStrategy,
                    " scoringStrategy ne doit pas être null même en cas d'erreur");
        } catch (Exception e) {
            fail("La méthode createStrategy ne doit pas lancer d'exception");
        }
    }
}

