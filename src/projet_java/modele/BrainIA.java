package projet_java.modele;

import java.util.Random;

public class BrainIA {
    private Grille g;
    private Random random;

    public BrainIA(Grille g)
    {
        this.g = g;
        this.random = new Random();
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

    // équivalent du verifWinM mais pour le puissance 4
    // C'est à dire qu'elle simule le fait qu'un joueur va jouer et teste tout les colonne
    // Si il y a un moment où le pion du joueur tombe dans une case qui lui permet d'obtenir la victoire, alors on
    public int[]  verifwinP(Jeu jeu, Joueur j)
    {
        for (int col = 0; col<g.getNbcol(); col++)
        {
            int[] caseGagnant = new int[2];
            int[] caseVide = g.findCaseVide(col);
            int ligne = caseVide[0];
            int colonne = caseVide[1];

            // On vérifie d'abord si la colonne n'est pas pleine
            if (g.plateau[ligne][colonne] == 0)
            {
                // On simule le fait qu'un joueur joue un coup
                g.plateau[ligne][colonne] = j.idJoueur;

                // On enregistre le coup dans cette attribut car win regarde uniquement cette attribut
                j.setcaseTrouverCoord(caseVide);

                // On regarde si son coup lui permet de gagné
                if (jeu.win(j))
                {
                    // On récupère si c'est le cas les coord de la case gagnante
                    caseGagnant[0] = ligne;
                    caseGagnant[1] = colonne;

                    // On fait attention à bien annuler son coup
                    g.plateau[ligne][colonne] = 0;


                    // Et on renvoie les coord de la case gagnante
                    return caseGagnant;
                }

                // S'il ne gagne pas avec cette colonne, alors on annule son coup et on passe à la colonne suivante
                g.plateau[ligne][colonne] = 0;
            }
        }
        return null;
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
            else if  (cote != null)
            {
                placement(cote, ia);
                return;
            }
            else if( coin != null)
            {
                placement(coin, ia);
                return;
            }

        }

        // appeler meilleur coup dans lequel on a MinMax
    }

    public void entrerCoupIAP(Jeu jeu, Joueur ia, Joueur j) {

        // On vérif d'abord si le Joueur lors de son prochain coup pourrait gagné et si c'est le cas, l'ia joue la case qu'il était sensé jouer
        int[] caseGagnantJ = verifwinP(jeu, j);

        if (caseGagnantJ != null) {
            placement(caseGagnantJ, ia);
            return; // <- on met fin à la fct
        }

        // Si l'ia peut gagné
        if (verifwinP(jeu, ia) != null)
        {
            int[] caseGagnantIA = verifwinP(jeu, ia);
            placement(caseGagnantIA, ia);
        }
        else {

            int col;
            int[] caseVide;

            // Cherche une colonne qui n'est pas pleine
            do {
                col = randomeCol();
                caseVide = jeu.g.findCaseVide(col);
            }
            // tant qu'on a pas de case valide i.e s'il y a déjà un pion dessus on boucle
            while (!(verifColpleine(caseVide)));

            ia.setcaseTrouverCoord(caseVide);  // On a besoin de cela pour le win du puissance 4 qui va chercher dans cette attribue la valeur dont il a besoin
            placement(caseVide, ia);

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
            return false;
        }
        else
        {
            return true;
        }
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

    public void placement(int[] coord, Joueur j)
    {
        g.plateau[coord[0]][coord[1]] =j.idJoueur;
    }




//========================
}
