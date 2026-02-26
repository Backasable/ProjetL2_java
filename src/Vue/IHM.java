import java.util.Scanner;
public class IHM {

    private Scanner sc;

    public IHM() {
        sc = new Scanner(System.in);
    }

    // Méthode qui récupère les coordonnées(ligne et colonne)
    public int[] UserInputGameM() {

        int[] coordonnees = new int[2];

        System.out.println("Entrer la ligne : ");
        coordonnees[0] = sc.nextInt();

        System.out.println("Entrer la colonne : ");
        coordonnees[1] = sc.nextInt();

        sc.nextLine();
        return coordonnees;
    }

    // Récup la colonne saisi par le Joueur pour le Puissance 4
    public int UserInputGameP()
    {

        System.out.println("Entrer la colonne : ");
        return sc.nextInt();

    }

    // Description : récupère nom Joueur

    public int UserInputChoiceGame() {
        System.out.println("Choisissez un mode de jeu : " +"\n          1 : Morpion !\n          2 : Puissance 4 ! ");
        int choix = sc.nextInt();
        sc.nextLine();
        return choix;

    }

    // Description : récup nom Joueur
    public String UserInputName() {
        System.out.println("Entrez votre nom : ");
        return sc.nextLine();

    }

    // Description : recup la réponse du Joueur si il veut lancer Une nouvelle partie
    public String UserInputNewGame() {
        System.out.println("Voulez-vous lancer une nouvelle partie ? (Y/N)");
        return sc.nextLine();

    }
}
