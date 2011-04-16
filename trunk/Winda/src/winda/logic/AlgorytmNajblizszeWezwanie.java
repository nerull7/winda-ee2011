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
/*
 * Nie wiem czemu to gówno nie chce działać!
 * 
 *
 */

public class AlgorytmNajblizszeWezwanie implements IAlgorytm{
    int pietroWindy = 0;
    int maxPietro=11;
    int minPietro = 0;
    int stop = 0;
    int start = 0;
    int NpietroWindy = 0;
    Pasazer p = new Pasazer(-1,-1,-1);


    public List<Pietro> Trasa(List<Pasazer> pasazerowie){
        
        List<Pietro> trasa = new ArrayList();
        int licznik = 0;
        //List<Pasazer> pas_tmp = new ArrayList();
        int zmiana = 0;
        
        int i = 0;
        Pietro pietro = new Pietro();
        

        int ile = 0;
        while(i < pasazerowie.size()){

            Pietro pietro2 = new Pietro();
            Pietro []pietro3 = new Pietro[12];
            List<Pietro> pietro_tmp= new ArrayList();
            List<Pasazer> pas_tmp = new ArrayList();
            boolean dodano = false;
            boolean is = false;
            ile=0;
            for(int j = 0; j < pasazerowie.size(); j++){
                if(pietroWindy == pasazerowie.get(j).GetStart()){
                    if(j==0){
                        pietro.numerPietra = pietroWindy;
                    }
                    //is = true;
                    pietro.pasazerowieWsiadający.add(pasazerowie.get(j));
                    //pietro.pasazerowieWysiadajacy.add(p);
                    for(int k = 0; k < trasa.size(); k++){
                        for(int l = 0; l < trasa.get(k).pasazerowieWysiadajacy.size();l++){
                            if(pietro.numerPietra == trasa.get(k).pasazerowieWysiadajacy.get(l).GetStop()){
                                pietro.pasazerowieWysiadajacy.add(trasa.get(k).pasazerowieWysiadajacy.get(l));
                                trasa.get(k).pasazerowieWysiadajacy.get(l).SetStop(-1);
                            }
                        }
                    }
                    pas_tmp.add(pasazerowie.get(j));
                    is = true;
                }
            }
            if(is){
                pietroWindy = pas_tmp.get(pas_tmp.size()-1).GetStop();
                trasa.add(pietro);
                pietro = new Pietro();
                i++;
                is = false;
            }
            for(int k = 0; k < pas_tmp.size()-1; k++){
                pietro.numerPietra = pas_tmp.get(k).GetStop();
                pietro.pasazerowieWysiadajacy.add(pas_tmp.get(k));
                trasa.add(pietro);
                pietro = new Pietro();
                i++;
            }
            pas_tmp.clear();
            

            for(int m = 0; m < maxPietro; m++){
                if(pietroWindy + m <= maxPietro){
                    for(int n = 0; n < pasazerowie.size(); n++){
                        if(pietroWindy + m == pasazerowie.get(n).GetStart()){
                            if(n == 0){
                                pietro.numerPietra = pietroWindy+m;
                            }
                            //is = true;
                            pietro.pasazerowieWsiadający.add(pasazerowie.get(n));
                            //pietro.pasazerowieWysiadajacy.add(p);
                            for(int k = 0; k < trasa.size(); k++){
                                for(int l = 0; l < trasa.get(k).pasazerowieWysiadajacy.size();l++){
                                    if(pietro.numerPietra == trasa.get(k).pasazerowieWysiadajacy.get(l).GetStop()){
                                        pietro.pasazerowieWysiadajacy.add(trasa.get(k).pasazerowieWysiadajacy.get(l));
                                        trasa.get(k).pasazerowieWysiadajacy.get(l).SetStop(-1);
                                    }
                                }
                            }
                            pas_tmp.add(pasazerowie.get(n));
                            is = true;
                            dodano = true;
                        }
                    }
                    if(is){
                        pietroWindy = pas_tmp.get(pas_tmp.size()-1).GetStop();
                        trasa.add(pietro);
                        pietro = new Pietro();
                        i++;
                        is = false;
                    }
                    
                    for(int k = 0; k < pas_tmp.size()-1; k++){
                                pietro.numerPietra = pas_tmp.get(k).GetStop();
                                //System.out.println("L:"+pietro2.numerPietra);
                                pietro.pasazerowieWysiadajacy.add(pas_tmp.get(k));
                                trasa.add(pietro);
                                pietro = new Pietro();
                                i++;
                    }
                    pas_tmp.clear();
                }
                if(dodano) break;

                if(pietroWindy - m >= minPietro){
                    for(int n = 0; n < pasazerowie.size(); n++){
                        if(pietroWindy - m == pasazerowie.get(n).GetStart()){
                            if(n==0){
                                pietro.numerPietra = pietroWindy-m;
                            }
                            //is = true;
                            pietro.pasazerowieWsiadający.add(pasazerowie.get(n));
                            //pietro.pasazerowieWysiadajacy.add(p);
                            for(int k = 0; k < trasa.size(); k++){
                                for(int l = 0; l < trasa.get(k).pasazerowieWysiadajacy.size();l++){
                                    if(pietro.numerPietra == trasa.get(k).pasazerowieWysiadajacy.get(l).GetStop()){
                                        pietro.pasazerowieWysiadajacy.add(trasa.get(k).pasazerowieWysiadajacy.get(l));
                                        trasa.get(k).pasazerowieWysiadajacy.get(l).SetStop(-1);
                                    }
                                }
                            }
                            pas_tmp.add(pasazerowie.get(n));
                            is = true;
                            dodano = true;
                        }
                    }
                    if(is){
                        pietroWindy = pas_tmp.get(pas_tmp.size()-1).GetStop();
                        trasa.add(pietro);
                        pietro = new Pietro();
                        i++;
                        is = false;
                    }
                    
                    for(int k = 0; k < pas_tmp.size()-1; k++){
                                pietro.numerPietra = pas_tmp.get(k).GetStop();
                                //System.out.println("L:"+pietro2.numerPietra);
                                pietro.pasazerowieWysiadajacy.add(pas_tmp.get(k));
                                trasa.add(pietro);
                                pietro = new Pietro();
                                i++;
                    }
                    pas_tmp.clear();
                }
                if(dodano) break;

            }

            
    }
    

    return trasa;
    }

