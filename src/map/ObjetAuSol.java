package map;

import equipement.Equipement;

public class ObjetAuSol {
    private int x, y;
    private Equipement equipement;

    public ObjetAuSol(int x, int y, Equipement equipement) {
        this.x = x;
        this.y = y;
        this.equipement = equipement;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Equipement getEquipement() { return equipement; }
}

