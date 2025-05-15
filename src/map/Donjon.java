package map;

import equipement.*;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import jeux.De;
import entite.personnage.Personnage;

import java.util.ArrayList;
import java.util.List;

public class Donjon {
    public enum ModeGeneration {
        AUTO,
        MANUEL
    }

    private char[][] grille;
    private List<ObjetAuSol> objetsAuSol = new ArrayList<>();
    private List<Obstacle> obstacles = new ArrayList<>();

    private static final int[][][] CONFIGS_OBSTACLES = {
            // Config 1: T simple
            {{5,3}, {5,4}, {5,5}, {4,4}, {6,4}},
            // Config 2: Carré central
            {{6,4}, {6,5}, {7,4}, {7,5},{11,9},{11,10},{12,9},{12,10}},
            // Config 3: L
            {{4,3}, {4,4}, {4,5}, {5,5},{14,13},{14,14},{14,15},{15,15}}
    };
    private int configChoisie = 0;

    public Donjon(int largeur, int hauteur, ModeGeneration mode) {
        grille = new char[hauteur][largeur];
        if (mode == ModeGeneration.AUTO) {
            genererDonjon();
        } else {
            initialiserGrilleVide(); // Manuel
        }
    }

    public void initialiserGrilleVide() {
        for (int y = 0; y < grille.length; y++) {
            for (int x = 0; x < grille[0].length; x++) {
                if (x == 0 || y == 0 || x == grille[0].length - 1 || y == grille.length - 1) {
                    grille[y][x] = '■';
                } else {
                    grille[y][x] = '.';
                }
            }
        }
    }

    public void genererDonjon() {
        initialiserGrilleVide();

        for (int[] obstacle : CONFIGS_OBSTACLES[configChoisie]) {
            int x = obstacle[0];
            int y = obstacle[1];
            if (x < grille[0].length && y < grille.length) {
                ajouterObstacle(x, y);
            }
        }

        int nbEquipements = 7;
        for (int i = 0; i < nbEquipements; i++) {
            placerEquipementAvecDistance(5); // min 5 cases d'écart
        }
    }

    public boolean ajouterObstacle(int x, int y) {
        if (grille[y][x] == '.') {
            grille[y][x] = '┼';
            obstacles.add(new Obstacle(x, y));
            return true;
        }
        return false;
    }

    public boolean estObstacle(int x, int y) {
        for (Obstacle o : obstacles) {
            if (o.getX() == x && o.getY() == y) return true;
        }
        return false;
    }

    private void placerEquipementAvecDistance(int distanceMin) {
        int x, y;
        boolean valide = false;

        while (!valide) {
            x = (int)(Math.random() * (grille[0].length - 2)) + 1;
            y = (int)(Math.random() * (grille.length - 2)) + 1;

            if (grille[y][x] == '.' && respecteDistance(x, y, distanceMin)) {
                grille[y][x] = 'E';

                Equipement e = Math.random() < 0.5 ?
                        new Arme("Épée rouillée", new De(1,6), 1, false) :
                        new Armure("Cotte de cuir", 3,false);

                objetsAuSol.add(new ObjetAuSol(x, y, e));
                valide = true;
            }
        }
    }

    private boolean respecteDistance(int x, int y, int distanceMin) {
        for (ObjetAuSol objet : objetsAuSol) {
            int dx = objet.getX() - x;
            int dy = objet.getY() - y;
            if (Math.sqrt(dx * dx + dy * dy) < distanceMin) {
                return false;
            }
        }
        return true;
    }

    public void afficherDonjon() {
        // En-tête des colonnes
        System.out.print("   ");
        for (int x = 0; x < grille[0].length; x++) {
            char colonne = (char) ('A' + x);
            System.out.print(" " + colonne + " ");
        }
        System.out.println();

        // Ligne supérieure
        System.out.print("   *");
        for (int x = 0; x < grille[0].length; x++) {
            System.out.print("---");
        }
        System.out.println("*");

        // Corps du donjon
        for (int y = 0; y < grille.length; y++) {
            System.out.printf("%2d |", y + 1);
            for (int x = 0; x < grille[0].length; x++) {
                System.out.print(" " + grille[y][x] + " ");
            }
            System.out.println("|");
        }

        // Ligne inférieure
        System.out.print("   *");
        for (int x = 0; x < grille[0].length; x++) {
            System.out.print("---");
        }
        System.out.println("*");
    }

    private void placerEntite(Object entite, char symbole) {
        int x, y;
        boolean place = false;

        while (!place) {
            x = (int)(Math.random() * (grille[0].length - 2)) + 1;
            y = (int)(Math.random() * (grille.length - 2)) + 1;

            if (grille[y][x] == '.') {
                grille[y][x] = symbole;
                // Ajouter position à l'entité si nécessaire
                place = true;
            }
        }
    }

    public void placerJoueurs(List<Personnage> personnages) {
        for (Personnage p: personnages) {
            placerEntite(p, 'J');
        }
    }

    public boolean estAccessible(int x, int y){
        return grille[y][x] != '■' && grille[y][x] != '┼';
    }

    public char getCase(int x, int y) {
        return grille[y][x];
    }

    public void setCase(int x, int y, char valeur) {
        grille[y][x] = valeur;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
}
