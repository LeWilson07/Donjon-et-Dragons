package classe;

import equipement.Arme;
import equipement.Armure;
import jeux.De;
import personnage.Personnage;

public class Magicien implements Classe{
    private De UnDeQuatre = new De(4,1);
    private De UnDeSix = new De(6,1);

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new Arme("Bâton", UnDeSix, 1, false);
        Arme ArmeParDefaut2 = new Arme("fronde", UnDeQuatre, 6, false);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.setM_pv(12);
    }
}
