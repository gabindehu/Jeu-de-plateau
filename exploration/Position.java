package exploration;

/**
 *Permet de gérer une position dans un plateau donné à partir de son numéro de ligne et de colonne
 * ou de son rang en parcourant le plateau de gauche à droite et de haut en bas. Une position hors du plateau est invalide (isValide() vaut false)
 */
public class Position
{
    private static char getNumChar(int num){return (char)('A'+num-1);}    

    /**
     *
     * @param num Les positions sont numérotées dans un plateau donné à partir de 0 (coin haut gauche) en parcourant dans le sens horizontal de gauche à droite puis vertical de haut en bas
     * @param p Le plateau
     * @return restitue une position correspondant au rang num dans le plateau p
     */
    public static Position get(int num, Plateau p)
    {
        return new Position(num, p);
    }
    
    private Plateau plateau;

    /**
     * Le plateau dans lequel la position est définie
     * @return
     */
    public Plateau getPlateau() {return this.plateau;}

    /**
     *
     * @param plateau
     */
    public void setPlateau(Plateau plateau){this.plateau = plateau;}
     
    private int rang;
    /**
     * Le rang de la position dans le plateau : si un plateau a 5 colonnes, le rang 4 est en haut à droite et le rang 5 est au début de la ligne d'en dessous
     * @return
     */
    public int getRang(){return this.rang;}
    /**
     *
     * @param rang
     */
    public void setRang(int rang){this.rang = rang;}
    
    /**
     * Retourne le numéro de ligne correspondant à la position entre 1 et le nombre total de lignes (1 est la première ligne)
     * @return un entier
     */
    public int getLig(){return this.getRang()/this.getPlateau().getNbCol()+1;}

    /**
     * Retourne un caractère qui représente le numéro de ligne (A,B…)
     * @return
     */
    public char getCharLig(){return this.getNumChar(this.getLig());}

    /**
     * Retourne le numéro de colonne de la position de 1 au nombre de colonnes
     * @return
     */
    public int getCol(){return this.getRang()%this.getPlateau().getNbCol()+1;}

    /**
     * Restitue le numéro de colonne sous forme d'une lettre (A,B,…)
     * @return
     */
    public char getCharCol(){return this.getNumChar(this.getCol());}
    
    /**
     * Restitue true si la position est dans le plateau et false sinon
     * @return
     */
    public boolean isValide()
    {
        return this.getRang()>=0 && this.getRang()<=this.getPlateau().getNbSalles();
    }
    public boolean isValide(Direction d){               // Nouvel isValide() qui ne prends pas en compte le rang qui posait probleme
        int nouvellePosLig=this.getLig() + d.getdLig();
        int nouvellePosCol=this.getCol() + d.getdCol();
        return (nouvellePosLig>0 && nouvellePosLig<= plateau.getNbLig() && nouvellePosCol>0 && nouvellePosCol<=plateau.getNbCol());
    }
    /**
     * Définit une nouvelle position à partir de son rang en parcourant le plateau de gauche à droite et de haut en bas (début à 0)
     * @param rang
     * @param p
     */
    public Position(int rang, Plateau p)
    {
        this.setRang(rang);
        this.setPlateau(p);
    }

    /**
     * Définit une position à partir du numéro de sa ligne et de sa colonne (Début à 1)
     * @param lig
     * @param col
     * @param p
     */
    public Position(int lig, int col, Plateau p)
    {
        this((lig-1)*p.getNbCol()+col-1,p);
    }
    
    /**
     *
     * @param p
     * @return
     */
    public Position getSuivante(Direction p)
    {
        return new Position (this.getLig()+p.getdLig(),this.getCol()+p.getdCol(),this.getPlateau());
    }

    /**
     * Retourne la position suivante dans le parcours gauche -> droite haut -> bas. Cette position peut être invalide si la position courante est la position en bas à droite ou qu'elle est elle-même invalide
     * @return
     */
    public Position getSuivante()
    {
        return new Position(this.getRang()+1,this.getPlateau());
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 53 * hash + this.rang;
        return hash;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        final Position autre = (Position) o;
        return this.getRang()==autre.getRang();
    }
    
    @Override
    public String toString()
    {
        return this.getRang()+":("+this.getLig()+","+this.getCol()+")";
    }
}
