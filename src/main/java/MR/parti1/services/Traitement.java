package MR.parti1.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import MR.parti1.services.*;

public class Traitement {

    public Traitement() throws IOException {
    }

    // Lister les mots vide
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
}

