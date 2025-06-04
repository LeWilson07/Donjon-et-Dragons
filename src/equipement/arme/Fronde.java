package equipement.arme;

import jeux.De;

public class Fronde extends Arme{
    public Fronde(){
        super("Fronde",new De(4,1),6,false);
    }

    public String toString(){
        return "Nom : " +getNom()+ "Portee :" + getM_porte() ;
    }

}
