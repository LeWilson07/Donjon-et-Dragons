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

    public Tour(List<Entite> entities, Donjon donjon, ArrayList<Personnage> personnages) {
        this.entities = entities;
        this.donjon = donjon;
        this.m_personnage = personnages;
    }

    public void start() {
        rollInitiative();

        Scanner scanner = new Scanner(System.in);

        while (!checkGameEnd()) {
            for (Entite entity : entities) {
                if (!entity.estVivant()) continue;

                donjon.afficherDonjon();

                if (entity.estUnPersonnage()) {
                    Personnage joueur = (Personnage) entity;
                    System.out.println("\n---------- Tour de " + joueur.getRaceNom() + " : " + joueur.getM_nom() + " ----------");

                    int actionsRestantes = 3;

                    while (actionsRestantes > 0) {
                        System.out.println("\n" + joueur.getM_nom() + ", il vous reste " + actionsRestantes + " action(s).");
                        if(joueur.isClerc()){
                            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, guerison <case>, ramasser, pass");
                        }
                        else if(joueur.isMagicien()){
                            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, guerison <case>,boogieWoogie <case> <case>, Arme Magique ( 'am <case>'), ramasser, pass");
                        }
                        else{
                            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, ramasser, pass");
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
                                Entite cible = donjon.getEntiteAt(c[0], c[1]);
                                if (cible != null && cible.estVivant()) {
                                    joueur.attaquer(cible);
                                    actionsRestantes--;
                                } else {
                                    System.out.println("Aucune cible valide à cette position.");
                                }
                                break;

                            case "equip":
                                if (parts.length < 2) {
                                    System.out.println("Précisez le nom de l'objet à équiper.");
                                    break;
                                }
                                boolean equipeOk = joueur.equiperObjetParNom(parts[1]);
                                if (equipeOk) {
                                    System.out.println(parts[1] + " équipé.");
                                    actionsRestantes--;
                                } else {
                                    System.out.println("Impossible d'équiper cet objet.");
                                }
                                break;

                            case "ramasser":
                                joueur.ramasserObjetAuSol(joueur.getX(),joueur.getY(),donjon);
                                actionsRestantes--;
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

                                            Scanner scann = new Scanner(System.in);
                                            System.out.print("Entrez le numéro de l'arme que vous souhaitez améliorer : ");
                                            String num = scanner.nextLine();

                                            joueur.getSort().ArmeMagique( donjon.getPersonnageAt(co[0], co[1]), Integer.parseInt(num)-1 );
                                            actionsRestantes--;

                                            System.out.print( "\nBonus Arme : " +donjon.getPersonnageAt(co[0], co[1]).getBonusArme()+ "\n");
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
                                                joueur.getSort().BoogieWoogie( donjon.getEntiteAt(co1[0], co1[1]), donjon.getEntiteAt(co2[0], co2[1]), donjon);
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
                    // Monstre : gestion par Maître du Jeu dcp
                    Monstre monstre = (Monstre)entity;
                    System.out.println("\n========== Tour du Monstre ==========");
                    System.out.println("Monstre '" + entity.getSymbole() + "' en [" + entity.getX() + "," + entity.getY() + "]");
                    System.out.println("PV : " + entity.getPv() + " | Force : " + entity.getForce() + " | Dext : " + entity.getDexterite() + " | Vit : " + entity.getVitesse());
                    System.out.println("Le Maître du Jeu prend la main. Appuyez sur [Entrée] pour continuer.");

                    int actionsRestantes = 3;
                    while(actionsRestantes >0){

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
        // 1. Vérifie s'il reste des monstres vivants
        boolean monstreEncoreVivant = false;
        for (Entite monstre : donjon.getMonstres()) {
            if (monstre.estVivant()) {
                monstreEncoreVivant = true;
                break;
            }
        }
        // Si aucun monstre vivant => victoire
        if (!monstreEncoreVivant) {
            System.out.println("Tous les monstres ont été éliminés ! Victoire !");
            return true;
        }

        // 2. Vérifie si un personnage est mort => défaite immédiate
        for (Entite entity : entities) {
            if (entity.estUnPersonnage() && !entity.estVivant()) {
                Personnage p = (Personnage) entity;
                System.out.println(p.getM_nom() + " a été tué ! Défaite !");
                return true;
            }
        }

        // Sinon, le jeu continue
        return false;
    }


}
