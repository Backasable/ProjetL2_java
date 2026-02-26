public class Morpion extends Jeu
{
    private Grille plateau;
    public Morpion(String PlayerName1, String PlayerName2)
    {
        super(PlayerName1, PlayerName2);
        this.plateau = new Grille(3,3);
    }
}