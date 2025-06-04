package equipement.arme;

import jeux.De;

public class Baton extends Arme{
    public Baton(){
        super("Baton",new De(6,1),1,false);
    }

    public String toString(){
        return "Nom : " +getNom()+ "Portee :" + getM_porte() ;
    }

}
