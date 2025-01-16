package exploration;

public class Joueur
{    
    private String nom;
    public String getNom(){return this.nom;}
    private void setNom(String nom){this.nom = nom;}
    private void setNom()
    {
        setNom(Lire.S("Quel est votre nom"));
    }
    
    private Position position;
    /**
     * Position du joueur
     * @return une référence à une position
     */
    public Position getPosition(){return this.position;}    
    /**
     * Salle où se situe le joueur
     * @return une référence à une salle
     */
    public Salle getSalle()
    {
        return this.position.getPlateau().getSalle(this.position);
    }
    private void setPosition(Position position)
    {
        if(!position.isValide())
            throw new RuntimeException("Position impossible");
        else{
            this.position=position;                 // affectation de la position en déclenchant la méthode d'entrée de la salle
            this.getSalle().entree(this);        // marche parce que getSalle renvoie position joueur deja actualisée
        }
    }
        
    /**
     * Avance dans une direction donnée à condition que le mur soit ouvert dans cette direction
     * @param d direction dans laquelle avancer
     */
    public void avance(Direction d)
    {
        Plateau plateau = this.getPosition().getPlateau();

        // Vérifier si la nouvelle position est valide sur le plateau
        if (this.getPosition().isValide(d)) {

            // Vérifier si l'accès dans la direction donnée est possible
            if (this.getSalle().isPossible(d)) {
                // Déplacer le joueur vers la nouvelle position
                this.setPosition(getPosition().getSuivante(d));

                // Vérifier si le joueur atteint la sortie du plateau (position deja actualisée)
                if (plateau.getSortie().equals(this.getPosition())) {
                    plateau.getJeu().setFini(true);
                }
            } else {
                // Afficher un message indiquant que le joueur ne peut pas avancer dans cette direction
                System.out.println("\nVous n'avez pas cassé le mur dans cette direction ! Impossible d'avancer.\n");
            }
        } else {
            // Afficher un message indiquant que le joueur atteint le bord du plateau
            System.out.println("\nVous avez atteint le bord du plateau.\n");
        }
    }
    
    private LesOutils outils;
    public LesOutils getOutils()
    {
        return this.outils;
    }
    private void setOutils(LesOutils outils)
    {
        this.outils = outils;
    }

    /**
     * Récupération d'un outil trouvé dans une salle
     */
    public void recupere(Outil nouvelOutil) {this.getOutils().ajoute(nouvelOutil);}       //Cette méthode est déclenchée par l'interaction avec l'outil
    
    private boolean perdant;
    public boolean isPerdant(){return this.perdant;}
    public void setPerdant(boolean perdant){
        this.perdant = perdant;
        this.getSalle().getPlateau().getJeu().setFini(perdant);
    }

    private int nbGrenade;
    private static int NBGRENADE_INIT=10;
    public void setNbGrenade(int nbGrenade){this.nbGrenade=nbGrenade;}
    public int getNbGrenade() {return nbGrenade;}
    public void ajouteGrenade(int nbGrenade){
        this.setNbGrenade(this.nbGrenade+nbGrenade);
    }
    public void initGrenade(){ // possible initialisation des grenades (a mettre dans initJeu() de Jeu) mais pas utilisée car sujet fixe grenades init
        Plateau plateau = this.getPosition().getPlateau();
        setNbGrenade( (plateau.getNbCol()+plateau.getNbLig()) / 2);
    }
    public void utiliseGrenade(){
        if(this.nbGrenade-1 >= 0)
            this.nbGrenade--;
    }


    /**
    La grenade est perdue si un mur est déjà ouvert dans la direction spécifiée
    Sinon,un accès est ajouté à la salle courante vers la salle contiguë dans la direction d (et réciproquement)
    et le joueur est « aspiré » dans la salle nouvellement ouverte. La réserve de grenades du joueur est décrémentée
    */
    public void lanceGrenade(Direction d) {
        Salle nouvelleSalle = this.getSalle().getVoisine(d);
        Plateau plateau = this.getPosition().getPlateau();

        if(this.nbGrenade <= 0)                 // Vérifie si le joueur a encore des grenades
            System.out.println("\nVous n'avez plus de grenades !\n");
        else {
            if (nouvelleSalle==null) {  // Si la nouvelleSalle renvoie null la position dans la direction d n'est pas valide et le joueur veut lancer une grenade sur un bord et le tue
                this.setPerdant(true);
                System.out.println("\nVous avez envoyé une grenade sur le bord qui a explosé dans votre salle !\n");
            } else {
                if (this.getSalle().isPossible(d)) { //isPossible renvoie si le mur est déja détruit dans la direction d, la grenade lancée est perdue et détruit l'objet de la salle si il y en a un
                    this.utiliseGrenade();
                    if(nouvelleSalle.getContenu()==null)
                        System.out.println("\nVous avez envoyé une grenade dans la salle "+d.toStringPhrase()+" mais le mur " +
                                "est déja détruit et elle explose dans la salle et rien ne se passe\n"); // Si rien dans la salle la grenade est perdue
                    else{
                        System.out.println("\nVous avez envoyé une grenade dans la " +
                                "salle "+d.toStringPhrase()+" mais le mur est déja détruit\n" +
                                "et elle explose et détruit l'objet : "+nouvelleSalle.getContenu().getNature()+"\n"); //Si objet dans la salle la grenade le détruit
                        nouvelleSalle.setObjet(null);
                    }
                }
                else{
                    System.out.println("\nVous avez cassé le mur "+d.toStringPhrase()+" avec votre grenade et vous êtes aspirés dans la salle "); //Le mur est détruit et le joueur avance dans la salle
                    nouvelleSalle.setVisible(true);
                    this.utiliseGrenade();
                    this.getSalle().setAcces(d);    //Acces est possible dans la direction(= le mur est détruit)
                    this.avance(d);
                }
            }
        }
    }

    private int energie;
    private static int ENERGIE_INIT=20;
    public int getEnergie() {
        return energie;
    }
    public void setEnergie(int energie) {
        this.energie = energie;
    }
    public void ajouteEnergie(int nbEnergie){
        this.setEnergie(this.energie+nbEnergie);
    }
    public void enleveEnergie(int nbEnergie){
        if(this.energie-nbEnergie>0)
            this.energie-=nbEnergie;
    }

    public Joueur(String nom, Position position)
    {
        this.position=position; //affectations
        this.nom=nom;
        this.perdant=false;     // Joueur initialement ne perd pas
        this.energie=Joueur.ENERGIE_INIT;    // Initialisation energie et nbGrenade du joueur a la constante specifiée
        this.nbGrenade=Joueur.NBGRENADE_INIT;
        this.outils=new LesOutils(this);    //Création de classe lesOutils qui initialise les outils avec le constructeur

        if(nom.equals("GODMODE")){  //??
            setEnergie(999999);
            setNbGrenade(999999);
        }
    }
    public Joueur(Position position)
    {
        this(null,position);
    }
}
