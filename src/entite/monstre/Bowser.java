package entite.monstre;

import jeux.De;

public class Bowser extends Monstre{
    private De m_UnDeSept = new De(6,1);
    public Bowser(int num) {
        super(num, 5, 0, 5, 60, 23, 1, 10);
        super.setDamage(m_UnDeSept);
        super.setEspece("Bowser");

    }
}
