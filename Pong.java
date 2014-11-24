import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Pong{
	StringBuilder keyString;
	int width, height;
	boolean win, debug;
	
	RootPane root;
	PlayCanvas canvas;
	GameObject p1, p2, ball;
	
	public Pong(){
		debug = true;
	
		keyString = new StringBuilder("");;
		width = 640;
		height = 280;
		win = false;
		
		p1 = new GameObject(15, 50);
		p2 = new GameObject(15, 50);
		ball = new GameObject(10, 10);
		ball.vx = 5.0;
		ball.vy = -3.0;
        p1.x = 10; 			p1.y = height/2;
		p2.x = 610; 		p2.y = height/2;
        ball.x = width/2; 	ball.y = 100;
		
		root = new RootPane(width, height, keyString, p1, p2, ball);
	}
	
	public static void main(String args[]){
		Pong p = new Pong();
		p.start();
		while(!p.win){
			try{
            Thread.sleep(80);
			p.loop();
            }
            catch(InterruptedException ie){
            ie.printStackTrace();
            }
		}
	}
	
	void start(){
		root.setup();
	}
	
	void processKeys(){
		
		if(debug) System.out.println("Keys Pressed: " + keyString.toString());
		if(keyString.indexOf("w") != -1){
			p1.vy = -1;
		}else if(keyString.indexOf("s") != -1){
			p1.vy = 1;
		}else{
			p1.vy = 0;
		}
		if(keyString.indexOf("i") != -1){
			p2.vy = -1;
		}else if(keyString.indexOf("k") != -1){
			p2.vy = 1;
		}else{
			p2.vy = 0;
		}
		keyString.setLength(0);
		keyString.trimToSize();
	}
	
	void doPhysics(){

		if(ball.x - ball.w < -10){
            System.out.println("Player 2 wins!");
            win = true;
        }
        else if(ball.x + ball.w > width + 10){
        	win = true;
            System.out.println("PLayer 1 wins!");
        }
        else if((ball.y + ball.w*3>= height) || (ball.y) <= 0 ){
        	ball.vy *=-1;
        }
        else if((ball.x - ball.w + 10) <= (p1.x + p1.w) && ((ball.y+ball.w) <= (p1.y + p1.h) && (ball.y - ball.w) >= p1.y)){
        	ball.vx *=-1;
        }
        else if(((ball.x + ball.w) >= (p2.x)) && ((ball.y + ball.w) <= (p2.y + p2.h) && (ball.y - ball.w) >= p2.y)){
        	ball.vx *=-1;
        }

        /*
		p1.x += p1.vx;
		p1.y += p1.vy;
		p2.x += p2.vx;
		p2.y += p2.vy;
		ball.x += ball.vx;
		ball.y += ball.vy;
		*/

		ball.x += ball.vx;
		ball.y += ball.vy;
		if(debug)System.out.println("Player 1 : " + p1.x + " " + p1.y + "\nPlayer 2: " + p2.x + " " + p2.y +  "\nBall: " + ball.x + " " + ball.y);
	}
	
	void updateGraphics(){
		root.update();
	}
	
	void loop(){
		processKeys();
		doPhysics();
		updateGraphics();
	}
	
}

class GameObject{
	int x, y;
	double vx, vy;
	int w, h;
	public GameObject(int w, int h){
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;
		this.w = w;
		this.h = h;
	}
}

class RootPane extends JFrame{
	PlayCanvas canvas;
	StringBuilder keyString;
	public RootPane(int w, int h, StringBuilder ky, GameObject a, GameObject b, GameObject c){
		setTitle("HW02");
		setSize(w,h);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		keyString = ky;	
		canvas = new PlayCanvas(w, h, a, b, c);
	}
	
	void setup(){
		canvas.addKeyListener(new PongListener());
		setVisible(true);
        setFocusable(true);
		add(canvas);
		canvas.createBufferStrategy(2);
	}
	
	void update(){
		canvas.repaint();
	}
	
	class PongListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
			char x = e.getKeyChar();
			if(x == 'w'){
				keyString.append('w');
			}
			if(x == 's'){
				keyString.append('s');
			}
			if(x == 'i'){
				keyString.append('i');
			}
			if(x == 'k'){
				keyString.append('k');
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
}

class PlayCanvas extends Canvas{
	int width, height;
	GameObject p1, p2, ball;
	
	public PlayCanvas(int w, int h, GameObject a, GameObject b, GameObject c){
		width = w;
		height = h;
		p1 = a;
		p2 = b;
		ball = c;
		setBackground(Color.WHITE);
		setSize(width,height);
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0,0,getWidth(),getHeight());
		g2.setPaint(new Color(200,200,200));
		g2.fillRect(p1.x,p1.y,p1.w,p1.h);
		g2.fillRect(p2.x,p2.y,p2.w,p2.h);
		g2.setColor((Color.RED));
		g2.fillOval(ball.x, ball.y, ball.w, ball.h);
	}
	
	public void update(Graphics g){
		BufferStrategy bf = getBufferStrategy();
		Graphics bg = bf.getDrawGraphics();
		paint(bg);
		bg.dispose();
		bf.show();
	}
}
