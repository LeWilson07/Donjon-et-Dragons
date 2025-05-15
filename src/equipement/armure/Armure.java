package equipement.armure;

import equipement.Equipement;

public class Armure extends Equipement {
    private boolean m_IsLourde;
    private int m_classArmure;

    public Armure(String nom, int classArmure, boolean isLourde) {
        super(nom);
        m_IsLourde = isLourde;
        m_classArmure = classArmure;
    }

    public boolean getIsLourde() {
        return m_IsLourde;
    }

    public int getClassArmure() {
        return m_classArmure;
    }
}
