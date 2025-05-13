package equipement;

public class Armure {
    private String m_nom;
    private boolean m_IsLourde;
    private int m_classArmure;

    public Armure(String nom, int classArmure, boolean isLourde) {
        m_nom = nom;
        m_IsLourde = isLourde;
        m_classArmure = classArmure;
    }
    public String getNom() {
        return m_nom;
    }

    public boolean getIsLourde() {
        return m_IsLourde;
    }

    public int getClassArmure() {
        return m_classArmure;
    }
}
