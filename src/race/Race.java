
package race;
import entite.personnage.Personnage;

public interface Race {

    void appliquerBonusStat(Personnage personnage);
    public String getM_nom();
}
