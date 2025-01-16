package exploration;

public class CaisseGrenades extends Objet
{
    private final static int MAX = 9;// nombre maximal dans les caisses de grenades trouvées dans les salles
    private int nbGrenade;
    public int getNbGrenade() {
        return nbGrenade;
    }
    public void setNbGrenade(int nbGrenade) {
        this.nbGrenade = nbGrenade;
    }

    /** S'il reste des grenades dans cette réserve,
     propose au joueur d'en prendre un certain nombre,
     ce qui augmente les grenades du joueur et diminue d'autant cette réserve
     */
    @Override
    public void interaction(Joueur j)
    {
        if(this.nbGrenade>0){       //Vérifie si il reste des grenades dans la caisse sinon ne fait rien
            System.out.print("Elle contient "+getNbGrenade()+" grenarde(s). Souhaitez vous en prendre [a] ou continuer à jouer [b]?\n");

            char choix=Lire.c(); // Choix continuer ou prendre des grenades
            if(choix=='a'){     // Le joueur choisit de prendre des grenades
                int choixNbGrenade;
                System.out.println("Prendre combien de grenade(s) ? (Maximum = "+getNbGrenade()+")");choixNbGrenade=Lire.i();
                while(choixNbGrenade<0 || choixNbGrenade>this.nbGrenade){       //Vérification que le choix de nombre de grenades est compris entre 0 et nbGrenade
                    System.out.println("\nNombre de grenade(s) impossible. Prendre combien de grenade(s) ? (Maximum = "+getNbGrenade()+")");
                    choixNbGrenade=Lire.i();
                };
                System.out.println("Vous avez pris "+choixNbGrenade+" grenade(s).\n");
                j.ajouteGrenade(choixNbGrenade);        // Ajout grenades au joueur
                this.nbGrenade -= choixNbGrenade;         // Diminution grenades de la réserve
                if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
                    this.setSymbole("☌"+this.nbGrenade);
                else
                    this.setSymbole("G"+this.nbGrenade); // Actualisation du nombre de grenades affiché dans le symboleen fonction de l'affichage
            }
            else
                System.out.println("Vous continuez sans avoir pris de grenade dans la caisse.\n");
        }
    }

    public CaisseGrenades(int nbGrenades)
    {       //symbole normal "☌"//  si des ? s'affichent utiliser comme symbole "G"
        super("","Caisse de grenades");
        this.nbGrenade=nbGrenades;
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole("☌"+nbGrenades);
        else
            this.setSymbole("G"+nbGrenades);
    }
    
    public CaisseGrenades() {this((int)(1+(CaisseGrenades.MAX)*Math.random()));}  //nb de grenades tirées au hasard entre 1 et CaisseGrenades.MAX

    
}
