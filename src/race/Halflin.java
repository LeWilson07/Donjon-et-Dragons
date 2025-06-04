package race;

import entite.personnage.Personnage;

public class Halflin implements Race{
    private String m_nom = "Halfin";
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setDexterite(personnage.getDexterite()+4);
        personnage.setVitesse(personnage.getVitesse()+2);

    }

    public String getM_nom() {
        return m_nom;
    }
    public String toString(){
        return "Race : " + m_nom;
    }
}
