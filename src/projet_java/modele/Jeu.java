package projet_java.modele;

import projet_java.ColonneHorsLimite;
import projet_java.PionDejaPresent;
import projet_java.CoordonneHorsLimite;


public abstract class Jeu
{

    public Grille g;

    public Jeu(int nbLigne, int nbColonne)
    {
        this.g = new Grille(nbLigne,nbColonne);
    }

    public abstract boolean win(Joueur j);

    public abstract void placement(int[] coord, Joueur j) throws PionDejaPresent, ColonneHorsLimite, CoordonneHorsLimite;

    public abstract void displayGrilleP();

    public abstract void displayGrilleM();



}
