package equipement.armure;

import jeux.De;

public class DemiPlate extends Armure{
    public DemiPlate(){
        super("Demi plate",10,false);
    }
    public String toString(){
        return "Nom : " +getNom()+ "Classe d'armure :" + getClassArmure() ;
    }
}
