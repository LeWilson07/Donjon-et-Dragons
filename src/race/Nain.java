package race;

import entite.personnage.Personnage;

public class Nain implements Race {

    private String m_nom = "Nain";
    @Override
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setM_force(personnage.getM_force()+6);
    }

    public String getM_nom() {
        return m_nom;
    }
}
