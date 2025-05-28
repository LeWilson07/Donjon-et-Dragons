package equipement.arme;
import jeux.De;

public class Arme extends equipement.Equipement{
    private De m_degat;
    private int m_porte;
    private boolean m_IsGuerre;
    private int m_bonus = 0;

    public Arme(String nom, De degat, int porte, boolean isGuerre) {
        super(nom);
        m_degat = degat;
        m_porte = porte;
        m_IsGuerre = isGuerre;
    }

    public De getM_degat() {
        return m_degat;
    }

    public int getBonus(){
        return m_bonus;
    }
    public void setBonus(int bonus){
        m_bonus = bonus;
    }

    public int getM_porte() {
        return m_porte;
    }

    public String getNom() {
        return super.getNom();
    }
    public boolean getM_IsGuerre() {
        return m_IsGuerre;
    }
}
