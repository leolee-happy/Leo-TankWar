package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	public static final int SPEED = 5; 
	public static final int WIDTH = 30, HEIGHT = 30;
	int x, y;
	TankClient tc;
	
	boolean bL = false, bU = false, bR = false, bD = false; 
	enum Direction {
		L, R, U, D, LU, LD, RU, RD, STOP;
	}
	
	private Direction dir = Direction.STOP; 
	private Direction fireDir = Direction.R;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y, TankClient tc) {
		this(x,y);
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();//save the original color of the graphics 
		g.setColor(Color.red);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_CONTROL:
			tc.setMissile(fire());
		}
		locateDirection();
	}
	
	private Missile fire() {
		Missile missile = new Missile(x+WIDTH/2-Missile.WIDTH/2,y+HEIGHT/2-Missile.HEIGHT/2,dir);
		return missile;
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
		}
		locateDirection();
	}
	
	void move() {
		switch(dir) {
		case L:
			x -= SPEED;
			break;
		case LU:
			x -= SPEED;
			y -= SPEED;
			break;
		case U:
			y -= SPEED;
			break;
		case RU:
			x += SPEED;
			y -= SPEED;
			break;
		case R:
			x += SPEED;
			break;
		case RD:
			x += SPEED;
			y += SPEED;
			break;
		case D:
			y += SPEED;
			break;
		case LD:
			x -= SPEED;
			y += SPEED;
			break;
		case STOP:
			break;
		}
	}
	
	private void locateDirection() {
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}
}
