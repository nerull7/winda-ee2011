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
    }

    public AlgorytmGoraDol(int floor_count){
        this.start_floor = 0;
        this.floor_count = floor_count;
    }

    public AlgorytmGoraDol(int floor_count, int start_floor){
        this.floor_count = floor_count;
        this.start_floor = start_floor;
    }

    public void setStartFloor(int start_floor){
        this.start_floor = start_floor;
    }

    public int getStartFloor(){
        return this.start_floor;
    }

    public void setFloorCount(int floor_count){
        this.floor_count = floor_count;
    }

    public int getFloorCount(int floor_count){
        return this.floor_count;
    }

    private void goUp(){
        int i =0;
        ArrayList<Pietro> pietra = this.setupPietra();
        while(this.pasazerowie.get(i).GetStart()<this.acctual_floor)
            i++;
        while(i<this.pasazerowie.size())
            if( this.pasazerowie.get(i).GetStart()<this.pasazerowie.get(i).GetStop()){
                pietra.get(this.pasazerowie.get(i).GetStart()).pasazerowieWsiadający.add(this.pasazerowie.get(i));
                pietra.get(this.pasazerowie.get(i).GetStop()).pasazerowieWysiadajacy.add(this.pasazerowie.get(i));
            }
        this.clearPietra(pietra);
        this.floors.addAll(pietra);
    }

    private void goDown(){
        int i =0;
        ArrayList<Pietro> pietra = this.setupPietra();
        while(this.pasazerowie.get(i).GetStart()<this.acctual_floor)
            i++;
        while(i<this.pasazerowie.size())
            if( this.pasazerowie.get(i).GetStart()>this.pasazerowie.get(i).GetStop()){
                pietra.get(this.pasazerowie.get(i).GetStart()).pasazerowieWsiadający.add(this.pasazerowie.get(i));
                pietra.get(this.pasazerowie.get(i).GetStop()).pasazerowieWysiadajacy.add(this.pasazerowie.get(i));
            }
        this.clearPietra(pietra);
        this.floors.addAll(pietra);
    }

    private void clearPietra(ArrayList<Pietro> pietro){
        Pietro tmp;
        for(int i=0;i<pietro.size();i++){
            tmp=pietro.get(i);
            if(tmp.pasazerowieWsiadający.isEmpty() && tmp.pasazerowieWysiadajacy.isEmpty())
                pietro.remove(i);
        }
    }

    public List<Pietro> setCourse(){
        PasazerSort sort = new PasazerSort(pasazerowie);
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
        this.setFloorCount(floor_count);
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
}
