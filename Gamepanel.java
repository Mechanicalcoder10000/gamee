package snakeGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import javax.swing.JPanel;
import java.util.Random;

public class Gamepanel extends JPanel implements ActionListener  {
	
	static final int SCREEN_WIDTH=600;
	static final int SCREEN_HEIGHT=600;
	static final int UNIT_SIZE=25;
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY=75;
	final int[] x=new int[GAME_UNITS];
	final int[] y=new int[GAME_UNITS];
	int bodyparts=6;
	int applesEaten;
	int applesX;
	int applesY;
	Timer timer;
	Random random;
	boolean running=false;
	char direction='R';
	
	
	
	
	Gamepanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new Mykeyadapter());
		startGame();
		
		
	}
	public void startGame() {
		newapple();
		running=true;
		timer=new Timer(DELAY,this);
		timer.start();
		
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {
		for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
			g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
			
			
		}
		g.setColor(Color.red);
		g.fillOval(applesX, applesY,UNIT_SIZE, UNIT_SIZE);
		
		for(int i=bodyparts;i>0;i--) {
			if(i==0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i],UNIT_SIZE,UNIT_SIZE);
			}
			else {
				g.setColor(new Color(45,180,0));
				g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
				g.fillRect(x[i], y[i],UNIT_SIZE,UNIT_SIZE);
				
			}
		 }
		g.setColor(Color.red);
		g.setFont(new Font("INK FREE",Font.BOLD,40));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Score:"+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score:"+applesEaten))/2, g.getFont().getSize());
		
		}
		else {
			gameover(g);
			
		}
	}
	public void newapple() {
		applesX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		applesY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
		
	}
	public void move() {
		for(int i=bodyparts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
			
			
		}
		switch(direction) {
		case 'U':
		y[0]=y[0]-UNIT_SIZE;
		break;
		case 'D':
			y[0]=y[0]+UNIT_SIZE;
			break;
		case 'L':
			x[0]=x[0]-UNIT_SIZE;
			break;
		case 'R':
			x[0]=x[0]+UNIT_SIZE;
			break;
		}
		
		
	}
	public void checkapple() {
		if(x[0]==applesX && y[0]==applesY) {
			bodyparts++;
			applesEaten++;
			newapple();
		}
		
	}
	public void checkcollisions() {
		for(int i=bodyparts;i>0;i--) {
			if((x[0]==x[i]) && (y[0]==y[i])) {
				running=false;
			}
		}
		if(x[0]<0) {
			running=false;
		}
		if(x[0]>SCREEN_WIDTH) {
		running=false;
		}
		
		if(y[0]<0) {
			running=false;
		}
		if(y[0]>SCREEN_HEIGHT) {
			running=false;
		}
		if(!running) {
			timer.stop();
		}
		
	}
	public void gameover(Graphics g) {
		
		g.setColor(Color.red);
		g.setFont(new Font("INK FREE",Font.BOLD,40));
		FontMetrics metrics1=getFontMetrics(g.getFont());
		g.drawString("Score:"+applesEaten, (SCREEN_WIDTH-metrics1.stringWidth("Score:"+applesEaten))/2, g.getFont().getSize());
		
		g.setColor(Color.red);
		g.setFont(new Font("INK FREE",Font.BOLD,60));
		FontMetrics metrics2=getFontMetrics(g.getFont());
		g.drawString("GAME OVERR", (SCREEN_WIDTH-metrics2.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
		
		
	}
	public class Mykeyadapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_A:
				if(direction!='R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_D:
				if(direction!='L') {
					direction='R';
				}
				break;
			case KeyEvent.VK_W:
				if(direction!='D') {
					direction='U';
				}
				break;
			case KeyEvent.VK_Z:
				if(direction!='U') {
					direction='D';
				}
				break;
			}
		
			
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkapple();
			checkcollisions();
			
		}
		repaint();
		
	}

}
