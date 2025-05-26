    package entite.monstre;
    import entite.Entite;
    import entite.personnage.Personnage;
    import jeux.De;
    import map.Donjon;

    public class Monstre extends entite.Entite {
        private String m_espece;
        private int m_classArmure;
        private int m_porteAttaque;
        private int m_num;
        private De m_damage;

        public Monstre(int num, int force, int dexterite, int initiative, int pv, int classArmure, int porteAttaque, int vitesse, char sym) {
            super(pv, force, dexterite, vitesse, initiative, sym);
            m_num = num;
            m_classArmure = classArmure;
            m_porteAttaque = porteAttaque;
        }


        public int getClassArmure() {
            return m_classArmure;
        }

        public void setDamage(De damage) {
            m_damage = damage;
        }

        public void setPV(int pv) {
            super.setPv(pv);
        }

        public int getPV() {
            return super.getPv();
        }

        public int getNum() {
            return m_num;
        }

        public int getPorteAttaque() {
            return m_porteAttaque;
        }

        public String getEspece() {
            return m_espece;
        }

        public void setEspece(String espece) {
            m_espece = espece;
        }

        public void RecevoirAttaqueDe(Personnage p, int degat) {

            if (super.distance(p.getX(), p.getY()) <= m_porteAttaque) {
                if (degat > p.getArmureEquipe().getClassArmure()) {
                    System.out.println("\nLe monstre n°" + m_num + " à percer l'armure de " + p.getM_nom() + "\n");
                    degat = m_damage.LancerDe();
                    p.setM_pv(p.getM_Pv() - degat);
                    System.out.println(p.getM_nom() + " à perdu " + degat + "pv\n");
                } else {
                    System.out.println("\n " + p.getM_nom() + " n'a pas réussit à percer l'armure du monstre n° " + this.m_num + "\n");
                }
            } else {
                System.out.println("Le Monstre que vous souhaitez attaquer est hors de porté !");
            }
        }

        public void RecevoirAttaqueDe(Monstre m, int degat) {
            System.out.println("Vous ne pouvez pas attaquer un autre monstre !");
        }

        public void attaquer(Entite cible) {
            int damage;
            if (super.getForce() == 0) {
                damage = m_damage.UnDeVingt() + super.getDexterite();
            } else {
                damage = m_damage.UnDeVingt() + super.getForce();
            }
            cible.RecevoirAttaqueDe(this, damage);
        }


        /// /////

        public void effectuerTour(Donjon donjon) {
            System.out.println("C'est le tour de " + m_symbole + " !");
        }

        @Override
        public boolean estVivant() {
            return getPV() > 0;
        }

        @Override
        public boolean estUnPersonnage() {
            return false;
        }

    }