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
    private Thread clock;
        
	public hw02(){
		length = 640;
		width = 280;
		rw = 15;
		rh = 50;
        rad = 10;
        cx = 100;
        cy = 100;
        p1x = 10; p1y = 0; 
    	p2x = 610; p2y = 0;
        win = false;
        vx = 2.0;
        vy = 0;
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


    public void movementBall(){ // collision detection for the ball
        cx += vx;
        cy += vy;
        System.out.println(cx);
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
            Thread.sleep(50);
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