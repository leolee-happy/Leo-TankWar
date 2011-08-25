package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import leo.tankWar.Direction;

public class Missile {
	public static final int SPEED = 6;
	public static final int HEIGHT = 10, WIDTH = 10;
	int x, y;
	TankClient tc;
	
	boolean live = true;
	boolean isEnemy;
	
	private Direction dir = Direction.STOP; 
	
	public Missile(int x, int y, Direction dir, TankClient tc, boolean isEnemy) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc = tc;
		this.isEnemy = isEnemy;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();//save the original color of the graphics.lalalla 
		if (!isEnemy)g.setColor(Color.black);
		else g.setColor(Color.MAGENTA);
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
		if (live&&t.isLive()&&getRectangle().intersects(t.getRectangle())) {
			t.gotFired();
			
			if (t.getLife() <= 0) {
				t.setLive(false);
				System.out.print(t.getScore());
				tc.addScore(t.getScore());
			}
			
			live = false;
			tc.addExplosion(new Explosion(x,y,tc));
			return true;
		}
		return false;
	}
	
	public boolean isEnemy() {
		return isEnemy;
	}

	boolean hitTanks() {
		if (!live) return false;
		Iterator<Tank> it = tc.getTanks();
		while (it.hasNext()) {
			Tank tempTank = it.next();
			if (tempTank.isEnemy() != isEnemy) {
				if (this.hitTank(tempTank)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean collidWithWall(Wall w) {
		if (live && getRectangle().intersects(w.getRect())) {
			live = false;
			return true;
		}
		return false;
	}
	
	public boolean collidWithWalls() {
		if (!live) return false;
		Iterator<Wall> it = tc.getWalls();
		while (it.hasNext()) {
			Wall tempWall = it.next();
			if (this.collidWithWall(tempWall)) {
				return true;
			}
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
