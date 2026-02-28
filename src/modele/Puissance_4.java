package projet_java.modele;

import projet_java.ColonneHorsLimite;
import projet_java.PionDejaPresent;

public class Puissance_4
{
    public Grille g;


    public Puissance_4()
    {

        this.g = new Grille(6,7);

    }


    // On a déjà les coordonnés de la case où se trouve le pion que le joueur a placé dans this.caseTrouverCoord
// On a donc juste a placer dans le win le joueur
    public boolean win(Joueur j)
    {
        int[] coordCasePion = j.getcaseTrouverCoord();
        int ligne = coordCasePion[0];
        int colonne = coordCasePion[1];
        int id = j.idJoueur;





        if(verifLigne(ligne, colonne, id))
        {
            return true;
        }
        else if(verifColonne(ligne, colonne, id))
        {
            return true;
        }
        // Si rien n'est vrai alors on retourne false :
        return false;


    }



    // Stratégie qui va s'appliquer pour determiner si on a un alignement de 4 pion de la même couleur
    // elle s'applique

    // Après que le joueur est placer son pion,
    // Je recupère les coordonnées de la case où le pion du joueur est attérie
    // Ensuite :
    // Si sur la ligne où est le pion, il y a un pion de la même couleur sur la colonne de gauche
    // Alors, on se déplace sur cette colonne (en faisant colonne --) et on réitère cette opération
    // tant que le pion de la prochaine colonne est de la même couleur
    // Ensuite,
    // lorsqu'on atteint la case, où la prochaine colonne n'est plus la même couleur,
    // Alors on s'arrête,
    // Et la partie sur le if entre en scène
    // De la case où on s'est arrêter, on repart dans l'autre sens, (colonne ++)
    // avec un compteur cette fois-ci, qui va s'incémenter,
    // pour toutes les cases contenant un pion de la même couleur que le pion de base que le Joueur avait placé
    // Si le compteur arrive à 4 alors ça veut dire qu'on a une ligne avec 4 pion de la même couleur aligné
    // donc : on casse la boucle et on passe gagne à true et on renvoie gagne
    // Sinon, gagne reste tjrs à false
    // et on renvoie quand même : gagne

    // On vérifie si on a un alignement : * * * *  de 4 pions de même couleur sur les colonnes
    public boolean verifColonne(int Ligne, int Colonne, int identifiant)
    {
        int cpt = 1;  // <- 1 et non 0 car je ne prend pas en compte la case où
        //  lorsqu'il part dans l'autre sens dans le prog vue que je ne fait que regarder si
        // la prochaine case est de la même couleur,
        // donc je dois initialiser le compteur à 1 pour prendre cette case en considération cf shema

        int ligne = Ligne;
        int colonne = Colonne;
        int id = identifiant;
        boolean gagne = false;

        // On vérifie d'abord si lorsqu'on fait un déplacement vers la gauche sur la ligne, on est pas hors limite
        // Et
        // ensuite on verif si le pion de la case à gauche est de la même couleur
        while(0<=colonne-1 && colonne-1 <g.getNbcol()    && g.plateau[ligne][colonne-1]==id )
        {
            // System.out.println("Colonne actuel : "+colonne);
            colonne--;
            // System.out.println("On va à gauche");
            // System.out.println("On se déplace sur la colonne : "+colonne);

        }

        // On fait l'op inverse donc on se déplace mtn vers la droite et non plus vers la gauche
        // Mais mtn on a un compteur :
        // Note : la condition du if est tout le temps vrai donc elle est un peu conne mais j'avais besoin d'un true
        if (g.plateau[ligne][colonne]==id)
        {
            // Pareil ici on vérif d'abord si lorsqu'on fait nos déplacement sur la ligne, on est pas hors limite
            // Ensuite on vérif si la case de droite a un pion de la même couleur
            while (0<=colonne+1 && colonne+1 <g.getNbcol()    &&     g.plateau[ligne][colonne+1]==id)
            {
                colonne++;
                cpt++;
                // System.out.println("Dans l'autre sens");
                // System.out.println("Compteur : "+cpt);
                // System.out.println("On se déplace à droite sur la colonne : "+ colonne);
                if (cpt == 4)
                {
                    gagne = true;
                    break;

                }
            }
        }
        return gagne;
    }









