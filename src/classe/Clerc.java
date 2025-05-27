package classe;

import equipement.arme.ArbaleteLegere;
import equipement.arme.Arme;
import equipement.arme.MasseArme;
import equipement.armure.Armure;
import entite.personnage.Personnage;
import equipement.armure.ArmureEcaille;
import jeux.De;

public class Clerc implements Classe{

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new MasseArme();
        Arme ArmeParDefaut2 = new ArbaleteLegere();
        Armure ArmureParDefaut = new ArmureEcaille();
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.EquiperArmure(ArmureParDefaut);
        personnage.setPv(16);
    }
}
