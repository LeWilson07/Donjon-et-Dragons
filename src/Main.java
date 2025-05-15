import classe.Classe;
import classe.Guerrier;
import entite.monstre.Bowser;
import entite.monstre.Dragon;
import entite.monstre.Monstre;
import entite.personnage.Personnage;
import equipement.arme.Arme;
import equipement.arme.EpeeLongue;
import race.Nain;
import race.Race;

public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");

        Race nain = new Nain();
        Classe guerrier = new Guerrier();

        Personnage Senshi =  new Personnage("Senshi",nain, guerrier);
        System.out.println("arme équipé :"+Senshi.getM_armeEquipe().getNom()+"\n");
        Arme epee = new EpeeLongue();

        System.out.println("Nom : " + Senshi.getM_nom());
        System.out.println("Dexterite: " + Senshi.getM_dexterite());
        System.out.println("Vitesse : " + Senshi.getM_vitesse());
        System.out.println("Point de vie : " + Senshi.getM_Pv());
        System.out.println("Force " + Senshi.getM_force());
        System.out.println("Initiative " + Senshi.getM_initiative());

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

    }
}