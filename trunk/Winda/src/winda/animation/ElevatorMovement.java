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

    Elevator first;
    Elevator second;

    private int floor_count;
    private final int floor_size = 50;
    private int time_for_floor; // Tymczasowo /* Miliseconds */
    private int actual_floor;
    private double jump_time;
    private double speed;
    private int enter_exit_time;
    private boolean second_elevator;

    private ArrayList<Pietro> pietro;
    private ArrayList<Pietro> second_pietro;

    @Override
    public void run(){
        this.first.setPietro(this.pietro);
        this.first.start();
        if(!this.second_elevator){
            this.second.setPietro(this.second_pietro);
            this.second.start();
        }
    }

    public ElevatorMovement(int floor_count){
        this.floor_count = floor_count;
        this.init();
        this.speed = 1;
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
        this.first.setTimeForFloor(time);
        if(this.second_elevator)
            this.second.setTimeForFloor(time);
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
        this.first.setSpeed(this.speed);
        if(this.second_elevator)
            this.second.setSpeed(this.speed);
    }

    public void setFloorsCount(int floors){
        this.floor_count = floors;
        this.first.setFloorsCount(floors);
        if(this.second_elevator)
            this.second.setFloorsCount(floors);
        this.init();
    }

    public void setEnterExitTime(int time){
        this.enter_exit_time = time;
        this.first.setEnterExitTime(time);
        if(this.second_elevator)
            this.second.setEnterExitTime(time);
    }

    private void init(){
        this.time_for_floor = 1000;
        this.actual_floor = 0;
        this.ea = new ElevatorAnimation(this.floor_count, this.second_elevator);
        /* Start z parteru */
        this.ea.shift = this.floor_size * (this.floor_count-1);
        this.ea.shift2 = this.floor_size * (this.floor_count-1);
        this.jump_time = ((double)this.time_for_floor)/((double)(this.floor_size));
        this.second_elevator = false;

        this.first = new Elevator(this.ea, this.floor_count, 1);
    }

    public void setPietro(ArrayList pietro, int elevator){
        if(elevator == 1){
            this.pietro = pietro;
            this.first.setPietro(pietro);
        }
        else{
            this.second_pietro = pietro;
            this.second.setPietro(pietro);
        }
    }

    public void enableSecondElevator(){
        this.second_elevator = true;
        this.second = new Elevator(this.ea, this.floor_count, 2);
        this.second.setSpeed(this.speed);
        this.ea.repaint();
    }

    public void disableSecondElevator(){
        this.second_elevator = false;
        this.second.interrupt();
        this.second = null;
        this.ea.repaint();
    }
}
