package exploration;

public class Jeu
{
    private Plateau plateau;
    public Plateau getPlateau(){return this.plateau;}
    public void setPlateau(Plateau plateau){ this.plateau = plateau;}  

    private Categorie[] listeCategories;
    public void setListeCategories(Categorie[] listeCategories){this.listeCategories = listeCategories;}
    public Categorie[] getListeCategories(){return this.listeCategories;}

    private int proportionVides;

    /**
     * Proportion de salles vides par rapport à l'ensemble des salles : 50 signifie que 50% des salles du plateau sont vides
     * @return
     */
    public int getProportionVides(){return this.proportionVides;}
    private void setProportionVides(int proportionVides){this.proportionVides = proportionVides;}
    
    private boolean fini; // Gere si le jeu est fini ou non
    public boolean isFini(){return fini;}

    public void setFini(boolean fini) {
        this.fini = fini;
    }

    /**
     * Restitue le joueur qui a été créé dans le plateau (Le joueur pourrait aussi être référencé dans une instance de jeu)
     * @return
     */
    public Joueur getJoueur(){return this.getPlateau().getJoueur();}
    
    public void initJeu(){}

    public  Direction demandeDirection(){ //Demande une direction et continue si non valide
        System.out.println("Entrez une direction en combinant 'h','b','g','d' ou 'haut','bas','gauche','droite'");String dirText=Lire.S();
        while(!new Direction(dirText).isValide()){
            System.out.println("Direction invalide\nEntrez une direction en combinant 'h','b','g','d' ou 'haut','bas','gauche','droite'");dirText=Lire.S();}
        return new Direction(dirText);
    }

    public int choisirOutil(){  //Menu de choix d'outils utilisé si le joueur veut utiliser un outil
        System.out.println("Selectionnez un outil dans votre inventaire :\n");

        for(int indiceOutils = 0 ; indiceOutils < this.getJoueur().getOutils().getTaille(); indiceOutils++){
            if(indiceOutils == 3)
                System.out.print(this.getJoueur().getOutils().get(indiceOutils).getNature()+" ["+(indiceOutils+1)+"]\n");
            else{
                if (indiceOutils == this.getJoueur().getOutils().getTaille()-1)
                    System.out.print(this.getJoueur().getOutils().get(indiceOutils).getNature()+" ["+(indiceOutils+1)+"]");
                else{
                    if(indiceOutils==4)
                        System.out.print(this.getJoueur().getOutils().get(indiceOutils).getNature()+" ["+(indiceOutils+1)+"] / ");
                    else
                        System.out.print(this.getJoueur().getOutils().get(indiceOutils).getNature()+" ["+(indiceOutils+1)+"] / ");
                }
            }
        }
        int choixOutil=Lire.i();
        while(choixOutil <= 0 || choixOutil > this.getJoueur().getOutils().getTaille()) {
            System.out.println("Ce nombre ne correspond pas à un outil, recommencez");
            choixOutil = Lire.i();
        }
        return choixOutil;
    }

    public static void rafraichitConsole(){
        System.out.print("\033[H\033[2J");  // Normalement rafraichît la console pour un affichage plus clair mais marche pas dans la console IntelliJ
        System.out.flush();
    }
    
