package entite.monstre;
import entite.Entite;
import entite.personnage.Personnage;
import jeux.De;

public class Monstre extends entite.Entite {
    private String m_espece;
    private int m_classArmure;
    private int m_porteAttaque;
    private int m_num;
    private De m_damage;



    public Monstre(int num, int force, int dexterite, int initiative, int pv, int classArmure, int porteAttaque, int vitesse) {
        super(pv, force, dexterite, vitesse, initiative);
        m_num = num;
        m_classArmure = classArmure;
        m_porteAttaque = porteAttaque;
    }


    public int getClassArmure() {
        return m_classArmure;
    }

    public void setDamage(De damage) {
        m_damage = damage;
    }

    public void setPV(int pv){
        super.setPv(pv);
    }

    public int getPV(){
        return super.getPv();
    }

    public int getNum() {return m_num;}

    public void setEspece(String espece){m_espece = espece;}

    public void attaquer(Entite p){
        if(p instanceof Personnage){
            Personnage perso = (Personnage)p;
            int damage = m_damage.UnDeVingt() + super.getDexterite();
            if (damage > perso.getArmureEquipe().getClassArmure()){
                System.out.println("\nLe monstre n°" + m_num + " à percer l'armure de " + perso.getM_nom() + "\n");
                damage = m_damage.LancerDe();
                perso.setM_pv(perso.getM_Pv()-damage);
                System.out.println(perso.getM_nom() + " à perdu " + damage + "pv\n");
            }
            else{
                System.out.println("\nLe monstre n°" + m_num + " n'a pas réussit à percer l'armure de " + perso.getM_nom() + "\n");
            }
        }
        else{
            System.out.println("Ce n'est pas un personnage !");
        }

    }

    public void SeDeplacer(){}
}
