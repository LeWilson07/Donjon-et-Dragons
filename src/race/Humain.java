package race;

import entite.personnage.Personnage;

public class Humain implements Race {
    private String m_nom = "Humain";

    @Override
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setM_pv(personnage.getM_Pv()+2);
        personnage.setM_dexterite(personnage.getM_dexterite()+2);
        personnage.setM_force(personnage.getM_force()+2);
        personnage.setM_vitesse(personnage.getM_vitesse()+2);
        personnage.setM_initiative(personnage.getM_initiative()+2);
    }

    public String getM_nom() {
        return m_nom;
    }
}
