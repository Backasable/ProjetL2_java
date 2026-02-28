package projet_java.vue;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IHM {

    private Scanner sc;

    public IHM() {
        sc = new Scanner(System.in);
    }

    public void acceuil()
    {
        System.out.println("===========================================");
        System.out.println("||     Bienvenue Dans La Boite à Jeu  !  ||");
        System.out.println("===========================================");
        System.out.println();
    }

    public void nameVainqueur(String name)
    {
        System.out.println();
        if (name == null){
            System.out.println("Aucun Joueur n'a gagné ! ");
        }
        else
        {
            System.out.println(name+ " a gagné le plus de partie");
        }

    }

    // Récup la colonne saisi par le Joueur pour le Puissance 4
    public int UserInputGameP(String PlayerName)
    {
        System.out.println(PlayerName+ " c'est à vous de jouer !");
        System.out.println();
        System.out.print("Entrer une colonne : ");
        return sc.nextInt() -1;  // <- le -1 permet de convertir la colonne saisi en int que java comprend

    }

    // Méthode qui récupère les coordonnées(ligne et colonne)
    public int[] UserInputGameM(String playerName) {

        System.out.println(playerName + " C'est à vous de jouer !");
        // je n'ai pas de condition qui vérifi si il a bien saisi 2 coordonnées SOS (mais ça devrais passer)
        int[] coordonnees = new int[2];

        System.out.print("(Exemple de saisi :1 3)\nEntrer les coordonnées de la case :  ");

        int cpt=0;
        int user = 0;

        while( cpt <2)
        {
            try
            {
                user=sc.nextInt();
                coordonnees[cpt]=user;
                cpt++;
            }
            catch(InputMismatchException e)
            {
                sc.next();
            }
        }


        // Je convertie les coordonnées pour que java les interprète correctement !
        // Donc j'enlève -1 à la ligne et -1 à la colonne

        coordonnees[0]--;
        coordonnees[1]--;


        return coordonnees;
    }

    // Description : récupère nom Joueur

    public int UserInputChoiceGame() {
        boolean valide = false;
        while(!(valide))
        {
            System.out.print("        1: Puissance 4 !\n        2: Morpion !\n\nChoisissez un mode de jeu : ");
            int choix = sc.nextInt();
            System.out.println();

            if(choix == 1 || choix == 2)
            {
                valide = true;
                return choix;

            }
        }
        return 0;
    }

    // Description : récup nom Joueur
    public String UserInputName() {
        System.out.print("Veulliez, entrez votre nom : ");
        return sc.nextLine();

    }

    // Description : recup la réponse du Joueur si il veut lancer Une nouvelle partie
    public String UserInputNewGame() {

        boolean valide = false;
        // Tant que le user n'as pas rentrer un valeur valide on lui repose la question
        while (!(valide))
        {
            System.out.println();
            System.out.print("Voulez-vous lancer une nouvelle partie (y/n) ? : ");
            String user = sc.nextLine();
            System.out.println();

            if(user.equals("y") || user.equals("n" ))
            {
                valide = true;
                return user;
            }

        }
        return "erreur innatandue";
    }


    public void affichierGagnant(String playerName)
    {
        System.out.println();
        System.out.println("Félicitation ! "+ playerName+ " vous avez gagné !!!");
    }









}
