package exploration;

import exploration.Joueur;
import exploration.Objet;

public abstract class Outil extends Objet
{    
    private int conso;
    public int getConsommationEnergetique(){return this.conso;}
    protected void setConsommationEnergetique(int conso){this.conso = conso;}

    private String descriptif;
    public String getDescriptif(){return this.descriptif;}
    protected void setDescriptif(String descriptif){this.descriptif = descriptif;}

    public boolean isUtilisablePar(Joueur j)
    {
        return j.getEnergie()>=this.getConsommationEnergetique();        // a vérification que le crédit énergétique du joueur est suffisant pour utiliser l'outil
    }
    
    /**
     * Restitue des infos sur l'outil qui serviront à afficher un men
     * @return
     */
    public String getInfos(){return "("+this.getSymbole()+") "+this.getNature()+" : "+this.getDescriptif();}
    
    @Override
    public void interaction(Joueur j){ //Activé quand le joueur trouve un outil dans une salle propose de prendre ou non l'outil
        System.out.print("Souhaitez vous prendre l'outil [a] ou continuer à jouer [b]?\n");
        char choix=Lire.c();
        if(choix=='a')
            j.recupere(this);
        else
            System.out.println("\nVous continuez a jouer sans prendre l'outil : "+this.getNature()+"\n");
    }

    /**
            Utilisation de l'outil. Il faut vérifier que le joueur a assez d'énergie (Sinon, faire un message d'erreur),
            activer l'outil (différemment suivant l'outil) et diminuer l'énergie du joueur en conséquence
        */
    public void utilise(Joueur j)
    {
        if(this.isUtilisablePar(j)) {
            this.activation(j);
            j.enleveEnergie(this.getConsommationEnergetique()); //Consomme l'énergie du joueur
            if(!j.getNom().equals("GODMODE")) //??
                j.getOutils().enleve(this);     //Enleve l'outil de son inventaire
        }
        else
            System.out.println("\nVous n'avez pas assez d'énergie pour utiliser cet objet !\n");
    }
    
    public abstract void activation(Joueur j); // activation spécifique à chaque outil
    
    public Outil(String symbole,String nature, String descriptif, int conso)
    {
        super(symbole,nature);
        this.setDescriptif(descriptif);
        this.setConsommationEnergetique(conso);
    }

    @Override
    public boolean equals(Object autre)
    {
        if (this == autre) return true;
        if (autre == null) return false;
        if (getClass() != autre.getClass())return false;
        return true;
    }

    @Override
    public String toString() { // Affiche la nature de l'outil avec sa conso energie (pour l'inventaire)
        return this.getNature()+" ("+this.getConsommationEnergetique()+Plateau.SYMBOLE_ENERGIE+")";
    }
}
