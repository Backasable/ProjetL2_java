public class Morpion
// lien de la vidéo : https://www.youtube.com/watch?v=vEf9Ar_4wH8&t=634s
// rend toi au time code 14:25
// C'est ici qu'il détail la partie sur win
// Tu devras peut-être crée un méthode equals en plus de win et
// /!\ tu n'as pas à faire de methode checkCoord() /!\
// Elle est déjà dans Grille
{
    private Grille plateau;
    public Morpion(String PlayerName1, String PlayerName2)
    {
        super(PlayerName1, PlayerName2);
        this.plateau = new Grille(3,3);
    }

    public static boolean equals(int entier1, int entier2, int entier3, int entier4){
        boolean compare = false;

        if(entier1 == entier2 && entier2 == entier3 && entier3 == entier4){
            compare = true;
        }
        return compare;

    }

    @Override
    public static boolean win(int[][] plateau, int numJoueur){
        //Ligne
        for (int cptLignes = 0; cptLignes < plateau.length; cptLignes++){
            if(equals(plateau[cptLignes][0], plateau[cptLignes][1], plateau[cptLignes][2], numJoueur)){
                return true;
            }
        }
        //colonne
        for(int cptColonnes = 0; cptColonnes < plateau.length; cptColonnes++) {
            if (equals(plateau[0][cptColonnes], plateau[0][cptColonnes],plateau[0][cptColonnes], numJoueur)) {
                return true;
            }
        }
        // Diagonal
        if(equals(plateau[0][0], plateau[1][1], plateau[2][2], numJoueur) || equals(plateau[2][0], plateau[1][1], plateau[0][2], numJoueur)){
            return true;
        }

        return false;

    }



}