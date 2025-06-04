package race;

import entite.personnage.Personnage;

public class Nain implements Race {

    private String m_nom = "Nain";
    @Override
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setForce(personnage.getForce()+6);
    }

    public String getM_nom() {
        return m_nom;
    }
    public String toString(){
        return "Race : " + m_nom;
    }
}
