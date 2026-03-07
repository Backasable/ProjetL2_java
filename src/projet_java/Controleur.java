package projet_java;

import projet_java.modele.Enregistre;
import projet_java.modele.Joueur;
import projet_java.modele.Morpion;
import projet_java.modele.Puissance_4;
import projet_java.modele.Jeu;
import projet_java.vue.IHM;


public class Controleur {
    private IHM ihm;
    private Enregistre save;


    public Controleur(IHM ihm)
    {
        this.ihm = new IHM();
        this.save = new Enregistre();



    }

    public void lancerJeu() {

        Joueur[] tabJoueur = creationJoueur(ihm);
        Joueur j1 = tabJoueur[0];
        Joueur j2 = tabJoueur[1];




        int choicePlayerG = ihm.UserInputChoiceGame();
        Jeu jeu = creationJeu(choicePlayerG);

        int cptNbPartie = 0;


        while (newGame(ihm)) {

            cptNbPartie++;
            String res = loopGame(jeu, ihm, j1, j2);
            save.ajouterRes(res);
            ihm.affichierGagnant(res);
            jeu.g.clearGrille();

        }

        if (cptNbPartie ==0 )
        {
            ihm.aucunePartieJouer();
            return;  // <- le return pour mettre fin au programme
        }
        else if(save.affichMap().length == 0)
        {
            ihm.aucunePartiegagne();
        }
        else
        {
            ihm.affichageScoreJeu(save.affichMap());
            ihm.nameVainqueur(save.CalculerVainqueur());
        }

    }


    public Jeu creationJeu(int choicePlayerG) {

        if (choicePlayerG == 1) {

            Jeu jeu = new Puissance_4(6, 7);
            return jeu;
        } else {
            Jeu jeu = new Morpion(3, 3);
            return jeu;
        }
    }

    public boolean newGame(IHM ihm) {
        String choice = ihm.UserInputNewGame();
        return choice.equals("y");
    }


// Fonction Pour le tout début :


    public Joueur[] creationJoueur(IHM ihm) {
        ihm.acceuil();
        Joueur[] tabJoueur = new Joueur[2];

        String playerName = ihm.UserInputName();
        Joueur j1 = new Joueur(playerName);
        tabJoueur[0] = j1;

        String playerName2 = ihm.UserInputName();
        Joueur j2 = new Joueur(playerName2);
        tabJoueur[1] = j2;
        return tabJoueur;

    }


    public static void entrerCoup(Jeu jeu, IHM ihm, Joueur j) {
        boolean coupValid = false;
        while (!(coupValid))
        {
            try {
                if (jeu instanceof Puissance_4) {
                    ihm.displayGrilleP(jeu.g);
                    int[] user = ihm.userInputGame(jeu, j.nom);
                    jeu.placement(user, j);
                    ihm.displayGrilleP(jeu.g);
                    coupValid = true;

                } else if (jeu instanceof Morpion) {
                    ihm.displayGrilleM(jeu.g);
                    int[] user = ihm.userInputGame(jeu, j.nom);
                    jeu.placement(user, j);
                    ihm.displayGrilleM(jeu.g);
                    coupValid = true;
                }


            } catch (ColonneHorsLimite e) {
                System.out.println(e.getMessage());

            } catch (PionDejaPresent e) {
                System.out.println(e.getMessage());

            } catch (CoordonneHorsLimite e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }


    public static String loopGame(Jeu jeu, IHM ihm, Joueur j1, Joueur j2) {
        boolean vainqueur = false;
        do {

            entrerCoup(jeu, ihm, j1);

            if (jeu.win(j1)) {

                vainqueur = true;
                return j1.nom;
            }

            if (jeu.g.checkGrillefull())
            {
                vainqueur = true; // <- optionnel mais pour que ce soit joli je le met
                return "ex aequo";
            }


            entrerCoup(jeu, ihm, j2);

            if (jeu.win(j2)) {
                vainqueur = true;
                return j2.nom;
            }


            // Tant qu'on ne trouve pas de vainqueur ou que le grille de jeu n'est pas pleine on boucle
        } while (!(vainqueur));

        return "Erreur innatendue Controleur -> meth :loopGame ";  // <- On met ce return pour les mêmes raison que la méthode userInputChoiceGame dans la class IHM
    }


}

