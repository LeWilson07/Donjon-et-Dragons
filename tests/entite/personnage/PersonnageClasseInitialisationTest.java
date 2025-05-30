package entite.personnage;

import classe.*;
import equipement.arme.*;
import equipement.armure.*;
import org.junit.jupiter.api.Test;
import race.Humain;

import static org.junit.jupiter.api.Assertions.*;

class PersonnageClasseInitialisationTest {

    @Test
    void testGuerrierInitialisation() {
        Personnage guerrier = new Personnage("GuerrierTest", new Humain(), new Guerrier(), 'G');

        assertEquals(22, guerrier.getPvInitial());
        assertTrue(guerrier.getArmureEquipe() instanceof CoteDeMaille);

        boolean aEpeeLongue = false;
        boolean aArbalete = false;

        for (var arme : guerrier.getInventaireArme()) {
            if (arme instanceof EpeeLongue) aEpeeLongue = true;
            if (arme instanceof ArbaleteLegere) aArbalete = true;
        }

        assertTrue(aEpeeLongue);
        assertTrue(aArbalete);
    }

    @Test
    void testClercInitialisation() {
        Personnage clerc = new Personnage("ClercTest", new Humain(), new Clerc(), 'C');

        assertEquals(18, clerc.getPvInitial());
        assertTrue(clerc.getArmureEquipe() instanceof ArmureEcaille);

        boolean aMasse = false;
        boolean aArbalete = false;

        for (var arme : clerc.getInventaireArme()) {
            if (arme instanceof MasseArme) aMasse = true;
            if (arme instanceof ArbaleteLegere) aArbalete = true;
        }

        assertTrue(aMasse);
        assertTrue(aArbalete);
    }

    @Test
    void testMagicienInitialisation() {
        Personnage mage = new Personnage("MageTest", new Humain(), new Magicien(), 'M');

        assertEquals(14, mage.getPvInitial());
        // Pas d'armure équipée ici (à confirmer selon ton code)

        boolean aBaton = false;
        boolean aFronde = false;

        for (var arme : mage.getInventaireArme()) {
            if (arme instanceof Baton) aBaton = true;
            if (arme instanceof Fronde) aFronde = true;
        }

        assertTrue(aBaton);
        assertTrue(aFronde);
    }

    @Test
    void testRoublardInitialisation() {
        Personnage roublard = new Personnage("RoublardTest", new Humain(), new Roublard(), 'R');

        assertEquals(18, roublard.getPvInitial());
        // Pas d'armure équipée ici (à confirmer selon ton code)

        boolean aRapiere = false;
        boolean aArcCourt = false;

        for (var arme : roublard.getInventaireArme()) {
            if (arme instanceof Rapiere) aRapiere = true;
            if (arme instanceof ArcCourt) aArcCourt = true;
        }

        assertTrue(aRapiere);
        assertTrue(aArcCourt);
    }

}
