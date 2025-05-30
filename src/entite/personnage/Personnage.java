
package entite.personnage;

import classe.Classe;
import entite.Entite;
import jeux.De;
import map.Donjon;
import map.ObjetAuSol;
import race.Race;
import equipement.arme.Arme;
import equipement.armure.Armure;

import java.util.*;
import entite.monstre.*;
import sort.Sort;

public class Personnage extends entite.Entite{

    private int m_pvInitial;
    private String m_nom;
    private Race m_race;
    private Classe m_classe;
    private int m_bonusArme = 0;
    private De m_degatArme;
    private boolean m_IsMagicien = false;
    private boolean m_IsClerc = false;
    private Arme m_armeEquipe;
    private Armure m_armureEquipe;
    private Sort m_sort = null;
    private ArrayList<Arme> m_inventaireArme = new ArrayList<Arme>();
    private ArrayList<Armure> m_inventaireArmure = new ArrayList<Armure>();

    public Personnage(String nom,Race race,Classe classe, char sym){
        this.m_nom = nom;
        this.m_race = race;
        this.m_classe = classe;
        super.setSymbole(sym);

        loadState();
        classe.definirCaracsBase(this);
        race.appliquerBonusStat(this);

        m_pvInitial = getPv();

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

    public void setSort(Sort sort){
        this.m_sort = sort;
    }
    public Sort getSort(){
        return this.m_sort;
    }


    public int getPvInitial(){
        return this.m_pvInitial;
    }
    public void setIsMagicien(boolean isMagicien){
        m_IsMagicien = isMagicien;
    }
    public void setIsClerc(boolean isClerc){
        m_IsClerc = isClerc;
    }
    public boolean isClerc(){
        return m_IsClerc;
    }
    public boolean isMagicien(){
        return m_IsMagicien;
    }
    public ArrayList<Arme> getM_inventaireArme() {
        return m_inventaireArme;
    }

    public String getM_nom() {
        return m_nom;
    }

    public Arme getArmeEquipe() {
        return m_armeEquipe;
    }
    public void LoadStatArmeEquipe(){
        this.m_bonusArme = m_armeEquipe.getBonus();
        this.m_degatArme = m_armeEquipe.getM_degat();
    }
    public int getBonusArme() {
        return m_bonusArme;
    }
    public De getDegatArmee() {
        return m_degatArme;
    }

    public ArrayList<Armure> getInventaireArmure() {return m_inventaireArmure;}

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
            LoadStatArmeEquipe();
        }
        else{
            System.out.println("Vous ne possédez pas cette arme dans votre inventaire");
        }
    }

    public void ramasserObjetAuSol(int x, int y, Donjon donjon) {
        // Vérifie si une arme est présente sur la case
        Arme arme = donjon.getArmeAt(x, y);
        if (arme != null) {
            m_inventaireArme.add(arme);          // ajoute l'arme à l'inventaire du personnage
            donjon.retirerArme(arme);      // retire l'arme du sol
            System.out.println("Vous avez ramassé une arme : " + arme.getNom());
            return;
        }

        // Sinon, vérifie si une armure est présente sur la case
        Armure armure = donjon.getArmureAt(x, y);
        if (armure != null) {
            m_inventaireArmure.add(armure);        // ajoute l'armure à l'inventaire du personnage
            donjon.retirerArmure(armure);  // retire l'armure du sol
            System.out.println("Vous avez ramassé une armure : " + armure.getNom());
            return;
        }

        // Aucun équipement trouvé sur la case
        System.out.println("Il n'y a aucun équipement à ramasser ici.");
    }



    public void EquiperArmure(Armure armure) {
        m_armureEquipe = armure;
    }

    public boolean equiperObjetParNom(String nomObjet) {
        // Cherche dans les armes
        for (Arme arme : m_inventaireArme) {
            if (arme.getNom().equalsIgnoreCase(nomObjet)) {
                EquiperArme(arme);
                System.out.println("Arme '" + nomObjet + "' équipée.");
                return true;
            }
        }
        // Cherche dans les armures
        for (Armure armure : m_inventaireArmure) {
            if (armure.getNom().equalsIgnoreCase(nomObjet)) {
                EquiperArmure(armure);
                System.out.println("Armure '" + nomObjet + "' équipée.");
                return true;
            }
        }
        System.out.println("Aucun équipement nommé '" + nomObjet + "' trouvé dans l'inventaire.");
        return false;
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

    public void RecevoirAttaqueDe(Personnage p, int degat){
        System.out.println("Vous ne pouvez pas attaquez un autre personnage !");
    }
    public void RecevoirAttaqueDe(Monstre monstre, int degat) {
        if (super.distance(monstre.getX(), monstre.getY()) <= m_armeEquipe.getM_porte()) {
            if (m_armureEquipe != null && degat > m_armureEquipe.getClassArmure()) {
                System.out.println("\n" + monstre.getEspece() + " à percer l'armure de" + m_nom + "\n");
                degat = monstre.getDamage().LancerDe();
                this.setPv(this.getPv() - degat);
                System.out.println(m_nom + " à perdu " + degat + "pv\n");
            }
            else{
                degat = monstre.getDamage().LancerDe();
                this.setPv(this.getPv() - degat);
                System.out.println(m_nom + " à perdu " + degat + "pv\n");
            }
        }
    }
    @Override
    public void infoEntite(Donjon donjon) {
        System.out.println(m_nom + ", c'est votre tour !");
        System.out.println("Nom : " + getM_nom());
        System.out.println("Dexterite: " + getDexterite());
        System.out.println("Vitesse : " + getVitesse());
        System.out.println("Point de vie : " + getPv());
        System.out.println("Force : " + getForce());
        System.out.println("Initiative : " + getInitiative());

    }

    @Override
    public boolean estVivant() {
        return getPv() > 0;
    }

    @Override
    public boolean estUnPersonnage() {
        return true;
    }

    public String getRaceNom(){
        return m_race.getM_nom();
    }

    public void attaquer(Entite cible) {
        int damage;
        if(m_armeEquipe.getM_porte() == 1){
            damage = super.getDe().UnDeVingt() + super.getForce();
        }
        else{
            damage = super.getDe().UnDeVingt() + super.getDexterite();
        }
        cible.RecevoirAttaqueDe(this, damage);
    }

    public void Afficheinventaire(){
        String inventaire = "";
        for(int i=0;i<m_inventaireArme.size();i++){
            inventaire += (i+1) + ")" + m_inventaireArme.get(i).getNom() + " -- ";
        }
        System.out.println(inventaire + "\n");
    }
}
