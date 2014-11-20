package cs124_lab01;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class hw02 implements KeyListener{
	JFrame frame;
	int length;
	int width;
	int p1x, p1y, p2x, p2y;
        int rad,cx,cy;
	int rw, rh;
        boolean win;
	public hw02(){
		length = 640;
		width = 480;
		rw = 15;
		rh = 50;
                rad = 10;
                cx = 100;
                cy = 100;
                p1x = 10; p1y = 0; 
		p2x = 610; p2y = 0;
                win = false;
	}
	
	public static void main(String args[]){
		hw02 wat = new hw02();
		wat.setupGraphics();
                
	}
	
	void setupGraphics(){
		frame = new mainFrame();
                frame.addKeyListener(this);
		frame.setVisible(true);
                frame.setFocusable(true);
		frame.add(new mainCanvas());
	}

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
                p1y -= 10;
                frame.repaint();
                System.out.println("p1 up");
                break;
            case 's':
                p1y += 10;
                frame.repaint();
                System.out.println("p1 down");
                break;
            case 'o':
                p2y += 10;
                frame.repaint();
                System.out.println("p2 up");
                break;
            case 'l':
                p2y -= 10;
                frame.repaint();
                System.out.println("p2 down");
                break;
        };
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
}