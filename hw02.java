//package cs124_lab01;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class hw02{
	private JFrame frame;
    private mainCanvas canvas;
	private int length;
	private int width;
	private int p1x, p1y, p2x, p2y;
    private int rad,cx,cy;
	private int rw, rh;
    private double vx, vy;
    private boolean win;
    private String playerMovement;
    private Thread clock;
    private Rectangle p1,p2;  
	public hw02(){
        playerMovement = "";
		length = 640;
		width = 280;
		rw = 15;
		rh = 50;
        rad = 10;
        cx = length/2;
        cy = 100;
        p1x = 10; p1y = 0; 
    	p2x = 610; p2y = 0;
        win = false;
        vx = 5.0;
        vy = 5.0;
        clock = new Thread(new runner());
	}
	
	public static void main(String args[]){
		hw02 wat = new hw02();
        wat.setupGraphics();
        wat.letTheGamesBegin();
	}
	
	public void setupGraphics(){
		frame = new mainFrame();
        frame.addKeyListener(new kl());
		frame.setVisible(true);
        frame.setFocusable(true);
        canvas = new mainCanvas();
		frame.add(canvas);
	}

    public void movementBall(){ 
        // collision detection for the ball | we can also check in this method kung may nanalo na ba o wala.
        if(cx - rad < -10){
            System.out.println("Player 2 wins!");
            win = true;
        }
        else if(cx + rad > length + 10){
            win = true;
            System.out.println("PLayer 1 wins!");
        }
        else if((cy + rad*3>= width) || (cy) <= 0 ){
            vy *=-1;
        }
        else if( (cx - rad + 10) <= (p1x + rw) && ((cy+rad) <= (p1y + rh) && (cy - rad) >= p1y) ){ // this method not working! D:
            vx *= -1;
        }
        else if(((cx + rad) >= (p2x)) && ((cy + rad) <= (p2y + rh) && (cy - rad) >= p2y)){
            vx *= -1;
        }
        cy -= vy;
        cx -= vx;
    }

    public void letTheGamesBegin(){
        clock.start();
    }

	class mainFrame extends JFrame{
		public mainFrame(){
			setTitle("HW02");
			setSize(length,width);
            setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}

	class mainCanvas extends Canvas{
		public mainCanvas(){
			setBackground(Color.WHITE);
			setSize(length,width);
		}
		
		public void paint(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
					   g2.fillRect(p1x,p1y,rw,rh);
					   g2.fillRect(p2x,p2y,rw,rh);
                       g2.setColor((Color.RED));
                       g2.fillOval(cx, cy, rad, rad);
		}
	}

    class runner implements Runnable{
        public void run(){
            while(!win){
            try{
            Thread.sleep(80);
            }
            catch(InterruptedException ie){
            ie.printStackTrace();
            }
            movementBall();
            canvas.repaint();
            }
        }
    }

    class kl implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
            switch(e.getKeyChar()){
                case 'w':
                    if(p1y <= 0){}
                    else
                    p1y -= 10;                        
                    canvas.repaint();
                    break;
                case 's':
                    if(p1y >= width - 70){}
                    else
                    p1y += 10;
                    canvas.repaint();
                    break;
                case 'o':
                    if(p2y <= 0){}
                    else
                    p2y -= 10;
                    canvas.repaint();
                    break;
                case 'l':
                    if(p2y >= width - 70){}
                    else
                    p2y += 10;
                    canvas.repaint();
                    break;
            };
        }
    }


}