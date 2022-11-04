package MR.parti1.services;

public class Question1 {
    public String Niveau;
    int Nombre_Mots;
    int Nombre_Lemmes;
    int Nouv_lemmes;

    public Question1(String niveau, int nombre_Mots, int nombre_Lemmes, int nouv_lemmes) {
        Niveau = niveau;
        Nombre_Mots = nombre_Mots;
        Nombre_Lemmes = nombre_Lemmes;
        Nouv_lemmes = nouv_lemmes;
    }


    public String getNiveau() {
        return Niveau;
    }

    public int getNombre_Mots() {
        return Nombre_Mots;
    }

    public int getNombre_Lemmes() {
        return Nombre_Lemmes;
    }

    public int getNouv_lemmes() {
        return Nouv_lemmes;
    }
}
