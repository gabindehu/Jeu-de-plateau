package exploration;

public class ScannerOmnidirectionnel extends Outil
{
    public ScannerOmnidirectionnel()
    {
        super       //symbole normal "⊞ "//  si des ? s'affichent utiliser comme symbole "SO"
                (
                        "",
                        "Scanner omnidirectionnel",
                        "Permet de détecter au travers des murs le nombre de salles contiguës contenant un objet. Son \n" +
                                " utilisation nécessite 1 unité d'énergie",
                        1//coût énergétique de l'utilisation du scanner
                );
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole("⊞ ");
        else
            this.setSymbole("SO");
    }

    /** Expliquer
     */
    @Override
    public void activation(Joueur j)
    {
        System.out.println("\nVous utilisez un scanner omnidirectionnel et perdez "+this.getConsommationEnergetique()+" énergie");
        int compteurObjet=0;
        for(int rang=0;rang<8;rang++)
            if(j.getSalle().getVoisine(new Direction(rang)) != null)        //vérifie si la salle existe
                if(j.getSalle().getVoisine(new Direction(rang)).getContenu() != null)       //vérifie si la salle a un objet
                    compteurObjet++;
        System.out.println("\n"+compteurObjet+" salles autour de vous contiennent un objet\n");
    }
}
