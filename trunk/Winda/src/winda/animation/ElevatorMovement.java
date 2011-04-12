package winda.animation;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Przemo
 */
public class ElevatorMovement {
//    private final int FPS = 50; /* Frames Per Second */
//    private final int THREAD_WAIT_TIME = 1000/this.FPS;

    private ElevatorAnimation ea;

    private int floor_count;
    private final int floor_size = 50;
    private int time_for_floor; // Tymczasowo /* Miliseconds */
    private int actual_floor;
    private double jump_time;

    public ElevatorMovement(int floor_count){
        this.floor_count = floor_count;
        this.init();
    }

    public void goToFloor(int number){
        if(this.actual_floor<number)
            this.goUp(number);
        else if(this.actual_floor>number)
            this.goDown(number);
        this.actual_floor = number;
    }

    private void goUp(int number){
        while(this.ea.shift>(this.floor_size*(this.floor_count-(number+1)))){
            try {
                this.ea.shift--;
                this.ea.repaint();
                Thread.sleep((long) jump_time);
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
                Thread.sleep((long) jump_time);
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
        this.ea.elevator_passangers = passangers;
    }

    public int getPassangersInElevator(){
        return this.ea.elevator_passangers;
    }

    public int getPassangersOnFloor(int floor){
        return this.ea.floor_passangers[floor];
    }

    public int getSpeed(){
        return this.time_for_floor;
    }

    public void setSpeed(int speed){
        this.time_for_floor = speed;
    }

    public void setFloorsCount(int floors){
        this.floor_count = floors;
        this.init();
    }

    private void init(){
        this.time_for_floor = 1000;
        this.actual_floor = 0;
        ea = new ElevatorAnimation(this.floor_count);
        ea.shift = this.floor_size * (this.floor_count-1);
        this.jump_time = ((double)this.time_for_floor)/((double)(this.floor_size));
    }
}
