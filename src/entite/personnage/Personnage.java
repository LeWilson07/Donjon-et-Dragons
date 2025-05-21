
package entite.personnage;

import classe.Classe;
import entite.Entite;
import race.Race;
import equipement.arme.Arme;
import equipement.armure.Armure;

import java.util.*;
import entite.monstre.*;

public class Personnage extends entite.Entite{

    private String m_nom;
    private Race m_race;
    private Classe m_classe;

    private Arme m_armeEquipe;
    private Armure m_armureEquipe;
    ArrayList<Arme> m_inventaireArme = new ArrayList<Arme>();
    ArrayList<Armure> m_inventaireArmure = new ArrayList<Armure>();

    public Personnage(String nom,Race race,Classe classe, char sym){
        this.m_nom = nom;
        this.m_race = race;
        this.m_classe = classe;
        super.setSymbole(sym);

        loadState();
        classe.definirCaracsBase(this);
        race.appliquerBonusStat(this);

    }

    private void loadState(){
                int result = super.getDe().QuatreDeQuatre() + 3;
                super.setForce(result);
                result = super.getDe().QuatreDeQuatre() + 3;
                super.setDexterite(result);
                result = super.getDe().QuatreDeQuatre() + 3;
                super.setVitesse(result);
                result = super.getDe().QuatreDeQuatre() + 3;
                super.setInitiative(result);
    }

    public int getM_Pv(){
        return super.getPv();
    }

    public void setM_pv(int m_pv) {super.setPv(m_pv);}

    public int getM_force() {return super.getForce();}

    public void setM_force(int m_force) {
        this.setForce(m_force);
    }

    public int getM_dexterite() {
        return super.getDexterite();
    }

    public void setM_dexterite(int m_dexterite) {super.setDexterite(m_dexterite);}

    public int getM_vitesse() {
        return super.getVitesse();
    }

    public void setM_vitesse(int m_vitesse) {super.setVitesse(m_vitesse);}

    public int getM_initiative() {
        return super.getInitiative();
    }

    public void setM_initiative(int m_initiative) {
        this.setInitiative(m_initiative);
    }

    public String getM_nom() {
        return m_nom;
    }

    public Arme getM_armeEquipe() {return m_armeEquipe;}

    public void EquiperArme(Arme arme) {
        if(m_inventaireArme.contains(arme)){
            if(m_armeEquipe != null && m_armeEquipe.getM_IsGuerre()){
                super.setVitesse(super.getVitesse()+2);
                super.setForce(super.getForce()-4);
            }
            if(arme.getM_IsGuerre()){
                super.setVitesse(super.getVitesse()-2);
                super.setForce(super.getForce()+4);
            }
            m_armeEquipe = arme;
        }
        else{
            System.out.println("Vous ne possédez pas cette arme dans votre inventaire");
        }
    }

    public void EquiperArmure(Armure armure) {
        m_armureEquipe = armure;
    }

    public void ramasser(Arme arme) {
        m_inventaireArme.add(arme);
    }

    public void ramasser(Armure armure) {
        m_inventaireArmure.add(armure);
    }

    public Armure getArmureEquipe() {
        return m_armureEquipe;
    }

    public void attaquer(Entite cible) {
        if(cible instanceof Monstre){
            Monstre monstre = (Monstre) cible;
            if(super.distance(monstre.getX(), monstre.getY()) <= m_armeEquipe.getM_porte()){
                int damage;
                if(m_armeEquipe.getM_porte() == 1){
                    damage = super.getDe().UnDeVingt() + super.getForce();
                }
                else{
                    damage = super.getDe().UnDeVingt() + super.getDexterite();
                }
                if (damage > monstre.getClassArmure()){
                    System.out.println("\n"+ m_nom + " à percer l'armure du monstre n°" + monstre.getNum()+ "\n");
                    damage = m_armeEquipe.getM_degat().LancerDe();
                    monstre.setPV(monstre.getPV()-damage);
                    System.out.println("Le monstre n°" +monstre.getNum()+ " à perdu " + damage + "pv\n");
                }
            }
            else{
                System.out.println("Le monstre que vous souhaitez attaquer est hors de porté !");
            }
        }
        else{
            System.out.println("Vous ne pouvez pas attaquez un autre personnage !");
        }
    }
}
