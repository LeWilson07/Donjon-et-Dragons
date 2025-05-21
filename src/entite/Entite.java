package entite;

import jeux.De;
import map.Donjon;

public abstract class Entite {
    private De m_de = new De();
    private int m_pv;
    private int m_force;
    private int m_dexterite;
    private int m_vitesse;
    private int m_initiative;
    private int m_x;
    private int m_y;
    private char m_symbole;

    public Entite() {}


    public Entite(int pv, int force, int dexterite, int vitesse, int initiative, char symbole) {
        m_pv = pv;
        m_force = force;
        m_dexterite = dexterite;
        m_vitesse = vitesse;
        m_initiative = initiative;
        m_symbole = symbole;
    }

    public void setPv(int pv) {
        m_pv = pv;
    }
    public void setForce(int force) {
        m_force = force;
    }
    public void setDexterite(int dexterite) {
        m_dexterite = dexterite;
    }
    public void setVitesse(int vitesse) {
        m_vitesse = vitesse;
    }
    public void setInitiative(int initiative) {
        m_initiative = initiative;
    }
    public void setSymbole(char symbole) {
        m_symbole = symbole;
    }
    public char getSymbole() {
        return m_symbole;
    }
    public int getPv() {
        return m_pv;
    }
    public int getForce() {
        return m_force;
    }
    public int getDexterite() {
        return m_dexterite;
    }
    public int getVitesse() {
        return m_vitesse;
    }
    public int getInitiative() {
        return m_initiative;
    }
    public De getDe() {
        return m_de;
    }
    public void setX(int x) {
        m_x = x;
    }
    public void setY(int y) {
        m_y = y;
    }
    public int getX() {
        return m_x;
    }
    public int getY() {
        return m_y;
    }

    public int distance(int x, int y) {
        int dx = Math.abs(getX() - x);
        int dy = Math.abs(getY() - y);
        return dx + dy;
    }
    public int[] ConvertCoord(String coord) {
        char lettre = coord.charAt(0);
        int x = lettre - 'A';
        int y = Integer.parseInt(coord.substring(1))-1;
        return new int[] { x, y };
    }

    public abstract void attaquer(Entite entite);

    public void SeDeplacer(String c, Donjon donjon) {
        int[] coord = ConvertCoord(c);
        char[][] grille = donjon.getGrille();
        System.out.println("x : "+coord[0] + " y : " + coord[1]);
        if(distance(coord[0], coord[1]) <= getVitesse()){

            if (grille[coord[1]][coord[0]] == '.') {
                grille[coord[1]][coord[0]] = m_symbole;
                grille[m_y][m_x] = '.';
                donjon.setGrille(grille);
                m_x = coord[0];
                m_y = coord[1];
            }
            else{
                System.out.println(c +" est déjà occupé !");
            }
        }
        else{
            System.out.println("La case sur laquelle vous souhaitez vous déplacez est trop éloigné de votre joueur !");
        }
    }
}
