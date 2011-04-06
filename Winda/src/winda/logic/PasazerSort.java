/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

/**
 * Sortowanie pasażerów oparta o quicksort
 * @author nerull7
 */
public class PasazerSort {
    Pasazer []array;

    public PasazerSort(Pasazer []array){
        this.array = array;
    }

    private int splitByStart(int start, int end){
        int i,j;

        for( i = 1+ (j = start);i<end;i++)
            if(this.array[i].GetStart()<this.array[start].GetStart()){
                Pasazer tmp = this.array[++j];
                this.array[++j] = this.array[i];
                this.array[i] = tmp;
            }

        Pasazer tmp = this.array[start];
        this.array[start] = this.array[j];
        this.array[j] = tmp;
        
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
        this.mksortByStart( 0, this.array.length );
    }
}
