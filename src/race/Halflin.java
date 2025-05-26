package race;

import entite.personnage.Personnage;

public class Halflin implements Race{
    private String m_nom = "Halfin";
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setM_dexterite(personnage.getM_dexterite()+4);
        personnage.setM_vitesse(personnage.getM_vitesse()+2);

    }

    public String getM_nom() {
        return m_nom;
    }
}
