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
    private double CzasPietra;
    private int IloscPieter;
    private int IloscPasazerow;
    private List<Pasazer> pasazerowieCollection = new ArrayList<Pasazer>();
    private Parser parser = new Parser();
    private List<Pietro> Trasa;
    private List<Pietro> TrasaDwa;
    private boolean DwieWindy = false;
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

    public void SetDwieWindy (){
        this.DwieWindy = true;
    }

    public void SetJednaWindy (){
        this.DwieWindy = false;
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
        this.pasazerowieCollection = new ArrayList<Pasazer>();
        IloscPasazerow = 0;
    }

    public void AddPasazer(int start, int stop){
        IloscPasazerow++;
        this.getPasazerowieCollection().add(new Pasazer(IloscPasazerow, start, stop));
    }

    public double GetCzasJazdy(){
        return this.CzasJazdy;
    }

    public double GetCzasSredniObslugi(){
        return this.CzasSredniObslugi;
    }

    public double GetCzasPietra(){
        return this.CzasPietra;
    }

    public List<Pietro> GetTrasa(){
        return this.Trasa;
    }

    public List<Pietro> GetTrasaDwa(){
        return this.TrasaDwa;
    }

    public void ZapiszPasazerow(String filename){
        parser.Zapisz(filename, getPasazerowieCollection());
    }

    public void WczytajPasazerow(String filename){
        this.pasazerowieCollection = new ArrayList<Pasazer>();
        pasazerowieCollection = parser.Wczytaj(filename);
        IloscPasazerow = getPasazerowieCollection().size();
    }

    public void ZapiszLog(String filename){
        log.SaveLog(filename);
    }

    public void Start(){
        AlgorytmWindy.SetMaxPietro(IloscPieter);
        Trasa.clear();
        if (DwieWindy){
            List<List<Pietro>> trasy = AlgorytmWindy.TrasaDwieWindy(pasazerowieCollection);
            this.Trasa = trasy.get(0);
            this.TrasaDwa = trasy.get(1);
        } else {
            Trasa = AlgorytmWindy.Trasa(getPasazerowieCollection());
        }
        CzasJazdy = Trasa.size()*CzasJazdyPietro+IloscPasazerow*CzasWeWyOsoby*2;
        CzasSredniObslugi = CzasJazdy / IloscPasazerow;
        CzasPietra = CzasJazdy / IloscPieter;
        log = new Logger(this.Trasa);
    }

    public void UsunOstatniegoPasazera(){
        this.getPasazerowieCollection().remove(this.getPasazerowieCollection().size()-1);
        this.IloscPasazerow --;
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

    /**
     * @return the pasazerowieCollection
     */
    public List<Pasazer> getPasazerowieCollection() {
        return pasazerowieCollection;
    }
}
