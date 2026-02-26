package modele;
public class Joueur
{
    public String nom;
    public int idJoueur;
    public static int cptJoueur = 1;


    public Joueur(String nom)
    {
        this.nom = nom;
        this.idJoueur = cptJoueur;

        cptJoueur++;
    }

    public int  getidJoueur()
    {
        return idJoueur;
    }

    public String getNom()
    {
        return nom;
    }


}