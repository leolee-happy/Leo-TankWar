package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import leo.tankWar.Tank.Direction;

public class Missile {
	public static final int SPEED = 6;
	public static final int HEIGHT = 10, WIDTH = 10;
	int x, y;
	TankClient tc;
	
	boolean live = true;
	
	private Direction dir = Direction.STOP; 
	
	public Missile(int x, int y, Direction dir, TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();//save the original color of the graphics.lalalla 
		g.setColor(Color.black);
		g.fillOval(x, y, WIDTH,HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	public boolean dead() {
		if (x < 0 || y <0 || x > TankClient.GAMEWIDTH || y > TankClient.GAMEHEIGHT || !live)
			return true;
		return false;
	}
	
	Rectangle getRectangle() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	boolean hitTank(Tank t) {
		if (t.isLive()&&getRectangle().intersects(t.getRectangle())) {
			t.setLive(false);
			live = false;
			tc.addExplosion(new Explosion(x,y,tc));
			return true;
		}
		return false;
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
		}
	}	
	
}
