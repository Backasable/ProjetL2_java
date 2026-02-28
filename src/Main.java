package projet_java;

import projet_java.vue.IHM;

public class Main {
    public static void main(String[] args)
    {

        IHM ihm = new IHM();
        Controleur ctl = new Controleur(ihm);
        ctl.lancerJeu();
    }
}
