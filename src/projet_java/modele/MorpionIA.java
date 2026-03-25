package projet_java.modele;

import java.util.ArrayList;

public class MorpionIA extends BrainIA
{
    public MorpionIA(Grille g, Difficulte difficulte)
    {
        super(g, difficulte);
    }


    // Methode vérifant si le joueur ou l'IA peuvent gagner au prochain coup et renvoi la case gagante si vrai sinon elle revoie null
    public int[] verifwinM(Jeu jeu, Joueur j)
    {
        int[] caseGagnant = null;
        for (int ligne = 0; ligne < g.getNbLigne(); ligne++)
        {
            for (int col = 0; col<g.getNbcol(); col++ )
            {
                if (g.plateau[ligne][col] == 0)
                {
                    g.plateau[ligne][col] = j.idJoueur;
                    if (jeu.win(j))
                    {
                        g.plateau[ligne][col] = 0;
                        caseGagnant = new int[] {ligne, col};
                    }
                    else
                    {
                        g.plateau[ligne][col] = 0;
                    }
                }

            }
        }
        // on met un null car il faut qu'il y ait un return statement et ça m'arrange aussi car quand on vérif si l'ia a gagné lors du premier tour, elle n'a pas encore joué donc il me faut un retourne null
        return caseGagnant;
    }



    // On récupe les coord d'un coin qui est vide
    public int[] getCoinVide()
    {

        int[] coup = new int[2];
        if (g.plateau[0][0] == 0)
        {
            coup[0]= 0;
            coup[1]= 0;
            return coup;

        }
        else if (g.plateau[0][2] == 0)
        {
            coup[0]= 0;
            coup[1]= 2;
            return coup;
        }
        else if (g.plateau[2][0] == 0)
        {
            coup[0]= 2;
            coup[1]= 2;
            return coup;
        }
        else if (g.plateau[2][2] == 0)
        {
            coup[0]= 2;
            coup[1]= 2;
            return coup;
        }
        else
        {
            return null;
        }

    }


    public int[] prendreCoin()
    {
        int[] coup = getCoinVide();
        if (coup != null) {
            return coup;

        }
        return null;
    }

    public int[] prendreCentre()
    {
        int[] coup = new int[2];
        if(g.plateau[1][1] == 0)
        {
            coup[0] = 1;
            coup[1] = 1;
            return coup;
        }

        return null;

    }

    // récup les coord des cote vide
    public int[] getCoteVide()
    {

        int[] coup = new int[2];
        if (g.plateau[0][1] == 0)
        {
            coup[0]= 0;
            coup[1]= 1;
            return coup;

        }
        else if (g.plateau[1][0] == 0)
        {
            coup[0]= 1;
            coup[1]= 0;
            return coup;
        }
        else if (g.plateau[1][2] == 0)
        {
            coup[0]= 1;
            coup[1]= 2;
            return coup;
        }
        else if (g.plateau[2][1] == 0)
        {
            coup[0]= 2;
            coup[1]= 1;
            return coup;
        }
        else
        {
            return null;
        }

    }

    public int[] prendreCote()
    {
        int[] coup = getCoteVide();
        if (coup != null) {
            return coup;

        }
        return null;
    }


    public void entrerCoupIA(Jeu jeu, Joueur ia, Joueur j)
    {
        // Si on a pas 4 case vide restante alors :
        if (jeu.g.compteurCaseVide() > 4)
        {
            // Si le joueur s'apprête à gagner l'ia joue sur la case qui fera gagner le joueur
            int[] caseGagantJ = verifwinM(jeu, j);
            int[] caseGagantIA = verifwinM(jeu, ia);
            int[] centre  = prendreCentre();
            int[] cote = prendreCote();
            int[] coin = prendreCoin();

            if (caseGagantIA != null)
            {
                placement(caseGagantIA, ia);
                return; // <- pour mettre fin à la méthode
            }
            else if (caseGagantJ != null)
            {
                placement(caseGagantJ, ia);
                return;
            }
            else if (centre != null)
            {
                placement(centre, ia);
                return;
            }
            else if( coin != null)
            {
                placement(coin, ia);
                return;
            }

            else if  (cote != null)
            {
                placement(cote, ia);
                return;
            }


        }

        else
        {
            int[] coupIA = meilleurCoup(jeu, ia, j);
            placement(coupIA, ia);
        }

    }

    public ArrayList<int[]> recupCoordCaseVide()
    {
        ArrayList<int[]> tabCoord= new ArrayList<>();  // tabCoord ressemblera à cela par exemple : [ [0,1], [3,0] ]
        for (int ligne = 0; ligne < g.getNbcol(); ligne++)
        {
            for (int col = 0; col < g.getNbcol(); col++)
            {
                if(g.plateau[ligne][col] == 0)
                {
                    tabCoord.add(new int[]{ligne, col});
                }
            }
        }
        return tabCoord;
    }

    public int[] meilleurCoup(Jeu jeu, Joueur ia, Joueur j)
    {
        ArrayList<int[]> coupPossible = recupCoordCaseVide();
        int[] meilleurCoup = new int[2];
        int bestscore = -23456789;     // <- comme on veut que l'ia ait le plus haut score, alors on initialise le sien avec le plus bas possible

        for (int[] coup : coupPossible)
        {
            placement(coup, ia);
            int score = minmax(ia, j, jeu, false);


            if ( score > bestscore)
            {
                bestscore = score;
                meilleurCoup = coup;
            }
            annulerCoup(coup);
            System.out.println("score : " +score);
            System.out.println( "bestscore : "+ bestscore);

        }
        return meilleurCoup;
    }

    private int  minmax(Joueur ia, Joueur j, Jeu jeu, boolean maximiseur)
    {
        ArrayList<int[]> coupPossible = recupCoordCaseVide(); // <- on recherche les coup possible une nouvelle fois

        int eval = evaluer(jeu, j, ia);
        if (eval==2)
        {
            if (maximiseur) // <- tour de l'ia de jouer
            {
                int bestScore = -23456541; // on initialise au plus bas car on veut le plus haut score

                for (int[] coups : coupPossible) {
                    placement(coups, ia);
                    int score = minmax(ia, j, jeu, false);
                    annulerCoup(coups);

                    if (score > bestScore)
                    {
                        bestScore = score;
                    }
                    // System.out.println(bestScore + " bestscore ia ");

                }


                return bestScore;

            } else  // <- tour du joueur du jouer
            {
                int bestScore = 3456754; // On initilalise au plus haut afin de d'obtenir le plus bas score

                for (int[] coups : coupPossible)
                {
                    placement(coups, j);
                    int score = minmax(ia, j, jeu, true);
                    annulerCoup(coups);

                    // Lorsqu'on remonte, on compare bien les score
                    if (score < bestScore)
                    {
                        bestScore = score;
                    }
                    //System.out.println(bestScore + " bestscore J ");

                }

                return bestScore;
            }
        }
        return eval;
    }
    private int evaluer(Jeu jeu, Joueur j, Joueur ia)
    {
        if (jeu.win(j))
        {
            return -1;
        }
        else if(jeu.win(ia))
        {
            return 1;
        }
        else if (g.checkGrillefull())
        {
            return 0;
        }
        else
        {
            return 2;   // <- valeur renvoyer par evaluer si aucune des autre condi n'est vrai (PS : j'aurai voulut mettre null met ça marche pas donc j'ai prit 2)
        }
    }

    private void placement(int[] coord, Joueur j){
        g.plateau[coord[0]][coord[1]] =j.idJoueur;
    }

    private void annulerCoup(int[] coord)
    {
        g.plateau[coord[0]][coord[1]] = 0;
    }

}
