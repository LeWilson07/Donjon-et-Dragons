package equipement.arme;

import jeux.De;

public class Rapiere extends Arme{
    public Rapiere(){
        super("Rapiere",new De(8,1),1,true);
    }

    public String toString(){
        return "Nom : " +getNom()+ "Portee :" + getM_porte() ;
    }

}
