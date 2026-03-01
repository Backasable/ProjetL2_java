package projet_java;

import projet_java.modele.Enregistre;
import projet_java.modele.Joueur;
import projet_java.modele.Morpion;
import projet_java.modele.Puissance_4;
import projet_java.modele.Jeu;
import projet_java.vue.IHM;


public class Controleur {
    private IHM ihm;

    public Controleur(IHM ihm) {
        this.ihm = new IHM();
    }

    public void lancerJeu() {

        Joueur[] tabJoueur = debJeu(ihm);
        Joueur j1 = tabJoueur[0];
        Joueur j2 = tabJoueur[1];

        Enregistre save = new Enregistre();


        int choicePlayerG = ihm.UserInputChoiceGame();
        Jeu jeu = creationJeu(choicePlayerG);


        while (newGame(ihm)) {


            String res = loopGame(jeu, ihm, j1, j2);
            save.ajouterRes(res);
            ihm.affichierGagnant(res);
            jeu.g.clearGrille();

        }
        ihm.nameVainqueur(save.CalculerVainqueur());

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


    public Joueur[] debJeu(IHM ihm) {
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
        while (!(coupValid)) {
            try {
                if (jeu instanceof Puissance_4) {
                    jeu.displayGrilleP();
                    int[] user = ihm.userInputGame(jeu, j.nom);
                    jeu.placement(user, j);
                    jeu.displayGrilleP();
                    coupValid = true;
                } else if (jeu instanceof Morpion) {
                    jeu.displayGrilleM();
                    int[] user = ihm.userInputGame(jeu, j.nom);
                    jeu.placement(user, j);
                    jeu.displayGrilleM();
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

            entrerCoup(jeu, ihm, j2);

            if (jeu.win(j2)) {
                vainqueur = true;
                return j2.nom;
            }

            // Tant qu'on ne trouve pas de vainqueur ou que le grille de jeu n'est pas pleine on boucle
        } while (!(vainqueur || jeu.g.checkGrillefull()));

        return "ex aequo";

    }


}

