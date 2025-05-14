import classe.Classe;
import classe.Guerrier;
import personnage.Personnage;
import race.Nain;
import race.Race;
import map.Donjon;

public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");

        Race nain = new Nain();
        Classe guerrier = new Guerrier();

        Personnage Senshi =  new Personnage("Senshi",nain,guerrier);

        System.out.println("Nom : " + Senshi.getM_nom());
        System.out.println("Dexterite: " + Senshi.getM_dexterite());
        System.out.println("Vitesse : " + Senshi.getM_vitesse());
        System.out.println("Point de vie : " + Senshi.getM_Pv());
        System.out.println("Force " + Senshi.getM_force());
        System.out.println("Initiative " + Senshi.getM_initiative());

        Donjon Donjon1 = new Donjon(26,18);
        Donjon1.afficherDonjon();


    }
}