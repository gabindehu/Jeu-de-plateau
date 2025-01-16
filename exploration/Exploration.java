package exploration;

/**
 * Classe d'amorce qui créne une instance de jeu. À compléter avec d'autres objets/outils
 */
public class Exploration {

    /**
     * Création des différentes catégories d'objets et des proportions de leurs instances par rapport aux instances d'autres catégories dans le plateau
     * Par exemple, ici, dans le plateau il y aura deux fois plus de réserves d'énergie que de caisses de grenades
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String nomJoueur = Lire.S("Bienvenue dans le jeu ! Quel est votre nom ?");

        int difficulte=2;   // Sans changement du joueur difficulte intermédiare
        setDifficulte(difficulte);  //Changement des variables de classes qui représentent proportions et les paramètres du jeu

        int affichage=1;    //Gere entre 3 modes d'affichage plus ou moins simples si pb avec affichage
        setChoixAffichage(affichage);   //Changement de la variable de classe choixAffichage

        //Initialisation des categories en fonction des proportions changées dans la methode de classe setDifficulte
        Categorie
                grenades = new Categorie(proportionGrenade, CaisseGrenades.class),
                mines = new Categorie(proportionMines, Mine.class),
                energie = new Categorie(proportionReserveEnergie, ReserveEnergie.class),
                scannerUni = new Categorie(proportionScannerUni, ScannerUnidirectionnel.class),
                detecteurMine = new Categorie(proportionDetecteurMine, DetecteurMines.class),
                scannerOmni = new Categorie(proportionScannerOmni, ScannerOmnidirectionnel.class),
                detecteurMass = new Categorie(proportionDetecteurMass, DetecteurMassiqueUnidirectionnel.class),
                excavatrice = new Categorie(proportionExcavatrice, Excavatrice.class),
                deminage = new Categorie(proportionUniteDeminage, UniteDeminage.class);

        // Menu
        char choixMenu;
        do {
            System.out.println("\nQue voulez vous faire " + nomJoueur + " ? Difficulté : "+determinerDifficulte(difficulte)+" " +
                    "/ Affichage : "+determinerAffichage()+"\n\n" +
                    "[a] Jouer\n" +
                    "[b] Changer la difficulté\n" +
                    "[c] Changer votre nom\n" +
                    "[d] Changer l'affichage\n" +
                    "[e] Quitter le jeu");
            choixMenu = Lire.c();
            switch (choixMenu) {
                case 'a': { // Jouer avec les parametres de la difficulté choisie
                    Jeu jeu = new Jeu(nbLig, nbCol, proportionVide, nomJoueur, grenades, mines, energie, scannerUni, detecteurMine, scannerOmni, detecteurMass, excavatrice, deminage);
                    break;
                }
                case 'b': { // Choix difficulté
                    difficulte = Lire.i("\nChoisissez une difficulté : [1] Facile / [2] Intermédiaire / [3] Difficile / [4] Impossible");
                    while (difficulte < 0 || difficulte > 4) {
                        difficulte = Lire.i("\nDifficulté invalide\n\nChoisissez une difficulté : [1] Facile / [2] Intermédiaire / [3] Difficile / [4] Impossible");
                    }setDifficulte(difficulte);break;
                }
                case 'c': { //Changer nom
                    nomJoueur = Lire.S("\nEntrez votre nouveau nom");break;
                }
                case 'd': { //Changer l'affichage
                    affichage = Lire.i("\nChoisissez un affichage : [1] Symboles spéciaux et salles vides en espaces\n" +
                            "[2] Symboles spéciaux et salles vides en croix / [3] Lettres et chiffres uniquement");
                    while (affichage < 0 || affichage > 3) {
                        affichage = Lire.i("\nAffichage invalide\n\nChoisissez un affichage : [1] Symboles spéciaux et salles vides en espaces\n" +
                                "[2] Symboles spéciaux et salles vides en croix / [3] Lettres et chiffres uniquement");
                    }setChoixAffichage(affichage); // Change le static choixAffichage
                    break;
                }

            }
        } while (choixMenu != 'e'); //Touche pour arreter le programme
    }
    // Variables et methodes pour changer la difficulté et l'affichage
    static int proportionVide;
    static int nbLig, nbCol;
    static int proportionGrenade, proportionMines, proportionReserveEnergie, proportionScannerUni, proportionDetecteurMine ,
            proportionScannerOmni , proportionDetecteurMass , proportionExcavatrice , proportionUniteDeminage ;
    static int choixAffichage=1;

    public static void setChoixAffichage(int affichage){choixAffichage=affichage;}

    public static String determinerDifficulte(int difficulte) {
        switch (difficulte) {
            case 1:
                return "Facile";
            case 2:
                return "Intermédiaire";
            case 3:
                return "Difficile";
            case 4:
                return "Impossible";
            default:
                return "Inconnu";
        }
    }

    public static void setDifficulte(int difficulte){
        switch (difficulte) {
            case 1: {
                proportionGrenade = 7;
                proportionMines = 3;
                proportionReserveEnergie = 5;
                proportionScannerUni = 4;
                proportionDetecteurMine = 4;
                proportionScannerOmni = 5;
                proportionDetecteurMass = 5;
                proportionExcavatrice = 4;
                proportionUniteDeminage = 4;
                proportionVide = 55;
                nbLig = 5;
                nbCol = 5;
                break;
            }
            case 2: {
                proportionGrenade = 5;
                proportionMines = 4;
                proportionReserveEnergie = 5;
                proportionScannerUni = 3;
                proportionDetecteurMine = 3;
                proportionScannerOmni = 4;
                proportionDetecteurMass = 4;
                proportionExcavatrice = 3;
                proportionUniteDeminage = 2;
                proportionVide = 50;
                nbLig = 7;
                nbCol = 9;
                break;
            }
            case 3: {
                proportionGrenade = 4;
                proportionMines = 5;
                proportionReserveEnergie = 4;
                proportionScannerUni = 3;
                proportionDetecteurMine = 3;
                proportionScannerOmni = 4;
                proportionDetecteurMass = 3;
                proportionExcavatrice = 2;
                proportionUniteDeminage = 2;
                proportionVide = 40;
                nbLig = 11;
                nbCol = 15;
                break;
            }
            case 4: {
                proportionGrenade = 4;
                proportionMines = 7;
                proportionReserveEnergie = 3;
                proportionScannerUni = 2;
                proportionDetecteurMine = 2;
                proportionScannerOmni = 3;
                proportionDetecteurMass = 3;
                proportionExcavatrice = 1;
                proportionUniteDeminage = 1;
                proportionVide = 30;
                nbLig = 17;
                nbCol = 19;
                break;
            }
        }
    }
    private static String determinerAffichage() {
        switch (choixAffichage) {
            case 1:
                return "Symboles spéciaux et salles vides en espaces";
            case 2:
                return "Symboles spéciaux et salles vides en croix";
            case 3:
                return "Lettres et chiffres uniquement";
            default:
                return "Inconnu";
        }
    }

}
