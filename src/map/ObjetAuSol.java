package map;

import equipement.Equipement;
import equipement.TypeEquipement;

public class ObjetAuSol {
    private int m_x, m_y;
    private Equipement m_equipement;

    public ObjetAuSol(int x, int y, Equipement equipement) {
        this.m_x = x;
        this.m_y = y;
        this.m_equipement = equipement;
    }

    public int getX() { return m_x; }
    public int getY() { return m_y; }

    public boolean estTypeEquipement(TypeEquipement type) {
        return m_equipement.getTypeEquipement() == type;
    }
    public Equipement getEquipement() { return m_equipement; }

    @Override
    public String toString(){
        return m_equipement.getNom();
    }
}

