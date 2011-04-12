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
public class AlgorytmNajblizszeWezwanie implements IAlgorytm{
    int pietroWindy = 1;
    int maxPietro;
    int minPietro = 1;
    int stop = 0;
    int start = 0;
    Pasazer p = new Pasazer(0,0,0);


    public List<Pietro> Trasa(List<Pasazer> pasazerowie){

        List<Pietro> trasa = new ArrayList();
        int [] t = new int[2*pasazerowie.size()+1];
        int[][] ttt = new int[2*pasazerowie.size()+1][3];
        int tab_len = 0;
        int iluPasazerow = 0;
        t[tab_len] = pietroWindy;
        tab_len++;
        int zmiana = 0;

        int i = 0;
        while(i < pasazerowie.size()){
            Pietro pietro = new Pietro();
            int z = 0; int s;
            Pasazer[] ppp = new Pasazer[2*pasazerowie.size()]; int g=0;
            for(int l = 0; l < pasazerowie.size(); l++){
                if(pietroWindy == pasazerowie.get(l).GetStart()){
                    z = 1;
                    ppp[g] = pasazerowie.get(l); g++;
                }
                else{
                    z = 0;
                }
            }

            if(z == 1){
                for(int q=0;q<g;q++){
                    t[tab_len] = ppp[q].GetStart();
                    tab_len++;
                    pietro.numerPietra = ppp[q].GetStart();
                    pietro.pasazerowieWsiadający.add(pasazerowie.get(q));

                }
                for(int w=0;w<g;w++){
                    t[tab_len] = ppp[w].GetStop();
                    tab_len++;
                    pietroWindy = ppp[w].GetStop();

                    ppp[w].SetStart(-1);
                    ppp[w].SetStop(-1);
                    i+=1;
                }

                for(int y = 0; y < trasa.size(); y++){
                    for(s = 0; s < trasa.get(y).pasazerowieWysiadajacy.size(); s++){
                        if(pietroWindy == trasa.get(y).pasazerowieWysiadajacy.get(s).GetStop()){
                            pietro.pasazerowieWysiadajacy.add(trasa.get(y).pasazerowieWysiadajacy.get(s));
                        }
                    }
                }
             }

             zmiana = 0;

             for(int k = 0; k < maxPietro; k++){
                 if(pietroWindy + k <= maxPietro){
                     Pasazer[] pp = new Pasazer[2*pasazerowie.size()];
                     int e=0;
                     for(int j = 0; j < pasazerowie.size(); j++){
                         if(pietroWindy + k == pasazerowie.get(j).GetStart()){
                             pp[e] = pasazerowie.get(j); e++;
                         }
                     }
                     for(int q=0;q<e;q++){
                         t[tab_len] = pp[q].GetStart();
                         tab_len++;
                         pietro.numerPietra = ppp[q].GetStart();
                         pietro.pasazerowieWsiadający.add(pasazerowie.get(q));
                     }
                     for(int w=0;w<e;w++){
                         t[tab_len] = pp[w].GetStop();
                         tab_len++;

                         pietroWindy = pp[w].GetStop();

                         pp[w].SetStart(-1);
                         pp[w].SetStop(-1);
                         i+=1;
                         zmiana = 1;
                     }
                     for(int y = 0; y < trasa.size(); y++){
                        for(s = 0; s < trasa.get(y).pasazerowieWysiadajacy.size(); s++){
                            if(pietroWindy == trasa.get(y).pasazerowieWysiadajacy.get(s).GetStop()){
                                 pietro.pasazerowieWysiadajacy.add(trasa.get(y).pasazerowieWysiadajacy.get(s));
                            }
                        }
                     }
                 }

                 if(zmiana == 1)
                     break;

                 if(pietroWindy - k >= minPietro){
                     Pasazer[] pp = new Pasazer[2*pasazerowie.size()];
                     int e=0;
                     for(int j = 0; j < pasazerowie.size(); j++){
                         if(pietroWindy - k == pasazerowie.get(j).GetStart()){
                             pp[e] = pasazerowie.get(j); e++;
                         }
                     }
                     for(int q=0;q<e;q++){
                         t[tab_len] = pp[q].GetStart();
                         tab_len++;
                         pietro.numerPietra = ppp[q].GetStart();
                         pietro.pasazerowieWsiadający.add(pasazerowie.get(q));
                     }
                     for(int w=0;w<e;w++){
                         t[tab_len] = pp[w].GetStop();
                         tab_len++;

                         pietroWindy = pp[w].GetStop();

                         pp[w].SetStart(-1);
                         pp[w].SetStop(-1);
                         i+=1;
                         zmiana = 1;
                      }
                      for(int y = 0; y < trasa.size(); y++){
                            for(s = 0; s < trasa.get(y).pasazerowieWysiadajacy.size(); s++){
                                if(pietroWindy == trasa.get(y).pasazerowieWysiadajacy.get(s).GetStop()){
                                       pietro.pasazerowieWysiadajacy.add(trasa.get(y).pasazerowieWysiadajacy.get(s));
                                }
                            }
                      }
                 }

                 if(zmiana == 1)
                     break;

            }

         trasa.add(pietro);
    }
    

    return trasa;
    }

    public void SetMaxPietro(int maxPietro) {
        this.maxPietro = maxPietro;
    }
}