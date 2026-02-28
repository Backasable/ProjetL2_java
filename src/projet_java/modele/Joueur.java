package projet_java.modele;

public class Joueur
{
    public String nom;
    public int idJoueur;
    public static int cptJoueur = 1;
    private int[] caseTrouverCoord;  // <- Utilisé dans le puissance 4, on récupère les coordonnées de la case où le pion jouer par le joueur est tombé

    public Joueur(String nom)
    {
        this.nom = nom;
        this.idJoueur = cptJoueur;
        this.caseTrouverCoord = null;

        cptJoueur++;
    }

    public int  getidJoueur()
    {
        return this.idJoueur;
    }

    public String getNom()
    {
        return this.nom;
    }

    public int[] getcaseTrouverCoord()
    {
        return this.caseTrouverCoord;
    }

    public void setcaseTrouverCoord(int[] caseFind)
    {
        this.caseTrouverCoord= caseFind;
    }


}