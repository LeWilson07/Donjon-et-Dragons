package classe;

import personnage.Personnage;

public class Roublard implements Classe{
    @Override
    public void definirCaracsBase(Personnage personnage) {
        personnage.setM_pv(16);
    }
}
