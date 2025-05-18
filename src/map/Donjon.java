package map;

import equipement.*;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import jeux.De;
import entite.personnage.Personnage;
import entite.monstre.*;

import java.util.ArrayList;
import java.util.List;

public class Donjon {
    //Bon j'ai déjà finis de placer des monstres obstacles et equipement sur les donjons par défaut, faudrait que je rendre le code + propre parce que
    //j'ai essayé bcp de chose et me suis un perdu, toute les fonctions sont peut être pas pertinente mais au moins j'ai un résultat
    //Aussi jvais ptet commencer a commenter bcp les fonctions que j'ai faite précédemment, historie de rendre le code + clair.
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

    private static final int[][][] MONSTRES_POSITIONS = {
            // Config 1 positions (x,y)
            {{3,3}, {7,3}},
            // Config 2 positions
            {{5,5}, {10,10}},
            // Config 3 positions
            {{2,2}, {10,12}}
    };


    private static final Monstre[][] MonstreDefault= {
            //a changer quand on aura établit une bonne liste de monstre
            {new Dragon(0),new Bowser(1)},
            {new Dragon(0),new Bowser(1)},
            {new Dragon(0),new Bowser(1)}
    };
    private int configChoisie = 1;

    public Donjon(int largeur, int hauteur, ModeGeneration mode) {
        grille = new char[hauteur][largeur];
        if (mode == ModeGeneration.AUTO) {
            genererDonjon();
        } else {
            initialiserGrilleVide();
        }
    }

    public void placerMonstres(List<Monstre> monstres) {
        for (Monstre m : monstres) {
            placerEntite(m, 'M');
        }
    }

    private void placerMonstresConfig() {
        Monstre[] monstres = MonstreDefault[configChoisie];
        int[][] positions = MONSTRES_POSITIONS[configChoisie];

        for (int i = 0; i < monstres.length; i++) {
            Monstre m = monstres[i];
            int x = positions[i][0];
            int y = positions[i][1];

            if (!estAccessible(x, y)) {
                continue;
            }

            m.setX(x);
            m.setY(y);
            placerEntiteManuellement(m, x, y, 'M');
        }
    }




    public void initialiserGrilleVide() {
        for (int y = 0; y < grille.length; y++) {
            for (int x = 0; x < grille[0].length; x++) {

                    grille[y][x] = '.';
            }
        }
    }

    public void genererDonjon() {
        initialiserGrilleVide();
        grille[0][0] = '!';

        for (int[] obstacle : CONFIGS_OBSTACLES[configChoisie]) {
            int x = obstacle[0];
            int y = obstacle[1];
            if (x < grille[0].length && y < grille.length) {
                ajouterObstacle(x, y);
            }
        }

        placerMonstresConfig();

        int nbEquipements = 5;
        for (int i = 0; i < nbEquipements; i++) {
            placerEquipementAvecDistance(5);
        }
    }


    public boolean ajouterObstacle(int x, int y) {
        if (grille[y][x] == '.') {
            grille[y][x] = '■';
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

    public void placerEquipementAvecDistance(int distanceMin) {
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

     boolean respecteDistance(int x, int y, int distanceMin) {
        for (ObjetAuSol objet : objetsAuSol) {
            int dx = objet.getX() - x;
            int dy = objet.getY() - y;
            if (Math.sqrt(dx * dx + dy * dy) < distanceMin) {
                return false;
            }
        }
        return true;
    }

    public boolean placerEntiteManuellement(Object entite, int x, int y, char symbole) {
        if (grille[y][x] == '.') {
            grille[y][x] = symbole;
            return true;
        }
        return false;
    }

    public boolean placerObjetAuSol(int x, int y, Equipement e) {
        if (grille[y][x] == '.') {
            grille[y][x] = 'E';
            objetsAuSol.add(new ObjetAuSol(x, y, e));
            return true;
        }
        return false;
    }


    public void afficherDonjon() {
        // En-tête des colonnes
        System.out.print("    ");
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
        return grille[y][x] != '■';
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
