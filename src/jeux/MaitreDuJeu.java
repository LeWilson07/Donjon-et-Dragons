package jeux;

import classe.Classe;
import entite.Entite;
import entite.monstre.Monstre;
import entite.personnage.Personnage;
import equipement.arme.Arme;
import equipement.armure.Armure;
import map.Donjon;
import race.*;
import classe.*;
import equipement.arme.*;
import equipement.armure.*;
import entite.monstre.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MaitreDuJeu {
    public MaitreDuJeu() {}

    public void giveStatJoueur(Personnage p){
        String stat = "\n";
        stat += "Vie : "+p.getPv() + "\n";
        stat += "Armure : "+ p.getArmureEquipe()+ "\n";
        stat += "Arme : "+ p.getArmeEquipe()+ "\n";
        stat += "Nom : "+ p.getM_nom()+ "\n";
        stat += "Classe : "+ p.getClass()+ "\n";
        System.out.println( stat );

    }
    public void giveStatMonstre(Monstre m){
        String stat = "\n";
        stat += "Vie : "+m.getPv() + "\n";
        stat += "Espèce : "+ m.getEspece()+ "\n";
        stat += "Portée Attaque : "+ m.getPorteAttaque()+ "\n";
        System.out.println( stat );
    }

    public void DeplacerEntite(Entite e, String c, Donjon d){
        e.SeDeplacer(c, d);
    }

    public boolean AttaqueEntite(Entite e, int nbD, int nbFace){
        boolean attaque = true;
        De de = new De(nbFace, nbD);
        int degat = de.LancerDe();
        e.setPv( e.getPv()-degat);
        if(e.getPv() <= 0){ attaque = false; }
        return attaque;
    }

    public int[] ConvertCoord(String coord) {
        if (coord == null || coord.length() < 2) {
            System.out.println("Coordonnée trop courte ou nulle.");
            return null;
        }

        char lettre = coord.charAt(0);
        if (lettre < 'A' || lettre > 'Z') {
            System.out.println("Lettre invalide : doit être entre A et Z.");
            return null;
        }

        int x = lettre - 'A';
        int y;
        try {
            y = Integer.parseInt(coord.substring(1)) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Format numérique invalide après la lettre.");
            return null;
        }

        return new int[] { x, y };
    }

    public void AjoutObstacle(Donjon d) {
        Scanner scanner = new Scanner(System.in);
        int nbObstacle = 0;

        // Demande le nombre d'obstacles, avec gestion d'erreur simple
        while (true) {
            System.out.println("Combien d'obstacle voulez-vous rajouter ?");
            try {
                nbObstacle = Integer.parseInt(scanner.nextLine());
                if (nbObstacle >= 0) break;
                else System.out.println("Veuillez entrer un nombre positif ou nul.");
            } catch (NumberFormatException e) {
                System.out.println("Erreur : ce n'est pas un nombre valide ! Veuillez en rentrer un autre.");
            }
        }

        for (int i = 0; i < nbObstacle; i++) {
            int[] coord = null;
            while (true) {
                System.out.println("Coordonnée de l'obstacle n°" + (i + 1) + " (ex: A1) :");
                String input = scanner.nextLine().trim();
                coord = ConvertCoord(input);

                if (coord == null || !d.estDansGrille(coord[0], coord[1])) {
                    System.out.println("Coordonnée invalide, merci de recommencer.");
                    continue;
                }

                if (d.getEntiteAt(coord[0], coord[1]) != null || d.estObstacle(coord[0], coord[1] )) {
                    System.out.println("Case déjà occupée, veuillez choisir une autre position.");
                } else {
                    break;
                }
            }
            d.ajouterObstacle(coord[0], coord[1]);
            System.out.println("Obstacle ajouté en " + (char)(coord[0] + 'A') + (coord[1] + 1));
        }
    }




    public Donjon creerDonjonCustom(ArrayList<Personnage> personnagesExistants) {
        Scanner scanner = new Scanner(System.in);
        String coord;
        System.out.println("=== CREATION DU DONJON CUSTOM ===");

        Donjon donjon = new Donjon(20, 20);

        // 1) Ajouter obstacles
        System.out.println("Ajout des obstacles :");
        AjoutObstacle(donjon);
        donjon.afficherDonjon();

        // 2) Positionner les joueurs existants
        System.out.println("Positionnement des joueurs existants :");
        for (Personnage joueur : personnagesExistants) {
            System.out.println("Positionnez le joueur " + joueur.getM_nom() + " (symbole " + joueur.getSymbole() + ")");
            int[] pos;
            while (true) {
                System.out.print("Coordonnées de départ (ex: A1) : ");
                coord = scanner.nextLine().trim();
                pos = ConvertCoord(coord);
                if (pos != null && donjon.estDansGrille(pos[0], pos[1]) &&
                        donjon.getEntiteAt(pos[0], pos[1]) == null &&
                        !donjon.estObstacle(pos[0], pos[1]))
                {
                    break;
                }
                System.out.println("Coordonnées invalides ou déjà occupées. Merci de recommencer.");
            }
            joueur.setX(pos[0]);
            joueur.setY(pos[1]);
            donjon.ajouterEntite(joueur);
            donjon.afficherDonjon();
            System.out.println("Joueur " + joueur.getM_nom() + " positionné en " + coord);
        }

        // 3) Ajouter monstres
        int nbMonstres = lireEntier(scanner, "Combien de monstres voulez-vous créer ?", 0, 50);

        for (int i = 0; i < nbMonstres; i++) {
            System.out.println("Création du monstre #" + (i + 1));

            int choixMonstre;
            while (true) {
                System.out.println("Choisissez un type de monstre : 1) Dragon  2) Bowser");
                choixMonstre = lireEntier(scanner, null, 1, 2);
                if (choixMonstre == 1 || choixMonstre == 2) break;
                System.out.println("Choix invalide. Réessayez.");
            }
            Monstre monstre = creerMonstreParChoix(choixMonstre, i);

            int[] posM;
            while (true) {
                System.out.print("Coordonnées du monstre (ex: A1) : ");
                coord = scanner.nextLine().trim();
                posM = ConvertCoord(coord);
                if (posM != null && donjon.estDansGrille(posM[0], posM[1]) &&
                        donjon.getEntiteAt(posM[0], posM[1]) == null &&
                        !donjon.estObstacle(posM[0], posM[1]))
                {
                    break;
                }
                System.out.println("Coordonnées invalides ou déjà occupées. Merci de recommencer.");
            }
            monstre.setX(posM[0]);
            monstre.setY(posM[1]);
            donjon.ajouterEntite(monstre);
            donjon.afficherDonjon();
            System.out.println("Monstre " + monstre.getEspece() + " ajouté au donjon en " + coord);
        }

        // 4) Ajouter équipement (armes)
        String[] armesDispo = {"EpeeLongue", "Baton", "ArbaleteLegere"};
        int nbArmes = lireEntier(scanner, "Combien d'armes voulez-vous ajouter ?", 0, 50);

        for (int i = 0; i < nbArmes; i++) {
            System.out.println("Création de l'arme #" + (i + 1));
            int choixArme;
            while (true) {
                System.out.println("Choisissez une arme :");
                for (int j = 0; j < armesDispo.length; j++) {
                    System.out.println((j + 1) + ") " + armesDispo[j]);
                }
                choixArme = lireEntier(scanner, null, 1, armesDispo.length);
                if (choixArme >= 1 && choixArme <= armesDispo.length) break;
                System.out.println("Choix invalide. Réessayez.");
            }
            Arme arme = creerArmeParNom(armesDispo[choixArme - 1]);

            int[] posA;
            while (true) {
                System.out.print("Coordonnées où placer l'arme (ex: A1) : ");
                coord = scanner.nextLine().trim();
                posA = ConvertCoord(coord);
                if (posA != null && donjon.estDansGrille(posA[0], posA[1]) &&
                        donjon.getEntiteAt(posA[0], posA[1]) == null &&
                        !donjon.estObstacle(posA[0], posA[1]))
                {
                    break;
                }
                System.out.println("Coordonnées invalides ou déjà occupées. Merci de recommencer.");
            }
            donjon.ajouterArme(arme, posA[0], posA[1]);
            donjon.afficherDonjon();
            System.out.println("Arme " + arme.getNom() + " ajoutée au donjon en " + coord);

        }

        // 5) Ajouter armures
        String[] armuresDispo = {"CoteDeMaille", "ArmureEcaille"};
        int nbArmures = lireEntier(scanner, "Combien d'armures voulez-vous ajouter ?", 0, 50);

        for (int i = 0; i < nbArmures; i++) {
            System.out.println("Création de l'armure #" + (i + 1));
            int choixArmure;
            while (true) {
                System.out.println("Choisissez une armure :");
                for (int j = 0; j < armuresDispo.length; j++) {
                    System.out.println((j + 1) + ") " + armuresDispo[j]);
                }
                choixArmure = lireEntier(scanner, null, 1, armuresDispo.length);
                if (choixArmure >= 1 && choixArmure <= armuresDispo.length) break;
                System.out.println("Choix invalide. Réessayez.");
            }
            Armure armure = creerArmureParNom(armuresDispo[choixArmure - 1]);

            int[] posAr;
            while (true) {
                System.out.print("Coordonnées où placer l'armure (ex: A1) : ");
                coord = scanner.nextLine().trim();
                posAr = ConvertCoord(coord);
                if (posAr != null && donjon.estDansGrille(posAr[0], posAr[1]) &&
                        donjon.getEntiteAt(posAr[0], posAr[1]) == null &&
                        !donjon.estObstacle(posAr[0], posAr[1]))
                {
                    break;
                }
                System.out.println("Coordonnées invalides ou déjà occupées. Merci de recommencer.");
            }
            donjon.ajouterArmure(armure, posAr[0], posAr[1]);
            System.out.println("Armure " + armure.getNom() + " ajoutée au donjon en " + coord);
        }

        System.out.println("Donjon custom créé avec succès !");
        return donjon;
    }

    // Méthode pour lire un entier dans un intervalle donné avec gestion d'erreurs
    private int lireEntier(Scanner scanner, String message, int min, int max) {
        int valeur = -1;
        boolean valide = false;
        while (!valide) {
            if (message != null) System.out.println(message);
            String line = scanner.nextLine().trim();
            try {
                valeur = Integer.parseInt(line);
                if (valeur >= min && valeur <= max) {
                    valide = true;
                } else {
                    System.out.println("Veuillez entrer un nombre entre " + min + " et " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre entier.");
            }
        }
        return valeur;
    }

    public ArrayList<Personnage> creerPersonnages(Scanner scanner) {
        ArrayList<Personnage> personnages = new ArrayList<>();

        System.out.println("Combien de joueurs voulez-vous créer ?");
        int nbJoueurs = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < nbJoueurs; i++) {
            System.out.println("Création du joueur #" + (i + 1));
            System.out.print("Nom : ");
            String nom = scanner.nextLine();

            System.out.println("Choisissez la race : 1) Elfe  2) Halfling  3) Humain  4) Nain");
            int choixRace = Integer.parseInt(scanner.nextLine());
            Race race = getRaceByChoice(choixRace);

            System.out.println("Choisissez la classe : 1) Clerc  2) Guerrier  3) Magicien  4) Nain");
            int choixClasse = Integer.parseInt(scanner.nextLine());
            Classe classe = getClasseByChoice(choixClasse);

            System.out.print("Symbole (un caractère) : ");
            char sym = scanner.nextLine().charAt(0);

            Personnage joueur = new Personnage(nom, race, classe, sym);
            personnages.add(joueur);
        }

        return personnages;
    }

    // Méthode pour récupérer Race selon choix
    private Race getRaceByChoice(int choix) {
        switch (choix) {
            case 1: return new Elfe();
            case 2: return new Halflin();
            case 3: return new Humain();
            case 4: return new Nain();
            default:
                System.out.println("Choix race invalide, Humain par défaut.");
                return new Humain();
        }
    }

    // Méthode pour récupérer Classe selon choix
    private Classe getClasseByChoice(int choix) {
        switch (choix) {
            case 1: return new Clerc();
            case 2: return new Guerrier();
            case 3: return new Magicien();
            case 4: return new Roublard();
            default:
                System.out.println("Choix classe invalide, Guerrier par défaut.");
                return new Guerrier();
        }
    }

    // Méthode pour créer arme par nom
    private Arme creerArmeParNom(String nom) {
        switch (nom) {
            case "EpeeLongue":
                return new EpeeLongue();
            case "Baton":
                return new Baton();
            case "ArbaleteLegere":
                return new ArbaleteLegere();
            default:
                System.out.println("Arme inconnue, Baton par défaut.");
                return new Baton();
        }
    }

    // Méthode pour créer armure par nom
    private Armure creerArmureParNom(String nom) {
        switch (nom) {
            case "CoteDeMaille":
                return new CoteDeMaille();
            case "ArmureEcaille":
                return new ArmureEcaille();
            default:
                System.out.println("Armure inconnue, ArmureEcaille par défaut.");
                return new ArmureEcaille();
        }
    }

    private Monstre creerMonstreParChoix(int choix,int numero) {

        switch (choix) {
            case 1:
                return new Dragon(numero);
            case 2:
                return new Bowser(numero);
            default:
                System.out.println("Choix invalide, un Dragon est créé par défaut.");
                return new Dragon(numero);
        }
    }



}
