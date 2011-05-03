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
        for(int i = 1; i < trasa.size()-1; i++){
            if(trasa.get(i).pasazerowieWsiadający.size() == 0 && trasa.get(i).pasazerowieWysiadajacy.size() == 0){
                trasa.remove(i);
            }
        }

        return trasa;
    }

    public void SetMaxPietro(int maxPietro) {
        this.maxPietro = maxPietro;
    }

    public static void main(String [] args){
        List<Pasazer> pasazerowie = new ArrayList();
        Parser p = new Parser();
        //pasazerowie = p.Wczytaj("c:\\DemoDane.txt");
        AlgorytmNajblizszeWezwanie anw = new AlgorytmNajblizszeWezwanie();
        anw.SetMaxPietro(12);

        Pasazer p1=new Pasazer(1,2,7);
        Pasazer p2=new Pasazer(2,8,2);
        Pasazer p3=new Pasazer(3,6,3);
        Pasazer p4=new Pasazer(4,2,6);
        Pasazer p5=new Pasazer(5,11,5);
        Pasazer p6=new Pasazer(6,10,2);
        pasazerowie.add(p1);pasazerowie.add(p2);pasazerowie.add(p3);
        pasazerowie.add(p4);pasazerowie.add(p5);pasazerowie.add(p6);
        AlgorytmNajblizszeWezwanie  nw = new AlgorytmNajblizszeWezwanie();
        nw.trasa = anw.Trasa(pasazerowie);
        nw.SetMaxPietro(12);

        for(int i = 0; i<nw.trasa.size(); i++){
            System.out.println("P: "+nw.trasa.get(i).numerPietra);
            for(int j=0; j < nw.trasa.get(i).pasazerowieWsiadający.size();j++){
                 System.out.print("Ws: "+nw.trasa.get(i).pasazerowieWsiadający.get(j).GetName());
            }
            for(int j=0; j < nw.trasa.get(i).pasazerowieWysiadajacy.size();j++){
                 System.out.print("Wy: "+nw.trasa.get(i).pasazerowieWysiadajacy.get(j).GetName());
            }
            System.out.println();
        }
    }

    public List<List<Pietro>> TrasaDwieWindy(List<Pasazer> pasazerowie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}