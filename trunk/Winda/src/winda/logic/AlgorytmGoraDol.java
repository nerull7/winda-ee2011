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
    private ArrayList<Integer> floors;
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

    private void sortUp(ArrayList<Integer> array){
        Sort.quicksort(array.toArray(), null);
    }

    private void goUp(int to){
        int i =0;
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        while(this.pasazerowie.get(i).GetStart()<this.acctual_floor)
            i++;
        while(i<this.pasazerowie.size())
            if( this.pasazerowie.get(i).GetStart()<this.pasazerowie.get(i).GetStop()&&
                this.pasazerowie.get(i).GetStart()<to){
                if(tmp.contains(this.pasazerowie.get(i).GetStart()))
                    tmp.add(this.pasazerowie.get(i).GetStart());
                if(tmp.contains(this.pasazerowie.get(i).GetStop()))
                    tmp.add(this.pasazerowie.get(i).GetStop());
            }
        this.sortUp(tmp);
        this.floors.addAll(tmp);
    }
    
    private ArrayList<Integer> sortDown(ArrayList<Integer> array){
        Sort.quicksort(array.toArray(), null);
        ArrayList<Integer> tmp = new ArrayList();
        for(int i= (array.size()-1);i>=0;i--)
            tmp.add(array.get(i));
        return tmp;
    }

    private void goDown(int to){
        int i =0;
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        while(this.pasazerowie.get(i).GetStart()<this.acctual_floor)
            i++;
        while(i<this.pasazerowie.size())
            if( this.pasazerowie.get(i).GetStart()>this.pasazerowie.get(i).GetStop()&&
                this.pasazerowie.get(i).GetStart()>to){
                if(tmp.contains(this.pasazerowie.get(i).GetStart()))
                    tmp.add(this.pasazerowie.get(i).GetStart());
                if(tmp.contains(this.pasazerowie.get(i).GetStop()))
                    tmp.add(this.pasazerowie.get(i).GetStop());
            }
        tmp = this.sortDown(tmp);
        this.floors.addAll(tmp);
    }

    public List<Pietro> setCourse(){
        this.floors = new ArrayList<Integer>();
        floors.add(this.start_floor);

        PasazerSort sort = new PasazerSort(pasazerowie);
        sort.sortByStart();
        this.acctual_floor = this.start_floor;
        
        /* Jeżeli winda jest poniżej połowy */
        if((this.floor_count/2)>this.acctual_floor){
            this.goDown(0);
            this.goUp(this.floor_count);
            this.goDown(this.start_floor);
        }
        else{
            this.goUp(0);
            this.goDown(this.floor_count);
            this.goUp(this.start_floor);
        }



        int []ret = new int[floors.size()];
        for(int i=0;i<floors.size();i++)
            ret[i] = floors.get(i).intValue();
        return ret;
    }

    public List<Pietro> Trasa(List<Pasazer> pasazerowie) {
        this.pasazerowie = pasazerowie;
        return this.setCourse();
    }

    public void SetMaxPietro(int maxPietro) {
        this.setFloorCount(floor_count);
    }
}
