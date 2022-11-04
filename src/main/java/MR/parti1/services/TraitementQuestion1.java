package MR.parti1.services;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import MR.parti1.services.Traitement;

public class TraitementQuestion1 {

    public TraitementQuestion1() throws IOException {
    }

//+++++++++++++++++++++++++++++++++Question 1+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public List<String> mot_vide() throws IOException {
        File Vide = new File("src/main/java/MR/parti1/Fichiers/MotsVide/stopwords-ar.txt");
        BufferedReader br =new BufferedReader(new FileReader(Vide));
        List<String> list_vide=new ArrayList<>();
        String mot_vide;
        while ((mot_vide=br.readLine())!=null){
            mot_vide=mot_vide.replace(" ","");
            list_vide.add(mot_vide);
        }
        return list_vide;
    }
    public List<String> listMotsVide=new ArrayList<>(this.mot_vide());

    public String  remp(String str){
        str=str.replace("...","");
        str=str.replace("","");
        str=str.replace("|","");
        str=str.replace("(","");
        str=str.replace(")","");
        str=str.replace("[","");
        str=str.replace("]","");
        str=str.replace("{","");
        str=str.replace("}","");
        str=str.replace(".","");
        str=str.replace(";","");
        str=str.replace(",","");
        str=str.replace("?","");
        str=str.replace("!","");
        str=str.replace("=","");
        str=str.replace("-","");
        str=str.replace("+","");
        str=str.replace(":","");
        str=str.replace("\"","");
        str=str.replace("\'","");
        str=str.replace("،","");
        str=str.replace("؛","");
        str=str.replace("‘","");
        str=str.replace("ـ","");
        str=str.replace("\t"," ");
        str=str.replace("<<","");
        str=str.replace(">>","");
        str=str.replace("&","");
        str=str.replace("#","");
        str=str.replace("*","");
        str=str.replace("؟","");
        str=str.replace("~","");
        str=str.replace("$","");
        str=str.replace("^","");
        str=str.replace("%","");
        str=str.replace("،","");
        str=str.replace("’","");
        str=str.replace("‘","");
        str=str.replace("؛","");
        str=str.replace("-","");
        str=str.replace("(","");
        str=str.replace("ǃ","");
        str=str.replace("«","");
        str=str.replace("\\","");
        str=str.replace("\n"," ");
        str=str.replace("\r"," ");
        str=str.replace("\t"," ");
        if(str.matches("^.*[0-9].*$")){
            str="";
        }
        return str;
    }

    // vérifier qu'un mot ne contient pas les caractére mentionné dans remp
    public boolean  Si_word(String mot) throws IOException {
        String str = remp(mot);
        if(!str.equals("") && !listMotsVide.contains(str)){
            return true;
        }
        return false;
    }

    // pour recupérer seulement le mot ou le lemme du balise
    public String Filtre_Mot_lemme(String ligne){
        String [] str;
        ligne=ligne.replace(">",">,");
        ligne=ligne.replace("<",",<");
        str=ligne.split(",");
        return str[2];
    }

    // les mots d'un fichier avec répétition
    public  List<String> List_MotAvecRép(File fichier_xml) throws IOException {
        BufferedReader br =new BufferedReader(new FileReader(fichier_xml));
        List<String> word =new ArrayList<>();
        String ligne;
        String str;
        while ((ligne= br.readLine())!=null){
            if(ligne.matches("^.*<word>.*$")){
                str=Filtre_Mot_lemme(ligne);
                if(Si_word(str)) {
                    word.add(Filtre_Mot_lemme(ligne));
                }
            }
        }
        return  word;
    }

    //les mots d'un fichier sans répétition
    public List<String > List_MotSansRép(File fichier_xml) throws IOException {
        return  new ArrayList<>(new HashSet<>(List_MotAvecRép(fichier_xml)));
    }

    //les lemmes d'un fichier avec répétition
    public  List<String> List_LemmeAvecRép(File fichier_xml) throws IOException {
        BufferedReader br =new BufferedReader(new FileReader(fichier_xml));
        List<String> lemme =new ArrayList<>();
        String ligne;
        String str;
        while ((ligne= br.readLine())!=null){
            if(ligne.matches("^.*<lemma>.*$")){
                str=Filtre_Mot_lemme(ligne);
                if(Si_word(str)) {
                    lemme.add(Filtre_Mot_lemme(ligne));
                }
            }
        }
        return  lemme;
    }

    //les lemmes d'un fichier sans répétition
    public List<String > List_LemmeSansRép(File fichier_xml) throws IOException {
        return  new ArrayList<>(new HashSet<>(List_LemmeAvecRép(fichier_xml)));
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // retourner tous les mots et le lemmes d'un livre (unique)
    public List<List<String>> mots_lemme_livre(File file) throws IOException {
        List<List<String>> list =new ArrayList<>();
        List<String> list_word=new ArrayList<>();
        List<String> list_lemme =new ArrayList<>();
        for (File f : file.listFiles()) {
            if (!f.isDirectory()) {
                list_word.addAll(List_MotAvecRép(f));
                list_lemme.addAll(List_LemmeAvecRép(f));

            }
        }
        list_word = new ArrayList<>(new HashSet<>(list_word));
        list_lemme = new ArrayList<>(new HashSet<>(list_lemme));
        list.add(list_word);
        list.add(list_lemme);
        return  list;
    }

    // pour retourner les nouveaux Lemmes qui existent dans un livre et qui n'existent pas dans les autres livre
    public List<String> Nouveau_Lemmes(File Livre,List<String> list_lemme_existe) throws IOException {
        List<String> Lemme =new ArrayList<>();
        for (File f : Livre.listFiles()) {
            if (!f.isDirectory()) {
                Lemme.addAll(List_LemmeAvecRép(f));
            }
        }
        Lemme=new ArrayList<>(new HashSet<>(Lemme));
        Lemme.removeAll(list_lemme_existe);
        list_lemme_existe.addAll(Lemme);
        return  Lemme;
    }

    // pour recuperer les differents lemmes et les mots pour chaque niveau
    public List<Question1> NB_Lemme_Word_Niveau(File file) throws IOException {
        List<Question1> nb_word_lemmeList =new ArrayList<>();
        List<String > list_lemme_exist=new ArrayList<>();
        int l=0;
        int w=0;
        int nl=0;
        System.out.println("-----------------------------------------");
        System.out.println(" Niveau  Nbr_Mots  Nbr_Lemmes  NvLemmes");
        System.out.println("-----------------------------------------");
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                //System.out.println(" niveau : "+f.getName());
                for (File f1 : f.listFiles()) {
                    //  System.out.println("Livre : "+f1.getName());
                    w+=mots_lemme_livre(f1).get(0).size();
                    l+=mots_lemme_livre(f1).get(1).size();
                    nl+=Nouveau_Lemmes(f1,list_lemme_exist).size();
                }
                System.out.println(" "+f.getName()+"       "+w+"       "+l+"       "+nl);
                System.out.println("-----------------------------------------");

                nb_word_lemmeList.add(new Question1(f.getName(),w,l,nl));
                w=l=nl=0;
            }
        }
        return nb_word_lemmeList;
    }

