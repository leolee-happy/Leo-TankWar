package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;

public class Explosion {
	int x, y;
	boolean live = true;
	
	int[] diameters = {4, 10, 20, 30, 50, 20, 10, 2};
	int step = 0;
	
	TankClient tc;
	
	public Explosion(int x, int y, TankClient tc) {
		this.x = x;
		this. y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics p) {
		if (step == diameters.length) {
			live = false;
			return;
		}
		
		Color c = p.getColor();
		p.setColor(Color.YELLOW);
		p.fillOval(x, y, diameters[step], diameters[step]);
		p.setColor(c);
		step++;
	}
	
	public boolean isDead() {
		return !live;
	}
	
}
