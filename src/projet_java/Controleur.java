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

        ihm.acceuil();
        Joueur j1 = new Joueur(ihm.UserInputName());
        int choiceMode = ihm.choiceModeJeu();

        if (choiceMode == 2) {
            lancerJeuMulti(ihm, j1);
        }
        else
        {
            lancerJeuIA(j1);
        }


    }

    public void lancerJeuIA(Joueur j1)
    {
        Joueur IA = new  Joueur("AI");
        int choicePlayerG = ihm.UserInputChoiceGame();
        Jeu jeu = creationJeu(choicePlayerG);

        int cptNbPartie = 0;


        while (newGame(ihm)) {

            cptNbPartie++;
            String res = loopGameIA(jeu, ihm, j1, IA);
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



    public String loopGameIA(Jeu jeu, Joueur j1,  Joueur IA)
    {


    }






    public void lancerJeuMulti(IHM ihm, Joueur j1)
    {

        Joueur j2 = new Joueur(ihm.UserInputName());

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


    public String loopGame(Jeu jeu, IHM ihm, Joueur j1, Joueur j2) {
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


            // Tant qu'on ne trouve pas de vainqueur on boucle
        } while (!(vainqueur));

        return "Erreur innatendue Controleur -> meth :loopGame ";  // <- On met ce return pour les mêmes raison que la méthode userInputChoiceGame dans la class IHM
    }


}

