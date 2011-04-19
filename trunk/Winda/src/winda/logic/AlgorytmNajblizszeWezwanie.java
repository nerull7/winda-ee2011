/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomek
 */

public class AlgorytmNajblizszeWezwanie implements IAlgorytm{
    private int aktualnePietro = 0;
    private int maxPietro;
    private boolean kierunekJazdy = true; //true - gora, false - dol
    private List<Pasazer> pasazerowie;
    private List<Pasazer> pozostaliPasazerowie = new ArrayList<Pasazer>();
    private List<Pietro> trasa = new ArrayList<Pietro>();

    public List<Pietro> Trasa(List<Pasazer> pasazerowie) {
        this.pasazerowie = pasazerowie;
        for (Pasazer p : pasazerowie)
            this.pozostaliPasazerowie.add(new Pasazer(p.GetName(), p.GetStart(), p.GetStop()));

        while (this.pozostaliPasazerowie.size() > 0) {
            Pietro pietro = new Pietro();
            pietro.numerPietra = this.aktualnePietro;
            List<Pasazer> tmpPasazerowie = new ArrayList<Pasazer>();

            for(Pasazer p : this.pasazerowie)
                for(Pasazer pp : this.pozostaliPasazerowie)
                {
                    boolean isIn = false;
                    for(Pietro pi : this.trasa)
                        for(Pasazer pw : pi.pasazerowieWsiadający)
                            if(pw.GetName() == p.GetName())
                                isIn = true;
                        if(!isIn && p.GetName() == pp.GetName() && p.GetStart() == this.aktualnePietro)
                            tmpPasazerowie.add(p);
                }

            pietro.pasazerowieWsiadający = tmpPasazerowie;

            tmpPasazerowie = new ArrayList<Pasazer>();
            List<Pasazer> doUsuniecia = new ArrayList<Pasazer>();
            for(Pietro p : this.trasa)
                for(Pasazer pasazer : p.pasazerowieWsiadający)
                    for(Pasazer pp : this.pozostaliPasazerowie)
                        if(pasazer.GetName() == pp.GetName() && pasazer.GetStop() == this.aktualnePietro) {
                            tmpPasazerowie.add(pasazer);
                            doUsuniecia.add(pp);
                        }

            pietro.pasazerowieWysiadajacy = tmpPasazerowie;
            this.pozostaliPasazerowie.removeAll(doUsuniecia);
            this.trasa.add(pietro);
            int nastepnePietro;
            boolean pustaWinda = true;
            if(this.kierunekJazdy)
                nastepnePietro = this.maxPietro;
            else
                nastepnePietro = 0;
            for(Pietro p : this.trasa)
                for(Pasazer pasazer : p.pasazerowieWsiadający)
                    for(Pasazer pp : this.pozostaliPasazerowie)
                        if(pasazer.GetName() == pp.GetName()){
                            pustaWinda = false;
                            if(this.kierunekJazdy && pasazer.GetStop() > this.aktualnePietro && pasazer.GetStop() < nastepnePietro)
                                nastepnePietro = pasazer.GetStop();
                            else if(!this.kierunekJazdy && pasazer.GetStop() < this.aktualnePietro && pasazer.GetStop() > nastepnePietro)
                                nastepnePietro = pasazer.GetStop();
                        }

            if(pustaWinda){
                for(Pasazer pp : this.pozostaliPasazerowie)
                    if(this.kierunekJazdy && pp.GetStart() > this.aktualnePietro && pp.GetStart() < nastepnePietro)
                            nastepnePietro = pp.GetStart();
                    else if(!this.kierunekJazdy && pp.GetStart() < this.aktualnePietro && pp.GetStart() > nastepnePietro)
                            nastepnePietro = pp.GetStart();
            }
            this.aktualnePietro = nastepnePietro;
            if(this.aktualnePietro == this.maxPietro)
                this.kierunekJazdy = false;
            else if(this.aktualnePietro == 0)
                this.kierunekJazdy = true;
        }

        return trasa;
    }

    public void SetMaxPietro(int maxPietro) {
        this.maxPietro = maxPietro;
    }

    public static void main(String [] args){
        List<Pasazer> pasazerowie;
        Parser p = new Parser();
        pasazerowie = p.Wczytaj("c:\\DemoDane.txt");
        AlgorytmNajblizszeWezwanie anw = new AlgorytmNajblizszeWezwanie();
        anw.SetMaxPietro(13);
        anw.Trasa(pasazerowie);
    }
}