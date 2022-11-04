package MR.parti1.services;

import java.util.Collections;
import java.util.List;

public class Nombre_mots implements  Comparable<Nombre_mots>{
    String mots;
    int nombre;

    public Nombre_mots() {
    }


    public Nombre_mots(String mots, int nombre) {
        this.mots = mots;
        this.nombre = nombre;
    }

    public String getMots() {
        return mots;
    }

    public int getNombre() {
        return nombre;
    }

    //trier la liste des mots par fréquence
    public List<Nombre_mots> Sort_nombre_mots(List<Nombre_mots> l)
    {
        Collections.sort(l);
        return l;
    }

    @Override
    public int compareTo(Nombre_mots o) {
        return this.nombre<o.getNombre() ? 1 : this.nombre==o.getNombre()? 0 : -1;
    }
}