    //                                                                       *
    //                                                                       *
    //                                                                       *
    // On vérif tout les ligne si y'a un alignement de 4 pions de comme ça : *
    public boolean verifLigne(int Ligne, int Colonne, int identifiant)
    {
        int cpt = 1; // cf verifLigne pour expli° du 1
        int ligne = Ligne;
        int colonne = Colonne;
        int id = identifiant;
        boolean gagne = false;

        // On vérifie d'abord si lorsqu'on fait ligne-1, on est pas hors limite
        //   ET
        // ensuite on verif si le pion de la case au dessus est de la même couleur
        while(0<=ligne-1 && ligne-1 <g.getNbLigne()    && g.plateau[ligne-1][colonne]==id )
        {
            // System.out.println("ligne actuel : "+ligne);
            ligne--;
            // System.out.println("On va en haut");
            // System.out.println("On se déplace sur la ligne du haut : "+ligne);

        }
        // On retourne donc mtn vers le bas et non plus vers le haut
        if (g.plateau[ligne][colonne]==id )
        {
            // Pareil ici on vérif d'abord si lorsqu'on fait ligne+1, on est pas hors limite
            //  ET
            // Ensuite on vérif si la case en dessous a un pion de la même couleur
            while (0<=ligne+1 && ligne+1 <g.getNbLigne()    &&     g.plateau[ligne+1][colonne]==id)
            {
                ligne++;
                cpt++;
                // System.out.println("Dans l'autre sens");
                // System.out.println("Compteur : "+cpt);
                // System.out.println("On se déplace sur la ligne du bas : "+ ligne);
                if (cpt == 4)
                {
                    gagne = true;
                    break;

                }
            }
        }
        return gagne;

    }







    // On détermine l'emplacement de la case où va se trouver le pion
    public void placement(int colonne, Joueur j)  throws PionDejaPresent, ColonneHorsLimite
    {

        // On vérifie déjà :
        // Si la colonne saisi, n'est pas dans l'interval de la taille du tabbleau
        // ALord on lève une exception
        if( !(0<= colonne && colonne < g.getNbcol()) )
        {
            throw new ColonneHorsLimite("La colonne saisi est hors de porté : "+ colonne);
        }
        else
        {
            // On récupère les coordonné de la case où le pion qu'a joué le joueur est tombé
            // Pourquoi j'ai mis caseTrouverCoord dans Joueur au lieu de le mettre dans le Puissance_4
            // Tout simplement car si un autre joueur saisi une valeur
            // i.e que si je met : entrerCoup(j1) et à la suite, entrerCoup(j2)
            // La valeur de caseTrouverCoord pour j1 sera écrasé par celle de j2
            // Donc chacun aura sa variabe caseTrouverCoord qui sera d'abord initialisé à null avant le deb de la game
            // Et dans la boucle :  do while, le do permettra d'exécuter une première fois le programme et de récup la val de caseTrouverCoord
            // après j'aurais pus faire un while mais pour le fun on est aller sur un do while
            // Mais la vrai  raison, c'est que :
            // au début comme je pensais qu'il me fallait une première exécution afin de récup la val de caseTrouverCoord sans condition, le do while me parraisser mieux

            j.setcaseTrouverCoord(g.findCaseVide(colonne));
            g.saisirVal(j.getcaseTrouverCoord(), j.getidJoueur());
        }
    }








    // Affiche la grille pour le Puissance 4
    public void displayGrilleP()
    {
        System.out.println();
        System.out.println("******** Etat Du Jeu *******");
        System.out.println();
        System.out.print(" ");
        for (int cptCol = 1; cptCol< g.getNbcol()+1; cptCol++) // Le +1 car pour rappel on veut afficher des coordonné entre 1 et 7 et comme getNbcol = 7 on aura jusqu'à 6 et pas 7
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