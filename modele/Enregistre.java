import java.util.Map;

public class Enregistre
{
    private Map<String, Integer> dico;

    public Enregistre()
    {
        this.dico = new HashMap<>();
    }

    public ajouterRes(String PlayerName)
    {
        if (containsKey(PlayerName))
        {
            dico.put(PlayerName,dico.get(PlayerName)+1)
        }
        else
        {
            this.dico.put(PlayerName, 1);
        }
    }

    public String CalculerVainqueur()
    {
    Set<String> EnembleCle= this.dico.keySet()
    {
        if
    }
    }
}