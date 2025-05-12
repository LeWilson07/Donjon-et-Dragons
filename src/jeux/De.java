package jeux;
import java.util.Random;

public class De {
    private int m_nbDe;
    private int m_nbFace;
    public De(int nbDe, int nbFace){
        m_nbDe = nbDe;
        m_nbFace = nbFace;
    }

    public int lancerDe(){
        int result = 0;
        Random rand = new Random();
        for(int i = 0; i < m_nbDe; i++){
            result += rand.nextInt(m_nbFace + 1);
        }
        return result;
    }

}
