package exploration;

public class DetecteurMassiqueUnidirectionnel extends Outil
{
    private static final int MARGE_ERREUR = 10;
    private int getMarge(){return DetecteurMassiqueUnidirectionnel.MARGE_ERREUR;}

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
    public DetecteurMassiqueUnidirectionnel()
    {
        super           //symbole normal "⧈ "//  si des ? s'affichent utiliser comme symbole "DM"
                (
                        "",
                        "Détecteur massique unidirectionnel",
                        "Permet de connaître à "+MARGE_ERREUR+"% près le nombre d'objets tous types confondus  situés dans une \n" +
                                " direction donnée. Son utilisation nécessite 2 unités d'énergie.",
                        2//coût énergétique de l'utilisation du scanner
                );
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole("⧈ ");
        else
            this.setSymbole("DU");
    }

    @Override
    public void activation(Joueur j)
    {
        System.out.println("Vous utilisez un détecteur massique unidirectionnel et perdez "+this.getConsommationEnergetique()+" énergies");
        this.setDirectionCourante(); // Demande la direction d'utilisation
        int nbSalleAvecObjet=0;
        Salle salleSuivante=j.getSalle().getVoisine(this.getDirectionCourante()); //Prend la salle suivante dans la direction donnée

        while(salleSuivante != null){ //Tant que la salle suivante ne depasse pas les limites du tableau
            if(salleSuivante.getContenu() != null) //Si la salle suivante a un objet on ajoute 1 a nbSalleAvecObjet
                nbSalleAvecObjet++;
            salleSuivante = salleSuivante.getVoisine(this.getDirectionCourante()); //On actualise la salleSuivante dans la meme direction
        }
        int erreur=(int)(-(nbSalleAvecObjet*MARGE_ERREUR*0.01)+(2*nbSalleAvecObjet*MARGE_ERREUR*0.01+1)*Math.random()); // Calcule une valeur aléatoire en plus/moins 10% du nb de salles

        System.out.println("Il y a "+nbSalleAvecObjet+" salle(s) avec un objet dans la direction "+this.directionCourante.toStringPhrase()+" !");
    }
}
