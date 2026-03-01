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
        // Verif si la case n'a pas déjà un pion
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




    // Grille pour le morpion
    public void displayGrilleM()
    {

        System.out.println();
        System.out.println("________Etat Du Jeu________");
        System.out.println();
        System.out.print("    ");

        // Affichage des coordonné des colonnes
        for (int cptCol = 1; cptCol< g.getNbcol()+1; cptCol++) // le +1 même principe que dans displayGrilleP
        {
            System.out.print(" "+cptCol + " ");
        }
        System.out.println();
        System.out.println("   -----------");




        // Début affichage de la grille
        int cptAffCoordLigne = 1;
        for (int cptLig = 0; cptLig< g.getNbLigne(); cptLig++)  // cpt = compteur
        {
            System.out.print(" " +cptAffCoordLigne +" |");  // <- affichage des coordonné des lignes
            cptAffCoordLigne++;

            for (int cptCol = 0; cptCol < g.getNbcol(); cptCol++)
            {

                if (g.plateau[cptLig][cptCol] == 0)
                {
                    System.out.print(" - ");

                } else if (g.plateau[cptLig][cptCol] == 1) // 1 = id du joueur1
                {
                    System.out.print(" X ");
                }
                else if(g.plateau[cptLig][cptCol] == 2) // 2 = id du joueur2
                {
                    System.out.print(" O ");
                }
            }
            System.out.print("|");
            System.out.println();


        }

        System.out.println("   -----------");
        System.out.println();

    }
    // Même principe que pour displayGrilleM dans Puissance_4
    // Affiche la grille pour le Puissance 4
    public void displayGrilleP()
    {
        System.out.println();
        System.out.println("******** Etat Du Jeu *******");
        System.out.println();
        System.out.print(" ");
        for (int cptCol = 1; cptCol< g.getNbcol()+1; cptCol++) // Le +1 car pour rappel on veut afficher des coordonné entre 1 et 7 et comme getNbcol = 7 (vu que java commence à compter de 0) on aura jusqu'à 6 et pas 7
        {
            System.out.print(" "+ cptCol + " ");
        }
        System.out.println();

        System.out.println("-----------------------");  // <- On dessine la ligne du haut du Puiss 4

        // Début affichage du plateau
        for (int cptLig = 0; cptLig< g.getNbLigne(); cptLig++)  // cpt = compteur
        {

            System.out.print("|");
            for (int cptCol = 0; cptCol < g.getNbcol(); cptCol++)
            {

                if (g.plateau[cptLig][cptCol] == 0)
                {
                    System.out.print(" - ");

                } else if (g.plateau[cptLig][cptCol] == 1) // 1 = id du joueur1
                {
                    System.out.print(" J "); // pion Jaune
                }
                else if(g.plateau[cptLig][cptCol] == 2) // 2 = id du joueur2
                {
                    System.out.print(" R "); // pion Rouge
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-----------------------"); // <- dessine la ligne de la grille du bas

    }


}