package jeux;

import entite.Entite;
import entite.monstre.Monstre;
import entite.personnage.Personnage;
import map.Donjon;

import java.util.*;

public class Tour
{
    private List<Entite> m_entities;
    private Donjon m_donjon;
    private ArrayList<Personnage> m_personnage;
    private MaitreDuJeu m_mj;

    public Tour(List<Entite> entities, Donjon donjon, ArrayList<Personnage> personnages) {
        this.m_entities = entities;
        this.m_donjon = donjon;
        this.m_personnage = personnages;
        this.m_mj = new MaitreDuJeu();
    }

    public void start() {
        rollInitiative();

        Scanner scanner = new Scanner(System.in);
        List<Entite> toRemove = new ArrayList<>();

        while (!checkGameEnd()) {
            for (Entite m_entity : new ArrayList<>(m_entities)) {
                if (!m_entity.estVivant()) continue;

                m_donjon.afficherDonjon();

                if (m_entity.estUnPersonnage()) {
                    gererTourPersonnage((Personnage) m_entity, scanner, toRemove);
                } else {
                    gererTourMonstre((Monstre) m_entity, scanner);
                }

                if (checkGameEnd()) break;
            }

            m_entities.removeAll(toRemove);
            toRemove.clear();

            tourMaitreDuJeu(scanner, toRemove);

            if (checkGameEnd()) break;
        }

        afficherMessageFinJeu();
    }

    private void gererTourPersonnage(Personnage joueur, Scanner scanner, List<Entite> toRemove) {
        if(m_donjon.estEquipement(joueur.getX(), joueur.getY())){
            System.out.println("Il y'a un objet sur votre case !");
        }

        System.out.println("\n---------- Tour de " + joueur.getRaceNom()  +" : " + joueur.getM_nom() + " ----------");
        System.out.println("PV : " + joueur.getPv() + " | Force : " + joueur.getForce() + " | Dext : " + joueur.getDexterite() + " | Vit : " + joueur.getVitesse());
        System.out.println("Arme équipée :  " + joueur.getNomArmeEquipee());
        System.out.println("Armure équipée : " + joueur.getNomArmureEquippe());

        int actionsRestantes = 3;

        while (actionsRestantes > 0) {
            afficherCommandes(joueur, actionsRestantes);
            System.out.print("  $ ");

            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ");
            String cmd = parts[0].toLowerCase();

            actionsRestantes -= executerCommandeJoueur(cmd, parts, joueur, scanner, toRemove);
            if (!joueur.estVivant() || checkGameEnd()) break;
        }
    }

    private int executerCommandeJoueur(String cmd, String[] parts, Personnage joueur, Scanner scanner, List<Entite> toRemove) {
        switch (cmd) {
            case "dep":
                if (parts.length < 2) {
                    System.out.println("Précisez la case où vous déplacer.");
                    return 0;
                }
                joueur.SeDeplacer(parts[1], m_donjon);
                m_donjon.afficherDonjon();
                return 1;

            case "att":
                if (parts.length < 2) {
                    System.out.println("Précisez la case cible pour attaquer.");
                    return 0;
                }
                int[] c = joueur.ConvertCoord(parts[1]);
                if (c == null) {
                    System.out.println("Coordonnée invalide.");
                    return 0;
                }
                Entite cible = m_donjon.getEntiteAt(c[0], c[1]);
                if (cible != null && cible.estVivant()) {
                    joueur.attaquer(cible);
                    gererMort(cible, toRemove);
                    return 1;
                } else {
                    System.out.println("Aucune cible valide à cette position.");
                    return 0;
                }

            case "equip":
                if (parts.length < 2) {
                    System.out.println("Précisez l'index de l'objet à équiper.");
                    return 0;
                }
                try {
                    int index = Integer.parseInt(parts[1]) - 1;
                    return joueur.equiperObjetParIndex(index) ? 1 : 0;
                } catch (NumberFormatException e) {
                    System.out.println("L'index doit être un nombre.");
                    return 0;
                }

            case "ramasser":
                joueur.ramasserObjetAuSol(joueur.getX(), joueur.getY(), m_donjon);
                return 1;

            case "inventaire":
                joueur.Afficheinventaire();
                return 0;

            case "pass":
                System.out.println(joueur.getM_nom() + " passe son tour.");
                return 3; // Fait passer toutes les actions restantes

            case "am":
                return gererSortAm(joueur, parts, scanner);

            case "bw":
            case "boogiewoogie":
                return gererSortBoogieWoogie(joueur, parts);

            case "guerison":
                return gererSortGuerison(joueur, parts);

            default:
                System.out.println("Commande invalide.");
                return 0;
        }
    }


