    package entite.monstre;

    import jeux.De;

    public class Dragon extends Monstre{
        private De m_UnDeSix = new De(6,1);
        public Dragon(int num) {
            super(num, 0, 4, 5, 50, 20, 10,12);
            super.setDamage(m_UnDeSix);
            super.setEspece("Dragon");
            setSymbole(getEspece().substring(0, 3)+num);
        }

        @Override
        public String toString(){
            return "Dragon";
        }
    }
