package winda.logic;

import java.util.ArrayList;
import java.util.List;
import sun.misc.Sort;

/**
 * Algortym poruszania się windą przy wsiadaniu pasażerów
 * jadących tylko w kierunku w którym porusza się winda.
 *
 * Napisane nie testowane
 * 
 * @author Przemo
 */
public class AlgorytmGoraDol implements IAlgorytm {
    private int start_floor;
    private int floor_count;
    private ArrayList<Pietro> floors;
    private List<Pasazer> pasazerowie;
    private int acctual_floor;

    public AlgorytmGoraDol(){
        this.start_floor = 0;
        this.floor_count = 0;
        this.floors = new ArrayList();
    }

    public AlgorytmGoraDol(int floor_count){
        this.start_floor = 0;
        this.floor_count = floor_count;
        this.floors = new ArrayList();
    }

    public AlgorytmGoraDol(int floor_count, int start_floor){
        this.floor_count = floor_count;
        this.start_floor = start_floor;
        this.floors = new ArrayList();
    }

    public void setStartFloor(int start_floor){
        this.start_floor = start_floor;
    }

    public int getStartFloor(){
        return this.start_floor;
    }

    public void setFloorCount(int floor_count){
        System.out.println("setFloorCount ("+floor_count+")");
        this.floor_count = floor_count;
    }

    public int getFloorCount(){
        return this.floor_count;
    }

    private void goUp(){
        int i =0;
        ArrayList<Pietro> pietra = this.setupPietra();
        while(this.pasazerowie.get(i).GetStart()<this.acctual_floor)
            i++;
        while(i<this.pasazerowie.size()){
            if( this.pasazerowie.get(i).GetStart()<this.pasazerowie.get(i).GetStop()){
                pietra.get(this.pasazerowie.get(i).GetStart()).pasazerowieWsiadający.add(this.pasazerowie.get(i));
                pietra.get(this.pasazerowie.get(i).GetStop()).pasazerowieWysiadajacy.add(this.pasazerowie.get(i));
            }
            i++;
        }
        this.clearPietra(pietra);

        if(!pietra.isEmpty())
            this.floors.addAll(pietra);
    }

    private void goDown(){
        int i =0;
        ArrayList<Pietro> pietra = this.setupPietra();
//        while(this.pasazerowie.get(i).GetStart()<this.acctual_floor)
//            i++;
        while(i<this.pasazerowie.size()){
            if( this.pasazerowie.get(i).GetStart()>this.pasazerowie.get(i).GetStop()){
                pietra.get(this.pasazerowie.get(i).GetStart()).pasazerowieWsiadający.add(this.pasazerowie.get(i));
                pietra.get(this.pasazerowie.get(i).GetStop()).pasazerowieWysiadajacy.add(this.pasazerowie.get(i));
            }
            i++;
        }
        this.clearPietra(pietra);
        pietra = this.mirrorPietra(pietra);
        if(!pietra.isEmpty())
            this.floors.addAll(pietra);
    }

    private ArrayList mirrorPietra(ArrayList<Pietro> pietro){
        ArrayList<Pietro> mirror = new ArrayList();
        for(int i = pietro.size()-1;i>=0;i--)
            mirror.add(pietro.get(i));
        return mirror;
    }

    private void clearPietra(ArrayList<Pietro> pietro){
        for(int i=pietro.size()-1;i >= 0;i--){
            if(pietro.get(i).pasazerowieWsiadający.isEmpty() && pietro.get(i).pasazerowieWysiadajacy.isEmpty())
                pietro.remove(i);
        }
    }

    public List<Pietro> setCourse(){
        this.calculateFloors();

        PasazerSort sort = new PasazerSort(this.pasazerowie);
        sort.sortByStart();
        this.acctual_floor = this.start_floor;
        
        this.goUp();
        this.goDown();
        return this.floors;
    }

    public List<Pietro> Trasa(List<Pasazer> pasazerowie) {
        this.pasazerowie = pasazerowie;
        return this.setCourse();
    }

    public void SetMaxPietro(int maxPietro) {
        this.setFloorCount(floor_count+1);
    }

    /*
     * Fix for wrong number of floors
     */
    private void calculateFloors(){
        for(int i=0;i<this.pasazerowie.size();i++){
            if(this.pasazerowie.get(i).GetStart()>this.floor_count)
                this.floor_count = this.pasazerowie.get(i).GetStart();
            if(this.pasazerowie.get(i).GetStop()>this.floor_count)
                this.floor_count = this.pasazerowie.get(i).GetStop();
        }
        this.floor_count++;
    }

    private ArrayList<Pietro> setupPietra(){
        ArrayList<Pietro> pietra = new ArrayList();
        for(int i=0;i<this.floor_count;i++){
            Pietro tmp = new Pietro();
            tmp.numerPietra = i;
            pietra.add(tmp);
        }
        return pietra;
    }

    public List<List<Pietro>> TrasaDwieWindy(List<Pasazer> pasazerowie) {
        //Zakładam że winda pierwsza (index 0 w List<List<Pietro>> trasa) stoi na dole i jedzie w górę,
        //a winda druga (index 1 w List<List<Pietro>> trasa) stoi na górze i jedzie na dół
        List<List<Pietro>> trasa = new ArrayList<List<Pietro>>();
        List<Pasazer> pasazerowieJadacyWGore = new ArrayList<Pasazer>();
        List<Pasazer> pasazerowieJadacyWDol = new ArrayList<Pasazer>();

        for(Pasazer p : pasazerowie)
            if(p.GetStart() < p.GetStop())
                pasazerowieJadacyWGore.add(p);
            else
                pasazerowieJadacyWDol.add(p);

        trasa.add(this.Trasa(pasazerowieJadacyWGore));
        trasa.add(this.Trasa(pasazerowieJadacyWDol));

        return trasa;
    }
}
