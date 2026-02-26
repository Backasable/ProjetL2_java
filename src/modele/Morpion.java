public class Morpion extends Jeu
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


}