//+++++++++++++++++++++++++++++++Question 2++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    // pour avoir une liste de tous les mots qui existent dans les fichiers (XML) d'un livre du répertoire Mosar_XML
    public List<String> Words_Livre(File livre) throws IOException {
        List<String> list=new ArrayList<>();
        for (File f : livre.listFiles()){
            if(!f.isDirectory()){
                list.addAll(List_MotAvecRép(f));
            }
        }
        return  list;
    }

    // pour avoir une liste de tous les Lemmes qui existent dans les fichiers (XML) d'un livre du répertoire Mosar_XML
    public List<String> Lemme_Livre(File livre) throws IOException {
        List<String> list=new ArrayList<>();
        for (File f : livre.listFiles()){
            if(!f.isDirectory()){
                list.addAll(List_LemmeAvecRép(f));
            }
        }
        return  list;
    }

    // pour avoir une list de tous le mots du répertoire Mosar
    public List<String> Words_Mosar(File Mosar_XML) throws IOException {
        List<String> list =new ArrayList<>();
        for (File f : Mosar_XML.listFiles()){
            if(f.isDirectory()){
                for (File f1 : f.listFiles()) {
                    list.addAll(Words_Livre(f1));
                }
            }
        }
        return  list;
    }

    // pour avoir une list de tous le Lemmes du répertoire Mosar
    public List<String> Lemme_Mosar(File Mosar_XML) throws IOException {
        List<String> list =new ArrayList<>();
        for (File f : Mosar_XML.listFiles()){
            if(f.isDirectory()){
                for (File f1 : f.listFiles()) {
                    list.addAll(Lemme_Livre(f1));
                }
            }
        }
        return  list;
    }

    // retourner une liste qui contient les mots et leur frequence dans tout le repertoire Mosar
    public List<Nombre_mots> mot_Frequ(File Mosar_xml) throws IOException {
        List<String> tous_les_mots=new ArrayList<>(Words_Mosar(Mosar_xml));
        List<String> mots_sans_repetition =new ArrayList<>(new HashSet<>(tous_les_mots));
        List<Nombre_mots> list_mot_frequent=new ArrayList<>();

        for (int i=0;i<mots_sans_repetition.size();i++){
            list_mot_frequent.add(new Nombre_mots(mots_sans_repetition.get(i), Collections.frequency(tous_les_mots,mots_sans_repetition.get(i))));
        }
        Nombre_mots mf =new Nombre_mots();
        mf.Sort_nombre_mots(list_mot_frequent);
        // System.out.println(list_mot_frequent);
        return list_mot_frequent;
    }

    // retourner une liste qui contient les lemmes et leur frequence dans tout le repertoire Mosar
    public List<Nombre_Lemmes> Lemme_Frequ(File Mosar_xml) throws IOException {
        List<String> tous_les_lemmes=new ArrayList<>(Lemme_Mosar(Mosar_xml));
        List<String> lemmes_sans_repetition =new ArrayList<>(new HashSet<>(tous_les_lemmes));
        List<Nombre_Lemmes> list_lemme_frequent=new ArrayList<>();

        for (int i=0;i<lemmes_sans_repetition.size();i++){
            list_lemme_frequent.add(new Nombre_Lemmes(lemmes_sans_repetition.get(i),Collections.frequency(tous_les_lemmes,lemmes_sans_repetition.get(i))));
        }
        Nombre_Lemmes mf =new Nombre_Lemmes();
        mf.Sort_nombre_lemmes(list_lemme_frequent);
        // System.out.println(list_mot_frequent);
        return list_lemme_frequent;
    }

    //ajouter la liste des mots et leur frequence dans le fichier test/Fréq_Mots.txt
    public void Frenquent_Mot(File Mosar_XML) throws IOException {
        List<Nombre_mots> list =new ArrayList<>(mot_Frequ(Mosar_XML));
        File index_Frequence = new File("src/main/java/MR/parti1/Fichiers/test/Fréq_Mots.txt");
        if(!index_Frequence.exists()){
            FileWriter f = new FileWriter(index_Frequence);
            for (int i=0;i<list.size();i++){
                f.write(list.get(i).mots+","+list.get(i).nombre+"\n");
            }
            f.close();
        }
    }

    //ajouter la liste des lemmes et leur frequence dans le fichier index/frenquent.txt
    public void Frenquent_Lemme(File Mosar_XML) throws IOException {
        List<Nombre_Lemmes> list =new ArrayList<>(Lemme_Frequ(Mosar_XML));
        File index_Frequence = new File("src/main/java/MR/parti1/Fichiers/test/Fréq_Lemmes.txt");
        if(!index_Frequence.exists()){
            FileWriter f = new FileWriter(index_Frequence);
            for (int i=0;i<list.size();i++){
                f.write(list.get(i).lemme+","+list.get(i).nombre+"\n");
            }
            f.close();
        }
    }



    // recuperer les mots ou les lemmes et leur frequence du fichier index/indexFrequent.txt, cella pour optimiser les calcule du traitement
    public List<Nombre_mots> mot_Frequent_fichier(File file_index_mot, int n) throws IOException {
        BufferedReader br =new BufferedReader(new FileReader(file_index_mot));
        List<Nombre_mots> list =new ArrayList<>();
        String ligne;
        String [] str;
        int i=0;
        while ((ligne=br.readLine())!=null && i<n){
            str=ligne.split(",");
            list.add(new Nombre_mots(str[0],Integer.valueOf(str[1])));
            i++;

        }
        System.out.println(list.size());
        return  list;
    }
    public List<Nombre_Lemmes> lemme_Frequent_fichier(File file_index_lemme, int n) throws IOException {
        BufferedReader br =new BufferedReader(new FileReader(file_index_lemme));
        List<Nombre_Lemmes> list =new ArrayList<>();
        String ligne;
        String [] str;
        int i=0;
        while ((ligne=br.readLine())!=null && i<n){
            str=ligne.split(",");
            list.add(new Nombre_Lemmes(str[0],Integer.valueOf(str[1])));
            i++;

        }
        //System.out.println(list.size());
        return  list;
    }



    // retourne un Objet qui contient un lemme donné sa frequence  dans un niveau parmis le 6 niveaux
    public Niveau_Fréq_lemme lemme_donne(File niveau, String lemme) throws IOException {
        List<String> list_lemme =new ArrayList<>();
        for (File f : niveau.listFiles()){
            list_lemme.addAll(Lemme_Livre(f));
        }
        return new Niveau_Fréq_lemme(niveau.getName(),lemme,Collections.frequency(list_lemme,lemme));
    }

    // pour specifier la frequence d'un lemme dans tous les niveaux (niveau par niveau)
    public List<Niveau_Fréq_lemme> lemme_niveau(String lemme,File Mosar_XML) throws IOException {
        List<Niveau_Fréq_lemme> lemmeFrequenceList=new ArrayList<>();
        for (File f :Mosar_XML.listFiles()){
            lemmeFrequenceList.add(lemme_donne(f,lemme));
        }

        return  lemmeFrequenceList;
    }
}
