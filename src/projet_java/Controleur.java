package projet_java;

import projet_java.modele.*;
import projet_java.vue.IHM;


public class Controleur {
    private IHM ihm;
    private Enregistre save;
    private Difficulte niveauDiff;



    public Controleur(IHM ihm)
    {
        this.ihm = new IHM();
        this.save = new Enregistre();
        this.niveauDiff = new Difficulte();

    }

//=======================Methode_de_base_necessaire==================================================


    public void lancerJeu() {

        Joueur[] joueurs = creationjoueur();
        Joueur j1 = joueurs[0];
        Joueur j2 = joueurs[1];

        int choicePlayerG = ihm.UserInputChoiceGame();
        Jeu jeu = creationJeu(choicePlayerG);


        BrainIA iaBrain = null;
        if (choicePlayerG == 1)
        {
            iaBrain = new Puissance4_IA(jeu.g, niveauDiff);
        }
        else
        {
            iaBrain = new MorpionIA(jeu.g, niveauDiff);
        }



        int cptNbPartie = 0;


        while (newGame(ihm)) {

            // Comme je ne sais comment faire
            // Je vérifie à chaque partie si le j2 est bien une IA est si c'est le cas on
            // demande le niveau de difficulte que l'ia doit avoir à chaque new game
            if (j2.getNom().equals("IA"))
            {
                niveauDiff.setDifficulte(ihm.difficultyChoice());  // <- On change le niveau de difficulé
            }

            cptNbPartie++;
            String res = loopGame(jeu, j1, j2, iaBrain);
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

    public boolean newGame(IHM ihm)
    {
        String choice = ihm.UserInputNewGame();
        return choice.equals("y");
    }

    public Joueur[] creationjoueur()
    {
        ihm.acceuil();
        Joueur j1 = new Joueur(ihm.UserInputName());
        int choiceMode = ihm.choiceModeJeu();

        Joueur j2 = null;

            if (choiceMode == 2) {
                j2 = new Joueur(ihm.UserInputName());

            }
            else
            {
                j2 = new Joueur("IA");
            }

        Joueur[] joueurs = new Joueur[2];
        joueurs[0] = j1;
        joueurs[1] = j2;

        return joueurs;

    }

    public void entrerCoup(Jeu jeu, IHM ihm, Joueur j) {
        boolean coupValid = false;
        while (!(coupValid))
        {
            try {
                ihm.displayGrille(jeu);
                int[] user = ihm.userInputGame(jeu, j.nom);
                jeu.placement(user, j);
                ihm.displayGrille(jeu);
                coupValid = true;

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

    public String loopGame(Jeu jeu, Joueur j1, Joueur j2, BrainIA iaBrain)
    {
        // On regarde si le j1 a saisi l'option multiJoueur ou IA et si c'est
        // l'ia alors le j2 est automatiquement renommer IA d'où ce que je fait en dessous
        // on stockera ensuite cela dans une var qu'on réutilise ligne : 169
        boolean ia = false;
        if (j2.getNom().equals("IA")) // On supposera que si le j1 selection le multijoueur alors j2 ne se nommera jamais IA
        {
            ia = true;
        }


        boolean vainqueur = false;
        do {

            // On check si la grille est plein avant que le joueur joue
            if (jeu.g.checkGrillefull())
            {
                vainqueur = true; // <- optionnel mais pour que ce soit joli je le met
                return "ex aequo";
            }

            // j1 joue son coup
            entrerCoup(jeu, ihm, j1);

            // On vérif si j1 a gagné après son coup
            if (jeu.win(j1)) {

                vainqueur = true;
                return j1.nom;
            }

            // On check la grille avant que l'ia ou le 2ème joueur joue
            if (jeu.g.checkGrillefull())
            {
                vainqueur = true; // <- optionnel mais pour que ce soit joli je le met
                return "ex aequo";
            }


            // si oui l'ia joue
            if (ia)
            {
                iaBrain.entrerCoupIA(jeu, j2, j1);
                ihm.displayGrille(jeu);
            }
            // sinon, le j2 joue son coup
            else
            {
                entrerCoup(jeu, ihm, j2);
            }

            // On vérif si l'ia ou j2 a gagné après son coup.
            if (jeu.win(j2)) {
                vainqueur = true;
                return j2.nom;
            }



            // Tant qu'on ne trouve pas de vainqueur on boucle
        } while (!(vainqueur));

        return "Erreur innatendue Controleur -> meth :loopGame ";  // <- On met ce return pour les mêmes raison que la méthode userInputChoiceGame dans la class IHM
    }


}

