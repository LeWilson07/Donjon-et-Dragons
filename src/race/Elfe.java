package race;

import entite.personnage.Personnage;

public class Elfe implements Race{
    private String m_nom = "Elfe";

    @Override
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setM_dexterite(personnage.getM_dexterite()+6);
    }

    public String getM_nom() {
        return m_nom;
    }

}
