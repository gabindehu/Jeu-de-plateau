package exploration;
import java.util.ArrayList;

public class Plateau
{
    //Symboles tous déclarés pour permettre différents affichages
    private static String SYMBOLE_BORD = "▓ ";
    public static String SYMBOLE_SALLE_INVISIBLE="░ ";
    public static String SYMBOLE_SALLE_VIDE="      ";
    public static String SYMBOLE_JOUEUR="♛";
    public static String SYMBOLE_SORTIE=" S  ";
    public static String SYMBOLE_ENERGIE="⚛";

    //Change les symboles en fonction des modes d'affichages, utilisé dans le constructeur de plateau
    public void setSymbole(int affichage){
        if(affichage==1){
            SYMBOLE_BORD ="▓ ";
            SYMBOLE_SALLE_INVISIBLE="░ ";
            SYMBOLE_SALLE_VIDE="      ";
            SYMBOLE_JOUEUR="♛";
            SYMBOLE_SORTIE="  S  ";
            SYMBOLE_ENERGIE="⚛";
        }
        if(affichage==2){
            SYMBOLE_BORD ="▓ ";
            SYMBOLE_SALLE_INVISIBLE="░ ";
            SYMBOLE_SALLE_VIDE="╳ ";
            SYMBOLE_JOUEUR="♛";
            SYMBOLE_SORTIE="S";
            SYMBOLE_ENERGIE="⚛";
        }
        if(affichage==3){
            SYMBOLE_BORD ="[]";
            SYMBOLE_SALLE_INVISIBLE="()";
            SYMBOLE_SALLE_VIDE="  ";
            SYMBOLE_JOUEUR="J ";
            SYMBOLE_SORTIE="S ";
            SYMBOLE_ENERGIE="E";
        }
    }
    public static boolean VISIBLE = false; // mode déboggage : si true, toutes les salles doivent afficher leur contenu

    private Jeu jeu;
    public Jeu getJeu(){return this.jeu;}
    private void setJeu(Jeu jeu){this.jeu = jeu;}

    private boolean visible;
    public boolean isVisible(){return this.visible||Plateau.VISIBLE;}
    public void setVisible(boolean visible){this.visible = visible;}

    private Salle[][] grille;
    public int getNbLig(){return this.grille.length;}
    public int getNbCol(){return this.grille[0].length;}
    public int getNbSalles(){return this.getNbCol()*this.getNbLig();}
    public Salle getSalle(Position p)
    {
        if(p.isValide()) return this.grille[p.getLig()-1][p.getCol()-1];
        else return null;
    }
    public Salle getSalle(int lig, int col){return this.getSalle(new Position(lig,col,this));}

    /**
     * Place une salle à une position donnée (Les positions en Lig et Col commencent à 1)
     * @param s Une salle
     */
    public void placeSalle(Salle s)
    {
        Position p = s.getPosition();
        if(!p.isValide())
            throw new RuntimeException("Affectation de la salle à une position non valide");
        else {
            this.grille[p.getLig()-1][p.getCol()-1] = s;
        }
    }

    /**
     * Crée une salle contenant un objet référencé par o (vide si o = null) et l'affecte au plateau
     * @param p la position dans le plateau
     * @param o l'objet que la salle doit contenir
     */
    public void setNouvelleSalle(Position p, Objet o)
    {
        placeSalle(new Salle(p,this,o));       
    }
    public void setNouvelleSalle(Position p){setNouvelleSalle(p, null);}
    
    private Joueur joueur;
    public Joueur getJoueur(){return this.joueur;}
    private void setJoueur(Joueur joueur){this.joueur = joueur;}
    public Position getPosJoueur(){return this.getJoueur().getPosition();}

    //Position de la sortie
    private Position sortie;
    public Position getSortie(){
        return this.sortie;
    }

    //Change la variable sortie poue qu'elle soit un des 4 coins du plateau en fonction des nbLig et nbCol
    public void setSortieHasard(){
        int tirage = (int)(4*Math.random());
        switch(tirage){
            case 0 : {sortie = new Position(1,1,this);break;}           // tirage entre les 4 coins et création d'un objet sortie en fonction de la taille du plateau
            case 1 : {sortie = new Position(1,this.getNbCol(),this);break;}
            case 2 : {sortie = new Position(this.getNbLig(),1,this);break;}
            case 3 : {sortie = new Position(this.getNbLig(),this.getNbCol(),this);break;}
        }
    }

