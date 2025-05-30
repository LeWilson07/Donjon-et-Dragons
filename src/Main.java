import classe.Clerc;
import classe.Guerrier;
import classe.Magicien;
import entite.Entite;
import entite.monstre.Monstre;
import entite.personnage.Personnage;
import equipement.arme.EpeeLongue;
import jeux.MaitreDuJeu;
import jeux.Tour;
import map.Donjon;
import race.Humain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MaitreDuJeu mj = new MaitreDuJeu();

        // Création des personnages par méthode dédiée dans MaitreDuJeu
        ArrayList<Personnage> personnages = mj.creerPersonnages(scanner);

        // Ajout d’armes pour test (optionnel)
        for (Personnage p : personnages) {
            p.ramasser(new EpeeLongue());
            if (!p.getM_inventaireArme().isEmpty()) {
                p.EquiperArme(p.getM_inventaireArme().get(0));
            }
        }

        // Liste des donjons à parcourir
        List<Donjon.TypeDonjon> donjonsAExplorer = Arrays.asList(
                Donjon.TypeDonjon.DONJON1,
                Donjon.TypeDonjon.DONJON2,
                Donjon.TypeDonjon.DONJON3
        );

        for (int i = 0; i < donjonsAExplorer.size(); i++) {
            System.out.println("\n=== DÉBUT DU DONJON " + (i + 1) + " ===");
            Donjon donjon = null;

            // Demande custom ou prédéfini
            System.out.println("Voulez-vous utiliser le donjon prédéfini " + donjonsAExplorer.get(i) + " ? (oui/non)");
            String reponse = scanner.nextLine().trim().toLowerCase();

            if (reponse.equals("oui") || reponse.equals("o")) {
                donjon = Donjon.creerDonjonPredefini(donjonsAExplorer.get(i));
            } else {
                donjon = mj.creerDonjonCustom(personnages);
            }

            System.out.println(donjon.getContexte());

            // On met à jour les personnages dans le donjon
            donjon.setPersonnages(personnages);

            // Si custom, les joueurs ont déjà leurs positions définies dans le donjon (via creerDonjonCustom)
            // Sinon, on place les joueurs (prédéfinis)
            if (reponse.equals("oui") || reponse.equals("o")) {
                donjon.placerJoueurs(personnages);
            }

            // Fusion des entités
            ArrayList<Entite> toutesEntites = new ArrayList<>();
            toutesEntites.addAll(personnages);
            toutesEntites.addAll(donjon.getMonstres());
            donjon.setEntites(toutesEntites);

            System.out.println("\n=== ÉTAT INITIAL DU DONJON " + (i + 1) + " ===");
            donjon.afficherDonjon();

            // Lancement du tour
            Tour tour = new Tour(toutesEntites, donjon, personnages);
            tour.start();

            // Affichage état final personnages
            System.out.println("\n=== ÉTAT DES PERSONNAGES APRÈS DONJON " + (i + 1) + " ===");
            for (Personnage p : personnages) {
                System.out.println(p.getM_nom() + " - PV: " + p.getPv());
                p.Afficheinventaire();
            }
        }

        System.out.println("\n=== FIN DE LA PARTIE ===");
    }
}
