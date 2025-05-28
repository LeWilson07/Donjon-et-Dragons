package sort;

import entite.personnage.Personnage;
import equipement.arme.Arme;

public class ArmeMagique extends Sort{
    public ArmeMagique(){}

    public void LancerSort(Personnage p, int index){
        AmelioreStat(p.getInventaireArme().get(index));
    }

    private void AmelioreStat(Arme arme){
        arme.setBonus(arme.getBonus() + 1);
    }
}
