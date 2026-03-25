package projet_java.modele;

// J'ai crée un class Difficulte car ainsi je peux directement stocker
// la donné dans cette class si et évité de devoir la gérer dans le controleur où je devrais la passer en paramètre
// dans différente méthode et très franchement je n'ai aucune idée de comment faire donc je pass par une class c'est plus simple
// Ainsi les autres class pourront connaître le niveau de difficulté de chaque partie
// Il leur suffira juste d'avoir la même instence Difficulte que celle du Controleur
// C'est ce que je fait en leur passant de leur instenciation, je leur passe en paramètre l'instence de Difficult



public class Difficulte {
    private int niv_diff;
    public Difficulte()
    {
        this.niv_diff = 0;
    }

    public void setDifficulte(int difficulte) {
        this.niv_diff = difficulte;
    }

    public int getNiv_diff() { return niv_diff; }
}
