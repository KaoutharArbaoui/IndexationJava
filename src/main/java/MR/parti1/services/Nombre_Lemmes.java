package MR.parti1.services;

import java.util.Collections;
import java.util.List;

public class Nombre_Lemmes implements  Comparable<Nombre_Lemmes>{

    String lemme;
    int nombre;

    public Nombre_Lemmes(String lemme, int nombre) {
        this.lemme = lemme;
        this.nombre = nombre;
    }

    public Nombre_Lemmes() {

    }

    //trier la liste des lemmes par fréquence
    public static List<Nombre_Lemmes> Sort_nombre_lemmes(List<Nombre_Lemmes> l)
    {
        Collections.sort(l);
        return l;
    }

    public String getLemme() {
        return lemme;
    }

    public int getNombre() {
        return nombre;
    }

    @Override
    public int compareTo(Nombre_Lemmes o) {
        return this.nombre<o.getNombre() ? 1 : this.nombre==o.getNombre()? 0 : -1;
    }

}
