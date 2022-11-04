package MR.parti1.services;

import java.io.File;

public class Lire_RepMosar {

    //Méthode utilisé dans lire_mosar pour générer les fichier XML
    public static void AlKhalil_XML(File file, String pth) {
        String str;
        String new_file;
        str= file.getName();
        new_file=str.replace(".txt","");
        net.oujda_nlp_team.ADATAnalyzer.getInstance().processLemmatization(file.getPath(),
                "utf-8",
                pth+"/"+new_file+"−OUT−Lemma.xml",
                "utf-8");
    }


    //Génération de tous les fichiers XML avec la même arboresence du dossier Mosar en utilisant
    // la méthode AlKhalil_XML
    public  void lire_mosar(File file){
        String str;
        String [] T;
        File f1;
        File f2;
        for (File f : file.listFiles()) {
            if (!f.isDirectory()) {
                str= String.valueOf(f.getParentFile()).replace("src\\main\\java\\MR\\parti1\\Fichiers\\Mosar\\","");
                str=str.replace("\\",",");
                T=str.split(",");
                f1=new File("src/main/java/MR/parti1/Fichiers/Mosar_XML/"+T[0]);
                if(!f1.exists()){
                    f1.mkdir();
                }else {
                    f2=new File("src/main/java/MR/parti1/Fichiers/Mosar_XML/"+T[0]+"/"+T[1]);
                    if (!f2.exists()){
                        f2.mkdir();
                    }else {
                    }
                }

            }else {
                lire_mosar(f);
            }
        }
    }

}
