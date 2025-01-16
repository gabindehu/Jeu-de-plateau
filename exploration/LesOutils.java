package exploration;
import java.util.ArrayList;

public class LesOutils
{
    private ArrayList<Outil> liste;
    public int getTaille(){return liste.size();}
    public Outil get(int i){return liste.get(i);}
    public void setListe(ArrayList<Outil> liste){this.liste = liste;}
    public void init()  // outils ajoutés dès le départ
    {
        liste = new ArrayList<Outil>(); // Crée une liste d'outils vide

        if(this.getProprietaire().getNom().equals("GODMODE")){ //??
            liste.add(new ScannerUnidirectionnel());
            liste.add(new DetecteurMines());
            liste.add(new Excavatrice());
            liste.add(new DetecteurMassiqueUnidirectionnel());
            liste.add(new ScannerOmnidirectionnel());
            liste.add(new UniteDeminage());
        }
        else{
            liste.add(new ScannerUnidirectionnel());
            liste.add(new DetecteurMines());
            liste.add(new ScannerOmnidirectionnel());
        }
    }
    public void ajoute(Outil o)
    {
        if(this.liste.contains(o)) System.out.println("\nMais "+this.getProprietaire().getNom()+" est déjà en" +
                " possession d'un(e) "+o.getNature()+"\n"); //Le joueur a deja l'outil
        else {
            this.liste.add(o);
            System.out.println("\nVous prenez l'objet : "+o.getNature()+"\n");
            this.getProprietaire().getSalle().setObjet(null); // Si l'outil est pris la salle du joueur n'a plus d'objets
        }
    }
    public void enleve(Outil o){this.liste.remove(o);} //Enleve outil quand il est utilisé

    private Joueur proprietaire;
    public Joueur getProprietaire(){return this.proprietaire;}
    private void setProprietaire(Joueur proprietaire){this.proprietaire = proprietaire;}
    
    public LesOutils(Joueur proprietaire)
    {
        this.setProprietaire(proprietaire);
        init();
    }
}
