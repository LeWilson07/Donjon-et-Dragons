package race;

import entite.personnage.Personnage;

public class Humain implements Race {
    private String m_nom = "Humain";

    @Override
    public void appliquerBonusStat(Personnage personnage) {
        personnage.setPv(personnage.getPv()+2);
        personnage.setDexterite(personnage.getDexterite()+2);
        personnage.setForce(personnage.getForce()+2);
        personnage.setVitesse(personnage.getVitesse()+2);
        personnage.setInitiative(personnage.getInitiative()+2);
    }

    public String getM_nom() {
        return m_nom;
    }
}
