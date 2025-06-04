package equipement.armure;

import jeux.De;

public class ArmureEcaille extends Armure{
    public ArmureEcaille(){
        super("Armure d'écaille",9,true);
    }

    public String toString(){
        return "Nom : " +getNom()+ "Classe d'armure :" + getClassArmure() ;
    }

}
