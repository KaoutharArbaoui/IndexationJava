package MR.parti1.services;

public class Niveau_Fréq_lemme {
    String niveau;
    String lemme;
    int Fréquence;

    public Niveau_Fréq_lemme(String niveau, String lemme, int Fréquence) {
        this.niveau = niveau;
        this.lemme = lemme;
        this.Fréquence = Fréquence;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getLemme() {
        return lemme;
    }

    public int getFréquence() {
        return Fréquence;
    }
}
