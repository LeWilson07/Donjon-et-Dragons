package equipement.arme;

import jeux.De;

public class MasseArme extends Arme{
    public MasseArme(){
        super("Masse d'arme",new De(6,1),1,false);
    }

    public String toString(){
        return "Nom : " +getNom()+ "Portee :" + getM_porte() ;
    }

}
