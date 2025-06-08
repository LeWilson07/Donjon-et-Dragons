package entite;

import classe.Clerc;
import entite.personnage.Personnage;
import classe.Classe;
import race.Humain;
import race.Race;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntiteTest {

    @Test
    void testDistance() {
        Race race = new Humain();
        Classe classe = new Clerc();

        Personnage perso = new Personnage("Bob", race, classe);
        perso.setX(2);
        perso.setY(3);

        // Même position
        assertEquals(0, perso.distance(2, 3), "Distance à la même position devrait être 0");

        // Déplacement horizontal
        assertEquals(4, perso.distance(6, 3), "Distance horizontale incorrecte");

        // Déplacement vertical
        assertEquals(2, perso.distance(2, 5), "Distance verticale incorrecte");

        // Déplacement en diagonale (Manhattan distance)
        assertEquals(6, perso.distance(5, 6), "Distance diagonale incorrecte");
    }

    @Test
    void testCoordonneeValide() {
        Race race = new Humain();
        Classe classe = new Clerc();
        Personnage perso = new Personnage("Goku", race, classe);

        int[] resultat = perso.ConvertCoord("A1");

        assertNotNull(resultat, "La conversion ne doit pas être nulle pour une coordonnée valide");
        assertEquals(0, resultat[0], "La colonne A doit correspondre à l'index 0");
        assertEquals(0, resultat[1], "La ligne 1 doit correspondre à l'index 0");
    }

    @Test
    void testCoordonneeTropCourte() {
        Race race = new Humain();
        Classe classe = new Clerc();
        Personnage perso = new Personnage("Mario", race, classe);

        int[] resultat = perso.ConvertCoord("C");

        assertNull(resultat, "La conversion doit retourner null pour une coordonnée trop courte");
    }

    @Test
    void testCoordonneeAvecNombreInvalide() {
        Race race = new Humain();
        Classe classe = new Clerc();
        Personnage perso = new Personnage("Slay", race, classe);

        int[] resultat = perso.ConvertCoord("BXYZ");

        assertNull(resultat, "La conversion doit retourner null si le numéro de ligne est invalide");
    }

    @Test
    void testCoordonneeNull() {
        Race race = new Humain();
        Classe classe = new Clerc();
        Personnage perso = new Personnage("Wiggler", race, classe);

        int[] resultat = perso.ConvertCoord(null);

        assertNull(resultat, "La conversion doit retourner null pour une entrée nulle");
    }
}
