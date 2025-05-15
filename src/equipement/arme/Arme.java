package equipement.arme;
import jeux.De;

public class Arme {
    private String m_nom;
    private De m_degat;
    private int m_porte;
    private boolean m_IsGuerre;

    public Arme(String nom, De degat, int porte, boolean isGuerre) {
        m_nom = nom;
        m_degat = degat;
        m_porte = porte;
        m_IsGuerre = isGuerre;
    }
    public String getNom() {
        return m_nom;
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
