/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomek
 */
public class Logger {
    public Logger (Pasazer [] pasazerowie, int [] trasa){
        //Jeszcze nie jestem pewien argumentów jakie przyjmuje konstruktor
        //i nie wiem co tak naprawdę jest w trasie
    }

    public void SaveLog(String filename){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            for (String s : log)
                fw.write(s+'\n');
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private List<String> log = new ArrayList<String>();
}
