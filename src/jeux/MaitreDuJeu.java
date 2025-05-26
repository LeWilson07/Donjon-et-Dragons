package jeux;

import entite.Entite;
import entite.monstre.Monstre;
import entite.personnage.Personnage;
import map.Donjon;

import java.util.Scanner;

public class MaitreDuJeu {
    public MaitreDuJeu() {}

    public void giveStatJoueur(Personnage p){
        String stat = "\n";
        stat += "Vie : "+p.getPv() + "\n";
        stat += "Armure : "+ p.getArmureEquipe()+ "\n";
        stat += "Arme : "+ p.getM_armeEquipe()+ "\n";
        stat += "Nom : "+ p.getM_nom()+ "\n";
        stat += "Classe : "+ p.getClass()+ "\n";
        System.out.println( stat );

    }
    public void giveStatMonstre(Monstre m){
        String stat = "\n";
        stat += "Vie : "+m.getPv() + "\n";
        stat += "Espèce : "+ m.getEspece()+ "\n";
        stat += "Portée Attaque : "+ m.getPorteAttaque()+ "\n";
        System.out.println( stat );
    }

    public void DeplacerEntite(Entite e, String c, Donjon d){
        e.SeDeplacer(c, d);
    }

    public boolean AttaqueEntite(Entite e, int nbD, int nbFace){
        boolean attaque = true;
        De de = new De(nbFace, nbD);
        int degat = de.LancerDe();
        e.setPv( e.getPv()-degat);
        if(e.getPv() <= 0){ attaque = false; }
        return attaque;
    }

    public int[] ConvertCoord(String coord) {
        char lettre = coord.charAt(0);
        int x = lettre - 'A';
        int y = Integer.parseInt(coord.substring(1))-1;
        return new int[] { x, y };
    }

    public void AjoutObstacle(Donjon d){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Combien d'obstacle voulez rajouter ?\n");
        int nbObstacle;
        boolean isOk = false;

        while(!isOk){
            try{
                nbObstacle = Integer.parseInt(scanner.nextLine());
                isOk = true;
                for(int i = 0; i < nbObstacle; i++){
                    System.out.println("Coordonné de l'obstacle n°"+ (i+1) +"\n");
                    int[] coord = ConvertCoord(scanner.nextLine());
                    d.ajouterObstacle(coord[0], coord[1]);
                }
            }catch (NumberFormatException e) {
                System.out.println("Erreur : ce n'est pas un nombre valide !\n Veuillez en rentrer un autre !!\n");
            }
        }




    }
}
