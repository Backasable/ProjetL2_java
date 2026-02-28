package projet_java.modele;

import java.util.HashMap;



public class Enregistre
{
    private HashMap<String, Integer> dico;

    public Enregistre()
    {
        this.dico = new HashMap<>();
    }

    public void ajouterRes(String PlayerName)
    {
        if (this.dico.containsKey(PlayerName))
        {
            dico.put(PlayerName,dico.get(PlayerName)+1);
        }
        else
        {
            this.dico.put(PlayerName, 1);
        }
    }

    public String CalculerVainqueur()
    {
        // Là j'adapte le tableau selon la taille du dico car au début j'avais mis une taille fixe 2 mais j'ai eut des problèmes
        // Comme le fait que les condition en dessous ne soit pas faite dans le bonne ordre
        // et puis c'est plus élégant comme cela !
        // et surtout c'est un galère avec entryset pour récup une clé à partir d'une valeur donc je passe par un tableau
        String[] keytab = new String[this.dico.size()];

        int cpt = 0;
        for (String key : this.dico.keySet())
        {
            keytab[cpt]=key;
            cpt++;
        }

        // On gère le cas où personne n'a gagné :
        if (keytab.length == 0)
        {
            return null;
        }

        // On gère le cas où : 1 joueur à gagné tout les parties
        else if (keytab.length == 1)
        {
            return keytab[0];
        }

        // On gère le cas où les 2 joueur on au moin gagné 1 partie
        else if (this.dico.get(keytab[0]) > this.dico.get(keytab[1]))
        {
            return keytab[0];
        }
        else
        {
            return keytab[1];
        }
    }
}