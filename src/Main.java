import classe.Guerrier;
import entite.Entite;
import entite.personnage.Personnage;
import equipement.arme.EpeeLongue;
import jeux.Tour;
import map.Donjon;
import race.Humain;
import entite.personnage.Personnage;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Création du donjon
        Donjon donjon = Donjon.creerDonjonPredefini(Donjon.TypeDonjon.DONJON3);
        System.out.println("=== CONTEXTE DU DONJON ===");
        System.out.println(donjon.getContexte());

        // Création des personnages
        Personnage joueur1 = new Personnage("Arthur", new Humain(), new Guerrier(), 'A');
        Personnage joueur2 = new Personnage("Luna", new Humain(), new Guerrier(), 'L');

        // Ajout d’une arme pour test
        joueur1.ramasser(new EpeeLongue());
        joueur2.ramasser(new EpeeLongue());
        joueur1.EquiperArme(joueur1.getM_inventaireArme().get(0));
        joueur2.EquiperArme(joueur2.getM_inventaireArme().get(0));

        // Liste de personnages
        ArrayList<Personnage> personnages = new ArrayList<>();
        personnages.add(joueur1);
        personnages.add(joueur2);

        // Placement des joueurs
        donjon.placerJoueurs(personnages);

        // Ajout de toutes les entités (joueurs + monstres)
        ArrayList<Entite> toutesEntites = new ArrayList<>();
        toutesEntites.addAll(personnages);
        toutesEntites.addAll(donjon.getMonstres());

        // Démarrage du système de tours
        System.out.println("\n=== ÉTAT INITIAL DU DONJON ===");
        donjon.afficherDonjon();

        Tour tour = new Tour(toutesEntites, donjon);
        tour.start();
    }
}
