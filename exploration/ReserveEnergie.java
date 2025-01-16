package exploration;

public class ReserveEnergie extends Objet
{
    private final static int MAX = 9;

    private int nbEnergie;
    public int getNbEnergie() {
        return nbEnergie;
    }

    /** S'il reste des unités d'énergie dans cette réserve,
           propose au joueur d'en prendre un certain nombre,
           ce qui augmente l'énergie du joueur et diminue d'autant cette réserve
     */
    @Override
    public void interaction(Joueur j)
    {
        if(this.nbEnergie>0){       //Vérifie si il reste des énergies dans la réserve
            System.out.print("Elle contient "+getNbEnergie()+" unité(s) d'énergie(s). Souhaitez vous en prendre [a] ou continuer à jouer [b]?\n");
            char choix=Lire.c(); // Choix continuer ou prendre de l'énergie
            if(choix=='a'){
                int choixNbEnergie;
                System.out.println("Prendre combien d'unités d'énergies ? (Maximum = "+getNbEnergie()+")");choixNbEnergie=Lire.i();
                while(choixNbEnergie<0 || choixNbEnergie>this.nbEnergie){       //Vérification que le choix d'énergie est compris entre 0 et nbEnergie
                    System.out.println("Nombre d'énergie impossible. Prendre combien d'unités d'énergies ? (Maximum = "+getNbEnergie()+")");choixNbEnergie=Lire.i();
                };
                System.out.println("Vous avez pris "+choixNbEnergie+" energie(s)\n");
                j.ajouteEnergie(choixNbEnergie);        // Ajout energie au joueur
                this.nbEnergie-=choixNbEnergie;         // Diminution energie réserve
                if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
                    this.setSymbole("⚛"+this.nbEnergie);
                else
                    this.setSymbole("E"+this.nbEnergie); // Change le nombre d'énergies affiché dans le symbole en foinction de l'affichage
            }
            else
                System.out.println("Vous continuez sans avoir pris d'énergie dans la réserve\n");
        }
    }

    public ReserveEnergie(int nbEnergie)
    {      //symbole normal "⚛"//  si des ? s'affichent utiliser comme symbole "E"
        super("","Réserve d'énergie");
        this.nbEnergie=nbEnergie;
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole("⚛"+nbEnergie);
        else
            this.setSymbole("E"+nbEnergie);
    }
    
    public ReserveEnergie() {this((int)(1+(ReserveEnergie.MAX)*Math.random()));} //nb d'unités d'énergie tirées au hasard entre 1 et ReserveEnergie.MAX
}
