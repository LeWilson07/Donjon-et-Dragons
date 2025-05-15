package race;

import entite.personnage.Personnage;

public class Halflin implements Race{
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setM_dexterite(personnage.getM_dexterite()+4);
        personnage.setM_vitesse(personnage.getM_vitesse()+2);

    }
}
