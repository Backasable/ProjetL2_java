package projet_java.modele;


import projet_java.ColonneHorsLimite;
import projet_java.CoordonneHorsLimite;
import projet_java.PionDejaPresent;
import projet_java.modele.Jeu;

public class Morpion extends Jeu
{

    public Morpion(int nbligne, int nbcolonne)
    {
        super(nbligne,nbcolonne);
    }


    public boolean equals(int entier1, int entier2, int entier3, int idJoueur){
        boolean compare = false;

        if(entier1 == entier2 && entier2 == entier3 && entier3 == idJoueur){
            compare = true;
        }
        return compare;

    }


    public boolean win(Joueur j){
        //Ligne
        int idJoueur = j.getidJoueur();
        int nbLigne = g.getNbLigne();
        int nbColonne = g.getNbcol();

        for (int cptLignes = 0; cptLignes < nbLigne; cptLignes++){
            if(equals(g.plateau[cptLignes][0], g.plateau[cptLignes][1], g.plateau[cptLignes][2], idJoueur)){
                return true;
            }
        }
        //colonne
        for(int cptColonnes = 0; cptColonnes < nbColonne; cptColonnes++) {
            if (equals(g.plateau[0][cptColonnes], g.plateau[1][cptColonnes],g.plateau[2][cptColonnes], idJoueur)) {
                return true;
            }
        }
        // Diagonal
        if(equals(g.plateau[0][0], g.plateau[1][1], g.plateau[2][2], idJoueur) || equals(g.plateau[2][0], g.plateau[1][1], g.plateau[0][2], idJoueur)){
            return true;
        }

        return false;

    }

    // On check les coordonné entrer par le Joueur
    public boolean checkCoordM(int[] coord) throws CoordonneHorsLimite
    {

        // Rappel :
        // coord[0] -> la ligne entrer par User
        // coord[1] -> la colonne enter par User


        // Si les coordonner ne sont pas dans l'interval de la taille de la grille
        //  alors faux
        if (!(0 <= coord[0] && coord[0] < g.getNbLigne() || 0 <= coord[1] && coord[1] < g.getNbcol()))
        {

            return false;
        }
        else
        {
            return true;
        }

    }

    public void placement(int[] coord, Joueur j) throws PionDejaPresent, CoordonneHorsLimite, ColonneHorsLimite
    {
        if(!(checkCoordM(coord)))
        {
            throw new CoordonneHorsLimite("Vous avez saisie des coordonné invalide : " + coord[0] +" "+ coord[1]);
        }
        else
        {
            g.saisirVal(coord, j.getidJoueur());
        }
    }

}