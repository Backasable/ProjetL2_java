package projet_java.vue;
import projet_java.modele.Jeu;
import projet_java.modele.Puissance_4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IHM {

    private Scanner sc;

    public IHM()
    {
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
        if (name.equals("ex aequo")){
            System.out.println("ex aequo");
        }
        else
        {
            System.out.println(name+ " a gagné le plus de partie.\nFélicitation vous avez Battue votre adversaire !!!");
        }

    }
    public int[] userInputGame(Jeu jeu, String playerName)
    {
        if (jeu instanceof Puissance_4)
        {
            return userInputGameP(playerName);
        }
        else
        {
            return userInputGameM(playerName);
        }
    }



    // Récup la colonne saisi par le Joueur pour le Puissance 4
    public int[] userInputGameP(String PlayerName)
    {
        int[] colonne = new int[2];
        System.out.println(PlayerName+ " c'est à vous de jouer !");
        System.out.println();
        System.out.print("Entrer une colonne : ");
        colonne[1] = sc.nextInt() -1;  // <- le -1 permet de convertir la colonne saisi en int que java comprend
        return colonne;

    }

    // Méthode qui récupère les coordonnées(ligne et colonne)
    public int[] userInputGameM(String playerName) {

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
            sc.nextLine();
// Note : Ce qui causait le bug où le prog n'arriver pas à lire ce qu'a saisi l'user lors de la première itération de la boucle (dans la methode userInputNewGame()) c'état un \n qui été causé lorsque user en appuyait sur Entrée après avoir tapé 1 ou 2. nextInt() lit uniquement le chiffre et laisse ce \n d'Entrée dans le buffer. C'est ce \n que sc.nextLine() vient consommer.
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

            System.out.print("Voulez-vous lancer une nouvelle partie (y/n) ? : ");
            String user = sc.next();

            if(user.equals("y") || user.equals("n" ))
            {
                valide = true;
                return user;
            }
            else
            {
                System.out.println("Veuillez saisir :y ou n !!!");
            }

        }
        return "erreur innatandue";
    }


    public void affichierGagnant(String playerName)
    {
        System.out.println();
        if (playerName.equals("ex aequo"))
        {
            System.out.println("ex aequo");
        }
        else
        {
            System.out.println("Félicitation ! "+ playerName+ " vous avez gagné !!!");
            System.out.println();
        }

    }

    public void affichageScoreJeu(String[] tableJeuScore)
    {
        System.out.println(); // <- pour laisser un espace après ce qui a été afficher avant

        // Tant qu'on n'a pas afficher tout les element du tableau incrémente le cpt
        // c'est plus jolie car au début je partais du principe que tableJeuScore avait 2 element vu qu'il y a 2 joueur mais admétons que les 2 joueurs décide de jouer qu'une seul partie est Enregistre ne va qu'ajouter le vainquer donc la taille de hashMap sera de 1 donc on aura un souci de taille vue que cette méthode là reçois un tableau qui représente l'état du HashMap donc si on veut afficher les 2 joueurs mais que le tab sa taille c'est 1 bah c'est mort
        // là on affiche peut import la taille du tableau donc on a en plus une sécurité pour eviter l'erreur index out of bound ou un truc comme cela
        int cpt = 0;
        while(cpt < tableJeuScore.length)
        {
            System.out.println(tableJeuScore[cpt]);
            cpt++;
        }

    }

}
