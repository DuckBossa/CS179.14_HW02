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
	
	boolean[] keys;
	
	public Pong(){
		debug = true;
	
		keyString = new StringBuilder("");;
		width = 640;
		height = 280;
		win = false;
		
		p1 = new GameObject(15, 50, 5);
		p2 = new GameObject(15, 50, 5);
		ball = new GameObject(10, 10, 0);
		ball.vx = 1.0;
		ball.vy = -1.0;
		keys = new boolean[4];
		
        p1.x = 10; 			p1.y = height/2;
		p2.x = 610; 		p2.y = height/2;
        ball.x = width/2; 	ball.y = 100;
		
		root = new RootPane(width, height, keys, p1, p2, ball);
	}
	
	public static void main(String args[]){
		Pong p = new Pong();
		p.start();
		while(!p.win){
			try{
            Thread.sleep(16);
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
		//if(debug) System.out.println("Keys Pressed: " + keyString.toString());
		if(keys[0]){
			p1.vy = -p1.spd;
		}else if(keys[1]){
			p1.vy = p1.spd;
		}else{
			p1.vy = 0;
		}
		if(keys[2]){
			p2.vy = -p2.spd;
		}else if(keys[3]){
			p2.vy = p2.spd;
		}else{
			p2.vy = 0;
		}
	}
	
	void doPhysics(){

		if(ball.x - ball.w < -10){ // win condition
            System.out.println("Player 2 wins!");
            win = true;
        }
        else if(ball.x + ball.w > width + 10){ //win condition
            System.out.println("PLayer 1 wins!");
        	win = true;
        }
        else if((ball.y + ball.w*3 >= height) || (ball.y) <= 0 ){ // edges of board
        	ball.vy *=-1;
        }
        else if((ball.x - ball.w + 10) <= (p1.x + p1.w) && ((ball.y+ball.w) <= (p1.y + p1.h) && (ball.y - ball.w) >= p1.y)){ // hits surface of p1
        	ball.vx *=-1;
        }
        else if(((ball.x + ball.w) >= (p2.x)) && ((ball.y + ball.w) <= (p2.y + p2.h) && (ball.y - ball.w) >= p2.y)){ // hits surface of p2
        	ball.vx *=-1;
        }  

        if(p1.y <= 0 ){
        	p1.vy = 1;
        }
        else if(p1.y >= height - p1.h - 30){
			p1.vy = -1;
        }
        	
        if(p2.y <= 0 ){
        	p2.vy = 1;
        }
        else if(p2.y >= height - p2.h - 30){
        	p2.vy = -1;
        }
		
			p1.y += p1.vy;
			p1.x += p1.vx;
        	p2.y += p2.vy;
        	p2.x += p2.vx;
        

        ball.x += ball.vx;
        ball.y += ball.vy; 

		//if(debug)System.out.println("Player 1 : " + p1.x + " " + p1.y + "\nPlayer 2: " + p2.x + " " + p2.y +  "\nBall: " + ball.x + " " + ball.y);
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
	double spd;
	public GameObject(int w, int h, double spd){
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;
		this.w = w;
		this.h = h;
		this.spd = spd;
	}
}

class RootPane extends JFrame{
	PlayCanvas canvas;
	boolean[] keys;
	public RootPane(int w, int h, boolean[] keys, GameObject a, GameObject b, GameObject c){
		setTitle("HW02");
		setSize(w,h);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.keys = keys;	
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
				keys[0] = true;
			}
			if(x == 's'){
				keys[1] = true;
			}
			if(x == 'i'){
				keys[2] = true;
			}
			if(x == 'k'){
				keys[3] = true;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			char x = e.getKeyChar();
			if(x == 'w'){
				keys[0] = false;
			}
			if(x == 's'){
				keys[1] = false;
			}
			if(x == 'i'){
				keys[2] = false;
			}
			if(x == 'k'){
				keys[3] = false;
			}
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
