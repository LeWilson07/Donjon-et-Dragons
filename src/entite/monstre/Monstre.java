    package entite.monstre;
    import entite.Entite;
    import entite.personnage.Personnage;
    import jeux.De;
    import map.Donjon;

    public abstract class Monstre extends entite.Entite {
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
                    System.out.println("\n"+p.getM_nom() + " à percer l'armure de" + m_espece + "\n");
                    degat = m_damage.LancerDe();
                    p.setPv(p.getPv() - degat);
                    System.out.println(m_espece + " à perdu " + degat + "pv\n");
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

        @Override
        public void infoEntite(Donjon donjon) {
            System.out.println(m_espece + m_num  + ", c'est votre tour !");
            System.out.println("Nom : " + getEspece());
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
            return false;
        }

    }