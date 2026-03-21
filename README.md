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
Class projet_java.Controleur : 

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
---------------------------------------------Competed
class Joueur
**Attribut**:
public String nom;
public int idJoueur;
public static int cptJoueur = 1;
private int[] caseTrouverCoord;

**Méthode**


Pourquoi avoir fait un identifiant ? 
Car quand j'ai voulut coder la class Morpion je me suis aperçus qu'il me manquer une information 
En effet, Afin de savoir quel Joueur avais quoi comme pion,
Je me suis aperçus qu'il fallait que je les identifie 
et comme je voulais pas utiliser le nom car je me faisais des neux à la tete pour rien 
car dans la grille les symbole sont représenter par 1 pour X et 2 pour O
Donc il fallait que je fasse une association avec le nom du Joueur et 1 et 2 
avec un identifiant entre 1 et 2 
dans la méthode qui vérifira les conditions de réussite j'aurai qu'à comparer si l'identifiant du joueur = le symbole
et on saura qui détient quoi 

Si on nous demande lors de la prochaine itération que le joueur choisisse son pion, on aura qu'a faire un méthode 
qui associe l'identifant du joueur au pion selon le choix entré par le Joueur 


public int  getidJoueur()


public String getNom()


public int[] getcaseTrouverCoord()

public void setcaseTrouverCoord(int[] caseFind)
 


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

        - Sous Note 2: Ce sera projet_java.Controleur qui passera les valeur nb ligne et nb colonne à Grille (Avant les boucles du jeu)
        En gros on fera un petit condition qui en fonction du résultat de la méthode : UserInputChoiceGame() dans l'IHM
        Si le choix et morption on crée l'instance de Grille en passant en paramètre : Grille(3,3)
        Sinon on passe en paramètre : Grille(6,7) <- Dimension plateau puissance 4


**Méthode**

public int getNbcol()

public int getNbLigne()
   
    
public void clearGrille()
Description : 
Rénitialise la grille à chaque nouvelle partie


public void SaisirVal(int [] coord, int idJoueur) throws PionDejaPresent




public checkGrillefull(int colonne)
Description : vérif si la colonne la grille est plein

 

public int[] findCaseVide(int col)
// -> Cette méthode est la stratégie pour : 
// Simuler que le pion déscende jusqu'à avoir touché
// le pion de la case d'en dessous :

    // Vérifie si la colonne indiquer est vide puis vérifie si celle en dessous si elle est vide ou pas
    // et se réitère jusqu'à trouver la colonne
    // pour laquelle, la colonne qui suit n'est plus vide
    // Et ainsi renvoyer les coordonnés de la colonne qui est vide
---------------------------------------------Competed
Class Enregistrement  :

