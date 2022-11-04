package MR.parti1.controllers;

import MR.parti1.services.*;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
@RestController
@RequestMapping("/api")
public class Controller1 {



    @GetMapping("/Q1")
    public List<Question1> Q1() throws IOException {
        File f = new File("src\\main\\java\\MR\\parti1\\Fichiers\\Mosar_XML");
        List<Question1> l =new ArrayList<>();
        List<Question1> Résultat =new ArrayList<>();
        TraitementQuestion1 tr = new TraitementQuestion1();
        l=tr.NB_Lemme_Word_Niveau(f);
        for(int i=0;i< l.size();i++)
        {
           Résultat.add(new Question1(l.get(i).getNiveau(),l.get(i).getNombre_Mots(),l.get(i).getNombre_Lemmes(),l.get(i).getNouv_lemmes()));
        }
        return Résultat;
    }

     @PostMapping(path = "/Q2a1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Q2a1(@RequestBody String n) throws IOException, JSONException {
        List<Nombre_mots> nombre_mots = new ArrayList<>();
        TraitementQuestion1 tr = new TraitementQuestion1();
        File f = new File("src\\main\\java\\MR\\parti1\\Fichiers\\test\\Fréq_Mots.txt");
        Object js = JSONValue.parse(n);
        JSONObject jsonObjectdecode = (JSONObject)js;
        JSONObject resultat = (JSONObject)jsonObjectdecode.get("n");
        String nbr = (String) resultat.get("n");
        int nb=Integer.valueOf(nbr);
        nombre_mots = tr.mot_Frequent_fichier(f,nb);
         List<String> bad = new ArrayList<>();
         bad.add("Vous avez deppasez le nombre de mots qui existe");
        if(nb >= 25698){
            return ResponseEntity.ok(bad);
        }
        return ResponseEntity.ok(nombre_mots);
    }


    @PostMapping(path = "/Q2a2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Q2a2(@RequestBody String n) throws IOException, JSONException {
        List<Nombre_Lemmes> nombre_lemmes = new ArrayList<>();
        TraitementQuestion1 tr = new TraitementQuestion1();
        File f = new File("src\\main\\java\\MR\\parti1\\Fichiers\\test\\Fréq_Lemmes.txt");
        Object js = JSONValue.parse(n);
        JSONObject jsonObjectdecode = (JSONObject)js;
        JSONObject resultat = (JSONObject)jsonObjectdecode.get("n");
        String nbr = (String) resultat.get("n");
        int nb=Integer.valueOf(nbr);
        nombre_lemmes = tr.lemme_Frequent_fichier(f,nb);
        List<String> bad = new ArrayList<>();
        bad.add("Vous avez deppasez le nombre de mots qui existe");
        if(nb >= 10299){
            return ResponseEntity.ok(bad);
        }
        return ResponseEntity.ok(nombre_lemmes);
    }

    @PostMapping("/Q2b")
    public ResponseEntity<?> Q2b(@RequestBody String lemme) throws IOException {
        List<Niveau_Fréq_lemme> lemmeFrequenceList=new ArrayList<>();
        Object js = JSONValue.parse(lemme);
        JSONObject jsonObjectdecode = (JSONObject)js;
        JSONObject resultat = (JSONObject)jsonObjectdecode.get("lemme");
        String res = (String) resultat.get("lemme");
        TraitementQuestion1 tr = new TraitementQuestion1();
        File f = new File("src\\main\\java\\MR\\parti1\\Fichiers\\Mosar_XML");
        lemmeFrequenceList = tr.lemme_niveau(res,f);
        return  ResponseEntity.ok(lemmeFrequenceList);
    }







}
