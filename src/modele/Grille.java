package projet_java.modele;

import projet_java.PionDejaPresent;

public class Grille
{
    public int[][] plateau;  // <- public car j'en ai besoin dans Puissance_4
    private int nbLigne;
    private int nbColonne;


    public Grille(int nbligne, int nbColonne)
    {
        this.nbLigne = nbligne;
        this.nbColonne = nbColonne;
        this.plateau = new int[nbLigne][nbColonne];

    }
    public int getNbcol()
    {
        return this.nbColonne;
    }

    public int getNbLigne()
    {
        return this.nbLigne;
    }



    // Efface la grille
    public boolean checkGrillefull()
    {
        for (int cptLig = 0; cptLig< nbLigne; cptLig++)  // cpt = compteur
        {
            for (int cptCol = 0; cptCol < nbColonne; cptCol++)
            {
                // Si on trouve ne serait-ce qu'une colonne qui est vide alors on retourne faux
                if(this.plateau[cptLig][cptCol] == 0)
                {
                    return false;
                }

            }
        }
        return true;
    }

    // Saisir les val pour le Morpion dans le tableau
    // Note :
    // coord[0] -> la ligne entrer par User
    // coord[1] -> la colonne enter par User
    public void saisirVal(int[] coord, int idJoueur) throws PionDejaPresent
    {
        // On vérif si il n'y pas déjà un pion sur cette case
        if (!(plateau[coord[0]][coord[1]] == 0))
        {
            throw new PionDejaPresent("Pion déjà Present : ");
        }
        else
        {
            plateau[coord[0]][coord[1]] = idJoueur;
        }

    }



//=====================================================================================================
    // Stratégie pour simuler que le pion déscende jusqu'à touché
    // le pion de la case d'en dessous :

    // Idée :
    // Vérifie si la colonne indiquer est vide puis vérifie si celle en dessous, est vide ou non
    // et on réitère jusqu'à trouver la colonne
    // pour laquelle, la colonne qui suit n'est plus vide
    // Et ainsi renvoyer les coordonnés de la colonne qui est vide
    public int[] findCaseVide(int col)
    {
        int[] CaseVide = new int[2];
        int ligne = 0; // <- la ligne de départ (Rappel -1 car, indice en java commence à 0 et le nb ligne est det avec lenght qui commence à 1)

        {
            // On vérifie tjr si on est pas hors limite !!!
            // Donc on regarde si, la valeur de ligne qu'on incrément est toujours dans l'interval de la taille du tableau
            //
            // ET Ensuite :
            // si la prochaine colonne en dessous est vide.

            while ( (0 <= ligne+1 && ligne+1 < this.nbLigne) && plateau[ligne+1][col] == 0)
            {
                // Alors on incrémente ligne pour descendre dans notre tab
                // et on réitère jusqu'à :
                //  - soit avoir atteint la limite du tab
                //  - soit avoir trouver une case vide pour laquelle la prochaine case est pleine

                ligne++;
            }

            // Là on est sorti de la boucle donc ça veut dire qu'on a trouver la case vide où notre pion va aller
            // On stock donc ses coordonnés dans un tableau
            CaseVide[0] = ligne;
            CaseVide[1] = col;

            return CaseVide;
        }
    }
//===================================================================================

}