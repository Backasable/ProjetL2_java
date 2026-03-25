package projet_java.modele;

import java.util.ArrayList;
import java.util.Random;

public abstract class BrainIA
{
    protected Grille g;
    protected Random random;
    protected Difficulte difficulte;



    public BrainIA(Grille g, Difficulte difficulte)
    {
        this.g = g;
        this.random = new Random();
        this.difficulte = difficulte;


    }

    public abstract void entrerCoupIA(Jeu jeu, Joueur ia, Joueur j);




//========================
}
