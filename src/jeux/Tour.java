package jeux;

import entite.Entite;
import entite.monstre.Monstre;
import entite.personnage.Personnage;
import equipement.arme.Arme;
import map.Donjon;

import java.util.*;

public class Tour {
    private List<Entite> entities;
    private Donjon donjon;
    private ArrayList<Personnage> m_personnage;
    private MaitreDuJeu mj;

    public Tour(List<Entite> entities, Donjon donjon, ArrayList<Personnage> personnages) {
        this.entities = entities;
        this.donjon = donjon;
        this.m_personnage = personnages;
        this.mj = new MaitreDuJeu();
    }

    public void start() {
        rollInitiative();

        Scanner scanner = new Scanner(System.in);
        List<Entite> toRemove = new ArrayList<>();

        while (!checkGameEnd()) {
            for (Entite entity : new ArrayList<>(entities)) {
                if (!entity.estVivant()) continue;

                donjon.afficherDonjon();

                if (entity.estUnPersonnage()) {
                    Personnage joueur = (Personnage) entity;
                    System.out.println("\n---------- Tour de " + joueur.getRaceNom() + " : " + joueur.getM_nom() + " ----------");

                    // Affichage des stats du personnage
                    System.out.println("PV : " + joueur.getPv() + " | Force : " + joueur.getForce() + " | Dext : " + joueur.getDexterite() + " | Vit : " + joueur.getVitesse());

                    int actionsRestantes = 3;

                    while (actionsRestantes > 0) {
                        System.out.println("\n" + joueur.getM_nom() + ", il vous reste " + actionsRestantes + " action(s).");
                        if(joueur.isClerc()){
                            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, guerison <case>, ramasser, inventaire, pass");
                        }
                        else if(joueur.isMagicien()){
                            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, guerison <case>, boogieWoogie <case> <case>, am <case>, ramasser, inventaire, pass");
                        }
                        else{
                            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, ramasser, inventaire, pass");
                        }
                        System.out.print("  $ ");

                        String input = scanner.nextLine().trim();
                        String[] parts = input.split(" ");
                        String cmd = parts[0].toLowerCase();

                        switch (cmd) {
                            case "dep":
                                if (parts.length < 2) {
                                    System.out.println("Précisez la case où vous déplacer.");
                                    break;
                                }
                                joueur.SeDeplacer(parts[1], donjon);
                                donjon.afficherDonjon();
                                actionsRestantes--;
                                break;

                            case "att":
                                if (parts.length < 2) {
                                    System.out.println("Précisez la case cible pour attaquer.");
                                    break;
                                }
                                int[] c = joueur.ConvertCoord(parts[1]);
                                if (c == null) {
                                    System.out.println("Coordonnée invalide.");
                                    break;
                                }
                                Entite cible = donjon.getEntiteAt(c[0], c[1]);
                                if (cible != null && cible.estVivant()) {
                                    joueur.attaquer(cible);
                                    gererMort(cible, toRemove);
                                    actionsRestantes--;
                                } else {
                                    System.out.println("Aucune cible valide à cette position.");
                                }

                                break;

                            case "equip":
                                if (parts.length < 2) {
                                    System.out.println("Précisez l'index de l'objet à équiper.");
                                    break;
                                }
                                try {
                                    int index = Integer.parseInt(parts[1]) - 1; // 1-based input pour l'utilisateur
                                    boolean equipeOk = joueur.equiperObjetParIndex(index);
                                    if (equipeOk) {
                                        actionsRestantes--;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("L'index doit être un nombre.");
                                }
                                break;


                            case "ramasser":
                                joueur.ramasserObjetAuSol(joueur.getX(), joueur.getY(), donjon);
                                actionsRestantes--;
                                break;

                            case "inventaire":
                                joueur.Afficheinventaire();
                                // Inventaire ne consomme pas d'action, mais tu peux changer ça si tu veux
                                break;

                            case "pass":
                                System.out.println(joueur.getM_nom() + " passe son tour.");
                                actionsRestantes = 0;
                                break;

                            case "a":
                                if(joueur.isMagicien()){
                                    if(parts.length != 2) {
                                        System.out.println("ERREUR : Nombre d'argument invalide");
                                    }
                                    else if(joueur.ConvertCoord(parts[1]) == null){
                                        System.out.println("ERREUR : argument invalide");
                                    }
                                    else{
                                        int[] co = joueur.ConvertCoord(parts[1]);

                                        if(donjon.getPersonnageAt(co[0], co[1]) == null){
                                            System.out.println("Aucune Entité trouvée sur une de vos coordonnées !!\nVeuillez réssayer ou changer d'action.\n");
                                        }
                                        else{
                                            donjon.getPersonnageAt(co[0], co[1]).Afficheinventaire();

                                            System.out.print("Entrez le numéro de l'arme que vous souhaitez améliorer : ");
                                            String num = scanner.nextLine();

                                            joueur.getSort().ArmeMagique(donjon.getPersonnageAt(co[0], co[1]), Integer.parseInt(num)-1);
                                            actionsRestantes--;

                                            System.out.print("\nBonus Arme : " + donjon.getPersonnageAt(co[0], co[1]).getBonusArme() + "\n");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("Vous ne pouvez pas lancer ce sort !!");
                                }
                                break;

                            case "bw":
                                if(joueur.isMagicien()){
                                    if(parts.length != 3) {
                                        System.out.println("ERREUR : Nombre d'argument invalide");
                                    }
                                    else if(joueur.ConvertCoord(parts[1]) == null || joueur.ConvertCoord(parts[2]) == null){
                                        System.out.println("ERREUR : argument invalide");
                                    }
                                    else{
                                        int[] co1 = joueur.ConvertCoord(parts[1]);
                                        int[] co2 = joueur.ConvertCoord(parts[2]);

                                        if(donjon.getEntiteAt(co1[0], co1[1])!= null && donjon.getEntiteAt(co2[0], co2[1]) != null){
                                            joueur.getSort().BoogieWoogie(donjon.getEntiteAt(co1[0], co1[1]), donjon.getEntiteAt(co2[0], co2[1]), donjon);
                                            actionsRestantes--;
                                            // Réafficher la map après utilisation du sort
                                            donjon.afficherDonjon();
                                        }
                                        else{
                                            System.out.println("Aucune Entité trouvée sur une de vos coordonnées !!\nVeuillez réssayer ou changer d'action.\n");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("Vous ne pouvez pas lancer ce sort !!");
                                }
                                break;

                            case "guerison":
                                if(joueur.isMagicien() || joueur.isClerc()){
                                    if(parts.length != 2) {
                                        System.out.println("ERREUR : Nombre d'argument invalide");
                                    }
                                    else if(joueur.ConvertCoord(parts[1]) == null){
                                        System.out.println("ERREUR : argument invalide");
                                    }
                                    else{
                                        int[] co = joueur.ConvertCoord(parts[1]);

                                        if(donjon.getPersonnageAt(co[0], co[1])!= null){
                                            System.out.println("\nVie : "+donjon.getPersonnageAt(co[0], co[1]).getPv());
                                            joueur.getSort().Guerison(donjon.getPersonnageAt(co[0], co[1]));
                                            System.out.println("\nVie : "+donjon.getPersonnageAt(co[0], co[1]).getPv());
                                            actionsRestantes--;
                                        }
                                        else{
                                            System.out.println("Aucune Entité trouvée sur une de vos coordonnées !!\nVeuillez réssayer ou changer d'action.\n");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("Vous ne pouvez pas lancer ce sort !!");
                                }
                                break;

                            default:
                                System.out.println("Commande invalide.");
                                break;
                        }

                        if (!joueur.estVivant() || checkGameEnd()) break;
                    }

                } else {
                    // Monstre : gestion par Maître du Jeu
                    Monstre monstre = (Monstre) entity;
                    System.out.println("\n========== Tour du Monstre ==========");
                    System.out.println("Monstre '" + entity.getSymbole() + "' en [" + entity.getX() + "," + entity.getY() + "]");
                    System.out.println("PV : " + entity.getPv() + " | Force : " + entity.getForce() + " | Dext : " + entity.getDexterite() + " | Vit : " + entity.getVitesse());

                    int actionsRestantes = 3;

                    while(actionsRestantes > 0){
                        System.out.println(monstre.getEspece() + ", il vous reste " + actionsRestantes + " action(s).");
                        System.out.println("Commandes possibles : dep <case>, att <case>, pass");
                        System.out.print("  $ ");

                        String input = scanner.nextLine().trim();
                        String[] parts = input.split(" ");
                        String cmd = parts[0].toLowerCase();

                        switch (cmd) {
                            case "att":
                                if (parts.length < 2) {
                                    System.out.println("Précisez la case cible pour attaquer.");
                                    break;
                                }
                                int[] c = monstre.ConvertCoord(parts[1]);
                                Entite cible = donjon.getEntiteAt(c[0], c[1]);
                                if (cible != null && cible.estVivant()) {
                                    monstre.attaquer(cible);
                                    actionsRestantes--;
                                } else {
                                    System.out.println("Aucune cible valide à cette position.");
                                }
                                break;

                            case "dep":
                                if (parts.length < 2) {
                                    System.out.println("Précisez la case où vous déplacer.");
                                    break;
                                }
                                monstre.SeDeplacer(parts[1], donjon);
                                donjon.afficherDonjon();
                                actionsRestantes--;
                                break;

                            case "pass":
                                System.out.println(monstre.getEspece() + " passe son tour.");
                                actionsRestantes = 0;
                                break;

                            default:
                                System.out.println("Commande invalide.");
                                break;
                        }
                    }
                }

                if (checkGameEnd()) break;
            }
            entities.removeAll(toRemove);
            toRemove.clear();
            tourMaitreDuJeu(scanner, toRemove);
            if (checkGameEnd()) break;

        }
        afficherMessageFinJeu();
    }


    private void gererMort(Entite e, List<Entite> toRemove) {
        if (!e.estVivant() && !toRemove.contains(e)) {
            if (!e.estUnPersonnage()) {
                Monstre monstre = (Monstre) e;
                System.out.println(monstre.getEspece() + " n°" + monstre.getNum() + " est mort !");
            }
            donjon.enleverEntite(e);
            toRemove.add(e);
        }
    }

    private void tourMaitreDuJeu(Scanner scanner, List<Entite> toRemove) {
        System.out.println("\n---------- Tour du Maître du Jeu ----------");
        boolean tourMJ = true;

        while (tourMJ && !checkGameEnd()) {
            System.out.println("Commandes possibles : ajouterObstacle, attaquer, deplacer, donnerInfos, pass");
            System.out.print("  $ ");

            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ");
            String cmd = parts[0].toLowerCase();

            switch (cmd) {
                case "ajouterobstacle":
                    mj.AjoutObstacle(donjon);
                    donjon.afficherDonjon();
                    break;

                case "attaquer":
                    if (parts.length < 2) {
                        System.out.println("Précisez la case cible (ex : A1).");
                        break;
                    }
                    int[] coAtt = mj.ConvertCoord(parts[1]);
                    Entite cible = donjon.getEntiteAt(coAtt[0], coAtt[1]);
                    if (cible == null || !cible.estVivant()) {
                        System.out.println("Aucune entité vivante à cette position.");
                        break;
                    }
                    System.out.print("Nombre de dés : ");
                    int nbDes;
                    int nbFaces;
                    try {
                        nbDes = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nombre de faces par dé : ");
                        nbFaces = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Entrée invalide, annulation de l'attaque.");
                        break;
                    }
                    boolean encoreVivant = mj.AttaqueEntite(cible, nbDes, nbFaces);
                    if (!encoreVivant) {
                        System.out.println("L'entité a été tuée !");
                        gererMort(cible, toRemove);
                    } else {
                        System.out.println("L'entité est encore vivante.");
                    }
                    break;

                case "deplacer":
                    if (parts.length < 3) {
                        System.out.println("Précisez la case de l'entité à déplacer et la case cible (ex : A1 B2).");
                        break;
                    }
                    int[] coDepl = mj.ConvertCoord(parts[1]);
                    Entite entiteDepl = donjon.getEntiteAt(coDepl[0], coDepl[1]);
                    if (entiteDepl == null || !entiteDepl.estVivant()) {
                        System.out.println("Aucune entité vivante à la position " + parts[1]);
                        break;
                    }
                    mj.DeplacerEntite(entiteDepl, parts[2], donjon);
                    donjon.afficherDonjon();
                    break;

                case "donnerinfos":
                    if (parts.length < 2) {
                        System.out.println("Précisez la case de l'entité (ex : A1).");
                        break;
                    }
                    int[] coInfo = mj.ConvertCoord(parts[1]);
                    Entite entiteInfo = donjon.getEntiteAt(coInfo[0], coInfo[1]);
                    if (entiteInfo == null) {
                        System.out.println("Aucune entité à cette position.");
                        break;
                    }
                    if (entiteInfo.estUnPersonnage()) {
                        mj.giveStatJoueur((Personnage) entiteInfo);
                    } else {
                        mj.giveStatMonstre((Monstre) entiteInfo);
                    }
                    break;

                case "pass":
                    System.out.println("Le Maître du Jeu passe son tour.");
                    tourMJ = false;
                    break;

                default:
                    System.out.println("Commande invalide.");
                    break;
            }
        }
    }


    private void rollInitiative() {
        Random rnd = new Random();
        for (Entite entity : entities) {
            int initiativeRoll = rnd.nextInt(20) + 1;
            entity.setInitiative(initiativeRoll + entity.getInitiative());
        }
        Collections.sort(entities, Comparator.comparingInt(Entite::getInitiative).reversed());
    }

    private boolean checkGameEnd() {
        boolean monstreEncoreVivant = false;
        for (Entite monstre : donjon.getMonstres()) {
            if (monstre.estVivant()) {
                monstreEncoreVivant = true;
                break;
            }
        }
        if (!monstreEncoreVivant) {
            return true; // Victoire
        }

        for (Entite entity : entities) {
            if (entity.estUnPersonnage() && !entity.estVivant()) {
                return true; // Défaite
            }
        }
        return false; // Partie continue
    }

    private void afficherMessageFinJeu() {
        boolean monstreEncoreVivant = false;
        for (Entite monstre : donjon.getMonstres()) {
            if (monstre.estVivant()) {
                monstreEncoreVivant = true;
                break;
            }
        }
        if (!monstreEncoreVivant) {
            System.out.println("Tous les monstres ont été éliminés ! Victoire !");
            return;
        }

        for (Entite entity : entities) {
            if (entity.estUnPersonnage() && !entity.estVivant()) {
                Personnage p = (Personnage) entity;
                System.out.println(p.getM_nom() + " a été tué ! Défaite !");
                return;
            }
        }
    }



}
