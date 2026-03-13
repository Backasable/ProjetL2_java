package projet_java.modele;



public class BrainIA {
    private Grille g;

    public BrainIA(Grille g)
    {
        this.g = g;
    }



    public int compteurCasevide()
    {
        int cpt = 0;
        for (int ligne = 0; ligne < g.getNbLigne(); ligne++)
        {
            for (int col = 0; col<g.getNbcol(); col++)
            {
                if (g.plateau[ligne][col] ==0)
                {
                    cpt ++;
                }
            }
        }

        return cpt;
    }

    // Methode vérifant si le joueur ou l'IA peuvent gagner au prochain coup et renvoi la case gagante si vrai sinon elle revoie null
    public int[] verifwin(Jeu jeu, Joueur j)
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
            coup[0] = 1 ;
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


    public void entrerCoupIAM(Jeu jeu, Joueur ia, Joueur j)
    {
        while (jeu.g.compteurCaseVide() > 4)
        {
            // Si le joueur s'apprête à gagner l'ia joue sur la case qui fera gagner le joueur
            int[] caseGagantJ = verifwin(jeu, j);
            int[] caseGagantIA = verifwin(jeu, ia);
            int[] centre  = prendreCentre();
            int[] cote = prendreCote();
            int[] coin = prendreCoin();

            if (caseGagantIA != null)
            {
                placementM(caseGagantIA, ia);
                return; // <- pour mettre fin à la méthode
            }
            else if (caseGagantJ != null)
            {
                placementM(caseGagantJ, ia);
                return;
            }
            else if (centre != null)
            {
                placementM(centre, ia);
                return;
            }
            else if  (cote != null)
            {
                placementM(cote, ia);
                return;
            }
            else if( coin != null)
            {
                placementM(coin, ia);
                return;
            }

        }

        // appeler meilleur coup dans lequel on a MinMax
    }

    public void entrerCoupIAP(Jeu jeu, Joueur ia, Joueur j)
    {
        // Natacha partie
    }

    public void entrerCoupIA(Jeu jeu, Joueur ia, Joueur j)
    {
        if(jeu instanceof Morpion)
        {
            entrerCoupIAM(jeu, ia, j);
        }
        else if( jeu instanceof Puissance_4)
        {
            entrerCoupIAP(jeu, ia,j);
        }
    }

    public void placementM(int[] coord, Joueur j)
    {
        g.plateau[coord[0]][coord[1]] =j.idJoueur;
    }



//========================
}
