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
        while (!(verifColpleine(caseVide)));

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


    public int derminerScoreCoup(int[] coup)
    {

    }

    public int[] calculerMeilleurCoup(Map<int[], Integer> coupEtScore)
    {
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

        return meilleurCoup;
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
            int score = derminerScoreCoup(coup);

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
            return false;
        }
        else
        {
            return true;
        }
    }


    // C'est à dire qu'elle simule le fait qu'un joueur va jouer et teste si la colonnne est joué permet de gagné ou non
    // Si il y a un moment où le pion du joueur tombe dans une case qui lui permet d'obtenir la victoire, alors on retourne le coup gagnant
    public boolean  verifwinP(Jeu jeu, int col, Joueur j)
    {
        // On récupère la case où va tomber le pion
        int[] caseVide = g.findCaseVide(col);
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
            // Sinon on retourne false
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


}
