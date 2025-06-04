package equipement.arme;

import jeux.De;

public class EpeeDeuxMain extends Arme{
    public EpeeDeuxMain() {
        super("épée à deux main",new De(6,2),1,true);
    }

    public String toString(){
        return "Nom : " +getNom()+ "Portee :" + getM_porte() ;
    }
}
