package classe;

import equipement.Arme;
import equipement.Armure;
import jeux.De;
import personnage.Personnage;

public class Guerrier implements Classe
{
    private De de = new De();

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new Arme("épée longue", de.UnDeHuit(), 1, true);
        Arme ArmeParDefaut2 = new Arme("arbalète légère", de.UnDeHuit(), 16, false);
        Armure ArmureParDefaut = new Armure("Cote de maille", 11, true);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.EquiperArmure(ArmureParDefaut);
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.setM_pv(20);
    }
}