    public void SetMaxPietro(int maxPietro) {
        this.maxPietro = maxPietro;
    }

    public static void main(String[] args) {
        Pasazer p1 = new Pasazer(1,0,6);
        Pasazer p2 = new Pasazer(2,9,1);
        Pasazer p3 = new Pasazer(3,0,6);
        Pasazer p4 = new Pasazer(4,6,1);
        Pasazer p5 = new Pasazer(5,0,4);
        Pasazer p6 = new Pasazer(6,0,5);
        Pasazer p7 = new Pasazer(7,9,7);

        List<Pasazer> lp = new ArrayList();
        lp.add(p1);
        lp.add(p2);
        lp.add(p3);
        lp.add(p4);
        lp.add(p5);
        lp.add(p6);
        lp.add(p7);

        List<Pietro> tr = new ArrayList();
        AlgorytmNajblizszeWezwanie l = new AlgorytmNajblizszeWezwanie();
        tr = l.Trasa(lp);

       for(int h = 0; h < tr.size(); h++){
           System.out.println("Pietro: "+tr.get(h).numerPietra);
           System.out.print("Ws: ");
           for(int i =0; i <tr.get(h).pasazerowieWsiadający.size();i++){
               System.out.print(""+tr.get(h).pasazerowieWsiadający.get(i).GetName()+", ");
           }
           System.out.print("\nWy: ");
           for(int i =0; i <tr.get(h).pasazerowieWysiadajacy.size();i++){
               System.out.print(""+tr.get(h).pasazerowieWysiadajacy.get(i).GetName()+", ");
           }
           System.out.println();

       }

    }
}


