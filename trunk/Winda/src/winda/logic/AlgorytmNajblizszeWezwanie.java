/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.logic;

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



    public int[] Trasa(Pasazer[] pasazerowie){
        int [] t = new int[2*pasazerowie.length+1];
        int tab_len = 0;
        int iluPasazerow = 0;
        t[tab_len] = pietroWindy;
        tab_len++;
        int zmiana = 0;

        int i = 0;
        while(i < pasazerowie.length){
            int z = 0;
            Pasazer[] ppp = new Pasazer[2*pasazerowie.length]; int g=0;
            for(int l = 0; l < pasazerowie.length; l++){
                if(pietroWindy == pasazerowie[l].GetStart()){
                    z = 1;
                    ppp[g] = pasazerowie[l]; g++;
                }
                else{
                    z = 0;
                }
            }

            if(z == 1){
                for(int q=0;q<g;q++){
                    t[tab_len] = ppp[q].GetStart();
                    tab_len++;
                }
                for(int w=0;w<g;w++){
                    t[tab_len] = ppp[w].GetStop();
                    tab_len++;
                    pietroWindy = ppp[w].GetStop();

                    ppp[w].SetStart(-1);
                    ppp[w].SetStop(-1);
                    i+=1;
                }
             }

             zmiana = 0;

             for(int k = 0; k < maxPietro; k++){
                 if(pietroWindy + k <= maxPietro){
                     Pasazer[] pp = new Pasazer[2*pasazerowie.length];
                     int e=0;
                     for(int j = 0; j < pasazerowie.length; j++){
                         if(pietroWindy + k == pasazerowie[j].GetStart()){
                             pp[e] = pasazerowie[j]; e++;
                         }
                     }
                     for(int q=0;q<e;q++){
                         t[tab_len] = pp[q].GetStart();
                         tab_len++;
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
                 }

                 if(zmiana == 1)
                     break;

                 if(pietroWindy - k >= minPietro){
                     Pasazer[] pp = new Pasazer[2*pasazerowie.length];
                     int e=0;
                     for(int j = 0; j < pasazerowie.length; j++){
                         if(pietroWindy - k == pasazerowie[j].GetStart()){
                             pp[e] = pasazerowie[j]; e++;
                         }
                     }
                     for(int q=0;q<e;q++){
                         t[tab_len] = pp[q].GetStart();
                         tab_len++;
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
                 }

                 if(zmiana == 1)
                     break;

            }

        }

    return t;
    }

    public void SetMaxPietro(int maxPietro) {
        this.maxPietro = maxPietro;
    }
}