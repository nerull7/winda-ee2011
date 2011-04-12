package winda.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JApplet;

/**
 *
 * @author Przemo
 */
public class ElevatorAnimation extends JApplet {

    private int x_dimension = 200;
    private int y_dmension;
    private int floor_count; 

    private final int floor_size = 50;
    private final int elevator_size_x = 40;
    private final int elevator_size_y = 50;

    private Font elevator_font;
    private Font floor_font;

    public int shift;
    public int elevator_passangers;
    public int[] floor_passangers;

    public ElevatorAnimation(int floor_count){
        this.floor_count = floor_count;
        this.y_dmension = floor_size*this.floor_count;
        this.elevator_font = new Font("Serif", Font.PLAIN, 30);
        this.floor_font = new Font("Serif", Font.PLAIN, 16);
        this.floor_passangers = new int[floor_count];
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(this.x_dimension, this.y_dmension);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(this.buffImage(), null, 0, 0);        
    }

    /*
     * Buforowana klatka
     */
    private BufferedImage buffImage(){
        BufferedImage buffi = new BufferedImage(this.x_dimension, this.y_dmension, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D buffig = (Graphics2D) buffi.getGraphics();
        Rectangle r = new Rectangle(0,0,this.x_dimension,this.y_dmension);

        buffig.setBackground(Color.WHITE);
        buffig.setColor(Color.BLACK);
        buffig.clearRect(0, 0, this.x_dimension, this.y_dmension);

        this.drawElevatorShaft(buffig);
        this.drawFloors(buffig);
        this.drawPassangers(buffig);
        this.drawElevator(buffig);

        return buffi;
    }

    /*
     * Rysowanie szybu windy
     */
    private void drawElevatorShaft(Graphics2D g2){
        Line2D left = new Line2D.Double(45, 0, 45, this.y_dmension);
        Line2D right = new Line2D.Double(95, 0, 95, this.y_dmension);
        g2.draw(left);
        g2.draw(right);
    }

    /*
     * Rysowanie Pięter z numerami
     */
    private void drawFloors(Graphics2D g2){
        g2.setFont(this.floor_font);
        for(int i=1;i<=this.floor_count;i++){
            int tmp = this.floor_count-i;
            g2.draw(new Line2D.Double(0, this.floor_size*i, this.x_dimension, this.floor_size*i));
            g2.drawString(""+tmp, 100, (this.floor_size*i)-5);
        }
    }

    /*
     * Rysowanie ilosci pasażerów na piętrach
     */
    private void drawPassangers(Graphics2D g2){
        g2.setFont(elevator_font);
        for(int i=1;i<=this.floor_count;i++){
            g2.drawString(""+this.floor_passangers[this.floor_count-i], 120 ,(this.floor_size*i)-15);
        }
    }

    /*
     * Rysowanie windy
     */
    private void drawElevator(Graphics2D g2){
        Rectangle2D winda = new Rectangle2D.Double(50, 00, this.elevator_size_x, this.elevator_size_y);

        g2.translate(0, this.shift);
        g2.clearRect(50, 00, this.elevator_size_x, this.elevator_size_y);
        g2.draw(winda);

        g2.setFont(this.elevator_font);
        if(this.elevator_passangers < 10)
            g2.drawString("0"+this.elevator_passangers, 55, 35);
        else
            g2.drawString(""+this.elevator_passangers, 55, 35);
    }
    
}
