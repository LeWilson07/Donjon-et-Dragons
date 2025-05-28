package classe;

import equipement.arme.Arme;
import equipement.arme.Baton;
import equipement.arme.Fronde;
import jeux.De;
import entite.personnage.Personnage;
import sort.ArmeMagique;
import sort.Sort;

import java.util.ArrayList;

public class Magicien implements Classe{

    private ArmeMagique m_armeMagique = new ArmeMagique();

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new Baton();
        Arme ArmeParDefaut2 = new Fronde();
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.setPv(12);
        personnage.setIsMagicien(true);

        ArrayList<Sort> sort = new ArrayList<>();
        sort.add(new ArmeMagique());
        personnage.setSort(sort);

        personnage.setSort1(new Sort());
    }

    public Sort getArmeMagique() {
        return m_armeMagique;
    }

}
