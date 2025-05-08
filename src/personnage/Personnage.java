package personnage;

import classe.Classe;
import race.Race;

public class Personnage {

    private String m_nom;
    private Race m_race;
    private Classe m_classe;

    private int m_pv;
    private int m_force = 5;
    private int m_dexterite = 5;
    private int m_vitesse = 5;
    private int m_initiative = 5;

    public Personnage(String nom,Race race,Classe classe){
        this.m_nom = nom;
        this.m_race = race;
        this.m_classe = classe;

        classe.definirCaracsBase(this);
        race.appliquerBonusStat(this);

    }

    public int getM_Pv(){
        return m_pv;
    }

    public void setM_pv(int m_pv) {
        this.m_pv = m_pv;
    }

    public int getM_force() {
        return m_force;
    }

    public void setM_force(int m_force) {
        this.m_force = m_force;
    }

    public int getM_dexterite() {
        return m_dexterite;
    }

    public void setM_dexterite(int m_dexterite) {
        this.m_dexterite = m_dexterite;
    }

    public int getM_vitesse() {
        return m_vitesse;
    }

    public void setM_vitesse(int m_vitesse) {
        this.m_vitesse = m_vitesse;
    }

    public int getM_initiative() {
        return m_initiative;
    }

    public void setM_initiative(int m_initiative) {
        this.m_initiative = m_initiative;
    }

    public String getM_nom() {
        return m_nom;
    }
}
