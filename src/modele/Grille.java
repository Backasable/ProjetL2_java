package modele;

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

    // Affiche la grille pour le Puissance 4
    public void displayGrilleP()
    {
        System.out.println();
        System.out.println("************************");
        System.out.print(" ");
        for (int cptCol = 0; cptCol< nbColonne; cptCol++)
        {
            System.out.print(" "+ cptCol + " ");
        }
        System.out.println();

        System.out.println("-----------------------");  // <- On dessine la ligne du haut du Puiss 4

        // Début affichage du plateau
        for (int cptLig = 0; cptLig< nbLigne; cptLig++)  // cpt = compteur
        {

            System.out.print("|");
            for (int cptCol = 0; cptCol < nbColonne; cptCol++)
            {

                if (plateau[cptLig][cptCol] == 0)
                {
                    System.out.print(" - ");

                } else if (plateau[cptLig][cptCol] == 1) // 1 = id du joueur1
                {
                    System.out.print(" J "); // pion Jaune
                }
                else if(plateau[cptLig][cptCol] == 2) // 2 = id du joueur2
                {
                    System.out.print(" R "); // pion Rouge
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-----------------------"); // <- dessine la ligne de la grille du bas

    }

    // Grille pour le morpion
    public void displayGrilleM()
    {
        // Affichage des coordonné des colonnes
        System.out.print("   ");


        for (int cptCol = 0; cptCol< nbColonne; cptCol++)
        {
            System.out.print(" "+cptCol + " ");
        }
        System.out.println();



        // Début affichage de la grille
        for (int cptLig = 0; cptLig< nbLigne; cptLig++)  // cpt = compteur
        {
            System.out.print(" " +cptLig +" ");  // <- affichage des coordonné des lignes

            for (int cptCol = 0; cptCol < nbColonne; cptCol++)
            {

                if (plateau[cptLig][cptCol] == 0)
                {
                    System.out.print(" - ");

                } else if (plateau[cptLig][cptCol] == 1) // 1 = id du joueur1
                {
                    System.out.print(" X ");
                }
                else if(plateau[cptLig][cptCol] == 2) // 2 = id du joueur2
                {
                    System.out.print(" O ");
                }
            }
            System.out.println();

        }

    }

    // Efface la grille
    public void clearGrille()
    {
        for (int cptLig = 0; cptLig< nbLigne; cptLig++)  // cpt = compteur
        {
            for (int cptCol = 0; cptCol < nbColonne; cptCol++)
            {
                if(this.plateau[cptLig][cptCol] != 0)
                {
                    this.plateau[cptLig][cptCol] = 0;
                }
            }
        }
    }

    // Saisir les val pour le Morpion dans le tableau
    // Note :
    // coord[0] -> la ligne entrer par User
    // coord[1] -> la colonne enter par User
    public void saisirVal(int[] coord, int idJoueur) throws PionDejaPresent
    {
        // On vérif si il n'y pas déjà un pion sur cette case
        if (!(plateau[coord[0]][coord[1]] != 0))
        {
            throw new PionDejaPresent("Pion déjà Present : ");
        }
        else
        {
            plateau[coord[0]][coord[1]] = idJoueur;
        }

    }

    // On check les coordonné entrer par le Joueur
    public boolean checkCoordM(int[] coord)
    {

        // Rappel :
        // coord[0] -> la ligne entrer par User
        // coord[1] -> la colonne enter par User


        // Si la ligne saisi par User et > au nb de ligne du plateau alors faux
        // même chose pour les colonne
        if (coord[0] > this.nbLigne || coord[1] > this.nbColonne)
        {

            return false;
        }
        // Verif si la case n'a pas déjà un pion
        else if(plateau[coord[0]][coord[1]] != 0)
        {
            System.out.println("pion déjà présent ");
            return false;

        }
        else
        {
            return true;
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
