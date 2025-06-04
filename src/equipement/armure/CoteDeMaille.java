package equipement.armure;

import jeux.De;

public class CoteDeMaille extends Armure{
    public CoteDeMaille(){
        super("Cote de maille",11,true);
    }
    public String toString(){
        return "Nom : " +getNom()+ "Classe d'armure :" + getClassArmure() ;
    }
}
