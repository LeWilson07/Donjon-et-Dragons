package sort;

import entite.Entite;
import entite.personnage.Personnage;
import equipement.arme.Arme;
import jeux.De;
import map.Donjon;

public class Sort {
    public Sort(){}

    public void ArmeMagique(Personnage p, int index){
        AmelioreStat(p.getInventaireArme().get(index));
        p.LoadStatArmeEquipe();
    }

    private void AmelioreStat(Arme arme){
        arme.setBonus(arme.getBonus() + 1);
    }

    public void BoogieWoogie(Entite e1, Entite e2, Donjon d){
        int x1 = e1.getX();
        int y1 = e1.getY();
        e1.setX(e2.getX());
        e1.setY(e2.getY());
        e2.setX(x1);
        e2.setY(y1);

        char[][] grille = d.getGrille();
        grille[y1][x1] = e2.getSymbole();
        grille[e1.getY()][e1.getX()] = e1.getSymbole();
    }

    public void Guerison(Personnage p){
        De de = new De();
        int heal = de.UnDeDix();
        if((p.getPv() + heal) > p.getPvInitial()){
            p.setPv(p.getPvInitial());
        }
        else{
            p.setPv(p.getPv() + heal);
        }
    }
}
