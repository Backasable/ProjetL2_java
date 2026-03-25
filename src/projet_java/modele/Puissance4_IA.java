package projet_java.modele;

public class Puissance4_IA extends BrainIA
{
    public Puissance4_IA(Grille g, Difficulte difficulte)
    {
       super(g, difficulte);
    }


    public void entrerCoupIA(Jeu jeu, Joueur ia, Joueur j) { /*enlève Joueur j*/

        // On vérif d'abord si le Joueur lors de son prochain coup pourrait gagné et si c'est le cas, l'ia joue la case qu'il était sensé jouer
        /*int[] caseGagnantJ = verifwinP(jeu, j);

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
        else {*/

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

    // équivalent du verifWinM mais pour le puissance 4
    // C'est à dire qu'elle simule le fait qu'un joueur va jouer et teste tout les colonne
    // Si il y a un moment où le pion du joueur tombe dans une case qui lui permet d'obtenir la victoire, alors on
    /*public int[]  verifwinP(Jeu jeu, Joueur j)
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
    }*/


    private void placement(int[] coord, Joueur j){
        g.plateau[coord[0]][coord[1]] =j.idJoueur;
    }


}
