package map;

import entite.Entite;
import entite.monstre.Bowser;
import entite.monstre.Dragon;
import entite.monstre.Monstre;
import equipement.Equipement;
import equipement.TypeEquipement;
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
        DONJON3,
        CUSTOM
    }

    private String[][] m_grille;
    private List<ObjetAuSol> m_objetsAuSol = new ArrayList<>();
    private List<Obstacle> m_obstacles = new ArrayList<>();
    //ptit reminder :pour la boucle du tour, dcp juste une boucle sur personnage et monstre pis bam ça compare initiative non pour savoir ? 
    private List<Monstre> m_monstres = new ArrayList<>();
    private List<Personnage> m_personnages;
    private List<Entite> m_entites;

    private int m_largeur;
    private int m_hauteur;

    private static final String[] CONTEXTES = {
            "Donjon 1 : Le donjon en forme de T avec ses dangers.",
            "Donjon 2 : Un donjon carré rempli de mystères.",
            "Donjon 3 : Un long couloir en L où l'aventure vous attend."
    };

    private int indexDonjon = 0;

    public List<Entite> getEntites() {
        return m_entites;
    }

    public Donjon(int largeur, int hauteur) {
        this.m_largeur = largeur;
        this.m_hauteur = hauteur;
        this.m_entites = new ArrayList<>();
        m_grille = new String[hauteur][largeur];
        initialiserGrilleVide();
    }

    public void setPersonnages(ArrayList<Personnage> listP) {
        m_personnages = listP;
    }

    public void setEntites(ArrayList<Entite> listE) {
        m_entites = listE;
    }

    private void initialiserGrilleVide() {
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                m_grille[y][x] = ".";
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


        for (int x = 4; x <= 10; x++) {
            if (x != 7) { // trou d'entrée
                d.ajouterObstacle(x, 4);
            }
        }
        for (int x = 4; x <= 10; x++) {
            d.ajouterObstacle(x, 10); // mur bas
        }
        for (int y = 5; y <= 9; y++) {
            d.ajouterObstacle(4, y);  // mur gauche
            d.ajouterObstacle(10, y); // mur droit
        }


        // Équipement au centre
        d.ajouterEquipement(new EpeeLongue(), 7, 7); // Centre de la salle

        d.ajouterMonstre(new Bowser(1),1,1);

        return d;
    }







    private static Donjon creerDonjon2() {
        Donjon d = new Donjon(20, 20);
        d.indexDonjon = 1;

        // Croix centrale (5x5)
        for (int x = 8; x <= 12; x++) {
            d.ajouterObstacle(x, 10); // Ligne horizontale
        }
        for (int y = 8; y <= 12; y++) {
            d.ajouterObstacle(10, y); // Ligne verticale
        }

        // 4 salles carrées simples dans les coins (3x3)
        int[][] coins = {{2,2}, {2,16}, {16,2}, {16,16}};
        for (int[] coin : coins) {
            int xStart = coin[0], yStart = coin[1];
            for (int x = xStart; x <= xStart+2; x++) {
                d.ajouterObstacle(x, yStart);
            }
            for (int y = yStart; y <= yStart+2; y++) {
                d.ajouterObstacle(xStart, y);
                d.ajouterObstacle(xStart+2, y);
            }
        }

        d.ajouterObstacle(10, 7);
        d.ajouterObstacle(10, 13);
        d.ajouterObstacle(7, 10);
        d.ajouterObstacle(13, 10);

        d.ajouterEquipement(new EpeeLongue(), 3, 3);
        d.ajouterEquipement(new CoteDeMaille(), 17, 3);

        // Monstres
        d.ajouterMonstre(new Dragon(1), 17, 17); // Centre
        d.ajouterMonstre(new Bowser(2), 3, 17);     // Coin bas-droit

        return d;
    }



    private static Donjon creerDonjon3() {
        Donjon d = new Donjon(25, 25);
        d.indexDonjon = 2;

        // Carré extérieur (bords)
        for (int x = 2; x <= 22; x++) {
            d.ajouterObstacle(x, 2);   // Mur haut
            d.ajouterObstacle(x, 22);  // Mur bas
        }
        for (int y = 2; y <= 22; y++) {
            d.ajouterObstacle(2, y);   // Mur gauche
            d.ajouterObstacle(22, y);  // Mur droit
        }

        // Carré intérieur (12x12)
        for (int x = 7; x <= 17; x++) {
            d.ajouterObstacle(x, 7);   // Mur haut
            d.ajouterObstacle(x, 17);  // Mur bas
        }
        for (int y = 7; y <= 17; y++) {
            d.ajouterObstacle(7, y);   // Mur gauche
            d.ajouterObstacle(17, y);  // Mur droit
        }

        // 4 piliers centraux (3x3)
        for (int x = 11; x <= 13; x++) {
            for (int y = 11; y <= 13; y++) {
                if (x == 12 || y == 12) continue; // Vide au centre
                d.ajouterObstacle(x, y);
            }
        }

        // Équipements
        d.ajouterEquipement(new EpeeLongue(), 12, 5);   // Nord
        d.ajouterEquipement(new CoteDeMaille(), 19, 12); // Est

        // Monstres
        d.ajouterMonstre(new Dragon(2), 12, 12); // Centre
        d.ajouterMonstre(new Bowser(1), 4, 4);   // Coin haut-gauche
        d.ajouterMonstre(new Bowser(1), 20, 20); // Coin bas-droit

        return d;
    }


    public void ajouterObstacle(int x, int y) {
        if (estDansGrille(x, y) && m_grille[y][x].equals(".")) {
            m_grille[y][x] = "■";
            m_obstacles.add(new Obstacle(x, y));
        }
    }

    public boolean estObstacle(int x, int y) {
        return m_grille[x][y].equals("■");
    }

    public boolean estEquipement(int x, int y) {
        return m_grille[x][y].equals("E");
    }

    private void ajouterEquipement(Equipement e, int x, int y) {
        if (estDansGrille(x, y) && m_grille[y][x].equals(".")) {
            m_grille[y][x] = "E";
            m_objetsAuSol.add(new ObjetAuSol(x, y, e));
        }
    }

    public boolean estCaseLibre(int x, int y) {
        return estDansGrille(x, y) && m_grille[y][x].equals(".");
    }

    public void ajouterArme(Arme arme, int x, int y) {
        ajouterEquipement(arme, x, y);
    }

    public void ajouterArmure(Armure armure, int x, int y) {
        ajouterEquipement(armure, x, y);
    }

    public boolean ajouterEntite(Entite e) {
        if (m_entites == null) m_entites = new ArrayList<>();
        if (m_personnages == null) m_personnages = new ArrayList<>();

        int x = e.getX();
        int y = e.getY();

        if (!estDansGrille(x, y) || !m_grille[y][x].equals(".")) {
            return false;
        }

        if (e.estUnPersonnage()) {
            m_personnages.add((Personnage) e);
        } else {
            m_monstres.add((Monstre) e);
        }
        m_entites.add(e);

        m_grille[y][x] = String.valueOf(e.getSymbole());
        return true;
    }

    private void ajouterMonstre(Monstre m, int x, int y) {
        if (estDansGrille(x, y) && m_grille[y][x].equals(".")) {
            m_grille[y][x] = String.valueOf(m.getSymbole());
            m.setX(x);
            m.setY(y);
            m_monstres.add(m);
        }
    }

    public void placerEntite(Entite entite, String symbole) {
        int x, y;
        boolean place = false;
        Random rnd = new Random();
        while (!place) {
            x = rnd.nextInt(m_grille[0].length - 2) + 1;
            y = rnd.nextInt(m_grille.length - 2) + 1;
            if (m_grille[y][x].equals(".")) {
                m_grille[y][x] = String.valueOf(symbole);
                entite.setX(x);
                entite.setY(y);
                place = true;
            }
        }
    }

    public boolean estDansGrille(int x, int y) {
        return x >= 0 && x < m_largeur && y >= 0 && y < m_hauteur;
    }

    public void placerJoueurs(List<Personnage> personnages) {
        for (Personnage p : personnages) {
            placerEntite(p, p.getSymbole());
        }
    }

    public void afficherDonjon() {
        int cellWidth = 4; // Largeur fixe : 3 caractères + 1 espace

        // Affichage de l'en-tête de colonnes
        System.out.print("    ");
        for (int x = 0; x < m_largeur; x++) {
            char col = (char) ('A' + x);
            System.out.printf("%-" + cellWidth + "s", col);
        }
        System.out.println();

        // Ligne supérieure du cadre
        System.out.print("   *");
        for (int x = 0; x < m_largeur; x++) {
            System.out.print("-".repeat(cellWidth));
        }
        System.out.println("*");

        // Affichage du contenu ligne par ligne
        for (int y = 0; y < m_hauteur; y++) {
            System.out.printf("%2d |", y + 1);  // Numéro de ligne
            for (int x = 0; x < m_largeur; x++) {
                String contenu = m_grille[y][x];
                // Forcer à 3 caractères max, pour garder la largeur constante
                if (contenu.length() > 3) contenu = contenu.substring(0, 3);
                System.out.printf("%-" + cellWidth + "s", contenu);
            }
            System.out.println("|");
        }

        // Ligne inférieure du cadre
        System.out.print("   *");
        for (int x = 0; x < m_largeur; x++) {
            System.out.print("-".repeat(cellWidth));
        }
        System.out.println("*");
    }


    public List<Obstacle> getObstacles() {
        return m_obstacles;
    }

    public List<ObjetAuSol> getObjetsAuSol() {
        return m_objetsAuSol;
    }

    public List<Monstre> getMonstres() {
        return m_monstres;
    }

    public String[][] getGrille() {
        return m_grille;
    }

    public void setGrille(String[][] grille) {
        this.m_grille = grille;
    }

    public String getContexte() {
        if (indexDonjon >= 0 && indexDonjon < CONTEXTES.length) {
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

    public void enleverEntite(Entite e) {
        int x = e.getX();
        int y = e.getY();
        if (estDansGrille(x, y)) {
            // Vérifie s'il y a un objet au sol à cet emplacement
            boolean objetPresent = false;
            for (ObjetAuSol o : m_objetsAuSol) {
                if (o.getX() == x && o.getY() == y) {
                    objetPresent = true;
                    break;
                }
            }
            // Si oui, on remet "E", sinon "."
            m_grille[y][x] = objetPresent ? "E" : ".";
        }

        m_entites.remove(e);
        if (e.estUnPersonnage()) {
            m_personnages.remove((Personnage) e);
        } else {
            m_monstres.remove((Monstre) e);
        }
    }


    public Arme getArmeAt(int x, int y) {
        for (ObjetAuSol o : m_objetsAuSol) {
            if (o.getX() == x && o.getY() == y && o.estTypeEquipement(TypeEquipement.ARME)) {
                return (Arme) o.getEquipement();
            }
        }
        return null;
    }

    public void retirerArme(Arme arme) {
        m_objetsAuSol.removeIf(o -> o.getEquipement() == arme);
    }

    public Armure getArmureAt(int x, int y) {
        for (ObjetAuSol o : m_objetsAuSol) {
            if (o.getX() == x && o.getY() == y && o.estTypeEquipement(TypeEquipement.ARMURE)) {
                return (Armure) o.getEquipement();
            }
        }
        return null;
    }

    public void retirerArmure(Armure armure) {
        m_objetsAuSol.removeIf(o -> o.getEquipement() == armure);
    }
}
