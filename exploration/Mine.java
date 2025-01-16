package exploration;

public class Mine extends Objet
{
    @Override
    public void interaction(Joueur j) {
        System.out.print("\nElle explose et vous tue !\n\n");
        j.setPerdant(true);
    }

    public Mine(){
        super("","Mine");
        if(Exploration.choixAffichage==1 || Exploration.choixAffichage==2)
            this.setSymbole(" * ");
        else
            this.setSymbole("* ");
    } //symbole normal " * "//  si des ? s'affichent utiliser comme symbole "* "
}
