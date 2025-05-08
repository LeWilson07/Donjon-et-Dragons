package classe;

import personnage.Personnage;

public class Guerrier implements Classe
{
    @Override
    public void definirCaracsBase(Personnage personnage) {
        personnage.setM_pv(20);
    }
}
