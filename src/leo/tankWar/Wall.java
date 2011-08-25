package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Wall {
	int x, y, width, height;
	TankClient tc;
	static Image img = java.awt.Toolkit.getDefaultToolkit().getImage(Wall.class.getClassLoader().getResource("images/bWall.jpg"));
	public Wall(int x, int y, int width, int height, TankClient tc) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tc = tc;
	}
	
	public void draw (Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.green);
		g.drawImage(img, x, y, width, height,null);
		g.setColor(c);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,width,height);
	}
	
	
	
}
