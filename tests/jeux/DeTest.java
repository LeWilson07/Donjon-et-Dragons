package jeux;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeTest {

    @Test
    void lancerDe() {
        De de = new De(6, 3); // 3 dés à 6 faces
        int result = de.LancerDe();
        assertTrue(result >= 3 && result <= 18, "Le lancer doit être entre 3 et 18");
    }

    @Test
    void unDeQuatre() {
        De de = new De();
        int result = de.UnDeQuatre();
        assertTrue(result >= 1 && result <= 4, "Un dé à 4 faces doit retourner entre 1 et 4");
    }

    @Test
    void unDeSix() {
        De de = new De();
        int result = de.UnDeSix();
        assertTrue(result >= 1 && result <= 6, "Un dé à 6 faces doit retourner entre 1 et 6");
    }

    @Test
    void unDeHuit() {
        De de = new De();
        int result = de.UnDeHuit();
        assertTrue(result >= 1 && result <= 8, "Un dé à 8 faces doit retourner entre 1 et 8");
    }

    @Test
    void unDeDix() {
        De de = new De();
        int result = de.UnDeDix();
        assertTrue(result >= 1 && result <= 10, "Un dé à 10 faces doit retourner entre 1 et 10");
    }

    @Test
    void unDeVingt() {
        De de = new De();
        int result = de.UnDeVingt();
        assertTrue(result >= 1 && result <= 20, "Un dé à 20 faces doit retourner entre 1 et 20");
    }

    @Test
    void quatreDeQuatre() {
        De de = new De();
        int result = de.QuatreDeQuatre();
        assertTrue(result >= 4 && result <= 16, "4 dés à 4 faces doivent retourner entre 4 et 16");
    }
}
