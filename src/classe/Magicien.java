package classe;

import equipement.arme.Arme;
import equipement.arme.Baton;
import equipement.arme.Fronde;
import entite.personnage.Personnage;
import sort.Sort;

public class Magicien implements Classe{

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new Baton();
        Arme ArmeParDefaut2 = new Fronde();
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.setPv(12);
        personnage.setIsMagicien(true);
        personnage.setSort(new Sort());
    }

}
