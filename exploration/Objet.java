package exploration;

import java.util.Objects;

/**
 * Regroupe les points communs à tous les objets : un symbole, une nature (Mine par exemple) et la possibilité d'interagir avec le joueur
 */
public abstract class Objet
{
    private String symbole;
    public String getSymbole(){return this.symbole;}
    public void setSymbole(String symbole){this.symbole = symbole;} // mis en public pour changer nombre energies
   
    private String nature;
    public String getNature(){return this.nature;}
    private void setNature(String nature){this.nature = nature;}
    
    public abstract void interaction(Joueur j);

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.symbole);
        return hash;
    }
    public Objet(String symbole, String nature)
    {
        this.setSymbole(symbole);
        this.setNature(nature);
    }

    @Override
    public boolean equals(Object autre)
    {
        if (this == autre) return true;
        if (autre == null) return false;
        if (getClass() != autre.getClass())return false;
        final Outil autreObjet = (Outil) autre;
        return Objects.equals(this.getSymbole(), autreObjet.getSymbole());
    }
    
    @Override
    public String toString()
    {
        return this.getSymbole();
    }
}
