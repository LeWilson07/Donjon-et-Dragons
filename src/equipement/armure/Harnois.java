package equipement.armure;

import jeux.De;

public class Harnois extends Armure {
    public Harnois(){
        super("Harnois",12,true);
    }

    public String toString(){
        return "Nom : " +getNom()+ "Classe d'armure :" + getClassArmure() ;
    }
}
