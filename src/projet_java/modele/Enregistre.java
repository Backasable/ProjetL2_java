package projet_java.modele;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class Enregistre
{
    private HashMap<String, Integer> dico;

    public Enregistre()
    {
        this.dico = new HashMap<>();
    }

    public void ajouterRes(String PlayerName)
    {
        // On ignore si le resultat de loopGame est ex eaquo
        if (PlayerName.equals("ex aequo"))
        {
            return;
        }
        else if (this.dico.containsKey(PlayerName))
        {
            dico.put(PlayerName,dico.get(PlayerName)+1);
        }
        else
        {
            this.dico.put(PlayerName, 1);
        }
    }

    // Permet de regrouper dans un tableau où la valeur d'une colonne est une String : PlayerName | partie gagné : valeur
    public String[] affichMap()
    {
        int cpt = 0;
        String[] j_resGame = new String[this.dico.size()];

        for (Map.Entry<String, Integer> entry : this.dico.entrySet()) {
            String cle = entry.getKey();
            Integer valeur = entry.getValue();
            j_resGame[cpt] = cle+" | Partie gagné : " +valeur;
            cpt++;

        }
        return j_resGame;
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
            return keytab[0]+" avec " + this.dico.get(keytab[0])+ " de partie gagné";
        }

        // Note 1 : avec == Java ne fait pas l'unboxing automatiquement,
        // il compare les références des objets Integer,
        // Donc afin d'éviter des problème on cast en int

        else if(((int)this.dico.get(keytab[0]) == (int)this.dico.get(keytab[1])))
        {
            return "ex aequo";
        }

        // Note 1 suite :
        // contrairement au : == qui s'applique sur les objet,
        // Le : > ne peut pas s'appliquer sur des objets Java fait donc,
        // automatiquement l'unboxing (convertit Integer en int) pour pouvoir comparer avec >. Ainsi, on compare bien les valeurs.
        // On gère le cas où les 2 joueur on au moin gagné 1 partie
        else if (this.dico.get(keytab[0]) > this.dico.get(keytab[1]))
        {
            return keytab[0]+ "avec " + this.dico.get(keytab[0])+ " de partie gagné";
        }
        else
        {
            return keytab[1] + "avec " +this.dico.get(keytab[1]+ " de partie gagné");
        }
    }
}