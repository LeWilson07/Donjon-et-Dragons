package equipement;
import jeux.De;

public class Arme extends Equipement {
    private De m_degat;
    private int m_porte;
    private boolean m_IsGuerre;

    public Arme(String nom, De degat, int porte, boolean isGuerre) {
        super(nom);
        m_degat = degat;
        m_porte = porte;
        m_IsGuerre = isGuerre;
    }

    public De getM_degat() {
        return m_degat;
    }

    public int getM_porte() {
        return m_porte;
    }

    public boolean getM_IsGuerre() {
        return m_IsGuerre;
    }
}