Description : 
Stockera les infos des différentes partie dans un Map (**clé** = Nom J; **valeur** = G (gagné)  ou P (perdue) (autre option tu met 1 pour G et 0 pour P comme ça cela évite de stocker des srting)
Note : Enregistrement communiquera uniquement avec projet_java.Controleur

**Attribut** : 
private Map<String, Integer> dico  (un map)




Réctification J'ai oublié qu'un map ne peut pas avoir de doublon au niveau des clé 
Donc la solution c'est d'avoir un Map comme cela : Map<String,int[]>
Ainsi, on aura juste 2 paire de valeur dont les clé seront le nom des joueurs et les valeur seront des tableaux qui regrouperont les sérit de victoire des joueur

Autre solution : 
faire un Map<String,Integer>
parail ici on a 2 paire de valeur mais la valeur c'est déjà le nb de victoire gagné qui sera incrémenter dans la méthode ajouterRes
-> Je part sur cette solution bcp plus simple


Note : la class enregistrement n'a pas à connaitre de class (De mon point de vu)
Donc seul un attribut map est suffisant 
// plus mtn :
car après avoir pensé aux alternatives, je pense que les class Morpion et Puissance 4 communiquront avec Enregistrement
Mais Enregistrement n'aura pas à connaitre de class 

**Méthode** : 

public void ajouterRes(string PlayerName, int Resultat)  (PS: 1 indiquera Gagné et 0 indiquera Perdu
Description : ajoute la pair clé (PlayerName) valeur (Résultat) dans dico


public String[] affichMap()
Description : // Permet de regrouper dans un tableau où la valeur
d'une colonne est une String : PlayerName | partie gagné : valeur

public string CalculerVainqueur()
Description : Va renvoyer le nom du joueur qui a remporter le plus de partie 
              donc elle ira chercher dans le map le joueur ayant le plus de 1 avec une boucle etc tu sais faire 





-------------------------------------------------Competed
Une class abstract mère: Jeu

(ici ce sera un class qui regroupera les methodes que vont avoir nos jeu
Et comme elle est abstraite on aura pas à les implémenter mtn)

**Attribut**
public Grille g;


**Méthode à implémenter dans les class enfant : Morpion et Puissance 4**

public boolean win()   <- Note : les paramètres dépendront du jeu (Si Morpion il prendre 3 valeur et vérif si c'est les même, Si Puissance 4 il prendra 4 valeur) 
Description : 
Vérif si un joueur a gagné et renvoie True si G ou false si non 
Note : à voir si dans les paramètres on met aussi l'id du joueur comme ça on sait qui a gagné 

-ENL7VE L'ANCIEN DESCRIPTION de boolean win?

public abstract boolean win(Joueur j);
Description :
Vérif si un joueur a gagné et renvoie True si G ou false si non

public abstract void placement(int[] coord, Joueur j) throws PionDejaPresent, ColonneHorsLimite, CoordonneHorsLimite;
Decription :
public abstract void displayGrilleP();
Description : 

public abstract void displayGrilleM();
Description :


-------------------------------------------------------Ongoing
Class enfant Morpion (hérite jeu)

**Attribut**
(
private int nbligne   <- Le constructeur de Morpion recevra un tableau contenant les valeur  nbligne, nbcolone qu'on dispatchra dans les attribut nbligne et nbcolonne
private int nbcolone  <-|
private Grille plateau = new Grille(nbligne, nbcolonne)
)
 -> C'est plus d'actualité, au début je voulais dans la class controleur une méthode
qui prenait en paramètre le choix de l'utilisateur et renvoyer un tableau avec la taille de la grille (nbLigne et nbcolonne)
déjà dedant 
Mais en faisant le lendemain la class Morpion j'ai crée directement dans le constructeur, la grille avec les dimensions déjà dedant 
Comme ça pas besoin de faire tout ce à quoi j'avais pensé avant et c'est plus simple !

**Methode**

public boolean win( int cas1, int case2, int case3)  
Description :
Détaillent les conditions de victoire Du Morpion 

- si case[0][0] = case[0][1] = case[0][2] Détecte la situation victoire ligne : x x x

- Si case[0][0] = case[1][0] = case[2][0] détècte la situation victoire colonne : x
                                                                                  x
                                                                                  x  
- Même principe pour la diagonal, au morpion tu en as 2 donc tu vérif les 2 avec une cond

**ENLEVE BOOLEAN WIN (case1.....)?
public boolean win(Joueur j)
Description: 


public boolean equals(int entier1, int entier2, int entier3, int idJoueur)
Description :


public boolean checkCoordM(int[] coord) throws CoordonneHorsLimite
Description : Vérifie si les coordonnés sont valide


public void placement(int[] coord, Joueur j) throws PionDejaPresent, CoordonneHorsLimite, ColonneHorsLimite
Description : 


public void displayGrilleM()
Description :
affiche la Grille pour le morpion


public void displayGrilleP()
Decription :
Même principe que pour displayGrilleM dans Puissance_4
Affiche la grille pour le Puissance 4

(
VerifCoord(int l, int c)  (l=ligne, c=colonne)
déscription : Vérifie si les coordonné renseigné par le joueur sont valides
c'est à dire :
- le joueur ne doit pas saisir des coordonnés hors de la grille
- Il ne doit pas ressaisir les coordonnés d'une case où il y a déjà un pion
)
// -> Je l'ai mis dans la class grille directement comme
cela on respecte le principe de : expert en information
car sinon si on verfi les coordonner saisi par l'user directement dans Morpion, 
faudra aller chercher les info de la grille dans la class Grille et c'est fatiguant !


-------------------------------------------------------Ongoing
Class enfant Puissance 4 (hérite jeu)


**Methode**


public boolean win(Joueur j)
Description :



public boolean verifColonne(int Ligne, int Colonne, int identifiant)
Description :  
On vérifie si on a un alignement : * * * *  de 4 pions de même couleur sur les colonnes


public boolean verifLigne(int Ligne, int Colonne, int identifiant)
Description : 
On vérif tout les ligne si y'a un alignement de 4 pions de comme ça : *


public boolean verifDiagoDecroissante(int Ligne, int Colonne, int identifiant)
Description : 
On vérif tout les diagonal décroissante si il y a un alignement de 4 pions de ça : *
// On vérifie d'abord si lorsqu'on fait ligne-1 et colonne-1 on est pas hors limite
//   ET
// ensuite on verif si le pion de la case au dessus à gauche est de la même couleur


public boolean verifDiagoCroissante(int Ligne, int Colonne, int identifiant)
Description :
On vérif tout les diagonal croissante si il y a un alignement de 4 pions de ça : *
// On vérifie d'abord si lorsqu'on fait ligne+1 et colonne+1 on est pas hors limite
//   ET
// ensuite on verif si le pion de la case au dessus à droite est de la même couleur


public boolean verifcoloneCoord(int colonne) throws ColonneHorsLimite
Description:
On vérifie Si la colonne saisie, n'est pas dans l'interval de la taille du tableau 
et si c'est le cas, on lève une exception

public void recupLigne(int[] colonne, Joueur j) throws ColonneHorsLimite
Description:
int col = colonne[1];  <- Pk ? colonne[1] et pas colonne[0], car colonne[0] c'est sensé être la ligne mais
par défaut elle est à zero car l'utilisateur n'a rentré que la colonne dans l'IHM



public void placement(int[] colonne, Joueur j)
Description :
On détermine l'emplacement de la case où va se trouver le pion
recupLigne(colonne, j) <- On récupère les coordonné de la case où le pion qu'a joué le joueur est tombé



public void displayGrilleP()
Description:
Affiche la grille pour le Puissance 4
for (int cptCol = 1; cptCol< g.getNbcol()+1; cptCol++)   <-  Le +1 car pour rappel on veut afficher
des coordonné entre 1 et 7 et comme getNbcol = 7 (vu que java commence à compter de 0) on aura jusqu'à 6 et pas 7



public void displayGrilleM()
Description :
affiche la Grille pour le morpion
Note: J'ai pas le choix que de la mettre là afin de pouvoir appliquer le polymorphisme car si je l'ai mettais 
dans l'IHM, sa implique que l'IHM connaisse Grille donc le modèle sauf que on est dans un MVC et c'est non !




Tache à faire : 

modifier enregistre pour que joueur connaisse son score  A voir mais Non flemme ! plus besoin
pour affichage des grilles on doit use toString  __Completed !
Gestion des jetons poussance 4 J R  et Morpion c'est X O  __completed !



Explication du code minimax pourquoi cela "marché" même si on l'avait mal implémenté :
Raison : 
    - 1 : car on l'appliquer sur un morpion donc on avait pas beaucoup de cas à traiter donc on voyait pas réellement le cas d'erreur 
    
Explication de ce qu'il manqué au minimax pourqu'il soit bien implémenté : 

Supposons qu'il reste 3 case vide et que c'est le tour de l'ia
   1 2 3
1| X O X |
2| O X - |
3| - - O |

On a l'arbre suivant que le prog va devoir parcourir :
       [IA joue - MAX]
     /       |       \
(2,3)       (3,1)      (3,2)
|             |           |
[X joue-MIN]  [X joue-MIN] [X joue-MIN]
/     \         /     \       /      \
(3,1)  (3,2)   (2,3)  (3,2)  (2,3)   (3,1)
|        |       |      |      |        |
[IA-MAX][IA-MAX][IA-MAX][IA-MAX][IA-MAX][IA-MAX]
|        |        |      |      |        |
(3,2)  (3,1)     (3,2)  (2,3)  (3,1)   (2,3)
|        |        |      |      |        |
-1       0       +1      0      +1      -1

Si on applique l'ancienne algo : 

    private int  minmax(int[] coup, Joueur ia, Joueur j, Jeu jeu, boolean maximiseur)
    {
        ArrayList<int[]> coupPossible = recupCoordCaseVide(); // <- on recherche les coup possible une nouvelle fois

        while(evaluer(jeu, j, ia)==2)  // <- tant qu'on a pas de vainqueur ou d'exaeqo on boucle
        {
            if (maximiseur) // <- tour de l'ia de jouer
            {
                for (int[] coups : coupPossible) {
                    placement(coups, ia);
                    int score = minmax(coups, ia, j, jeu, false);
                    annulerCoup(coups);
                    return score;          // <- /!\ cette ligne là est le problème 
                }

            } else  // <- tour du joueur du jouer
            {
                for (int[] coups : coupPossible) {
                    placement(coups, j);
                    int score = minmax(coups, ia, j, jeu, true);
                    annulerCoup(coups);
                    return score;          // <- /!\ cette ligne là est le problème 
                }
            }
        }
        return evaluer(jeu, j, ia);
    }

Pourquoi cette ligne là est le problème : return score; 

Car elle est placé au mauvais endroit !!!

Si on reprend notre arbre : 

       [IA joue - MAX]
     /       |       \
(2,3)       (3,1)       (3,2)
|             |              |
[X joue-MIN]  [X joue-MIN] [X joue-MIN]
/     \         /     \       /      \
(3,1)  (3,2)   (2,3)  (3,2)  (2,3)   (3,1)
|        |       |      |      |        |
[IA-MAX][IA-MAX][IA-MAX][IA-MAX][IA-MAX][IA-MAX]
|        |        |      |      |        |
(3,2)  (3,1)     (3,2)  (2,3)  (3,1)   (2,3)
|        |        |      |      |        |
-1       0       +1      0      +1      -1

l'ago minimax va uniquement parcourir pour chaque coup identifier dans 
la méthode meilleurCoup : (je fait reférence à ces coups là : ( (2,3) ; (3,1) ; (3,2) ))
UNIQUEMENT leur branche gauche respective
Donc pour le coup (2,3) par exemple, 
ce sera cette branche là qui sera parcourue Uniquement :

(2,3)
|
[X joue-MIN]   <- tour du joueur de jouer
/
(3,1)
|  
[IA-MAX]   <- tour de l'ia de jouer
|
(3,2)
|
-1  

La branche de droite ne sera jamais parcourue 
Donc il nous suffirai de mettre à la bonne place : return score
Alors faisons le !! : 
Voici l'algo corrigé : 

    private int  minmax(int[] coup, Joueur ia, Joueur j, Jeu jeu, boolean maximiseur)
    {
        ArrayList<int[]> coupPossible = recupCoordCaseVide(); // <- on recherche les coup possible une nouvelle fois

        if (evaluer(jeu, j, ia)==2)  // <- /!\ Note : J'ai changer le while en un if car la while ne sert à rien ici car on veut juste vérifié si on a atteint le fond de la récursion (c'est notre cas de base ici) while serait même dangereux pour crée des bug et rajoute une charge de travaille suplémentaire  
        {
            if (maximiseur) // <- tour de l'ia de jouer
            {
                for (int[] coups : coupPossible) {
                    placement(coups, ia);
                    int score = minmax(coups, ia, j, jeu, false);
                    annulerCoup(coups);
                            
                }

            return score;  // <- mtn, en mettant : return score ici on parcoure aussi la branche de droite (donc on regarde aussi, les autres coup que peut jouer le joueur)

            } else  // <- tour du joueur du jouer
            {
                for (int[] coups : coupPossible) 
                {
                    placement(coups, j);
                    int score = minmax(coups, ia, j, jeu, true);
                    annulerCoup(coups);
                             
                }
            return score; // <- pareil ici mtn, on peut aussi regarder les autre coup que peut joué l'ia (Note : si le prog arrive ici il ne reste plus qu'une case vide à joué donc c'est pas vraiment util mais si on prend un plateau plus grand ça change la donne)
            }
        }
        return evaluer(jeu, j, ia);
    }

Mtn : on pourrait pensé qu'on a terminé sauf qu'il nous manque une chose important ! 
Lorqu'on a atteint la fin d'une branche et qu'on remonte dans l'arbre avec les scores 
Comment fait-on pour les comparer ? 

Si on est dans cette situatino comment on fait : 

       [IA joue - MAX]
     /       |       \
(2,3)       (3,1)       (3,2)
|             |              |
[X joue-MIN]  [X joue-MIN]    [X joue-MIN]
  /    \         /   \            /     \
(3,1)  (3,2)   (2,3)  (3,2)     (2,3)   (3,1)
|        |       |      |          |       |
-1       0       -1     +1        +1       -1     

On fait Comment ? pour comparer les scores et remonter celui qui nous interesse ? 

==================================================================================
Avant de se poser la question comprenons comment l'algo minmax gère les scores pour les remonter

Supposons qu'on est dans la branche de gauche de l'arbre au dessus,
et que le Joueur du coup (vu que c'est son tour) joue (3,1)
L'algo va ensuite descendre dans la récursion donc 
il va aller, ici dans l'algo : int score = minmax(coups, ia, j, jeu, true);
Ensuite, il s'aperçois grâce à cette ligne : if (evaluer(jeu, j, ia)==2)   
Que la grille est pleine ! 
Donc il va aller ici : return evaluer(jeu, j, ia); 
Et retourner le score 
Ensuite on remonte dans la récursion avec le score : -1  

ET on retourne dans cette partie de l'aglo vu que le dernier qui a joué c'était le joueur :

            } else  // <- tour du joueur du jouer
            {
                for (int[] coups : coupPossible)    // Etape 3 : On va regarde le 2 ème coup possible ici : (3,2)  
                {
                    placement(coups, j);
                    int score = minmax(coups, ia, j, jeu, true);   // <- Etape 1: on se retrouve ici dans l'algo !!! avec notre -1 déterminé dans la récursion d'avant  
                    annulerCoup(coups);      // Etape 2 : on annule le coup ici (3,1)
                                               
                }
            return score; // <- pareil ici mtn, on peut aussi regarder les autre coup que peut joué l'ia (Note : si le prog arrive ici il ne reste plus qu'une case vide à joué donc c'est pas vraiment util mais si on prend un plateau plus grand ça change la donne)
            }

!!! Mais On a oublie Un étape Crucial !!!   Que fait on avec le score qu'on a reçus dans l'Etape 1 ???

parce que si laisse cela comme ça, là : int score = -1 mais 
Lorqu'on aura fini de regarder l'autre coup possible ici (3,2) et qu'on va remonté avec le score de se coup ici 0
Alors la valeur de la variable score qui était de -1 sera écrasé par 0
Et nous on veut pas ça !
Car nous ce qu'on veut c'est les comparer les scores !!! pas les écrasés
En respectant la règle : 
Si c'est maximiseur donc l'ia on veut le plus haut score 
et,
Si c'est le minimiseur donc le joueur (Note : quand maximisuer vaut false c'est équivalant au minimiseur)
On veut le plus bas score.  ( Car le Joueur doit faire en sorte que l'ia perde !!! donc il préfèrera choisir des scores comme -1 où là c'est lui qui gagne !! )

Donc ce qu'on va faire c'est avoir un bestscore dans les 2 cas : maximiser, minimiseur

Si c'est le cas 1 : maximiseur, bestscore sera initialisé à la valeur la plus basse (afin d'avoir la valeur la plus haute on commence par la plus basse)

Si c'est le cas 2 : minimiseur, bestscore sera initialisé à la valeur la plus haute (afin d'avoir la valeur la plus basse possible on commence par la plus haute)

