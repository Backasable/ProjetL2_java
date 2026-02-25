package modele;
public class Grille
{
    private int[][] plateau;
    private int nbLigne;
    private int nbColonne;


    public Grille(int nbligne, int nbColonne)
    {
        this.nbLigne = nbligne;
        this.nbColonne = nbColonne;
        this.plateau = new int[nbLigne][nbColonne];

    }

    // Affiche la grille pour le Puissance 4
    public void displayGrilleP()
    {
        // Affichage des coordonné des colonnes
        System.out.print("  ");

        for (int cptCol = 0; cptCol< nbColonne; cptCol++)
        {
            System.out.print(cptCol + " ");
        }
        System.out.println();

        // Début affichage du plateau
        for (int cptLig = 0; cptLig< nbLigne; cptLig++)  // cpt = compteur
        {
            System.out.print(cptLig +" ");  // <- affichage des coordonné des lignes

            for (int cptCol = 0; cptCol < nbColonne; cptCol++)
            {


                if (plateau[cptLig][cptCol] == 0)
                {
                    System.out.print("- ");

                } else if (plateau[cptLig][cptCol] == 1) // 1 = id du joueur1
                {
                    System.out.print("J "); // pion Jaune
                }
                else if(plateau[cptLig][cptCol] == 2) // 2 = id du joueur2
                {
                    System.out.print("R "); // pion Rouge
                }
            }
            System.out.println();
        }
    }

    // Grille pour le morpion
    public void displayGrilleM()
    {
        // Affichage des coordonné des colonnes
        System.out.print("  ");

        for (int cptCol = 0; cptCol< nbColonne; cptCol++)
        {
            System.out.print(cptCol + " ");
        }
        System.out.println();


        // Début affichage de la grille
        for (int cptLig = 0; cptLig< nbLigne; cptLig++)  // cpt = compteur
        {
            System.out.print(cptLig +" ");  // <- affichage des coordonné des lignes

            for (int cptCol = 0; cptCol < nbColonne; cptCol++)
            {

                if (plateau[cptLig][cptCol] == 0)
                {
                    System.out.print("- ");

                } else if (plateau[cptLig][cptCol] == 1) // 1 = id du joueur1
                {
                    System.out.print("X ");
                }
                else if(plateau[cptLig][cptCol] == 2) // 2 = id du joueur2
                {
                    System.out.print("O ");
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

    public void saisirVal(int ligne, int colonne, int idJoueur)
    {
        plateau[ligne][colonne] = idJoueur;
    }

    // On check les coordonné entrer par le Joueur
    public boolean CheckCoord(int ligne, int colonne, int idJoueur)
    {
        // Pourquoi -1 car le length compte à partir de 1 alors que la taille du tableau commence à 0
        if (ligne> plateau.length -1 || colonne > plateau[ligne].length -1 )
        {
            return false;
        }
        else if(plateau[ligne][colonne] != 0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

}
