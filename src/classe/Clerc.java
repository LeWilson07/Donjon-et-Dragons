package classe;

import equipement.Arme;
import equipement.Armure;
import personnage.Personnage;
import jeux.De;

public class Clerc implements Classe{
    private De UnDeHuit = new De(8,1);
    private De UnDeSix = new De(6,1);
    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new Arme("Masse d'arme", UnDeSix, 1, false);
        Arme ArmeParDefaut2 = new Arme("arbalète légère", UnDeHuit, 16, false);
        Armure ArmureParDefaut = new Armure("Armure d'écaille", 9, false);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.EquiperArmure(ArmureParDefaut);
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.setM_pv(16);
    }
}
