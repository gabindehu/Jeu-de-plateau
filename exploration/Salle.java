package exploration;

import java.util.ArrayList;
import java.util.Arrays;

public class Salle
{
    private boolean visible;

    /**
     * restitue true si la salle est visible
     * @return
     */
    public boolean isVisible()
    {
        return this.visible||this.getPlateau().isVisible();
    }
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    private Position position;
    public Position getPosition(){return this.position;}
    private void setPosition(Position position){this.position = position;}
    
    private Plateau plateau;
    public Plateau getPlateau()
    {
        return this.plateau;
    }
    private void setPlateau(Plateau p){this.plateau = p;}

    private Objet objet;

    /**
     * Restitue la référence à l'objet contenu dans la salle… ou null
     * @return une référence à un objet
     */
    public Objet getContenu() {return this.objet;}
    public void setObjet(Objet objet){this.objet = objet;}

    public boolean isVide(){return this.getContenu()==null;};
    
    /**
        Gère les accès aux salles contigües (horizontalement et verticalement). Au départ, la salle est emmurée : aucun accès.
        Quand un mur est détruit, on crée un accès vers la salle contiguë et un accès inverse à partir de cette salle
    */
    private ArrayList<Direction> acces;
    public boolean isPossible(Direction d){return acces.contains(d);}
    private void initAcces(){this.acces = new ArrayList<Direction>();}//Création d'une liste d'accès vide

    /**
     * Crée un accès vers la salle contiguë dans la direction donnée… si l'accès n'existe pas déjà et si la salle contiguë existe
     * L'accès inverse est aussi ajouté (direction inverse ajoutée à la liste d'accès de la salle contiguë)
     * @param d La direction : haut, bas, gauche, droite
     */
    public void setAcces(Direction d)
    {
        // Vérification si l'accès dans la direction donnée est déjà présent
        if (!acces.contains(d)) {
            // Obtention de la salle suivante dans la direction donnée
            Salle salleSuivante = this.getVoisine(d);

            // Vérification si la salle suivante existe
            if (salleSuivante != null) {
                // Ajout de l'accès dans la direction donnée
                this.acces.add(d);

                // Ajout de l'accès inverse dans la salle suivante
                salleSuivante.acces.add(d.getInverse());
            }
        }
    }
    public String getSymbole()
    {
        if(this.position.equals(this.getPlateau().getPosJoueur())){ //Si c'est la position du joueur on affiche son symbole
            return Plateau.SYMBOLE_JOUEUR; }
        else {
            if(this.position==this.getPlateau().getSortie())  //= des adresses mais marche quand même, affiche le symbole de la sortie
                return Plateau.SYMBOLE_SORTIE;
            else{
                if(this.objet!=null)    //Si la salle a un objet on renvoie son symbole dans objet
                    return this.objet.getSymbole();
                else{
                    return Plateau.SYMBOLE_SALLE_VIDE; //Sinon le symbole d'une salle vide
                }
            }
        }
        // renvoie des espaces ou le toString de l'objet contenu dans la salle
    }
    
    public void entree(Joueur j)
    {
        if(j.getSalle().getContenu()!=null){  // Si il y a un objet dans la salle lance son interaction
            System.out.print("\nIl y a un(e) "+j.getSalle().getContenu().getNature()+" dans la salle.\n");
            j.getSalle().getContenu().interaction(j);
        }
        else{
            if(this.getPlateau().getSortie().equals(j.getPosition())) //Si le joueur a trouvé la sortie
                System.out.println("\nVous avez trouvé la sortie !\n");
            else
                System.out.println("\nLa salle est vide.\n"); //Salle vide
        }
        // activé quand le joueur se positionne sur la salle. Déclenche l'intéraction avec l'objet contenu dans la salle s'il y en a un
    }
    
    @Override
    public  String toString()   //Si la salle est visible renvoie son symbole sinon le symbole d'une salle invisible
    {
        if(this.isVisible())
            return this.getSymbole();
        else
            return Plateau.SYMBOLE_SALLE_INVISIBLE; // salle non visible

    }
    
    /**
     * Restitue la salle contiguë dans la direction donnée… ou null si on sort du plateau
     * @param d
     * @return
     */
    public Salle getVoisine(Direction d)
    {
        if(this.getPosition().isValide(d))
            return this.getPlateau().getSalle(this.getPosition().getSuivante(d));
        else
            return null;
    }

    private static boolean SallesToutesAccessibles=false; //Debug permet d'acceder a toutes les salles si vrai
        
    public Salle(Position pos, Plateau p, Objet o)
    {
        this.position=pos;
        this.plateau=p;
        this.objet=o;
        this.initAcces();       // initialisations
        this.setVisible(false); // au départ, la salle n'est pas visible

        if(SallesToutesAccessibles){
            this.acces.add(new Direction("h"));
            this.acces.add(new Direction("b"));
            this.acces.add(new Direction("g"));
            this.acces.add(new Direction("d"));
        }
    }
    public Salle(Position pos, Plateau p)
    {
        this(pos,p,null);
    }
}

