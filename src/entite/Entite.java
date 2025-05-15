package entite;

import jeux.De;

public abstract class Entite {
    private De m_de = new De();
    private int m_pv;
    private int m_force;
    private int m_dexterite;
    private int m_vitesse;
    private int m_initiative;

    public Entite() {}

    public Entite(int pv, int force, int dexterite, int vitesse, int initiative) {
        m_pv = pv;
        m_force = force;
        m_dexterite = dexterite;
        m_vitesse = vitesse;
        m_initiative = initiative;
    }

    public void setPv(int pv) {
        m_pv = pv;
    }
    public void setForce(int force) {
        m_force = force;
    }
    public void setDexterite(int dexterite) {
        m_dexterite = dexterite;
    }
    public void setVitesse(int vitesse) {
        m_vitesse = vitesse;
    }
    public void setInitiative(int initiative) {
        m_initiative = initiative;
    }
    public int getPv() {
        return m_pv;
    }
    public int getForce() {
        return m_force;
    }
    public int getDexterite() {
        return m_dexterite;
    }
    public int getVitesse() {
        return m_vitesse;
    }
    public int getInitiative() {
        return m_initiative;
    }
    public De getDe() {
        return m_de;
    }
    public abstract void attaquer(Entite entite);

    public abstract void SeDeplacer();
}
