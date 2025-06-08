    package entite;

    import entite.monstre.Monstre;
    import entite.personnage.Personnage;
    import jeux.De;
    import map.Donjon;
    import map.ObjetAuSol;

    public abstract class Entite {
        private De m_de = new De();
        private int m_pv;
        private int m_force;
        private int m_dexterite;
        private int m_vitesse;
        private int m_initiative;
        private int m_x;
        private int m_y;
        private TypeEntite m_typeEntite;
        protected String m_symbole = "";

        protected Entite(TypeEntite type) {
            this.m_typeEntite = type;
        }
        public TypeEntite getTypeEntite() {
            return m_typeEntite;
        }


        public Entite(int pv, int force, int dexterite, int vitesse, int initiative,TypeEntite type) {
            m_pv = pv;
            m_force = force;
            m_dexterite = dexterite;
            m_vitesse = vitesse;
            m_initiative = initiative;
            m_typeEntite = type;
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
        public void setSymbole(String symbole) {
            m_symbole = symbole;
        }
        public String getSymbole() {
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
        public int[] ConvertCoord(String coord) { //Pour s'assurer que les coords sont bonnes vu que c'est une donnée qui va être beaucoup utilisé
            if (coord == null || coord.length() < 2) {
                System.out.println("Coordonnée trop courte ou nulle.");
                return null;
            }

            char lettre = coord.charAt(0);
            if (lettre < 'A' || lettre > 'Z') {
                System.out.println("Lettre invalide : doit être entre A et Z.");
                return null;
            }

            int x = lettre - 'A';
            int y;
            try {
                y = Integer.parseInt(coord.substring(1)) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Format numérique invalide après la lettre.");
                return null;
            }

            return new int[] { x, y };
        }

        public abstract void RecevoirAttaqueDe(Personnage p, int degat);
        public abstract void RecevoirAttaqueDe(Monstre m, int degat);


        public abstract void attaquer(Entite entite);

        public abstract boolean estVivant();


        public void SeDeplacer(String c, Donjon donjon) {
            int[] coord = ConvertCoord(c);
            if (coord == null) {
                System.out.println("Coordonnées invalides. Déplacement annulé.");
                return;
            }

            if (!donjon.estDansGrille(coord[0], coord[1])) {
                System.out.println("La case est hors de la grille.");
                return;
            }

            String[][] grille = donjon.getGrille();
            System.out.println("x : " + coord[0] + " y : " + coord[1]);

            if (distance(coord[0], coord[1]) <= getVitesse()) {
                if (grille[coord[1]][coord[0]].equals(".") || grille[coord[1]][coord[0]].equals("E")) {
                    grille[coord[1]][coord[0]] = m_symbole;

                    //Vérifie s’il y a un objet au sol à l’ancienne position
                    boolean equipementPresent = false;
                    for (ObjetAuSol o : donjon.getObjetsAuSol()) {
                        if (o.getX() == m_x && o.getY() == m_y) {
                            equipementPresent = true;
                            break;
                        }
                    }

                    //Restaure "E" ou "."
                    grille[m_y][m_x] = equipementPresent ? "E" : ".";

                    donjon.setGrille(grille);
                    m_x = coord[0];
                    m_y = coord[1];
                } else {
                    System.out.println(c + " est déjà occupé !");
                }
            } else {
                System.out.println("La case sur laquelle vous souhaitez vous déplacez est trop éloignée !");
            }
        }


        public boolean estUnPersonnage() {
            return this.m_typeEntite == TypeEntite.PERSONNAGE;
        }
        public boolean estUnMonstre() {
            return this.m_typeEntite == TypeEntite.MONSTRE;
        }
    }
