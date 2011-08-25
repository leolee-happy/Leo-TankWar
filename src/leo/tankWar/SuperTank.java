package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import leo.tankWar.Tank.BloodBar;

public class SuperTank extends Tank{

	public static final int WIDTH = 50, HEIGHT = 50;
	public static final int SCORE = 10;
	
	public SuperTank(int x, int y, TankClient tc, boolean isEnemy, Direction dir) {
		super(x, y, tc, isEnemy, dir);
		this.life = 100;
		bar = new BloodBar(x, y, WIDTH, HEIGHT, tc);
		// TODO Auto-generated constructor stub
	}
	
	public int getScore() {
		return SCORE;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();//save the original color of the graphics 
		g.setColor(Color.pink);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		bar.setColor(Color.pink);
		bar.updatePos(x, y);
		bar.draw(g);
		move();
	}
	
	public void fire() {
		if (!live) return;
		Missile missile;
		for (int i = 0; i < 8; i++) {
			Direction tDir = Direction.values()[i];
			missile = new Missile(x+WIDTH/2-Missile.WIDTH/2,y+HEIGHT/2-Missile.HEIGHT/2,tDir,tc,isEnemy);
			tc.addMissile(missile);
		}

	}
	
	public void boundCheck() {
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
	

}
