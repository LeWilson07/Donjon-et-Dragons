
package entite.personnage;

import classe.Classe;
import classe.TypeClasse;
import entite.Entite;
import entite.TypeEntite;
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
    private Arme m_armeEquipe;
    private Armure m_armureEquipe;
    private Sort m_sort = null;

    private TypeClasse m_TypeClasse;
    private ArrayList<Arme> m_inventaireArme = new ArrayList<Arme>();
    private ArrayList<Armure> m_inventaireArmure = new ArrayList<Armure>();

    public Personnage(String nom,Race race,Classe classe){
        super(TypeEntite.PERSONNAGE);
        this.m_TypeClasse = classe.getType();
        this.m_nom = nom;
        this.m_race = race;
        this.m_classe = classe;
        String symbole = nom;
        if (symbole.length() >= 3) {
            symbole = symbole.substring(0, 3);
        }

        super.setSymbole(symbole);

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
    public boolean isClerc(){
        return m_TypeClasse == TypeClasse.CLERC;
    }
    public boolean isMagicien(){
        return m_TypeClasse == TypeClasse.MAGICIEN;
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

    public String getNomArmeEquipee()
    {
        if(m_armeEquipe == null){
            return "Aucune arme d'équipée";
        }
        return m_armeEquipe.getNom();
    }



    public String getNomArmureEquippe()
    {
        if(m_armureEquipe == null)
        {
            return "Aucune armure d'équipée";
        }
        return m_armureEquipe.getNom();
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

    public int getPorteAttaque(){
        return m_armeEquipe.getM_porte();
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

    public boolean equiperObjetParIndex(int index) {
        int tailleArmes = m_inventaireArme.size();
        int tailleArmures = m_inventaireArmure.size();

        if (index < 0 || index >= (tailleArmes + tailleArmures)) { //Signifie que l'index est en dehors de ce qu'on veut
            System.out.println("Index invalide pour l'équipement.");
            return false;
        }

        if (index < tailleArmes) { //Il veut équiper une arme
            Arme arme = m_inventaireArme.get(index);
            EquiperArme(arme);
            System.out.println("Arme '" + arme.getNom() + "' équipée.");
        } else { //si c'est plus grand que l'index de taille arme, c'est donc une armure qu'il veut équiper plutot
            int indexArmure = index - tailleArmes;
            Armure armure = m_inventaireArmure.get(indexArmure);
            EquiperArmure(armure);
            System.out.println("Armure '" + armure.getNom() + "' équipée.");
        }

        return true;
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

    public List<Arme >getInventaireArme(){
        return m_inventaireArme;
    }
    public void RecevoirAttaqueDe(Personnage p, int degat){
        System.out.println("Vous ne pouvez pas attaquez un autre personnage !");
    }
    public void RecevoirAttaqueDe(Monstre monstre, int degat) {
        if (super.distance(monstre.getX(), monstre.getY()) <= monstre.getPorteAttaque()) {
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
    public boolean estVivant() {
        return getPv() > 0;
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

    public void Afficheinventaire() {
        String inventaireArme = "";
        String inventaireArmure = "";

        System.out.println("Armes\n");
        if (m_inventaireArme.isEmpty()) {
            inventaireArme = "Aucune arme dans l'inventaire";
        } else {
            for (int i = 0; i < m_inventaireArme.size(); i++) {
                inventaireArme += (i + 1) + ") " + m_inventaireArme.get(i).getNom() + " -- ";
            }
        }
        System.out.println(inventaireArme);

        System.out.println("Armure(s) : \n");
        if (m_inventaireArmure.isEmpty()) {
            inventaireArmure = "Aucune armure dans l'inventaire";
        } else {
            for (int j = m_inventaireArme.size(); j  < m_inventaireArmure.size() + m_inventaireArmure.size(); j++) { //On démarre avec j = taille de l'inventaire d'arme pour que la numérotation suis
                inventaireArmure += (j + 1) + ") " + m_inventaireArmure.get(j).getNom() + " -- ";
            }
        }
        System.out.println(inventaireArmure);
    }

    @Override
    public String toString(){
        String stat = "\n";
        stat += "Vie : "+ m_pvInitial +"/" + getPv()  + "\n";
        if(m_armureEquipe == null)
        {
            stat+="Armure : Pas d'armure";
        }
        else
        {
            stat += "Armure : "+ m_armureEquipe.getNom()+ "\n";
        }
        if(m_armeEquipe == null){
            stat+="Arme : Pas d'arme";
        }
        else
        {
            stat += "Arme : "+ m_armeEquipe.getNom()+ "\n";
        }
        stat += "Nom : "+ m_nom+ "\n";
        stat += "Classe : "+ m_classe.toString()+ "\n";
        return stat;
    }
}
