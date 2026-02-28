package projet_java;


import projet_java.modele.Enregistre;
import projet_java.modele.Jeu;
import projet_java.modele.Joueur;
import projet_java.modele.Morpion;
import projet_java.modele.Puissance_4;
import projet_java.vue.IHM;


public class Controleur
{
    private IHM ihm;
    public Controleur(IHM ihm)
    {
        this.ihm = ihm;
    }

    public void lancerJeu()
    {

        Joueur[] tabJoueur = debJeu(ihm);
        Joueur j1 = tabJoueur[0];
        Joueur j2 = tabJoueur[1];

        Enregistre save = new Enregistre();


        while(ihm.UserInputNewGame().equals("y"))
        {
            int choice =ihm.UserInputChoiceGame();
            if (choice ==1){

                Puissance_4 p4 = new Puissance_4();
                String res = loopGameP(p4, ihm, j1, j2);
                save.ajouterRes(res);
                ihm.affichierGagnant(res);


            }
            else
            {
                Morpion m = new Morpion();
                String res = loopGameM(m, ihm, j1, j2);
                save.ajouterRes(res);
                ihm.affichierGagnant(res);
            }

        }
        ihm.nameVainqueur(save.CalculerVainqueur());
    }


// Fonction Pour le tout début :


    public Joueur[] debJeu(IHM ihm)
    {
        ihm.acceuil();
        Joueur[] tabJoueur = new Joueur[2];

        String playerName = ihm.UserInputName();
        Joueur j1 = new Joueur(playerName);
        tabJoueur[0]=j1;

        String playerName2 = ihm.UserInputName();
        Joueur j2 = new Joueur(playerName2);
        tabJoueur[1]=j2;
        return tabJoueur;

    }




// Fonction pour le puissance_4

    public static void entrerCoupP(Puissance_4 p4, IHM ihm, Joueur j)
    {
        boolean coupValid = false;
        while(!(coupValid))
        {
            try
            {
                p4.displayGrilleP();
                int user =  ihm.UserInputGameP(j.nom);
                p4.placement(user, j);
                p4.displayGrilleP();
                coupValid = true;

            }
            catch(ColonneHorsLimite e)
            {
                System.out.println(e.getMessage());
            }
            catch(PionDejaPresent e)
            {
                System.out.println(e.getMessage());
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

    }


    public static String loopGameP(Puissance_4 p4, IHM ihm, Joueur j1, Joueur j2)
    {
        boolean vainqueur = false;
        do
        {
            entrerCoupP(p4, ihm, j1);

            if(p4.win(j1))
            {

                vainqueur = true;
                return j1.nom;
            }

            entrerCoupP(p4, ihm, j2);

            if(p4.win(j2))
            {
                vainqueur = true;
                return j2.nom;
            }

            // Tant qu'on ne trouve pas de vainqueur ou que le grille de jeu n'est pas pleine on boucle
        } while(!(vainqueur || p4.g.checkGrillefull()));

        return "ex aequo";

    }


// Fonction pour le Morpion :





    public static String loopGameM(Morpion m, IHM ihm, Joueur j1, Joueur j2)
    {
        boolean vainqueur = false;
        do
        {
            entrerCoupM(m, ihm, j1);

            if(m.win(j1))
            {

                vainqueur = true;
                return j1.nom;
            }

            entrerCoupM(m, ihm, j2);

            if(m.win(j2))
            {
                vainqueur = true;
                return j2.nom;
            }



        } while(!(vainqueur|| m.g.checkGrillefull()) );

        return "ex aequo";
    }




    public static void entrerCoupM(Morpion m, IHM ihm, Joueur j)
    {
        boolean coupValid = false;
        while(!(coupValid))
        {
            try
            {
                m.displayGrilleM();
                int[] user =  ihm.UserInputGameM(j.nom);
                m.placement(user, j);
                m.displayGrilleM();
                coupValid = true;

            }
            catch(CoordonneHorsLimite e)
            {
                System.out.println(e.getMessage());
            }
            catch(PionDejaPresent e)
            {
                System.out.println(e.getMessage());
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

    }
}