et à chaque fois qu'on remonte dans la récursion, on va comperer la valeur qu'on remonte avec celle du bestscore de chaque cas 

Et ensuite, une fois qu'on a regarder tous les coup possible ici : (3,1) et (3,2) (vu que pour rappel on a supposé au début qu'on était dans la branche de gauche) 
C'est à dire, qu'on a fait toutes les itérations de la boucle : for (int[] coups : coupPossible) 
Alors, on peut retourner bestScore ici entre -1 et 0 joueur va prendre -1 
Et ensuite, on est dans cette situation :


       [IA joue - MAX]
   -1 /       |       \
(2,3)       (3,1)       (3,2)

Donc là, le bestscore de l'IA est toujours initialisé avec la plus basse valeur possible 
PK ? :
c'est parce que chaque appel récursif crée ses propres variables 
locales indépendantes. Le bestscore de la récursion du bas et celui de 
la récursion du haut sont deux variables différentes en mémoire

De ce fait, on a jamais touché au bestScore de l'ia du haut pour cette récursion 
d'où le fait qu'elle soit toujours initialisé avec la plus petit valeur 

Ainsi on mettant en place ces changements, on a l'algo suivant : 


    private int  minmax(int[] coup, Joueur ia, Joueur j, Jeu jeu, boolean maximiseur)
    {
    ArrayList<int[]> coupPossible = recupCoordCaseVide(); // <- on recherche les coup possible une nouvelle fois
    
        int eval = evaluer(jeu, j, ia);
        if (eval==2)
        {
            if (maximiseur) // <- tour de l'ia de jouer
            {
                int bestScore = -2345654321; // on initialise au plus bas car on veut le plus haut score
                
                for (int[] coups : coupPossible) {
                    placement(coups, ia);
                    int score = minmax(coups, ia, j, jeu, false);
                    annulerCoup(coups);
    
                    if (score > bestScore)
                    {
                        bestScore = score;
                    }
                    
                }
    
                return score;
            } else  // <- tour du joueur du jouer
            {
                int bestScore = 3456754; // On initilalise au plus haut afin de d'obtenir le plus bas score
                
                for (int[] coups : coupPossible)
                {
                    placement(coups, j);
                    int score = minmax(coups, ia, j, jeu, true);
                    annulerCoup(coups);
                    
                    // Lorsqu'on remonte, on compare bien les score
                    if (score < bestScore)
                    {
                        bestScore = score;
                    }
    
                }
                return score; 
            }
        }
        return eval;
    }

// Note : il y a un complément sur une feuille qui détaille une partie avec une fork (pour le morpion) 
et comme l'ia est condannée (car tous les coup qu'elle a évalué sont à -1, et au lieu de faire en sorte de me bloqué, 
elle va joué son premier coup qu'elle a évalué à -1 regarde la feuille j'ai fait une explication 