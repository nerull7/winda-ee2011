/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

import java.util.ArrayList;
import sun.misc.Sort;

/**
 * Algortym poruszania się windą przy wsiadaniu pasażerów
 * jadących tylko w kierunku w którym porusza się winda.
 * @author Przemo
 */
public class AlgorytmGoraDol implements IAlgorytm {
    private int start_floor;
    private int floor_count;
    private ArrayList<Integer> floors;
    private Pasazer[] pasazerowie;
    private int acctual_floor;

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

    private void goUp(){
        int i =0;
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        while(this.pasazerowie[i].GetStart()<this.acctual_floor)
            i++;
        while(i<this.pasazerowie.length)
            if(this.pasazerowie[i].GetStart()<this.pasazerowie[i].GetStop()){
                if(tmp.contains(this.pasazerowie[i].GetStart()))
                    tmp.add(this.pasazerowie[i].GetStart());
                if(tmp.contains(this.pasazerowie[i].GetStop()))
                    tmp.add(this.pasazerowie[i].GetStop());
            }
        this.sortUp(tmp);
        this.floors.addAll(tmp);
    }

    public int[] setCourse(){
        this.floors = new ArrayList<Integer>();
        floors.add(this.start_floor);

        PasazerSort sort = new PasazerSort(pasazerowie);
        sort.sortByStart();
        this.acctual_floor = this.start_floor;

        int []ret = new int[floors.size()];
        for(int i=0;i<floors.size();i++)
            ret[i] = floors.get(i).intValue();
        return ret;
    }

    public int[] Trasa(Pasazer[] pasazerowie) {
        this.pasazerowie = pasazerowie;
        return this.setCourse();
    }

    public void SetMaxPietro(int maxPietro) {
        this.setFloorCount(floor_count);
    }

}
