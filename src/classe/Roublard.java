package classe;

import equipement.Arme;
import equipement.Armure;
import jeux.De;
import personnage.Personnage;

public class Roublard implements Classe{
    private De UnDeHuit = new De(8,1);
    private De UnDeSix = new De(6,1);

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new Arme("rapière", UnDeHuit, 1, true);
        Arme ArmeParDefaut2 = new Arme("arc court", UnDeSix, 16, false);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.setM_pv(16);
    }
}
