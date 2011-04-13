/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

import java.util.List;

/**
 * Sortowanie pasażerów oparta o quicksort
 * @author nerull7
 */
public class PasazerSort {
    List<Pasazer> array;

    public PasazerSort(List<Pasazer> array){
        this.array = array;
    }

    private int splitByStart(int start, int end){
        int i,j;

        for( i = 1+ (j = start);i<end;i++)
            if(this.array.get(i).GetStart()<this.array.get(start).GetStart()){
                Pasazer tmp = this.array.get(++j);
                this.array.set(++j, this.array.get(i));
                this.array.set(i, tmp);
            }

        Pasazer tmp = this.array.get(start);
        this.array.set(start, this.array.get(j));
        this.array.set(j, tmp);
        
        return j;
    }

    private void mksortByStart(int start, int end){
        if( start < end ){
            int tmp = this.splitByStart( start, end );
            this.mksortByStart( start, tmp-1 );
            this.mksortByStart( tmp+1, end );
        }
    }

    /*
     * QuickSort pasażerów po numerze piętra na którym wsiadają.
     */

    public void sortByStart(){
        this.mksortByStart( 0, this.array.size() );
    }
}