    @Override
    public String toString()
    {
        String plateau=""; //initialise String vide
        for(int i=-1;i<=getNbLig();i++) { //boucle taille plateau en lig et colonne+1 pour afficher les bords
            for(int j=-1 ; j<=getNbCol();j++){
                if(i==-1 || i==getNbLig()){
                    plateau += SYMBOLE_BORD+" ";
                }else{
                    if(j==-1 || j==getNbCol())
                        plateau += SYMBOLE_BORD+" ";
                    else
                        plateau += this.grille[i][j].toString()+" "; //toString salle
                }
            }
            plateau += "\n";
        }
        return plateau;
            // restitue la chaîne qui représente l'ensemble du plateau en combinant les toString des salles (voir exemple dans le sujet)
    }

    public Plateau(int nbLig, int nbCol, Jeu jeu,String nomJoueur)
    {
        this.jeu=jeu; //affectation jeu

        this.grille=new Salle[nbLig][nbCol]; //initialisation grille

        //Joueur
        this.joueur= new Joueur(nomJoueur,new Position((nbLig+1)/2,(nbCol+1)/2,this));     //initialisation Joueur au centre de la grille (voir pour setPosition joueur si nom deja choisi)
        setNouvelleSalle(this.getPosJoueur());  // Création d'une salle vide pour la salle du joueur
        getSalle(this.getPosJoueur()).setVisible(true); // Salle du joueur initialement visible

        //Sortie
        setSortieHasard();
        setNouvelleSalle(sortie);       // création d'une salle vide a la sortie
        if(Exploration.nbLig==5) //Correspond au parametre du mode facile
            this.getSalle(sortie).setVisible(true); //Affiche la sortie en mode facile

        //Placement objets dans plateau
        int nbObjetsTotal = (int)(nbLig*nbCol - nbLig*nbCol*jeu.getProportionVides()*0.01);
        int nbTotalProportion = 0;
        int colonne,ligne;
        for(int i = 0 ; i<jeu.getListeCategories().length ; i++) // Parcours des catégories
        {
            nbTotalProportion = nbTotalProportion+jeu.getListeCategories()[i].getProportion();  // Calcule le nombre total de proportions d'objets
        }
        int indiceObjet = 0;
        for(int indiceCategorie = 0 ; indiceCategorie<jeu.getListeCategories().length ; indiceCategorie++)
        {
            int nbObjetsCategorie = nbObjetsTotal*jeu.getListeCategories()[indiceCategorie].getProportion()/nbTotalProportion; // Calcule le nombre d'objets nbObjetsCategorie de la catégorie d'indice indicecategorie dans la liste de catégories du Jeu
            for(int compteurObjets = 1 ; compteurObjets<= nbObjetsCategorie ; compteurObjets++,indiceObjet++) // Crée nbObjetsCategorie instances de la catégorie itérée et la place au hasard dans la grille de salles
            {
                Objet o = jeu.getListeCategories()[indiceCategorie].getNouveau();
                do {                                    //salle tirée au hasard
                    ligne = (int) (nbLig * Math.random());
                    colonne = (int) (nbCol * Math.random());        //vérification qu'elle ne contient pas déja un objet ou qu'elle n'est pas la salle centrale (=salle pas encore crée)
                }while(this.grille[ligne][colonne]!=null);
                this.grille[ligne][colonne] = new Salle(new Position(ligne+1, colonne+1, this), this, o);
            }
        }
        for(int indiceLig = 0; indiceLig<nbLig; indiceLig++){       // remplissage autres salles non atribuées par des salles sans objets
            for(int indiceCol = 0; indiceCol<nbCol; indiceCol++){
                if(this.grille[indiceLig][indiceCol] == null)
                    this.grille[indiceLig][indiceCol]=new Salle(new Position(indiceLig+1,indiceCol+1,this),this);
            }
        }
        this.setSymbole(Exploration.choixAffichage); //Règle les symboles du plateau en fonction du choix dans le menu de exploration

        if(nomJoueur.equals("GODMODE")) //??
            this.setVisible(true);

        // Crée un joueur et initialise le plateau selon les spécifications du jeu
    }
}
