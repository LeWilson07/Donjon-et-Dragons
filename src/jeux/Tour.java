package jeux;

import entite.Entite;
import entite.personnage.Personnage;
import map.Donjon;

import java.util.*;

public class Tour {
    private List<Entite> entities;
    private Donjon donjon;

    public Tour(List<Entite> entities, Donjon donjon) {
        this.entities = entities;
        this.donjon = donjon;
    }

    public void start() {
        rollInitiative();

        while (!checkGameEnd()) {
            for (Entite entity : entities) {
                if (!entity.estVivant()) continue;

                if (entity.estUnPersonnage()) {
                    Personnage joueur = (Personnage) entity;
                    joueur.effectuerTour(donjon);
                    System.out.println("\n---------- Tour de " +  joueur.getRaceNom() xœ+ " : " +joueur.getM_nom() + " ----------");
                    int actionsRestantes = 3;

                    while (actionsRestantes > 0) {
                        System.out.println("\n" + joueur.getM_nom() + ", il vous reste " + actionsRestantes + " action(s).");
                        System.out.println("Commandes possibles : dep <case>, att <case>, pass");
                        System.out.print("  $ ");

                        Scanner scanner = new Scanner(System.in);
                        String input = scanner.nextLine().trim();

                        if (input.startsWith("dep ")) {
                            String coord = input.substring(4).trim();
                            joueur.SeDeplacer(coord, donjon);
                            donjon.afficherDonjon();
                            actionsRestantes--;
                        } else if (input.startsWith("att ")) {
                            String coord = input.substring(4).trim();
                            int[] c = joueur.ConvertCoord(coord);
                            Entite cible = donjon.getEntiteAt(c[0], c[1]);

                            if (cible != null) {
                                joueur.attaquer(cible);
                                actionsRestantes--;
                            } else {
                                System.out.println("Aucune cible valide à cette position.");
                            }
                        } else if (input.equals("pass")) {
                            System.out.println(joueur.getM_nom() + " passe son tour.");
                            break;
                        } else {
                            System.out.println("Commande invalide.");
                        }

                        if (!joueur.estVivant()) break;
                        if (checkGameEnd()) return;
                    }

                } else {
                    // Monstre : à automatiser ou gérer plus tard
                    System.out.println("Un monstre joue automatiquement (non implémenté).");
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
        if (donjon.getMonstres().isEmpty()) {
            System.out.println("Tous les monstres ont été éliminés ! Victoire !");
            return true;
        }

        for (Entite entity : entities) {
            if (entity.estUnPersonnage() && !entity.estVivant()) {
                // jme permet de cast car jverif que c'est un perso avant
                Personnage p = (Personnage) entity;
                System.out.println(p.getM_nom() + " a été tué ! Défaite !");
                return true;
            }
        }

        return false;
    }

}
