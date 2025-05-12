package classe;

import personnage.Personnage;

public class Clerc implements Classe{
    @Override
    public void definirCaracsBase(Personnage personnage) {
        personnage.setM_pv(16);
    }
}
