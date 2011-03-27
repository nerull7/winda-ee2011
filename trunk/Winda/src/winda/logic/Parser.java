/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Tomek
 */
public class Parser {

    public Pasazer [] Wczytaj(String filename){
        try {
            Pasazer [] pasazerowie;
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String s;
            String [] strings = new String[1000];
            int count = 0;

            while((s = br.readLine()) != null){
                strings[count] = s;
                count++;
            }
            pasazerowie = new Pasazer[count];
            for(int i = 0; i < count; i++){
                String [] st = null;
                st = strings[i].split(" ");
                pasazerowie[i] = new Pasazer(i+1, Integer.parseInt(st[0]), Integer.parseInt(st[1]));
            }
            
            fr.close();
            return pasazerowie;
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void Zapisz(String filename, Pasazer [] pasazerowie){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            for (Pasazer ps : pasazerowie) {
                fw.write(ps.GetStart() + " " + ps.GetStop() + '\r'+'\n');
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void main(String[] args) {
        //Do testów
        //plik odczytu z args
        //Przykładowy plik dodałem do svn w głównym katalogu projektu
        //nazywa się DemoDane.txt
        //Składnia pliku to:
        //Pietro_startowe spacja pietro_koncowe koniec lini
        System.out.println("Wczytywanie pliku " + args[0]);
        Parser p = new Parser();
        Pasazer [] pasazerowie = null;
        pasazerowie = p.Wczytaj(args[0]);
        System.out.println(pasazerowie.length);
        for(Pasazer ps: pasazerowie){
            System.out.println("Pasazer " + ps.GetName() + ": " + ps.GetStart() + " " + ps.GetStop());
        }

        System.out.println("Zapisywanie do pliku test.txt");
        p.Zapisz("test.txt", pasazerowie);
        pasazerowie = p.Wczytaj("test.txt");
        System.out.println(pasazerowie.length);
        for(Pasazer ps: pasazerowie){
            System.out.println("Pasazer " + ps.GetName() + ": " + ps.GetStart() + " " + ps.GetStop());
        }
    }
}
