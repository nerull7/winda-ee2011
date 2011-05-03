/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package winda.animation;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import winda.logic.Pietro;

/**
 *
 * @author Przemo
 */
public class Elevator extends Thread {
    private ElevatorAnimation ea;

    private int floor_count;
    private final int floor_size = 50;
    private int time_for_floor; // Tymczasowo /* Miliseconds */
    private int actual_floor;
    private double jump_time;
    private double speed;
    private int enter_exit_time;

    private ArrayList<Pietro> pietro;

    private int elevator;

    public Elevator(ElevatorAnimation ea, int floor_count, int elevator){
        this.setElevatorAnimation(ea);
        this.floor_count = floor_count;
        this.elevator = elevator;
        this.speed = 1;
        this.init();
    }

    @Override
    public void run(){
        for(int i=0;i<this.pietro.size();i++){
            this.goToFloor(this.pietro.get(i));
        }
    }
    
    private void init(){
        this.time_for_floor = 1000;
        this.actual_floor = 0;
        this.jump_time = ((double)this.time_for_floor)/((double)(this.floor_size));
    }

    public void setElevatorAnimation(ElevatorAnimation ea){
        this.ea = ea;
    }

    public void goToFloor(Pietro pietro){
        if(this.actual_floor<pietro.numerPietra)
            this.goUp(pietro.numerPietra);
        else if(this.actual_floor>pietro.numerPietra)
            this.goDown(pietro.numerPietra);
        this.actual_floor = pietro.numerPietra;

        this.exitElevator(pietro.pasazerowieWysiadajacy.size(), pietro.numerPietra);
        this.enterElevator(pietro.pasazerowieWsiadający.size(), pietro.numerPietra);
    }

    private void goUp(int number){
        while(this.ea.shift>(this.floor_size*(this.floor_count-(number+1)))){
            try {
                if(this.elevator==1)
                    this.ea.shift--;
                else
                    this.ea.shift2--;
                this.ea.repaint();
                this.sleep((long) (this.jump_time*this.speed));
            } catch (InterruptedException ex) {
                Logger.getLogger(ElevatorMovement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void goDown(int number) {
        while(this.ea.shift<(this.floor_size*(this.floor_count-(number+1)))){
            try {
                if(this.elevator==1)
                    this.ea.shift++;
                else
                    this.ea.shift2++;
                this.ea.repaint();
                this.sleep((long) (this.jump_time*this.speed));
            } catch (InterruptedException ex) {
                Logger.getLogger(ElevatorMovement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void exitElevator(int number, int floor){
        if(number<=0)
            return;
        System.out.println("exitElevator("+number+") elevator_passangers "+this.ea.elevator_passangers);
        for(int i=0;i<number;i++){
            try {
                if(this.elevator == 1)
                    this.ea.elevator_passangers--;
                else
                    this.ea.second_elevator_passangers--;
                this.ea.exited_floor_passangers[floor]++;
                this.ea.repaint();
                this.sleep((long) (this.enter_exit_time*this.speed));
            } catch (InterruptedException ex) {
                Logger.getLogger(ElevatorMovement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void enterElevator(int number, int floor){
        if(number<=0)
            return;
        for(int i=0;i<number;i++){
            try {
                if(this.elevator == 1)
                    this.ea.elevator_passangers++;
                else
                    this.ea.second_elevator_passangers++;
                this.ea.floor_passangers[floor]--;
                this.ea.repaint();
                this.sleep((long) (this.enter_exit_time*this.speed));
            } catch (InterruptedException ex) {
                Logger.getLogger(ElevatorMovement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setTimeForFloor(int time){
        this.time_for_floor = time;
        this.jump_time = ((double)time)/((double)(this.floor_size));
    }

    public void setFloorsCount(int floors){
        this.floor_count = floors;
        this.init();
    }

    public void setEnterExitTime(int time){
        this.enter_exit_time = time;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public void setPietro(ArrayList<Pietro> pietro){
        this.pietro = pietro;
    }
}
