package entite.personnage;

import classe.Guerrier;
import equipement.arme.EpeeLongue;
import equipement.armure.CoteDeMaille;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import race.Humain;

import static org.junit.jupiter.api.Assertions.*;

class PersonnageTest {

    private Personnage personnage;
    private EpeeLongue epee;
    private CoteDeMaille armure;

    @BeforeEach
    void setUp() {
        personnage = new Personnage("Testo", new Humain(), new Guerrier(), 'T');
        epee = new EpeeLongue();
        armure = new CoteDeMaille();

        personnage.ramasser(epee);
        personnage.ramasser(armure);
    }

    @Test
    void testNomEtSymbole() {
        assertEquals("Testo", personnage.getM_nom());
        assertEquals('T', personnage.getSymbole());
    }

    @Test
    void testRaceEtClasse() {
        assertEquals("Humain", personnage.getRaceNom());
    }

    @Test
    void testVie() {
        int vieInitiale = personnage.getPv();
        personnage.setPv(vieInitiale - 10);
        assertEquals(vieInitiale - 10, personnage.getPv());
    }

    @Test
    void testPositionXY() {
        personnage.setX(5);
        personnage.setY(3);
        assertEquals(5, personnage.getX());
        assertEquals(3, personnage.getY());
    }

    @Test
    void testEquipementArmeEtArmure() {
        personnage.EquiperArme(epee);
        personnage.EquiperArmure(armure);

        assertEquals(epee, personnage.getArmeEquipe());
        assertEquals(armure, personnage.getArmureEquipe());
    }

    @Test
    void testBonusArmeEtDegats() {
        personnage.EquiperArme(epee);
        assertEquals(epee.getBonus(), personnage.getBonusArme());
        assertEquals(epee.getM_degat(), personnage.getDegatArmee());
    }

    @Test
    void testEstVivant() {
        personnage.setPv(1);
        assertTrue(personnage.estVivant());
        personnage.setPv(0);
        assertFalse(personnage.estVivant());
    }

    @Test
    void testInventaireArmeEtArmure() {
        assertTrue(personnage.getInventaireArme().contains(epee));
        assertTrue(personnage.getInventaireArmure().contains(armure));
    }
}
