package map;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import jeux.De;
import java.util.ArrayList;
import java.util.List;

public class Donjon {

    private char[][] grille;
    private List<ObjetAuSol> objetsAuSol = new ArrayList<>();

    private static final int[][][] CONFIGS_OBSTACLES = {
            // Config 1: T simple
            {{5,3}, {5,4}, {5,5}, {4,4}, {6,4}},

            // Config 2: Carré central
            {{6,4}, {6,5}, {7,4}, {7,5},{11,9},{11,10},{12,9},{12,10}},

            // Config 3: L
            {{4,3}, {4,4}, {4,5}, {5,5},{14,13},{14,14},{14,15},{15,15}}
    };
    private int configChoisie = 2;
    public Donjon(int largeur, int hauteur) {
        grille = new char[hauteur][largeur];
        genererDonjon();
    }

    public void genererDonjon() {
        // Initialisation de la grille
        for (int y = 0; y < grille.length; y++) {
            for (int x = 0; x < grille[0].length; x++) {
                if (x == 0 || y == 0 || x == grille[0].length - 1 || y == grille.length - 1) {
                    grille[y][x] = '■'; // Murs extérieurs
                } else {
                    grille[y][x] = '.'; // Sol vide
                }
            }
        }

        for (int[] obstacle : CONFIGS_OBSTACLES[configChoisie]) {
            int x = obstacle[0];
            int y = obstacle[1];
            if (x < grille[0].length && y < grille.length) {
                grille[y][x] = '┼';
            }
        }


        int nbEquipements = 7;
        for (int i = 0; i < nbEquipements; i++) {
            placerEquipementAvecDistance(5); // minimum 5 cases d'écart
        }
    }


    private void placerEquipementAvecDistance(int distanceMin) {
        int x, y;
        boolean valide = false;

        while (!valide) {
            x = (int)(Math.random() * (grille[0].length - 2)) + 1;
            y = (int)(Math.random() * (grille.length - 2)) + 1;

            if (grille[y][x] == '.' && respecteDistance(x, y, distanceMin)) {
                grille[y][x] = 'E';

                Equipement e;
                if (Math.random() < 0.5) {
                    e = new Arme("Épée rouillée", new De(1,6), 1, false);
                } else {
                    e = new Armure("Cotte de cuir", 3,false);
                }

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
        // En-tête des colonnes (A, B, C, ...)
        System.out.print("   "); // espace pour l'alignement avec les numéros de ligne
        for (int x = 0; x < grille[0].length; x++) {
            char colonne = (char) ('A' + x);
            System.out.print(" " + colonne + " ");
        }
        System.out.println();

        // Ligne supérieure avec *
        System.out.print("   *");
        for (int x = 0; x < grille[0].length; x++) {
            System.out.print("---");
        }
        System.out.println("*");

        // Corps du donjon
        for (int y = 0; y < grille.length; y++) {
            // Numéro de ligne avec alignement
            if (y + 1 < 10) System.out.print(" " + (y + 1) + " ");
            else System.out.print((y + 1) + " ");

            System.out.print("|");

            for (int x = 0; x < grille[0].length; x++) {
                System.out.print(" " + grille[y][x] + " ");
            }
            System.out.println("|");
        }

        // Ligne inférieure avec *
        System.out.print("   *");
        for (int x = 0; x < grille[0].length; x++) {
            System.out.print("---");
        }
        System.out.println("*");
    }


    public boolean estAccessible(int x, int y){
        return grille[y][x] != '#';
    }

    public char getCase(int x, int y) {
        return grille[y][x];
    }

    public void setCase(int x, int y, char valeur) {
        grille[y][x] = valeur;
    }
}
