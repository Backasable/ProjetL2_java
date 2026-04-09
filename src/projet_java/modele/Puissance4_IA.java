package projet_java.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Puissance4_IA extends BrainIA
{
    public Puissance4_IA(Grille g, Difficulte difficulte)
    {
       super(g, difficulte);
    }

    public void entrerCoupIARandom(Joueur ia)
    {
        int col;
        int[] caseVide;

        // Cherche une colonne qui n'est pas pleine
        do {
            col = randomeCol();
            caseVide = g.findCaseVide(col);
        }
        // tant qu'on a pas de case valide i.e s'il y a déjà un pion dessus on boucle
        while (verifColpleine(caseVide));

        placement(caseVide, ia);
    }


    // On récupère les coup possible que peut jouer l'ia dans une arrayListe
    // Tout en vérifiant si le coup ne va jamais jouer dans une colonne déjà pleine.

    public ArrayList<int[]> verifCoupPossibble()
    {
        ArrayList<int[]> coupPossible = new ArrayList<>();

        for (int col=0 ; col < g.getNbcol(); col ++ )
        {
            int[] caseVide = g.findCaseVide(col);

            // SI la colonne n'est pas pleine, alors le coup est valide donc on le stock dans coupPossible
            if (!(verifColpleine(caseVide)))
            {
                coupPossible.add(caseVide);
            }

        }
        return coupPossible;
    }


    public int determinerScoreCoup(Jeu jeu, int[] coup, Joueur ia, Joueur j)
    {
        int scoreTotal = 0;

        // Note : Pk je ne met pas de if puis, encore un else if puis, else if ... puis enfin un else
        // car en java une fois qu'il a trouver une if ou else if vrai,
        // il exécute le bock du if ou else if vrai
        // puis va directement à la fin de la méthode.
        // Sauf que nous, on veut justement qu'il regarde tout les if, else if et else
        // donc pour cela, on met simplement des if, Ainsi java les parcouriras tous

        // Phase défensive :
        // Régle A :
        if (verifwinP(jeu, coup, j))
        {
            scoreTotal = scoreTotal + 900;
        }

        // Règle B devenu Obsselette et inutile  :
//        if (testerLigne(jeu, coup, j))
//        {
//            scoreTotal = scoreTotal + 500;
//        }

        // Phase offensive :

        // Règle A :
        if (verifwinP(jeu, coup, ia))
        {
            scoreTotal = scoreTotal + 1000;
        }

        // Règle B :
        int scoreB = compterNbCaseVideAlignement(ia, coup, 3);
        scoreTotal = scoreTotal + scoreB;

        // Règle C :
        int scoreC = compterNbCaseVideAlignement(ia, coup, 2);
        scoreTotal  = scoreTotal + scoreC;

        // Règle D :
        scoreTotal = scoreTotal + regleD(coup);


        return scoreTotal;
    }

    // Util pour la règle offensive d :
    private int regleD(int[] coup)
    {
        int colonne = coup[1];

        switch (colonne)
        {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 5;
            case 4:
                return 3;
            case 5:
                return 2;
            case 6:
                return 1;
        }

        return 0; // <- obligatoire pour eviter erreur missing return statement
    }

    // util pour la règle défensive b,
    // Commence la recherche d'une case gagnante pour l'advaisaire (qui n'est pas encore accessible mais qui permettrait à l'adversaire de gagné si elle était atteint)
    // à partir de la case au dessus du coup joué
    // Et teste toutes les case de la colonne jusqu'à remonter à la première case de la colonne
    // Si on en a trouve 1 alors on arrête la recherche et on retourne true
    // Si on a atteint la limite la première case de la colonne ça veut dire qu'on a atteint la limite de la grille donc on retourne false
    private boolean testerLigne(Jeu jeu, int[] coup, Joueur j)
    {
        int ligne = coup[0];
        int col = coup[1];

        int[] colonneVerif = new int[2];

        for  (int i = ligne; i > 0; i--)
        {
            colonneVerif[0] = i;
            colonneVerif[1] = col;
            if (verifwinP(jeu, colonneVerif, j))
            {
                return true;
            }

        }
        return false;
    }


    public int[] calculerMeilleurCoup(Map<int[], Integer> coupEtScore)
    {
        // Rappel si on a plusieurs coups qui ont le même meilleur score l'ia doit choisir aléatoirement entre ces coups
        int[] meilleurCoup = null;
        int ScoreMeilleur = 0;

        for (int[] cle : coupEtScore.keySet())
        {
            int score = coupEtScore.get(cle);

            System.out.println("coup : [" + (cle[0]+1) + "," + (cle[1]+1) + "] score: " + score);  // <- je l'ai mis ici

            if (score > ScoreMeilleur)
            {
                ScoreMeilleur = score;
                meilleurCoup = cle;
            }
        }

        // On fait donc cela à la fin, après avoir determiner le coup ayant le plus haut score

        // Donc on ajoute le meilleurCoup dans l'arrayListe
        // Puis on re regarde parmis le map si il n'y a pas un autre coup qui a luis aussi
        // le même score que le plus haut score et si le coup n'est pas déjà dedant,
        // alors on l'ajoute à l'arrayList plsCoup
        // Ensuite si,
        // la taille de l'arraylist = 1 alors ça veut dire qu'on a 1 seul element donc on le retourne
        // Sinon,
        // on choisi aléatoirement entre les elements de l'arrayList

        ArrayList<int[]> plsCoup = new ArrayList<>();

        plsCoup.add(meilleurCoup);

        for (int[] cle : coupEtScore.keySet())
        {

            int score = coupEtScore.get(cle);



            // rappel .contains() retourne un booleans il regarde si y'a l'element dans le ArrayList plsCoup
            if (score== ScoreMeilleur && !(plsCoup.contains(cle)))
            {
                plsCoup.add(cle);
            }
        }

        if (plsCoup.size()== 1)
        {
            return plsCoup.get(0);
        }
        else
        {
            int indice = random.nextInt(plsCoup.size());
            return plsCoup.get(indice);
        }
    }

    // Demande à determineScoreCol() de calculer le score de chaque coup
    // Puis stock la paire de valeur coup; score dans un Map
    // Et demmande à calculerMeilleurCoup de lui donné le coup ayant le plus haut score
    // et ainsi place le meilleur coup dans la grille
    public void entrerCoupIaIntel(Jeu jeu, Joueur ia, Joueur j)  // Note, on a besoin de jeu pour verifwinP il en a besoin pour fonctionner
    {
        int[] meilleurCoup = null;
        Map<int[], Integer> coupEtScore = new HashMap<>();

        ArrayList<int[]> coupPossible = verifCoupPossibble();

        for (int[] coup : coupPossible)
        {
            int score = determinerScoreCoup(jeu, coup, ia, j);

            // Note : il n'y a pas besoin de vérifier si le coup est déjà
            // dans le Map coupEtScore vu que ce coup sera enregister qu'une seul fois
            // Et à chaque tour une fois que le joueur à jouer
            // Ce map est rénitialisé donc pas besoin de faire de vérification de doublon

            coupEtScore.put(coup, score);
        }

        meilleurCoup = calculerMeilleurCoup(coupEtScore);

        placement(meilleurCoup, ia);
    }





    public void entrerCoupIA(Jeu jeu, Joueur ia, Joueur j)
    {
        if (difficulte.getNiv_diff()==1)
        {
            entrerCoupIARandom(ia);
        }
        else
        {
            entrerCoupIaIntel(jeu, ia, j);
        }
    }





    public int randomeCol()
    {
        return random.nextInt(7);
    }

    // Dans la situation où notre colonne serait plein cette méthode intervient ! et vérifie si c'est le cas en regardant si la case où va tomber notre pion il y aura déjà un pion
    public boolean verifColpleine(int[] coord)
    {
        if (g.plateau[coord[0]][coord[1]] != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    // C'est à dire qu'elle simule le fait qu'un joueur va jouer et teste si la colonnne est joué permet de gagné ou non
    // Si il y a un moment où le pion du joueur tombe dans une case qui lui permet d'obtenir la victoire, alors on retourne le coup gagnant
    public boolean  verifwinP(Jeu jeu, int[] caseVide, Joueur j)
    {
        // On récupère la case où va tomber le pion

        int ligne = caseVide[0];
        int colonne = caseVide[1];

        // On simule le fait qu'un joueur joue un coup
        g.plateau[ligne][colonne] = j.idJoueur;

        // On enregistre le coup dans cette attribut car win regarde uniquement cette attribut
        j.setcaseTrouverCoord(caseVide);

        // On regarde si son coup lui permet de gagné
        if (jeu.win(j))
        {
            // Si c'est le cas, on annule son coup est on retourne true
            g.plateau[ligne][colonne] = 0;
            return true;
        }
        else
        {
            // Sinon, on annule aussi le coup et on retourne false
            g.plateau[ligne][colonne] = 0;
            return false;
        }
    }


    private void placement(int[] coord, Joueur j){
        g.plateau[coord[0]][coord[1]] =j.idJoueur;

        // on est obligé de faire cela car win du puissance4 a bseoin du dernier coup qui a été joué pour voir si ce coup est gagnant ou pas
        j.setcaseTrouverCoord(coord);
    }

    public void annulerCoup(int[] coord, Joueur j)
    {
        g.plateau[coord[0]][coord[1]] = 0;

        // on remet à null pour que le win de prenne pas la mauvaise valeur
        // Mais après... cette valeur ce fait écrasé par la nouvelle dans tous les cas. Mais c'est plus joli
        j.setcaseTrouverCoord(null);
    }


//===================================================
    // Util pour les règle offensive B et C

    public int compterNbCaseVideAlignement(Joueur j, int[] coup, int nbJeton)
    {

        // On initilise un score pour calculer le score total en appliquant la règle offensive B ou C
        int score = 0;

        // On place le coup
        placement(coup, j);
        j.setcaseTrouverCoord(coup);

        int ligne = coup[0];
        int colonne = coup[1];
        int id = j.idJoueur;

        int nbCaseVideL =verifColonne(ligne, colonne, id, nbJeton);
        int nbCaseVideC = verifLigne(ligne, colonne, id, nbJeton);
        // diago croissante
        int nbCaseVideDC = verifDiagoCroissante(ligne, colonne, id, nbJeton);
        // diago decroissante
        int nbCaseVideDD = verifDiagoDecroissante(ligne, colonne, id, nbJeton);

        // Traite la règle offensive B si on a un alignement sur les ligne de 3 pion
        if(nbCaseVideL!=0)
        {
            if (nbJeton==3)
            {
                if(nbCaseVideL == 2)
                {
                    score = score + 200;
                }
                else if(nbCaseVideL == 1)
                {
                    score = score + 100;
                }
            }

            // Règle offensive C teste l'alignement sur la ligne
            else if (nbJeton==2)
            {

                if(nbCaseVideL== 4)
                {
                    score = score + 30; // Car si on a 4 vase vide détecter on peut former 3 paire au total et une paire vaut 10 point
                }
                else if(nbCaseVideL== 3)
                {
                    score = score + 20; // Car on peut faire 2 paire
                }
                else if(nbCaseVideL== 2)
                {
                    score = score + 10;
                }
            }

        }

        // Teste alignement sur la colonnes
        if(nbCaseVideC!=0)
        {
            if (nbJeton==3)
            {
                if(nbCaseVideC == 2)
                {
                    score = score + 200;
                }
                else if(nbCaseVideC == 1)
                {
                    score = score + 100;
                }
            }

            else if (nbJeton==2)
            {

                if(nbCaseVideC== 4)
                {
                    score = score + 30; // Car si on a 4 vase vide détecter on peut former 3 paire au total et une paire vaut 10 point
                }
                else if(nbCaseVideC== 3)
                {
                    score = score + 20; // Car on peut faire 2 paire
                }
                else if(nbCaseVideC== 2)
                {
                    score = score + 10;
                }
            }

        }
        // Teste alignement sur la diagonale croissante
        if(nbCaseVideDC!=0)
        {
            if (nbJeton==3)
            {
                if(nbCaseVideDC == 2)
                {
                    score = score + 200;
                }
                else if(nbCaseVideDC == 1)
                {
                    score = score + 100;
                }
            }

            else if (nbJeton==2)
            {

                if(nbCaseVideDC== 4)
                {
                    score = score + 30; // Car si on a 4 vase vide détecter on peut former 3 paire au total et une paire vaut 10 point
                }
                else if(nbCaseVideDC== 3)
                {
                    score = score + 20; // Car on peut faire 2 pair
                }
                else if(nbCaseVideDC== 2)
                {
                    score = score + 10; // Car on peut faire 1 seul pair
                }
            }

        }
        // Teste l'alignement sur la diagonal decroisante
        if(nbCaseVideDD!=0)
        {
            if (nbJeton==3)
            {
                if(nbCaseVideDD == 2)
                {
                    score = score + 200;
                }
                else if(nbCaseVideDD == 1)
                {
                    score = score + 100;
                }
            }

            else if (nbJeton==2)
            {

                if(nbCaseVideDD== 4)
                {
                    score = score + 30; // Car si on a 4 vase vide détecter on peut former 3 paire au total et une paire vaut 10 point
                }
                else if(nbCaseVideDD== 3)
                {
                    score = score + 20; // Car on peut faire 2 paire
                }
                else if(nbCaseVideDD== 2)
                {
                    score = score + 10;
                }
            }
        }
        // Si rien n'est vrai alors on retourne false :
        annulerCoup(coup, j);
        return score;
    }




    // ****
    public int verifColonne(int ligne, int colonne, int id, int nbJeton)
    {
        int cpt = 1;  // <- 1 et non 0 car je ne prend pas en compte la case où on est
        //  lorsqu'il part dans l'autre sens dans le prog projet_java.vue je ne fait que regarder si
        // la prochaine case est de la même couleur,
        // donc je dois initialiser le compteur à 1 pour prendre cette case en considération cf shema

        int nbCaseVide = 0; // <- pour la règle offensive b et c de l'it3

        // On vérifie d'abord si lorsqu'on fait un déplacement vers la gauche sur la ligne, on est pas hors limite
        // Et
        // ensuite on verif si le pion de la case à gauche est de la même couleur
        while (0 <= colonne - 1 && colonne - 1 < g.getNbcol() && g.plateau[ligne][colonne - 1] == id)
        {
            colonne--;
        }


        // (Règle offensive B)
        // Partie où on vérif la case vide à gauche de l'alignement
        if (nbJeton == 3)
        {
            // On oublit pas de vérif si on est hors limite quand on va regarder la prochaine case vide
            // vérif cette espace vide : (-)***
            if (0<= colonne-1 && colonne - 1 < g.getNbcol() &&    g.plateau[ligne][colonne - 1] == 0)
            {
                nbCaseVide++;
            }
        }

        // (Règle offensive C)
        // Partie où on Vérif les paire à gauche de l'alignement      // même chose, verif si on est hors limite
        else if (nbJeton == 2)
        {

            // regarde si y'a cette paire : (--)**

            // On vérif d'abord si on est pas hors limite avant de faire ce qu'on a dit
            if (0<= colonne-2 && colonne - 2 < g.getNbcol() &&    g.plateau[ligne][colonne - 1] == 0 && g.plateau[ligne][colonne - 2] == 0)
            {
                nbCaseVide += 2;
            }


            // 1er partie verif cette case de la paire : (-)**-

            // On vérif d'abord si on est pas hors limite avant de faire ce qu'on a dit
            else if (0<= colonne-2 && colonne -2 < g.getNbcol() &&    g.plateau[ligne][colonne - 1] == 0)
            {
                nbCaseVide++;
            }
        }

        // Pareil ici on vérif d'abord si lorsqu'on fait nos déplacement sur la ligne, on est pas hors limite
        // Ensuite on vérif si la case de droite a un pion de la même couleur
        while (0 <= colonne + 1 && colonne + 1 < g.getNbcol() &&    g.plateau[ligne][colonne + 1] == id)
        {
            colonne++;
            cpt++;
        }

        // (Règle offensive B SUITE)
        // Partie où on regarde la case vide à droite de l'alignement
        if (nbJeton == 3)
        {
            if (cpt == 3)
            {
                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                if (0<= colonne+1 && colonne + 1 < g.getNbcol()   && g.plateau[ligne][colonne + 1] == 0) // vérif cette espace vide ***(-)
                {
                    nbCaseVide++;

                }
            }
        }

        // (Règle offensive C SUITE)
        // Partie où on vérif les paires à droite de l'alignement
        else if (nbJeton == 2)
        {
            if (cpt == 2)
            {
                // regarde la paire **(--)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                if (0<= colonne+2 && colonne + 2 < g.getNbcol()     && g.plateau[ligne][colonne + 1] == 0 && g.plateau[ligne][colonne + 2] == 0) {
                    nbCaseVide += 2;


                }
                // 2 ème partie de cette paire : (-)**(-) où on regarde cette espace vide : -**(-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                else if (  0<= colonne+1 && colonne +1 < g.getNbcol() && g.plateau[ligne][colonne + 1] == 0)
                {
                    nbCaseVide++;


                }
            }
        }
        if (cpt==nbJeton)
        {
            System.out.println("nb de case vide détecté : "+nbCaseVide + " nb de jeton : "+nbJeton);
            return nbCaseVide;
        }
        return 0; // <- cf explication 33 feuille
    }

    // *
    // *
    // *
    // *
    public int verifLigne(int ligne, int colonne, int id, int nbJeton)
    {
        int cpt = 1; // cf verifCol pour expli° du 1

        int nbCaseVide = 0;

        // On vérifie d'abord si lorsqu'on fait ligne-1, on est pas hors limite
        //   ET
        // ensuite on verif si le pion de la case au dessus est de la même couleur
        while(0<=ligne-1 && ligne-1 <g.getNbLigne()    && g.plateau[ligne-1][colonne]==id )
        {
            ligne--;
        }

        // (Règle offensive B)
        // Partie où on vérif au dessus de l'alignement :
        if (nbJeton==3)
        {

            // On vérif d'abord si on est pas hors limite avant de faire :
            // Partie où Vérif si y'a case vide au dessus de l'alignement
            // (-)
            //  *
            //  *
            //  *

            // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
            if (0<=ligne-1 && ligne-1 <g.getNbLigne() &&    g.plateau[ligne - 1][colonne] == 0) {
                nbCaseVide++;
            }
        }

        // (Règle offensive C)
        // Partie où on vérif les paire au dessus de l'alignement :
        else if(nbJeton==2)
        {

            // Vérif la pair du dessus :
            // (-)
            // (-)
            //  *
            //  *

            // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
            if (0<= ligne-2 && ligne-2 < g.getNbLigne() &&      g.plateau[ligne - 1][colonne] == 0 && g.plateau[ligne - 2][colonne] == 0) {
                nbCaseVide += 2;
            }

            // 1er partie on on vérif la case vide au dessus de l'alignement
            // (-)
            //  *
            //  *
            //  -

            // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
            else if (0<= ligne-1 && ligne-1 < g.getNbLigne() &&     g.plateau[ligne - 1][colonne] == 0) {
                nbCaseVide++;
            }
        }

        // Pareil ici on vérif d'abord si lorsqu'on fait ligne+1, on est pas hors limite
        //  ET
        // Ensuite on vérif si la case en dessous a un pion de la même couleur et on réitère jusqu'à c
        while (0<=ligne+1 && ligne+1 <g.getNbLigne()    &&     g.plateau[ligne+1][colonne]==id)
        {
            ligne++;
            cpt++;
        }

        // (Règle offensive B SUITE)
        // Partie où on vérif en base de l'alignement :
        if (nbJeton==3)
        {
            if (cpt == 3)
            {
                // Partie où on Verif si la case en bas de l'aligement est vide
                //  *
                //  *
                //  *
                // (-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                if(0<= ligne+1 && ligne+1 < g.getNbLigne() &&    g.plateau[ligne+1][colonne] == 0)
                {
                    nbCaseVide++;
                }

            }
        }

        // (Règle offensive C SUITE)
        // Partie où on vérif les paire en bas de l'alignement :
        else if(nbJeton==2                                          && 0<= ligne+2 && ligne+2 < g.getNbLigne())
        {
            if (cpt == 2)
            {
                // Partie où on vérif la paire au dessus de l'alignement
                //  *
                //  *
                // (-)
                // (-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                if (0<= ligne+2 && ligne+2 < g.getNbLigne() &&    g.plateau[ligne+1][colonne] == 0 && g.plateau[ligne+2][colonne] == 0)
                {
                    nbCaseVide+=2;
                }


                // 2ème partie où on vérif la case en base de l'alignement :
                //  -
                //  *
                //  *
                // (-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                else if (0<= ligne+1 && ligne+1 < g.getNbLigne() &&     g.plateau[ligne+1][colonne] == 0)
                {
                    nbCaseVide++;
                }
            }
        }

        if(cpt == nbJeton)
        {
            return nbCaseVide;
        }
        return 0; // <- cf explication 33 de la feuille
    }


    public int verifDiagoDecroissante(int ligne, int colonne, int id, int nbJeton)
    {
        int cpt = 1; // cf verifLigne pour expli° du 1

        int nbcasevide = 0;


        // On vérifie d'abord si lorsqu'on fait ligne-1 et colonne-1 on est pas hors limite
        //   ET
        // ensuite on verif si le pion de la case au dessus à gauche est de la même couleur
        while (0 <= ligne - 1 && ligne - 1 < g.getNbLigne() && 0 <= colonne - 1 && colonne - 1 < g.getNbcol() && g.plateau[ligne - 1][colonne - 1] == id)
        {
            ligne--;
            colonne--;
        }

        // Partie où on vérif les cases vides en Haut à gauche :

        // (Règle offensive B)


        // Partie où on vérif la case en haut à gauche de la diago décroissante
        if (nbJeton == 3)
        {
            // (-)
            //    *
            //      *
            //        *

            // On vérif d'abord si on on est pas hors limite avant de faire ce qu'on a dit
            if (0 <= ligne - 1 && ligne - 1 < g.getNbLigne() && 0 <= colonne - 1 && colonne - 1 < g.getNbcol() &&        g.plateau[ligne - 1][colonne - 1] == 0) {
                nbcasevide++;
            }
        }

        // (Règle offensive C)
        // Partie où on regarde les paires en haut à gauche
        else if (nbJeton == 2)
        {

            // Partie où on regarde la paire en haut à gauche de la diago décroissante
            // (-)
            //   (-)
            //      *
            //        *

            // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
            if (0 <= ligne - 2 && ligne - 2 < g.getNbLigne() && 0 <= colonne - 2 && colonne - 2 < g.getNbcol() &&   g.plateau[ligne - 1][colonne - 1] == 0 && g.plateau[ligne - 2][colonne - 2] == 0) {
                nbcasevide += 2;
            }

            // 1er partie où on regarde la case vide en haut à gauche pour cette paire :
            // (-)
            //    *
            //      *
            //        -

            // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
            else if (0 <= ligne - 1 && ligne - 1 < g.getNbLigne() && 0 <= colonne - 1 && colonne - 1 < g.getNbcol() &&    g.plateau[ligne - 1][colonne - 1] == 0) {
                nbcasevide++;
            }
        }


        // Pareil ici on vérif d'abord si lorsqu'on fait ligne+1 et colonne + 1 on est pas hors limite
        //  ET
        // Ensuite on vérif si la case en dessous a un pion de la même couleur
        while (0 <= ligne + 1 && ligne + 1 < g.getNbLigne() && 0 <= colonne + 1 && colonne + 1 < g.getNbcol() && g.plateau[ligne + 1][colonne + 1] == id)
        {
            ligne++;
            colonne++;
            cpt++;
        }

        // Partie où on vérif les case vide en bas à droite :

        // (Règle offensive B SUITE)
        if (nbJeton == 3)
        {
            if (cpt == 3)
            {
                // Partie où vérif la case en bas de la diago décroissante
                //  *
                //    *
                //      *
                //       (-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                if (0 <= ligne + 1 && ligne + 1 < g.getNbLigne() && 0 <= colonne + 1 && colonne + 1 < g.getNbcol() &&      g.plateau[ligne + 1][colonne + 1] == 0)
                {
                    nbcasevide++;

                }
            }
        }

        // (Règle offensive C SUITE)
        // Partie où on regarde les paires en bas à droite de la diago décroissante :
        else if (nbJeton == 2)
        {
            if (cpt == 2)
            {

                // Partie où on regarde la paire en bas à droite :
                //  *
                //    *
                //     (-)
                //       (-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                if (0 <= ligne + 2 && ligne + 2 < g.getNbLigne() && 0 <= colonne + 2 && colonne + 2 < g.getNbcol() &&   g.plateau[ligne + 1][colonne + 1] == 0 && g.plateau[ligne + 2][colonne + 2] == 0)
                {
                    nbcasevide += 2;

                }

                // 2 eme partie où on vérif si la case en bas à droite de la diago décroissante est vide
                //  -
                //    *
                //      *
                //       (-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                else if (0 <= ligne + 1 && ligne + 1 < g.getNbLigne() && 0 <= colonne + 1 && colonne + 1 < g.getNbcol() &&      g.plateau[ligne + 1][colonne + 1] == 0)
                {
                    nbcasevide++;
                }
            }
        }
        if (cpt == nbJeton)
        {
            return nbcasevide;
        }
        return 0; // <- cf explication 33 de la feuille
    }


    public int verifDiagoCroissante(int ligne, int colonne, int id, int nbJeton) {
        int cpt = 1; // cf verifLigne pour expli° du 1

        int nbcasevide = 0;

        // On vérifie d'abord si lorsqu'on fait ligne+1 et colonne+1 on est pas hors limite
        //   ET
        // ensuite on verif si le pion de la case au dessus à droite est de la même couleur
        while (0 <= ligne - 1 && ligne - 1 < g.getNbLigne() && 0 <= colonne + 1 && colonne + 1 < g.getNbcol() && g.plateau[ligne - 1][colonne + 1] == id) {
            ligne--;
            colonne++;
        }

        // Partie où on regarde en haut à droite de la diago

        // (Règle offensive B)
        // Partie où on vérif la case vide en Haut à droite de la diago croissante
        if (nbJeton == 3)
        {
            //       (-)
            //     *
            //   *
            // *

            // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
            if (0 <= ligne - 1 && ligne - 1 < g.getNbLigne() && 0 <= colonne + 1 && colonne + 1 < g.getNbcol() &&    g.plateau[ligne - 1][colonne + 1] == 0) {
                nbcasevide++;
            }
        }

        // (Règle offensive C)
        // Partie où on regarde les paire en haut à droite de la diago croissante
        else if (nbJeton == 2)
        {
            // On regarde si la paire en haut à droite sont des case vide
            //       (-)
            //     (-)
            //   *
            // *

            // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
            if (0 <= ligne - 2 && ligne - 2 < g.getNbLigne() && 0 <= colonne + 2 && colonne + 2 < g.getNbcol() &&     g.plateau[ligne - 1][colonne + 1] == 0 && g.plateau[ligne - 2][colonne + 2] == 0)
            {
                nbcasevide += 2;
            }

            // 1er partie où on regarde si la case de la paire en haut à droite est vide
            //       (-)
            //     *
            //   *
            // -

            // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
            else if (0 <= ligne - 1 && ligne - 1 < g.getNbLigne() && 0 <= colonne + 1 && colonne + 1 < g.getNbcol() &&    g.plateau[ligne - 1][colonne + 1] == 0)
            {
                nbcasevide++;
            }
        }

        // Pareil ici on vérif d'abord si lorsqu'on fait ligne+1 et colonne - 1 on est pas hors limite
        //  ET
        // Ensuite on vérif si la case en dessous à gauche a un pion de la même couleur
        while (0 <= ligne + 1 && ligne + 1 < g.getNbLigne() && 0 <= colonne - 1 && colonne - 1 < g.getNbcol() && g.plateau[ligne + 1][colonne - 1] == id) {
            ligne++;
            colonne--;
            cpt++;
        }

        // (Règle offensive B SUITE)
        // Partie où on regarde la case en bas à gauche
        if (nbJeton == 3)
        {
            if (cpt == 3) {
                // On regarde cette case :
                //       *
                //     *
                //   *
                //(-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                if (0 <= ligne + 1 && ligne + 1 < g.getNbLigne() && 0 <= colonne - 1 && colonne - 1 < g.getNbcol() &&     g.plateau[ligne + 1][colonne - 1] == 0) {
                    nbcasevide++;
                }
            }
        }

        // (Règle offensive C SUITE)
        // Partie où on regarde les paires en bas à gauche de la diago
        else if (nbJeton == 2)
        {
            if (cpt == 2) {
                // On regarde les paire en bas à gauche de la diago croissante
                //       *
                //     *
                //  (-)
                //(-)

                // On vérif d'abord si on est pas hors limite avant de faire ce qu'on a dit
                if (0 <= ligne + 2 && ligne + 2 < g.getNbLigne() && 0 <= colonne - 2 && colonne - 2 < g.getNbcol() &&   g.plateau[ligne + 1][colonne - 1] == 0 && g.plateau[ligne + 2][colonne - 2] == 0) {
                    nbcasevide += 2;
                }

                // 2eme partie où on regarde la case en base à gauche correspondant à la paire
                //       -
                //     *
                //   *
                //(-)

                // On vérif d'abord son on est pas hors limite avant de faire ce qu'on a dit
                else if (0 <= ligne + 1 && ligne + 1 < g.getNbLigne() && 0 <= colonne - 1 && colonne - 1 < g.getNbcol() &&   g.plateau[ligne + 1][colonne - 1] == 0) {
                    nbcasevide++;

                }
            }
        }
        if (cpt == nbJeton)
        {
            return nbcasevide;
        }
        return 0; // <- cf explication 33 de la feuille
    }

// Fin de ce qui est util pour les règles offensive B et C
//==========================================================================



//=======================================
}
