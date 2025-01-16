package exploration;

public class Excavatrice extends Outil
{
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
    public Excavatrice()
    {
        super       //symbole normal "⧋"//  si des ? s'affichent utiliser comme symbole "EX"
                (
                        "",
                        "Excavatrice",
                        "Permet de creuser un mur sans faire exploser la mine qu'elle pourrait contenir. Mais la mine \n" +
                                "n'est pas désactivée et le joueur ne peut pas entrer dans la salle qui la contient sans la faire \n" +
                                "exploser. Son utilisation nécessite 8 unités d'énergie.",
                        8//coût énergétique de l'utilisation du scanner
                );
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole("⧋");
        else
            this.setSymbole("EX");
    }

    @Override
    public void activation(Joueur j)
    {
        System.out.println("\nVous utilisez une excavatrice et perdez "+this.getConsommationEnergetique()+" énergies");
        this.setDirectionCourante();
        while(!j.getPosition().isValide(this.getDirectionCourante())){ //Vérifie si la position dans la direction choisie n'est pas valide
            System.out.println("Vous ne pouvez pas creuser le mur du bord ! Choisissez une autre direction");
            this.setDirectionCourante();
        }
        j.getSalle().setAcces(this.directionCourante);      // Détruit le mur dans la direction choisie (=crée un acces dans la salle du joueur dans cette direction)
        j.getSalle().getVoisine(this.directionCourante).setVisible(true); //Affiche le symbole de l'objet sur le plateau
        // Affiche l'objet dans la salle ou rien si il n'y en a pas
        if(j.getSalle().getVoisine(this.directionCourante).getContenu()==null)
            System.out.println("La salle "+this.getDirectionCourante().toStringPhrase()+" est maintenant accessible mais ne contient rien\n");
        else
            System.out.println("La salle "+this.getDirectionCourante().toStringPhrase()+" est maintenant accessible et contient un(e) "+j.getSalle().getVoisine(this.directionCourante).getContenu().getNature()+"\n");
    }
}