    public void joue()
    {
        rafraichitConsole();
        char choixAction;
        do{
            System.out.println(this.getPlateau());  //Affiche plateau
            System.out.println("Joueur : "+this.getJoueur().getNom());  //Affiche nom Joueur
            System.out.println("Batterie : "+this.getJoueur().getEnergie()+" "+Plateau.SYMBOLE_ENERGIE+" énergie(s)"); //Affiche nb energies restantes
            System.out.print("Inventaire : "+this.getJoueur().getNbGrenade()+" grenade(s)");
            for(int indiceOutils = 0 ; indiceOutils < this.getJoueur().getOutils().getTaille(); indiceOutils++){ //Affiche la liste des outils du joueur
                if (indiceOutils==3)
                    System.out.print(" / "+this.getJoueur().getOutils().get(indiceOutils).toString()+"\n");
                else {
                    if (indiceOutils==4)
                        System.out.print(this.getJoueur().getOutils().get(indiceOutils).toString());
                    else
                        System.out.print(" / "+this.getJoueur().getOutils().get(indiceOutils).toString());
                }
            }

            System.out.println("\n");

            //Menu
            System.out.println("Choisissez une action : Lancer une grenade [a] / Avancer [b] / Utiliser un outil [c] / Afficher le contenu des salles [d] / Abandonner [e]");choixAction=Lire.c();
            switch(choixAction){
                case 'a' : { //Lancer grenade
                    Direction d = demandeDirection();
                    rafraichitConsole();
                    this.getJoueur().lanceGrenade(d);
                    break;}

                case 'b' : { //Avancer
                    Direction d = demandeDirection();
                    rafraichitConsole();
                    this.getJoueur().avance(d);
                    break;}
                case 'c' : { //Utiliser un outil
                    if(this.getJoueur().getOutils().getTaille() <= 0) //Verifie si le joueur a encore des outils
                        System.out.println("\nVous n'avez plus d'outils\n");
                    else{
                        int choixOutil = choisirOutil();
                        System.out.println("\nVous prenez votre "+this.getJoueur().getOutils().get(choixOutil-1).getNature()+". Que voulez vous en faire ? : L'utiliser [a] /" +
                                " Lire le descriptif [b] / Quitter [c]");char choixSelecOutil=Lire.c();

                        while(choixSelecOutil!='a' && choixSelecOutil!='c'){ //Si le choix est b ou un autre char lire la desc de l'outil
                            System.out.println(this.getJoueur().getOutils().get(choixOutil-1).getInfos());
                            System.out.println("\nQue voulez vous en faire ? : L'utiliser [a] / Lire le descriptif [b] / Quitter [c]");choixSelecOutil=Lire.c();}
                        rafraichitConsole();
                        if(choixSelecOutil=='a')
                            this.getJoueur().getOutils().get(choixOutil-1).utilise(this.getJoueur());
                    }
                    break;}
                case 'd' :{
                    int choixVisible;
                    do{
                        System.out.println("\n[1] Afficher le contenu des salles / [2] Cacher le contenu des salles");
                        choixVisible=Lire.i();
                    } while(choixVisible!=1 && choixVisible!=2);
                this.getPlateau().setVisible(choixVisible==1);
                break;}
                case 'e' :{
                    rafraichitConsole();
                    System.out.println("\nVous avez abandonné !\n");
                    this.getJoueur().setPerdant(true);}
            }
        }while(!isFini());

        System.out.println(this.getPlateau()); // Affiche le plateau une derniere fois pour voir le joueur sur la sortie

        if(this.getJoueur().isPerdant()) //Verifie si le joueur a gagné ou perdu
            System.out.println("Vous avez perdu !");
        else
            System.out.println("Vous avez gagné !");
    }

    /**  Initialisations du jeu : création d'un plateau et exécution de joue()
            nbLig et nbCol sont les tailles du plateau (Elles peuvent être fixes au moins au début)
        */
    public Jeu(int nbLig, int nbCol, int proportionVides,String nomJoueur, Categorie... listeCategories)
    {
        this.fini=false; // Initialisation fini
        this.proportionVides=proportionVides;   //affectations
        this.listeCategories=listeCategories;
        if(nbLig%2 == 0) { // Nombres de lignes et de colonnes doit etre impair pour avoir une salle centrale
            throw new RuntimeException("Nombre de lignes pair");
        } else {
            if(nbCol%2 == 0)
                throw new RuntimeException("Nombre de colonnes pair");
            else {
                this.plateau = new Plateau(nbLig, nbCol, this,nomJoueur);     // création nouveau plateau
                this.initJeu();     // initialise paramètres jeu encore non initialisés
                this.joue();        // execution joue
            }
        }

    }
}
