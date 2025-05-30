package map;

import entite.Entite;
import entite.monstre.Bowser;
import entite.monstre.Dragon;
import entite.monstre.Monstre;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.EpeeLongue;
import equipement.armure.Armure;
import equipement.armure.CoteDeMaille;
import entite.personnage.Personnage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Donjon {
    public enum TypeDonjon {
        DONJON1,
        DONJON2,
        DONJON3
    }

    private char[][] grille;
    private List<ObjetAuSol> objetsAuSol = new ArrayList<>();
    private List<Obstacle> obstacles = new ArrayList<>();
    private List<Monstre> monstres = new ArrayList<>();
    private List<Personnage> m_personnages;
    private List<Entite> m_entites;

    private int largeur;
    private int hauteur;

    private static final String[] CONTEXTES = {
            "Donjon 1 : Le donjon en forme de T avec ses dangers.",
            "Donjon 2 : Un donjon carré rempli de mystères.",
            "Donjon 3 : Un long couloir en L où l'aventure vous attend."
    };

    private int indexDonjon = 0;

    private Donjon(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        grille = new char[hauteur][largeur];
        initialiserGrilleVide();
    }

    public void setPersonnages(ArrayList<Personnage> listP) {
        m_personnages = listP;
    }
    public void setEntites(ArrayList<Entite> listE) {
        m_entites = listE;
    }

    private void initialiserGrilleVide() {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                grille[y][x] = '.';
            }
        }
    }

    public static Donjon creerDonjonPredefini(TypeDonjon type) {
        Donjon donjon;
        switch (type) {
            case DONJON1:
                donjon = creerDonjon1();
                break;
            case DONJON2:
                donjon = creerDonjon2();
                break;
            case DONJON3:
                donjon = creerDonjon3();
                break;
            default:
                donjon = creerDonjon1();
        }
        return donjon;
    }

    private static Donjon creerDonjon1() {
        Donjon d = new Donjon(15, 15);
        d.indexDonjon = 0;


        int[][] obstaclesCoords = {{5,3}, {5,4}, {5,5}, {4,4}, {6,4}};
        for (int[] c : obstaclesCoords) {
            d.ajouterObstacle(c[0], c[1]);
        }

        d.ajouterEquipement(new EpeeLongue(), 3, 3);
        d.ajouterEquipement(new CoteDeMaille(), 7, 2);
        d.ajouterEquipement(new CoteDeMaille(), 6, 6);

        //d.ajouterMonstre(new Monstre(1,0,0,0,10,5,1,2,'M'), 2, 7);
        //d.ajouterMonstre(new Monstre(2,0,0,0,10,5,1,2,'M'), 8, 8);
        //d.ajouterMonstre(new Monstre(3,0,0,0,10,5,1,2,'M'), 5, 8);

        return d;
    }

    private static Donjon creerDonjon2() {
        Donjon d = new Donjon(20, 20);
        d.indexDonjon = 1;

        int[][] obstaclesCoords = {{6,4}, {6,5}, {7,4}, {7,5}, {11,9}, {11,10}, {12,9}, {12,10}};
        for (int[] c : obstaclesCoords) {
            d.ajouterObstacle(c[0], c[1]);
        }

        d.ajouterEquipement(new CoteDeMaille(), 10, 8);
        d.ajouterEquipement(new EpeeLongue(), 13, 12);
        d.ajouterEquipement(new EpeeLongue(), 5, 6);

        d.ajouterMonstre(new Bowser(1), 8, 7);
        d.ajouterMonstre(new Dragon(2), 14, 11);
        d.ajouterMonstre(new Bowser(3), 13, 15);

        return d;
    }

    private static Donjon creerDonjon3() {
        Donjon d = new Donjon(20, 20);
        d.indexDonjon = 2;

        int[][] obstaclesCoords = {{4,3}, {4,4}, {4,5}, {5,5}, {14,13}, {14,14}, {14,15}, {15,15}};
        for (int[] c : obstaclesCoords) {
            d.ajouterObstacle(c[0], c[1]);
        }

        d.ajouterEquipement(new EpeeLongue(), 2, 2);
        d.ajouterEquipement(new CoteDeMaille(), 10, 13);
        d.ajouterEquipement(new CoteDeMaille(), 16, 16);

        //d.ajouterMonstre(new Monstre(1,0,0,0,11,6,1,3,'M'), 7, 8);
        //d.ajouterMonstre(new Monstre(2,0,0,0,9,5,1,2,'M'), 12, 10);
        //d.ajouterMonstre(new Monstre(3,0,0,0,14,7,1,3,'M'), 17, 17);

        return d;
    }

    public void ajouterObstacle(int x, int y) {
        if (estDansGrille(x, y) && grille[y][x] == '.') {
            grille[y][x] = '┼';
            obstacles.add(new Obstacle(x, y));
        }
    }

    private void ajouterEquipement(Equipement e, int x, int y) {
        if (estDansGrille(x, y) && grille[y][x] == '.') {
            grille[y][x] = 'E';
            objetsAuSol.add(new ObjetAuSol(x, y, e));
        }
    }

    private void ajouterMonstre(Monstre m, int x, int y) {
        if (estDansGrille(x, y) && grille[y][x] == '.') {
            grille[y][x] = m.getSymbole();
            m.setX(x);
            m.setY(y);
            monstres.add(m);
        }
    }

    public void placerEntite(Entite entite, char symbole) {
        int x, y;
        boolean place = false;
        Random rnd = new Random();
        while (!place) {
            x = rnd.nextInt(grille[0].length - 2) + 1;
            y = rnd.nextInt(grille.length - 2) + 1;
            if (grille[y][x] == '.') {
                grille[y][x] = symbole;
                entite.setX(x);
                entite.setY(y);
                place = true;
            }
        }
    }
    public boolean estDansGrille(int x, int y) {
        return x >= 0 && x < largeur && y >= 0 && y < hauteur;
    }

    public void placerJoueurs(List<Personnage> personnages) {
        for (Personnage p : personnages) {
            placerEntite(p, p.getSymbole());
        }
    }
    public void afficherDonjon() {
        System.out.print("    ");
        for (int x = 0; x < largeur; x++) {
            char col = (char)('A' + x);
            System.out.print(" " + col + " ");
        }
        System.out.println();

        System.out.print("   *");
        for (int x = 0; x < largeur; x++) {
            System.out.print("---");
        }
        System.out.println("*");

        for (int y = 0; y < hauteur; y++) {
            System.out.printf("%2d |", y + 1);
            for (int x = 0; x < largeur; x++) {
                System.out.print(" " + grille[y][x] + " ");
            }
            System.out.println("|");
        }

        System.out.print("   *");
        for (int x = 0; x < largeur; x++) {
            System.out.print("---");
        }
        System.out.println("*");
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<ObjetAuSol> getObjetsAuSol() {
        return objetsAuSol;
    }

    public List<Monstre> getMonstres() {
        return monstres;
    }

    public char[][] getGrille() {
        return grille;
    }

    public void setGrille(char[][] grille) {
        this.grille = grille;
    }

    public String getContexte() {
        if(indexDonjon >= 0 && indexDonjon < CONTEXTES.length) {
            return CONTEXTES[indexDonjon];
        }
        return "Donjon inexistant.";
    }

    public Entite getEntiteAt(int x, int y) {
        for (Entite e : m_entites) {
            if (e.getX() == x && e.getY() == y && e.estVivant()) {
                return e;
            }
        }
        return null;
    }
    public Personnage getPersonnageAt(int x, int y) {
        for (Personnage p : m_personnages) {
            if (p.getX() == x && p.getY() == y && p.estVivant()) {
                return p;
            }
        }
        return null;
    }

    // Retourne une Arme présente sur la case (x,y) si elle existe, sinon null
    public Arme getArmeAt(int x, int y) {
        for (ObjetAuSol o : objetsAuSol) {
            if (o.getX() == x && o.getY() == y && o.getEquipement() instanceof Arme) {
                return (Arme) o.getEquipement();
            }
        }
        return null;
    }

    // Supprime l'arme du sol
    public void retirerArme(Arme arme) {
        objetsAuSol.removeIf(o -> o.getEquipement() == arme);
    }

    // Retourne une Armure présente sur la case (x,y) si elle existe, sinon null
    public Armure getArmureAt(int x, int y) {
        for (ObjetAuSol o : objetsAuSol) {
            if (o.getX() == x && o.getY() == y && o.getEquipement() instanceof Armure) {
                return (Armure) o.getEquipement();
            }
        }
        return null;
    }

    // Supprime l'armure du sol
    public void retirerArmure(Armure armure) {
        objetsAuSol.removeIf(o -> o.getEquipement() == armure);
    }


}

