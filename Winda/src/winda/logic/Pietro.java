/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

/**
 *
 * @author Tomek
 */
public class Pietro {
    private Pasazer [] pasazerowie;

    public Pietro(){

    }

    public Pasazer [] GetPasazerowie(){
        return this.pasazerowie;
    }

    public void SetPasazerowie(Pasazer [] pasazerowie) {
        this.pasazerowie = pasazerowie;
    }

    public void AddPasazer(Pasazer pasazer) {
        this.pasazerowie[this.pasazerowie.length + 1] = pasazer;
    }

    public void DeletePasazer(Pasazer pasazer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
