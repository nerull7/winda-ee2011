/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomek
 */
public class Pietro {
    private List<Pasazer> pasazerowie;
    private int IloscWysiadajacych;
    private int NumerPietra;

    public Pietro(int numerPietra){
        this.NumerPietra = numerPietra;
    }

    public int GetIloscWysiadajacych(){
        return this.IloscWysiadajacych;
    }

    public void SetIloscWysiadajacych(int iloscWysiadajacych){
        this.IloscWysiadajacych = iloscWysiadajacych;
    }

    public List<Pasazer> GetPasazerowie(){
        return this.pasazerowie;
    }

    public void SetPasazerowie(List<Pasazer> pasazerowie) {
        this.pasazerowie = pasazerowie;
    }

    public void AddPasazer(Pasazer pasazer) {
        this.pasazerowie.add(pasazer);
    }

    public void DeletePasazer(Pasazer pasazer) {
        this.pasazerowie.remove(pasazer);
    }
}
