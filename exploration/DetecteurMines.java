package exploration;

public class DetecteurMines extends Outil
{
    //@Override
    //public void interaction(Joueur j){}     //interaction faite dans classe outil
    public DetecteurMines()
    {
        super   //symbole normal "⧆ "//  si des ? s'affichent utiliser comme symbole "DM"
        (
                "",
                "Détecteur de mines",
                "Permet de connaître le nombre total de mines qui se situent dans les salles contiguës. Chaque \n" +
                        " utilisation de ce détecteur consomme 3 unités d'énergie.",
                3     //coût énergétique de la détection des mines
        );
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole("⧆ ");
        else
            this.setSymbole("DM");
    }

    /** Explore les salles autour de la salle du joueur
     (direction à droite par rapport à la position de la salle du joueur,
     puis positions successives qui sont fondées sur les directions successives dans le sens trigonométrique : voir direction et position),
     compte les mines et affiche le résultat
     */
    @Override
    public void activation(Joueur j)
    {
        System.out.println("\nVous utilisez un détecteur de mines et perdez "+this.getConsommationEnergetique()+" énergies.");
        int compteurMine=0;
        for(int rang=0;rang<8;rang++)
            if(j.getSalle().getVoisine(new Direction(rang)) != null)        //vérifie si la salle existe
                if(j.getSalle().getVoisine(new Direction(rang)).getContenu() != null)       //vérifie si la salle a un objet
                    if(j.getSalle().getVoisine(new Direction(rang)).getContenu().getClass().equals(Mine.class))     //vérifie si cet objet est une mine
                        compteurMine++;
        System.out.println("\nIl y a "+compteurMine+" mine(s) dans les salles contiguës\n");
    }
}
