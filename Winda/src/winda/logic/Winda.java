/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

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
    private Pasazer [] Pasazerowie;
    private Parser parser = new Parser();
    private int [] Trasa;

    public Winda(){
        AlgorytmWindy = new AlgorytmGoraDol();
        CzasJazdyPietro = 1;
        CzasWeWyOsoby = 1;
        IloscPieter = 12;
        Pasazerowie = parser.Wczytaj("C:\\DemoDane.txt");
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
        this.Pasazerowie = new Pasazer[1000];
        IloscPasazerow = 0;
    }

    public void AddPasazer(int start, int stop){
        this.Pasazerowie[IloscPasazerow] = new Pasazer(IloscPasazerow, start, stop);
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
        parser.Zapisz(filename, Pasazerowie);
    }

    public void Start(){
        Trasa = AlgorytmWindy.Trasa(Pasazerowie);
        CzasJazdy = Trasa.length*CzasJazdyPietro+IloscPasazerow*CzasWeWyOsoby*2;
        CzasSredniObslugi = CzasJazdy / IloscPasazerow;
    }
}
