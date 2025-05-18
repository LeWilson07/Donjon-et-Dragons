import classe.Classe;
import classe.Guerrier;
import entite.monstre.Bowser;
import entite.monstre.Dragon;
import entite.monstre.Monstre;
import entite.personnage.Personnage;
import equipement.arme.Arme;
import equipement.arme.EpeeLongue;
import map.Donjon;
import race.Nain;
import race.Race;

public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");

        Race nain = new Nain();
        Classe guerrier = new Guerrier();

        Personnage Senshi =  new Personnage("Goku",nain, guerrier);
        System.out.println("arme équipé :"+Senshi.getM_armeEquipe().getNom()+"\n");
        Arme epee = new EpeeLongue();

        System.out.println("Nom : " + Senshi.getM_nom());
        System.out.println("Dexterite: " + Senshi.getM_dexterite());
        System.out.println("Vitesse : " + Senshi.getM_vitesse());
        System.out.println("Point de vie : " + Senshi.getM_Pv());
        System.out.println("Force " + Senshi.getM_force());
        System.out.println("Initiative " + Senshi.getM_initiative());

        //Donjon Donjon1 = new Donjon(26,18, Donjon.ModeGeneration.MANUEL);
        //Donjon1.afficherDonjon();
        System.out.println("-------------------------------------");
        Senshi.ramasser(epee);
        Senshi.EquiperArme(epee);
        System.out.println("arme équipé :"+Senshi.getM_armeEquipe().getNom()+"\n");

        System.out.println("Nom : " + Senshi.getM_nom());
        System.out.println("Dexterite: " + Senshi.getM_dexterite());
        System.out.println("Vitesse : " + Senshi.getM_vitesse());
        System.out.println("Point de vie : " + Senshi.getM_Pv());
        System.out.println("Force " + Senshi.getM_force());
        System.out.println("Initiative " + Senshi.getM_initiative());

        Monstre dragon = new Dragon(1);
        Monstre bowser = new Bowser(2);

        Donjon donjon1 = new Donjon(15,15, Donjon.ModeGeneration.AUTO);
        donjon1.afficherDonjon();

    }
}