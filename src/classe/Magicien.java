package classe;

import personnage.Personnage;

public class Magicien implements Classe{
    @Override
    public void definirCaracsBase(Personnage personnage) {
        personnage.setM_pv(12);
    }
}
