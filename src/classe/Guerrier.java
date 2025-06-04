package classe;

import equipement.arme.ArbaleteLegere;
import equipement.arme.Arme;
import equipement.arme.EpeeLongue;
import equipement.armure.Armure;
import equipement.armure.CoteDeMaille;
import jeux.De;
import entite.personnage.Personnage;

public class Guerrier implements Classe
{
    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new EpeeLongue();
        Arme ArmeParDefaut2 = new ArbaleteLegere();
        Armure ArmureParDefaut = new CoteDeMaille();
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.ramasser(ArmureParDefaut);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.EquiperArmure(ArmureParDefaut);
        personnage.setPv(20);
    }

    public TypeClasse getType(){
        return TypeClasse.GUERRIER;
    }

    @Override
    public String toString(){
        return "Guerrier";
    }
}
