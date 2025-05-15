package race;

import entite.personnage.Personnage;

public class Elfe implements Race{
    @Override
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setM_dexterite(personnage.getM_dexterite()+6);
    }

}
