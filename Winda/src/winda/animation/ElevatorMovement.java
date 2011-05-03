package winda.animation;

import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import winda.gui.WindaApp;
import winda.logic.Pietro;

/**
 *
 * @author Przemo
 */
public class ElevatorMovement extends Thread{
    private ElevatorAnimation ea;

    private int floor_count;
    private final int floor_size = 50;
    private int time_for_floor; // Tymczasowo /* Miliseconds */
    private int actual_floor;
    private double jump_time;
    private double speed;
    private int enter_exit_time;
    private boolean second_elevator;

    private ArrayList<Pietro> pietro;

    @Override
    public void run(){
        for(int i=0;i<this.pietro.size();i++){
            this.goToFloor(this.pietro.get(i));
            System.out.println("goto:"+this.pietro.get(i).numerPietra+" in:"+this.pietro.get(i).pasazerowieWsiadający.size()+" out:"+this.pietro.get(i).pasazerowieWysiadajacy.size());
        }
    }

    public ElevatorMovement(int floor_count){
        this.floor_count = floor_count;
        this.init();
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
                this.ea.shift--;
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
                this.ea.shift++;
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
                this.ea.elevator_passangers--;
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
        System.out.println("enterElevator("+number+") elevator_passangers "+this.ea.elevator_passangers);
        System.out.println("enterElevator("+number+") floor_passangers["+floor+"] "+this.ea.floor_passangers[floor]);
        for(int i=0;i<number;i++){
            try {
                this.ea.elevator_passangers++;
                this.ea.floor_passangers[floor]--;
                this.ea.repaint();
                this.sleep((long) (this.enter_exit_time*this.speed));
            } catch (InterruptedException ex) {
                Logger.getLogger(ElevatorMovement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ElevatorAnimation getElevatorAnimation(){
        return this.ea;
    }

    public void setPassangersOnFloor(int floor, int number){
        this.ea.floor_passangers[floor]=number;
    }

    public void setPassangersOnFloors(int []passangers){
        if(passangers.length == this.ea.floor_passangers.length)
            this.ea.floor_passangers = passangers;
        else
            for(int i=0;i<passangers.length;i++)
                this.ea.floor_passangers[i] = passangers[i];
    }

    public void setPassangersInElevator(int passangers){
        System.out.println("setPassangersInElevator("+passangers+")");
        this.ea.elevator_passangers = passangers;
    }

    public int getPassangersInElevator(){
        return this.ea.elevator_passangers;
    }

    public int getPassangersOnFloor(int floor){
        return this.ea.floor_passangers[floor];
    }

    public void setTimeForFloor(int time){
        this.time_for_floor = time;
        this.jump_time = ((double)time)/((double)(this.floor_size));
    }

    public double getSpeed(){
        return this.speed;
    }

    public void setSpeed(int speed){
        if(speed == 50)
            this.speed = 1;
        else if(speed >50){
            speed = 100-speed;
            this.speed = (((double)speed+50)/2)/100;
        }
        else{
            speed = 100-speed;
            this.speed = (((double)speed+50)*2)/100;
        }
    }

    public void setFloorsCount(int floors){
        this.floor_count = floors;
        this.init();
    }

    public void setEnterExitTime(int time){
        this.enter_exit_time = time;
    }

    private void init(){
        this.time_for_floor = 1000;
        this.actual_floor = 0;
        this.ea = new ElevatorAnimation(this.floor_count, this.second_elevator);
        /* Start z parteru */
        this.ea.shift = this.floor_size * (this.floor_count-1);
        this.ea.shift2 = this.floor_size * (this.floor_count-1);
        this.jump_time = ((double)this.time_for_floor)/((double)(this.floor_size));
        this.speed = 1;
    }

    public void setPietro(ArrayList pietro){
        this.pietro = pietro;
    }

    public void enableSecondElevator(){
        this.second_elevator = true;
        this.ea.repaint();
    }

    public void disableSecondElevator(){
        this.second_elevator = false;
        this.ea.repaint();
    }
}
