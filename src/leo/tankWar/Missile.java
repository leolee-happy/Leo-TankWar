package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import leo.tankWar.Tank.Direction;

public class Missile {
	public static final int SPEED = 10;
	public static final int HEIGHT = 10, WIDTH = 10;
	int x, y;
	
	private Direction dir = Direction.STOP; 
	
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();//save the original color of the graphics 
		g.setColor(Color.black);
		g.fillOval(x, y, WIDTH,HEIGHT);
		g.setColor(c);
		
		move();
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
