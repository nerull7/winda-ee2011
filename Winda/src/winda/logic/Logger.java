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
    public Logger (List<Pietro> trasa){
        for(Pietro pietro : trasa)
        {
            for(Pasazer pasazer : pietro.pasazerowieWysiadajacy)
                log.add("Na Piętrze " + pietro.numerPietra + " wysiadł pasażer " + pasazer.GetName());
            for(Pasazer pasazer : pietro.pasazerowieWsiadający)
                log.add("Na Piętrze " + pietro.numerPietra + " wsiadł pasażer " + pasazer.GetName());
        }
    }

    public void SaveLog(String filename){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            for (String s : log)
                fw.write(s+'\r'+'\n');
            fw.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Parser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private List<String> log = new ArrayList<String>();
}
