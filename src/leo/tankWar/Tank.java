package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {
	public static final int SPEED = 3; 
	public static final int WIDTH = 30, HEIGHT = 30;
	public static Random random = new Random();
	
	private boolean isEnemy;
	private boolean live = true;
	private int step = 20+random.nextInt(30);
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public boolean isEnemy() {
		return isEnemy;
	}

	private int x, y;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

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
	
	public Tank(int x, int y, TankClient tc, boolean isEnemy) {
		this(x,y);
		this.tc = tc;
		this.isEnemy = isEnemy;
	}
	
	public Tank(int x, int y, TankClient tc, boolean isEnemy, Direction dir) {
		this(x,y);
		this.tc = tc;
		this.isEnemy = isEnemy;
		this.dir = dir;
	}
	
	
	public void draw(Graphics g) {
		Color c = g.getColor();//save the original color of the graphics 
		if (isEnemy)g.setColor(Color.blue);
		else g.setColor(Color.red);
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
		}
		locateDirection();
		

	}
	
	private Missile fire() {
		if (!live) return null;
		Missile missile = new Missile(x+WIDTH/2-Missile.WIDTH/2,y+HEIGHT/2-Missile.HEIGHT/2,fireDir,tc,isEnemy);
		tc.addMissile(missile);
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
			break;
		case KeyEvent.VK_S:
			fire();
			break;
		}
		locateDirection();
	}
	
	void move() {
		if (isEnemy){
			if (step <= 0) {
				Direction[] directions = Direction.values();
				dir = directions[random.nextInt(directions.length-1)];
				step = 20+random.nextInt(30);
			}
			step--;
			if (random.nextInt(30)> 28) fire();
		}
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
		if (dir != Direction.STOP) {
			fireDir = dir;
		}
		if (x < 0) 
			x = 0;
		if (y < 23) 
			y = 23;
		if (x > TankClient.GAMEWIDTH - WIDTH) 
			x = TankClient.GAMEWIDTH-WIDTH;
		if (y > TankClient.GAMEHEIGHT - HEIGHT) 
			y = TankClient.GAMEHEIGHT-HEIGHT;
	}
	
	Rectangle getRectangle() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
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
