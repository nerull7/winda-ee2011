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
            if(trasa.get(i).pasazerowieWsiadający.isEmpty() && trasa.get(i).pasazerowieWysiadajacy.isEmpty()){
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

        List<List<Pietro>> tr = nw.TrasaDwieWindy(pasazerowie);
    }

    public List<List<Pietro>> TrasaDwieWindy(List<Pasazer> pasazerowie) {
        this.pasazerowie = pasazerowie;
        List<List<Pietro>> tr = new ArrayList<List<Pietro>>();
        List<Pietro> trasa1 = new ArrayList<Pietro>(); //trasa windy pierwszej
        List<Pietro> trasa2 = new ArrayList<Pietro>(); //trasa windy drugiej
        List<Pasazer> pozostaliPasazerowie1 = new ArrayList<Pasazer>();
        List<Pasazer> pozostaliPasazerowie2 = new ArrayList<Pasazer>();
        int aktualnePietro1 = 0;
        int aktualnePietro2 = 0;
        boolean kierunekJazdy1 = true;
        boolean kierunekJazdy2 = true;


        for (Pasazer p : pasazerowie)
            this.pozostaliPasazerowie.add(new Pasazer(p.GetName(), p.GetStart(), p.GetStop()));
        for (Pasazer p : pasazerowie)
            pozostaliPasazerowie1.add(new Pasazer(p.GetName(), p.GetStart(), p.GetStop()));
        for (Pasazer p : pasazerowie)
            pozostaliPasazerowie2.add(new Pasazer(p.GetName(), p.GetStart(), p.GetStop()));

        while (this.pozostaliPasazerowie.size() > 0) {
            Pietro pietro1 = new Pietro();
            pietro1.numerPietra = aktualnePietro1;
            List<Pasazer> tmpPasazerowie1 = new ArrayList<Pasazer>();

            List<Pasazer> weszli1 = new ArrayList<Pasazer>();
            for(Pasazer p : this.pasazerowie)
                for (Pasazer pp1 : pozostaliPasazerowie1)
                    for(Pasazer pp : this.pozostaliPasazerowie)
                    {
                        boolean isIn = false;
                        for(Pietro pi : trasa1)
                            for(Pasazer pw : pi.pasazerowieWsiadający)
                                if(pw.GetName() == p.GetName())
                                    isIn = true;
                            if(!isIn && p.GetName() == pp.GetName() && p.GetName() == pp1.GetName() && p.GetStart() == aktualnePietro1)
                                tmpPasazerowie1.add(p);
                    }

            for (Pasazer pp : pozostaliPasazerowie2)
                for (Pasazer p : tmpPasazerowie1)
                    if(p.GetName() == pp.GetName())
                        weszli1.add(pp);
            pietro1.pasazerowieWsiadający = tmpPasazerowie1;
            pozostaliPasazerowie2.removeAll(weszli1);

            tmpPasazerowie1 = new ArrayList<Pasazer>();
            List<Pasazer> doUsuniecia1 = new ArrayList<Pasazer>();
            for(Pietro p : trasa1)
                for(Pasazer pasazer : p.pasazerowieWsiadający)
                    for(Pasazer pp : this.pozostaliPasazerowie)
                        if(pasazer.GetName() == pp.GetName() && pasazer.GetStop() == aktualnePietro1) {
                            tmpPasazerowie1.add(pasazer);
                            doUsuniecia1.add(pp);
                        }

            pietro1.pasazerowieWysiadajacy = tmpPasazerowie1;
            this.pozostaliPasazerowie.removeAll(doUsuniecia1);
            trasa1.add(pietro1);
            int nastepnePietro1;
            boolean pustaWinda1 = true;
            if(kierunekJazdy1)
                nastepnePietro1 = this.maxPietro;
            else
                nastepnePietro1 = 0;
            for(Pietro p : trasa1)
                for(Pasazer pasazer : p.pasazerowieWsiadający)
                    for(Pasazer pp : this.pozostaliPasazerowie)
                        if(pasazer.GetName() == pp.GetName()){
                            pustaWinda1 = false;
                            if(kierunekJazdy1 && pasazer.GetStop() > aktualnePietro1 && pasazer.GetStop() < nastepnePietro1)
                                nastepnePietro1 = pasazer.GetStop();
                            else if(!kierunekJazdy1 && pasazer.GetStop() < aktualnePietro1 && pasazer.GetStop() > nastepnePietro1)
                                nastepnePietro1 = pasazer.GetStop();
                        }

            if(pustaWinda1){
                for(Pasazer pp : this.pozostaliPasazerowie)
                    if(kierunekJazdy1 && pp.GetStart() > aktualnePietro1 && pp.GetStart() < nastepnePietro1)
                            nastepnePietro1 = pp.GetStart();
                    else if(!kierunekJazdy1 && pp.GetStart() < aktualnePietro1 && pp.GetStart() > nastepnePietro1)
                            nastepnePietro1 = pp.GetStart();
            }
            aktualnePietro1 = nastepnePietro1;
            if(aktualnePietro1 == this.maxPietro)
                kierunekJazdy1 = false;
            else if(aktualnePietro1 == 0)
                kierunekJazdy1 = true;

            Pietro pietro2 = new Pietro();
            pietro2.numerPietra = aktualnePietro2;
            List<Pasazer> tmpPasazerowie2 = new ArrayList<Pasazer>();

            for(Pasazer p : this.pasazerowie)
                for(Pasazer pp2 : pozostaliPasazerowie2)
                    for(Pasazer pp : this.pozostaliPasazerowie)
                    {
                        boolean isIn = false;
                        for(Pietro pi : trasa2)
                            for(Pasazer pw : pi.pasazerowieWsiadający)
                                if(pw.GetName() == p.GetName())
                                    isIn = true;
                            if(!isIn && p.GetName() == pp.GetName() && p.GetName() == pp2.GetName() && p.GetStart() == aktualnePietro2)
                                tmpPasazerowie2.add(p);
                    }
            
            List<Pasazer> weszli2 = new ArrayList<Pasazer>();
            for (Pasazer pp : pozostaliPasazerowie2)
                for (Pasazer p : tmpPasazerowie1)
                    if(p.GetName() == pp.GetName())
                        weszli2.add(pp);
            pietro2.pasazerowieWsiadający = tmpPasazerowie2;
            pozostaliPasazerowie1.removeAll(weszli2);

            tmpPasazerowie2 = new ArrayList<Pasazer>();
            List<Pasazer> doUsuniecia2 = new ArrayList<Pasazer>();
            for(Pietro p : trasa2)
                for(Pasazer pasazer : p.pasazerowieWsiadający)
                    for(Pasazer pp : this.pozostaliPasazerowie)
                        if(pasazer.GetName() == pp.GetName() && pasazer.GetStop() == aktualnePietro2) {
                            tmpPasazerowie2.add(pasazer);
                            doUsuniecia2.add(pp);
                        }

            pietro2.pasazerowieWysiadajacy = tmpPasazerowie2;
            this.pozostaliPasazerowie.removeAll(doUsuniecia2);
            trasa2.add(pietro2);
            int nastepnePietro2;
            boolean pustaWinda2 = true;
            if(kierunekJazdy2)
                nastepnePietro2 = this.maxPietro;
            else
                nastepnePietro2 = 0;
            for(Pietro p : trasa2)
                for(Pasazer pasazer : p.pasazerowieWsiadający)
                    for(Pasazer pp : this.pozostaliPasazerowie)
                        if(pasazer.GetName() == pp.GetName()){
                            pustaWinda2 = false;
                            if(kierunekJazdy2 && pasazer.GetStop() > aktualnePietro2 && pasazer.GetStop() < nastepnePietro2)
                                nastepnePietro2 = pasazer.GetStop();
                            else if(!kierunekJazdy2 && pasazer.GetStop() < aktualnePietro2 && pasazer.GetStop() > nastepnePietro2)
                                nastepnePietro2 = pasazer.GetStop();
                        }

            if(pustaWinda2){
                for(Pasazer pp : this.pozostaliPasazerowie)
                    if(kierunekJazdy2 && pp.GetStart() > aktualnePietro2 && pp.GetStart() < nastepnePietro2)
                            nastepnePietro2 = pp.GetStart();
                    else if(!kierunekJazdy2 && pp.GetStart() < aktualnePietro2 && pp.GetStart() > nastepnePietro2)
                            nastepnePietro2 = pp.GetStart();
            }
            aktualnePietro2 = nastepnePietro2;
            if(aktualnePietro2 == this.maxPietro)
                kierunekJazdy2 = false;
            else if(aktualnePietro2 == 0)
                kierunekJazdy2 = true;
        }
        List<Pietro> toRemove1 = new ArrayList<Pietro>();
        for(int i = 1; i < trasa1.size()-1; i++)
            if(trasa1.get(i).pasazerowieWsiadający.isEmpty() && trasa1.get(i).pasazerowieWysiadajacy.isEmpty())
                toRemove1.add(trasa1.get(i));
        List<Pietro> toRemove2 = new ArrayList<Pietro>();
        for(int i = 1; i < trasa2.size()-1; i++)
            if(trasa2.get(i).pasazerowieWsiadający.isEmpty() && trasa2.get(i).pasazerowieWysiadajacy.isEmpty())
                toRemove2.add(trasa2.get(i));

        trasa1.removeAll(toRemove1);
        trasa2.removeAll(toRemove2);

        tr.add(trasa1);
        tr.add(trasa2);

        return tr;
    }
}