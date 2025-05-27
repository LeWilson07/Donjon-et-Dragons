package classe;

import equipement.arme.ArcCourt;
import equipement.arme.Arme;
import equipement.arme.Rapiere;
import jeux.De;
import entite.personnage.Personnage;

public class Roublard implements Classe{

    @Override
    public void definirCaracsBase(Personnage personnage) {
        Arme ArmeParDefaut = new Rapiere();
        Arme ArmeParDefaut2 = new ArcCourt();
        personnage.ramasser(ArmeParDefaut2);
        personnage.ramasser(ArmeParDefaut);
        personnage.EquiperArme(ArmeParDefaut);
        personnage.setPv(16);
    }
}
