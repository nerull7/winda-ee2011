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
    private int [] Trasa;

    public Winda(){
        AlgorytmWindy = new AlgorytmGoraDol();
        CzasJazdyPietro = 1;
        CzasWeWyOsoby = 1;
        IloscPieter = 12;
        Pasazer [] Pasazerowie = parser.Wczytaj("C:\\DemoDane.txt");
        pasazerowieCollection.addAll(Arrays.asList(Pasazerowie));
        IloscPasazerow = Pasazerowie.length;
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

    public void SetNoweProjekt(){
        this.pasazerowieCollection.clear();
        IloscPasazerow = 0;
    }

    public void AddPasazer(int start, int stop){
        this.pasazerowieCollection.add(new Pasazer(IloscPasazerow, start, stop));
        IloscPasazerow++;
    }

    public double GetCzasJazdy(){
        return this.CzasJazdy;
    }

    public double GetCzasSredniObslugi(){
        return this.CzasSredniObslugi;
    }

    public int [] GetTrasa(){
        return this.Trasa;
    }

    public void ZapiszPasazerow(String filename){
        Pasazer [] Pasazerowie = pasazerowieCollection.toArray(new Pasazer[0]);
        parser.Zapisz(filename, Pasazerowie);
    }

    public void WczytajPasazerow(String filename){
        this.pasazerowieCollection.clear();
        Pasazer [] Pasazerowie = parser.Wczytaj(filename);
        pasazerowieCollection.addAll(Arrays.asList(Pasazerowie));
        IloscPasazerow = Pasazerowie.length;
    }

    public void Start(){
        AlgorytmWindy.SetMaxPietro(IloscPieter);
        Pasazer [] Pasazerowie = pasazerowieCollection.toArray(new Pasazer[0]);
        Trasa = AlgorytmWindy.Trasa(Pasazerowie);
        CzasJazdy = Trasa.length*CzasJazdyPietro+IloscPasazerow*CzasWeWyOsoby*2;
        CzasSredniObslugi = CzasJazdy / IloscPasazerow;
    }

    public static void main(String[] args) {

        Winda w = new Winda();
        w.SetNajblizszeWzwanie();
        w.Start();
        int [] trasa = w.GetTrasa();
        for(int i = 0;i<trasa.length;i++)
            System.out.print(trasa[i] + " ");
        System.out.print('\n');
        System.out.println("Czas jazdy = " + w.GetCzasJazdy());
        System.out.println("Średni czas obsługi jednego pasażera = " + w.GetCzasSredniObslugi());
    }
}