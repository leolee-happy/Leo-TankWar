package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Explosion {
	int x, y;
	boolean live = true;
	
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	static BufferedImage img;
	

	public static BufferedImage[] eImages = new BufferedImage[11];
	
	static {
		try {
		    for (int i = 0; i < 11; i++)
			eImages[i] = ImageIO.read(Explosion.class.getClassLoader().getResource("images/"+i+".gif"));
		} catch (IOException e) {
		}

		
	}
	
	int step = 0;
	
	TankClient tc;
	
	public Explosion(int x, int y, TankClient tc) {
		this.x = x;
		this. y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics p) {
		if (step == eImages.length) {
			live = false;
			return;
		}
		
		Color c = p.getColor();
		p.setColor(Color.YELLOW);
		p.drawImage(eImages[step], x, y, null);
		p.setColor(c);
		step++;
	}
	
	public boolean isDead() {
		return !live;
	}
	
}
