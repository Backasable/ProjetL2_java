# Projet-Java

Source :
https://www.youtube.com/watch?v=gs-61l4Z32M architecture MVC explicat°


Explication des Note : 
Competed : veut dire que de mon point de vu il n'y a plus rien à rajouter
Ongoing : Il manque des choses (méthode ou attribut)
SOS : J'ai pas d'idée il faut en discuter
Semi-Competed : c'est quasiment fini

Plan de création suivant le modèle MVC : 

------------------------------------------------------------------------Competed
Class IHM (c'est la Vu) 
**Méthode**

public int[][] UserInputGame()  
Déscription : récupe les coordonné de la case saisit par le joueur et les retournent dans un tableau pour le controleur qui le communiquera à la grille

public int UserInputChoiceGame()
Déscription : récupe le choix du jeux du joueur PS il devra choisir rentrer un entier

public string UserInputName()
Description : récup nom Joueur

public string FactionChoice()  (Optionnel)  PS décide toi si tu la met dans la class controleur ou la class Jeu 
Déscription : choisit aléatoirement la faction du Joueur

public string UserInputNewGame()
Descption : recup la réponse du Joueur si il veut lancer Une nouvelle partie

---------------------------------------------Ongoing
Class Controleur : 

**Attribut** : 
private Joueur j                 (il a besoin de connaitre les joueur )
private Enregistrement save      (Il a besoin de communiqué avec enregistrement pour save les res des game)
private IHM ihm                  (Il a besoin de l'ihm (Vu) pour savoir ce qu'a saisit le User)
private Grille grille

**Méthode** 
pubic void lancerJeu()
Description : 
Contiendra les 2 boucles du jeu

2 car :
Boucle 1 :
pour que le jeu marche
Boucle 2 :
Demande au joueur s'il veut faire une nouvelle partie

public int[] paramettreGrille(IHM ihm)
Description : suivant le resultat de la méthode de l'ihm: public int UserInputChoiceGame()
la méthode paramettreGrille renvera un tableau qui contiendra le nbLigne de la grille et nb de colonne de la grille
Donc on aura une condition qui vérifira le choix du user et s'il a choisit morpion je crée directement un tableau qui contient les valeur nbligne et nb colonne 
même chose si le choix avait été puissance 4 
Ensuite l'idée est que le tableau qui sera renvoyé par le methode sera stocker dans une variable. Et avec cette variable, on 
Intancie grille pour lui mettre dans son constructeur les données de la variable 
Car dans la class grille on a un constructeur qui prend en paramètre le nb ligne et nb colonne
Comme cela on peut manipuler grille sans avoir à passer par une methode pour la crée 


==================================
    Plan de création du Modèle
==================================
---------------------------------------------SOS
class Joueur
**Attribut**:
private string PlayerName




---------------------------------------------Competed
Class Grille :
Note : Cette class communique avec Jeu

**Attribut**
private Jeu game
private int[][] plateau  <- Notre plateau de jeu sera un tableau 2 dimension (une matrice)
private int nbLigne 
private int nbColonne 

Note 1 : On aura besoin du nb de ligne et colonne afin de pouvoir faire des op° sur la grille

Note 2 : grille aura un constructeur qui prendra en paramettre le nb de ligne et nb de colonne et pourra crée un tableau
         à partir de ces valeurs
(oublie pas qu'on peut faire cela : this.plateau = new int[nbCol][nbLi];) <- on crée directement notre tableau

        - Sous Note 2: Ce sera Controleur qui passera les valeur nb ligne et nb colonne à Grille (Avant les boucles du jeu)
        En gros on fera un petit condition qui en fonction du résultat de la méthode : UserInputChoiceGame() dans l'IHM
        Si le choix et morption on crée l'instance de Grille en passant en paramètre : Grille(3,3)
        Sinon on passe en paramètre : Grille(6,7) <- Dimension plateau puissance 4


**Méthode**


public void clearGrille()
Description : 
Rénitialise la grille à chaque nouvelle partie

public void displayGrille()
Description : Affichera l'état de la grille donc tu aura une 2 boucle qui parcouront le tableau et afficheront les valeur sous la forme d'un plateau







---------------------------------------------Competed
Class Enregistrement  :

Description : 
Stockera les infos des différentes partie dans un Map (**clé** = Nom J; **valeur** = G (gagné)  ou P (perdue) (autre option tu met 1 pour G et 0 pour P comme ça cela évite de stocker des srting)
Note : Enregistrement communiquera uniquement avec Controleur

**Attribut** : 
private Map<String, Integer> dico  (un map)

Note : la class enregistrement n'a pas à connaitre de class (De mon point de vu)
        Donc seul un attribut map est suffisant 

**Méthode** : 

public void ajouterRes(string PlayerName, int Resultat)  (PS: 1 indiquera Gagné et 0 indiquera Perdu
Description : ajoute la pair clé (PlayerName) valeur (Résultat) dans dico

public string CalculerVainqueur()
Description : Va renvoyer le nom du joueur qui a remporter le plus de partie 
              donc elle ira chercher dans le map le joueur ayant le plus de 1 avec une boucle etc tu sais faire 




-------------------------------------------------Ongoing
Une class abstract mère: Jeu

(ici ce sera un class qui regroupera les methodes que vont avoir nos jeu
Et comme elle est abstraite on aura pas à les implémenter mtn)

**Attribut**
private Grille plateau 
private Joueur j1
private Joueur j2


**Méthode à implémenter dans les class enfant : Morpion et Puissance 4**

public boolean win()   <- Note : les paramètres dépendront du jeu (Si Morpion il prendre 3 valeur et vérif si c'est les même, Si Puissance 4 il prendra 4 valeur) 
Description : 
Vérif si un joueur a gagné et renvoie True si G ou false si non 
Note : à voir si dans les paramètre on met aussi le joueur comme ça on sait qui a gagné 

public boolean VerifCoord(int[] UserInputGame)
Description : 
Vérifie les coordonnées saisi par l'utilisateur sont valide et si c'est pas le cas, renvoie une exception donc sur la signature de la méthode on fait un throws InputMismatchException 
qu'on gérera avec un try catch dans le controleur 


-------------------------------------------------------Ongoing
Class enfant Morpion (hérite jeu)

**Attribut**
private int nbligne   <- Le constructeur de Morpion recevra un tableau contenant les valeur  nbligne, nbcolone qu'on dispatchra dans les attribut nbligne et nbcolonne
private int nbcolone  <-|
private Grille plateau = new Grille(nbligne, nbcolonne)
**Methode**

public boolean win( int cas1, int case2, int case3)  
Description :
Détaillent les conditions de victoire Du Morpion 

- si case[0][0] = case[0][1] = case[0][2] Détecte la situation victoire ligne : x x x

- Si case[0][0] = case[1][0] = case[2][0] détècte la situation victoire colonne : x
                                                                                  x
                                                                                  x  
- Même principe pour la diagonal, au morpion tu en as 2 donc tu vérif les 2 avec une cond


VerifCoord(int l, int c)  (l=ligne, c=colonne)
déscription : Vérifie si les coordonné renseigné par le joueur sont valides
c'est à dire :
- le joueur ne doit pas saisir des coordonnés hors de la grille
- Il ne doit pas ressaisir les coordonnés d'une case où il y a déjà un pion


-------------------------------------------------------Ongoing
Class enfant Puissance 4 (hérite jeu)










