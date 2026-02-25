import java.util.Scanner;
public class IHM {

    private Scanner sc;

    public IHM() {
        sc = new Scanner(System.in);
    }

    // Méthode qui récupère les coordonnées(ligne et colonne)
    public int[][] UserInputGame() {

        int[][] coordonnees = new int[1][2];

        System.out.println("Entrer la ligne : ");
        coordonnees[0][0] = sc.nextInt();

        System.out.println("Entrer la colonne : ");
        coordonnees[0][1] = sc.nextInt();

        sc.nextLine();
        return coordonnees;



    }

    // Description : récupère nom Joueur

    public int UserInputChoiceGame() {
        System.out.println("Choisissez un mode de jeu : ");
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
