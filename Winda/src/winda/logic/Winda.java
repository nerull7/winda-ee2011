/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tomek
 */
public class Winda {
    private IAlgorytm AlgorytmWindy;
    private double CzasJazdyPietro;
    private double CzasJazdy;
    private double CzasWeWyOsoby;
    private double CzasSredniObslugi;
    private int IloscPieter;
    private int IloscPasazerow;
    private List<Pasazer> pasazerowieCollection = new ArrayList<Pasazer>();
    private Parser parser = new Parser();
    private List<Pietro> Trasa;
    private Logger log;

    public Winda(){
        AlgorytmWindy = new AlgorytmGoraDol();
        CzasJazdyPietro = 1000;
        CzasWeWyOsoby = 1000;
        IloscPieter = 12;
        AlgorytmWindy.SetMaxPietro(IloscPieter);
        pasazerowieCollection = parser.Wczytaj("c:\\DemoDane.txt");
        IloscPasazerow = pasazerowieCollection.size();
        this.Trasa = new ArrayList();
    }

    public void SetCzasJazdyPietro(double czas){
        this.CzasJazdyPietro = czas;
    }

    public void SetCzasWeWyOsoby(double czas){
        this.CzasWeWyOsoby = czas;
    }

    public void SetGoraDol(){
        this.AlgorytmWindy = new AlgorytmGoraDol();
    }

    public void SetNajblizszeWzwanie(){
        this.AlgorytmWindy = new AlgorytmNajblizszeWezwanie();
    }

    public void SetIloscPieter(int iloscPieter){
        this.IloscPieter = iloscPieter;
    }

    public void SetNowyProjekt(){
        this.pasazerowieCollection.clear();
        IloscPasazerow = 0;
    }

    public void AddPasazer(int start, int stop){
        IloscPasazerow++;
        this.pasazerowieCollection.add(new Pasazer(IloscPasazerow, start, stop));
    }

    public double GetCzasJazdy(){
        return this.CzasJazdy;
    }

    public double GetCzasSredniObslugi(){
        return this.CzasSredniObslugi;
    }

    public List<Pietro> GetTrasa(){
        return this.Trasa;
    }

    public void ZapiszPasazerow(String filename){
        parser.Zapisz(filename, pasazerowieCollection);
    }

    public void WczytajPasazerow(String filename){
        this.pasazerowieCollection.clear();
        pasazerowieCollection = parser.Wczytaj(filename);
        IloscPasazerow = pasazerowieCollection.size();
    }

    public void ZapiszLog(String filename){
        log.SaveLog(filename);
    }

    public void Start(){
        AlgorytmWindy.SetMaxPietro(IloscPieter);
        Trasa.clear();
        Trasa = AlgorytmWindy.Trasa(pasazerowieCollection);
        CzasJazdy = Trasa.size()*CzasJazdyPietro+IloscPasazerow*CzasWeWyOsoby*2;
        CzasSredniObslugi = CzasJazdy / IloscPasazerow;
        log = new Logger(this.Trasa);
    }

    public static void main(String[] args) {

        Winda w = new Winda();
        //w.SetNajblizszeWzwanie();
        w.Start();
        List<Pietro> trasa = w.GetTrasa();
        for(int i = 0;i<trasa.size();i++)
            System.out.print(trasa.get(i).numerPietra + " ");
        System.out.print('\n');
        System.out.println("Czas jazdy = " + w.GetCzasJazdy());
        System.out.println("Średni czas obsługi jednego pasażera = " + w.GetCzasSredniObslugi());
    }
}
