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
        // Regle B :
        if (testerLigne(jeu, coup, j))
        {
            scoreTotal = scoreTotal + 500;
        }

        // Phase offensive :

        // Règle A :
        if (verifwinP(jeu, coup, ia))
        {
            scoreTotal = scoreTotal + 1000;
        }

        // Règle B :


        // Règle C :


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

            if (score > ScoreMeilleur)
            {
                ScoreMeilleur = score;
                meilleurCoup = cle;
            }
        }

        // On fait donc cela à la fin, après avoir determiner le coup ayant le plus haut score

        // Donc on ajoute le meilleurCoup dans l'arrayListe
        // Puis on re regarde parmis le map si il n'ya pas un autre coup qui a luis aussi
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



    public boolean compterAlignement(Joueur j, int[] coup, int nbJeton)
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
        else if(verifDiagoDecroissante(ligne, colonne, id))
        {
            return true;
        }
        else if(verifDiagoCroissante(ligne, colonne, id))
        {
            return true;
        }
        // Si rien n'est vrai alors on retourne false :
        return false;


    }




    // On vérifie si on a un alignement : * * * *  de 4 pions de même couleur sur les colonnes
    public boolean verifColonne(int Ligne, int Colonne, int identifiant)
    {
        int cpt = 1;  // <- 1 et non 0 car je ne prend pas en compte la case où
        //  lorsqu'il part dans l'autre sens dans le prog projet_java.vue que je ne fait que regarder si
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


    public boolean verifDiagoDecroissante(int Ligne, int Colonne, int identifiant)
    {
        int cpt = 1; // cf verifLigne pour expli° du 1
        int ligne = Ligne;
        int colonne = Colonne;
        int id = identifiant;
        boolean gagne = false;

        // On vérifie d'abord si lorsqu'on fait ligne-1 et colonne-1 on est pas hors limite
        //   ET
        // ensuite on verif si le pion de la case au dessus à gauche est de la même couleur
        while(0<=ligne-1 && ligne-1 <g.getNbLigne() && 0<=colonne-1 && colonne-1 <g.getNbcol()    && g.plateau[ligne-1][colonne-1]==id )
        {
            // System.out.println("ligne actuel : "+ligne);
            ligne--;
            colonne--;
            // System.out.println("On va en haut");
            // System.out.println("On se déplace sur la ligne du haut : "+ligne);

        }
        // Condition tjr vraie à tester sans ?
        if (g.plateau[ligne][colonne]==id )
        {
            // Pareil ici on vérif d'abord si lorsqu'on fait ligne+1 et colonne + 1 on est pas hors limite
            //  ET
            // Ensuite on vérif si la case en dessous a un pion de la même couleur
            while (0<=ligne+1 && ligne+1 <g.getNbLigne() && 0<=colonne+1 && colonne+1 <g.getNbcol()     &&     g.plateau[ligne+1][colonne+1]==id)
            {
                ligne++;
                colonne++;
                cpt++;
                // System.out.println("Dans l'autre sens");
                // System.out.println("Compteur : "+cpt);
                // System.out.println("On se déplace sur la ligne du bas : "+ ligne);
                // System.out.println("On se déplace sur la ligne du bas : "+ colonne);
                if (cpt == 4)
                {
                    gagne = true;
                    break;

                }
            }
        }
        return gagne;

    }


    public boolean verifDiagoCroissante(int Ligne, int Colonne, int identifiant)
    {
        int cpt = 1; // cf verifLigne pour expli° du 1
        int ligne = Ligne;
        int colonne = Colonne;
        int id = identifiant;
        boolean gagne = false;

        // On vérifie d'abord si lorsqu'on fait ligne+1 et colonne+1 on est pas hors limite
        //   ET
        // ensuite on verif si le pion de la case au dessus à droite est de la même couleur
        while(0<=ligne-1 && ligne-1 <g.getNbLigne() && 0<=colonne+1 && colonne+1 <g.getNbcol()    && g.plateau[ligne-1][colonne+1]==id )
        {
            // System.out.println("ligne , colonne actuel : "+ligne+ colonne);
            ligne--;
            colonne++;
            // System.out.println("On va en haut à droite");
            // System.out.println("On se déplace sur la colonne en haut à doite : "+ligne);

        }
        // Condition tjr vraie à tester sans ?
        if (g.plateau[ligne][colonne]==id )
        {
            // Pareil ici on vérif d'abord si lorsqu'on fait ligne+1 et colonne - 1 on est pas hors limite
            //  ET
            // Ensuite on vérif si la case en dessous à gauche a un pion de la même couleur
            while (0<=ligne+1 && ligne+1 <g.getNbLigne() && 0<=colonne-1 && colonne-1 <g.getNbcol()     &&     g.plateau[ligne+1][colonne-1]==id)
            {
                ligne++;
                colonne--;
                cpt++;
                // System.out.println("Dans l'autre sens");
                // System.out.println("Compteur : "+cpt);
                // System.out.println("On se déplace sur la ligne colonne du bas à gauche : "+ ligne+ "," + colonne);
                if (cpt == 4)
                {
                    gagne = true;
                    break;

                }
            }
        }
        return gagne;

    }



//=======================================
}
