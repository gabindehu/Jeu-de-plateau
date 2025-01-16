package exploration;

public class ScannerUnidirectionnel extends Outil
{
    private static final int MARGE_ERREUR = 20;
    private int getMarge(){return ScannerUnidirectionnel.MARGE_ERREUR;}
    
    private Direction directionCourante;
    public Direction getDirectionCourante()
    {
        return this.directionCourante;
    }
    protected void setDirectionCourante()
    {
        do
        {
            this.directionCourante = new Direction(Lire.S("Entrez une direction en combinant 'h','b','g','d' ou 'haut','bas','gauche','droite'"));
        }while(!directionCourante.isValide());
    }
    public ScannerUnidirectionnel()
    {
        super       //symbole normal "⊟ "//  si des ? s'affichent utiliser comme symbole "SU"
        (
                "",
                "Scanner unidirectionnel",
                "Détecte à travers les murs la distance en nombre de salles vides traversée à laquelle se situe le \n" +
                        "premier objet ou le mur du plateau dans une direction donnée. L'estimation de la distance est \n" +
                        "à "+MARGE_ERREUR+"% de la distance près . L'utilisation de cet outil nécessite 2 unités d'énergie.",
                2//coût énergétique de l'utilisation du scanner
        );
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole("⊟ ");
        else
            this.setSymbole("SU");
    }

    /** Recherche dans une direction demandée au joueur jusqu'à tomber sur une position null (en dehors de l'enceinte)
            ou une salle contenant un objet. Le nombre de salles parcourues est affiché à 20% près
        */
    @Override
    public void activation(Joueur j)
    {
        System.out.println("\nVous utilisez un scanner unidirectionnel et perdez "+this.getConsommationEnergetique()+" énergies");
        this.setDirectionCourante();
        int nbSalleAvantObjet=0;
        boolean rencontreObjet=false;   // boolean pour savoir si le scanner a rencontré une position invalide sans trouver un objet
        Salle salleSuivante=j.getSalle().getVoisine(this.getDirectionCourante());

        while(salleSuivante != null){ //Tant que la salle suivante ne depasse pas les limites du tableau
            if(salleSuivante.getContenu() != null) { //Si la salle suivante a un objet la boucle while s'arrete
                rencontreObjet=true;
                break;
            }
            nbSalleAvantObjet++; //La salle suivante n'a pas d'objet on ajoute 1 au compteur
            salleSuivante = salleSuivante.getVoisine(this.getDirectionCourante()); //On actualise la salleSuivante dans la meme direction
        }
        nbSalleAvantObjet++; //Pour que la salle prise en compte soit celle avec l'objet et pas celle juste avant
        int erreur=(int)(-(nbSalleAvantObjet*MARGE_ERREUR*0.01)+(2*nbSalleAvantObjet*MARGE_ERREUR*0.01+1)*Math.random()); // Calcule une valeur aléatoire en plus/moins 20% du nb de salles
        if(rencontreObjet)
            System.out.println("La prochaine salle avec un objet se situe à "+(nbSalleAvantObjet+erreur)+" salle(s) dans la direction "+this.directionCourante.toStringPhrase()+"\n");
        else
            System.out.println("Il n'y a pas d'objets dans la direction "+this.directionCourante.toStringPhrase()+" !\n"); //Si il n'y a pas d'objet texte différent
    }
}
