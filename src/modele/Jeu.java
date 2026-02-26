public abstract class Jeu
{
    protected Joueur j1;
    protected Joueur j2;

    public Jeu(String playerName1, String playerName2)
    {
        this.j1 = new Joueur(playerName1);
        this.j2 = new Joueur(playerName2);
    }

    public abstract boolean win();
}