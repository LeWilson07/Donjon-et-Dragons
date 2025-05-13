package classe;

import equipement.Arme;
import equipement.Armure;
import jeux.De;
import personnage.Personnage;

public class Magicien implements Classe{
    private De de = new De();

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new Arme("Bâton", de.UnDeSix(), 1, false);
        Arme ArmeParDefaut2 = new Arme("fronde", de.UnDeQuatre(), 6, false);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.setM_pv(12);
    }
}
