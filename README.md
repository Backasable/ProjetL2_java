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
int col = colonne[1];  <- car colonne[0] c'est sensé être la ligne mais
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
dans l'IHM, sa implique que l'IHM connaisse Grille donc le modème sauf que on est dans un MVC et c'est non !









