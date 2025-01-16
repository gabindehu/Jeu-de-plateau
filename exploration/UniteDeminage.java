package exploration;

public class UniteDeminage extends Outil
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
    public UniteDeminage()
    {
        super       //symbole normal "⎊ "//  si des ? s'affichent utiliser comme symbole "UD"
                (
                        "",
                        "Unité de déminage",
                        "Désactive moyennant 2 unités d'énergie une mine située dans une salle contiguë accessible \n" +
                                "(le mur entre les salles doit déjà avoir été détruit).",
                        2//coût énergétique de l'utilisation du scanner
                );
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole("⎊ ");
        else
            this.setSymbole("UD");
    }

    /** Expliquer
     */
    @Override
    public void activation(Joueur j)
    {
        System.out.println("\nVous utilisez une unité de déminage et perdez "+this.getConsommationEnergetique()+" énergies");
        this.setDirectionCourante();
        while(!j.getPosition().isValide(this.getDirectionCourante())){ //Demande une direction au joueur et vérifie que la position suivante n'est pas le bord
            System.out.println("Vous ne pouvez pas déminer le bord ! Choisissez une autre direction");
            this.setDirectionCourante();
        }
        if(!j.getSalle().isPossible(this.directionCourante)) //Verifie l'acces à la salle choisie si non le démineur est perdu
            System.out.println("Le mur de la salle "+this.directionCourante.toStringPhrase()+" n'a pas été détruit. Vous perdez votre unité de déminage\n");
        else{
            if(j.getSalle().getVoisine(this.directionCourante).getContenu() == null)  //Si rien dans la salle choisie demineur perdu
                System.out.println("Il n'y a rien dans la salle "+this.directionCourante.toStringPhrase()+". Vous perdez votre unité de déminage\n");
            else{
                if(!j.getSalle().getVoisine(this.directionCourante).getContenu().getClass().equals(Mine.class)) //Verifie si l'objet n'est pas une mine demineur perdu
                    System.out.println("Il y a un(e) "+j.getSalle().getVoisine(this.directionCourante).getContenu().getNature()+" dans la " +
                            "salle "+this.directionCourante.toStringPhrase()+". Vous perdez votre unité de déminage\n");
                else{
                    System.out.println("Vous désactivez la mine présente dans la salle "+this.directionCourante.toStringPhrase()+"\n"); //mine desactivée
                    j.getSalle().getVoisine(this.directionCourante).setObjet(null);//Plus d'objet dans la salle
                }
            }
        }
    }

}