    private int gererSortAm(Personnage joueur, String[] parts, Scanner scanner) {
        if (!joueur.isMagicien()) {
            System.out.println("Vous ne pouvez pas lancer ce sort !!");
            return 0;
        }

        if (parts.length != 2 || joueur.ConvertCoord(parts[1]) == null) {
            System.out.println("ERREUR : argument invalide");
            return 0;
        }

        int[] co = joueur.ConvertCoord(parts[1]);
        Personnage cible = m_donjon.getPersonnageAt(co[0], co[1]);

        if (cible == null) {
            System.out.println("Aucune Entité trouvée !");
            return 0;
        }

        cible.Afficheinventaire();
        System.out.print("Entrez le numéro de l'arme à améliorer : ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        joueur.getSort().ArmeMagique(cible, index);
        System.out.print("\nBonus Arme : " + cible.getBonusArme() + "\n");
        return 1;
    }

    private int gererSortBoogieWoogie(Personnage joueur, String[] parts) {
        if (!joueur.isMagicien() || parts.length != 3) {
            System.out.println("ERREUR : arguments invalides ou sort non autorisé.");
            return 0;
        }

        int[] co1 = joueur.ConvertCoord(parts[1]);
        int[] co2 = joueur.ConvertCoord(parts[2]);

        if (co1 == null || co2 == null) {
            System.out.println("ERREUR : Coordonnées invalides.");
            return 0;
        }

        Entite e1 = m_donjon.getEntiteAt(co1[0], co1[1]);
        Entite e2 = m_donjon.getEntiteAt(co2[0], co2[1]);

        if (e1 != null && e2 != null) {
            joueur.getSort().BoogieWoogie(e1, e2, m_donjon);
            m_donjon.afficherDonjon();
            return 1;
        }

        System.out.println("Entités non trouvées.");
        return 0;
    }

    private int gererSortGuerison(Personnage joueur, String[] parts) {
        if (!(joueur.isMagicien() || joueur.isClerc()) || parts.length != 2) {
            System.out.println("Sort non disponible ou mauvais argument.");
            return 0;
        }

        int[] co = joueur.ConvertCoord(parts[1]);
        if (co == null) return 0;

        Personnage cible = m_donjon.getPersonnageAt(co[0], co[1]);
        if (cible != null) {
            System.out.println("Vie avant : " + cible.getPv());
            joueur.getSort().Guerison(cible);
            System.out.println("Vie après : " + cible.getPv());
            return 1;
        }

        System.out.println("Aucune Entité trouvée.");
        return 0;
    }


    private void gererTourMonstre(Monstre monstre, Scanner scanner) {
        System.out.println("\n========== Tour du Monstre ==========");
        System.out.println("Monstre '" + monstre.getSymbole() + "' en [" + monstre.getX() + "," + monstre.getY() + "]");
        System.out.println("PV : " + monstre.getPv() + " | Force : " + monstre.getForce() + " | Dext : " + monstre.getDexterite() + " | Vit : " + monstre.getVitesse());

        int actionsRestantes = 3;

        while (actionsRestantes > 0) {
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
                    Entite cible = m_donjon.getEntiteAt(c[0], c[1]);
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
                    monstre.SeDeplacer(parts[1], m_donjon);
                    m_donjon.afficherDonjon();
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


    private void afficherCommandes(Personnage joueur, int actionsRestantes) {
        System.out.println("\n" + joueur.getM_nom() + ", il vous reste " + actionsRestantes + " action(s).");

        if (joueur.isClerc()) {
            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, guerison <case>, ramasser, inventaire, pass");
        } else if (joueur.isMagicien()) {
            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, guerison <case>, boogieWoogie <case> <case>, am <case>, ramasser, inventaire, pass");
        } else {
            System.out.println("Commandes possibles : dep <case>, att <case>, equip <nomObjet>, ramasser, inventaire, pass");
        }
    }



    private void gererMort(Entite e, List<Entite> toRemove) {
        if (!e.estVivant() && !toRemove.contains(e)) {
            if (!e.estUnPersonnage()) {
                Monstre monstre = (Monstre) e;
                System.out.println(monstre.getEspece() + " n°" + monstre.getNum() + " est mort !");
            }
            m_donjon.enleverEntite(e);
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
                    m_mj.AjoutObstacle(m_donjon);
                    m_donjon.afficherDonjon();
                    break;

                case "attaquer":
                    if (parts.length < 2) {
                        System.out.println("Précisez la case cible (ex : A1).");
                        break;
                    }
                    int[] coAtt = m_mj.ConvertCoord(parts[1]);
                    if (coAtt == null) {
                        System.out.println("Coordonnée invalide.");
                        break;
                    }
                    Entite cible = m_donjon.getEntiteAt(coAtt[0], coAtt[1]);
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
                    boolean encoreVivant = m_mj.AttaqueEntite(cible, nbDes, nbFaces);
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
                    int[] coDepl = m_mj.ConvertCoord(parts[1]);
                    if (coDepl == null) {
                        System.out.println("Coordonnée de départ invalide.");
                        break;
                    }
                    Entite entiteDepl = m_donjon.getEntiteAt(coDepl[0], coDepl[1]);
                    if (entiteDepl == null || !entiteDepl.estVivant()) {
                        System.out.println("Aucune entité vivante à la position " + parts[1]);
                        break;
                    }
                    m_mj.DeplacerEntite(entiteDepl, parts[2], m_donjon);
                    m_donjon.afficherDonjon();
                    break;

                case "donnerinfos":
                    if (parts.length < 2) {
                        System.out.println("Précisez la case de l'entité (ex : A1).");
                        break;
                    }
                    int[] coInfo = m_mj.ConvertCoord(parts[1]);
                    if (coInfo == null) {
                        System.out.println("Coordonnée invalide.");
                        break;
                    }
                    Entite entiteInfo = m_donjon.getEntiteAt(coInfo[0], coInfo[1]);
                    if (entiteInfo == null) {
                        System.out.println("Aucune entité à cette position.");
                        break;
                    }
                    if (entiteInfo.estUnPersonnage()) {
                        m_mj.giveStatJoueur((Personnage) entiteInfo);
                    } else {
                        m_mj.giveStatMonstre((Monstre) entiteInfo);
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
        for (Entite entity : m_entities) {
            int initiativeRoll = rnd.nextInt(20) + 1;
            entity.setInitiative(initiativeRoll + entity.getInitiative());
        }
        Collections.sort(m_entities, Comparator.comparingInt(Entite::getInitiative).reversed());
    }

    private boolean checkGameEnd() {
        boolean monstreEncoreVivant = false;
        for (Entite monstre : m_donjon.getMonstres()) {
            if (monstre.estVivant()) {
                monstreEncoreVivant = true;
                break;
            }
        }
        if (!monstreEncoreVivant) {
            return true; // Victoire
        }

        for (Entite entity : m_entities) {
            if (entity.estUnPersonnage() && !entity.estVivant()) {
                return true; // Défaite
            }
        }
        return false; // Partie continue
    }

    private void afficherMessageFinJeu() {
        boolean monstreEncoreVivant = false;
        for (Entite monstre : m_donjon.getMonstres()) {
            if (monstre.estVivant()) {
                monstreEncoreVivant = true;
                break;
            }
        }
        if (!monstreEncoreVivant) {
            System.out.println("Tous les monstres ont été éliminés ! Victoire !");
            return;
        }

        for (Entite entity : m_entities) {
            if (entity.estUnPersonnage() && !entity.estVivant()) {
                Personnage p = (Personnage) entity;
                System.out.println(p.getM_nom() + " a été tué ! Défaite !");
                return;
            }
        }
    }
}
