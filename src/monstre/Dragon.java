package monstre;

import personnage.Personnage;

public class Dragon implements Monstre{
    int m_pv = 50;
    private String m_espece = "Dragon";
    private int m_num;
    private int m_force;
    private int m_dexterite;
    private int m_initiative;

    public Dragon(int num) {
        m_num = num;
    }
    public void attaquer(Personnage p){}
    public void SeDeplacer(){}
}
