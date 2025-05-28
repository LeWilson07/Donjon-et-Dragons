package jeux;
import java.util.Random;

public class De {
    private int m_nbFace;
    private int m_nbDe;

    public De(){}

    public De(int nbface, int nbDe){
        m_nbFace = nbface;
        m_nbDe = nbDe;
    }

    public int LancerDe(){
        int result = 0;
        Random rand = new Random();
        for (int i = 0; i < m_nbDe; i++) {
            result += rand.nextInt(m_nbFace)+1;
        }
        return result;
    }

    public int UnDeQuatre(){
        Random rand = new Random();
        return rand.nextInt(4)+1;
    }
    public int UnDeSix(){
        Random rand = new Random();
        return rand.nextInt(6)+1;
    }
    public int UnDeHuit(){
        Random rand = new Random();
        return rand.nextInt(8)+1;
    }
    public int UnDeDix(){
        Random rand = new Random();
        return rand.nextInt(10)+1;
    }
    public int UnDeVingt(){
        Random rand = new Random();
        return rand.nextInt(20)+1;
    }
    public int QuatreDeQuatre(){
        int result = 0;
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            result += rand.nextInt(4)+1;
        }
        return result;
    }

}
