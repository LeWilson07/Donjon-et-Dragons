package race;

import personnage.Personnage;

public class Nain implements Race {

    @Override
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setM_force(personnage.getM_force()+6);
    }
}
