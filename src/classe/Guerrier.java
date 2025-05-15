package classe;

import equipement.arme.Arme;
import equipement.arme.EpeeLongue;
import equipement.armure.Armure;
import jeux.De;
import entite.personnage.Personnage;

public class Guerrier implements Classe
{
    private De UnDeHuit = new De(8,1);

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new EpeeLongue();
        Arme ArmeParDefaut2 = new Arme("arbalète légère", UnDeHuit, 16, false);
        Armure ArmureParDefaut = new Armure("Cote de maille", 11, true);
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.EquiperArmure(ArmureParDefaut);
        personnage.setM_pv(20);
    }
}
