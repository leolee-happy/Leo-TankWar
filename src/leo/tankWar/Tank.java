package leo.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Toolkit.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Tank {
	public static final int SPEED = 3; 
	public static final int WIDTH = 50, HEIGHT = 50;
	public static final int SCORE = 5;
	
	public static Random random = new Random();
	
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public static Image[] tankImages;
	public static Map<String,Image> imgs = new HashMap<String,Image>();
	public int lifeLimit;
	
	static {
		tankImages = new Image[] {
				toolkit.getImage(Tank.class.getClassLoader().getResource("images/TankD.gif")),
				toolkit.getImage(Tank.class.getClassLoader().getResource("images/TankL.gif")),
				toolkit.getImage(Tank.class.getClassLoader().getResource("images/TankU.gif")),
				toolkit.getImage(Tank.class.getClassLoader().getResource("images/TankR.gif")),
				toolkit.getImage(Tank.class.getClassLoader().getResource("images/TankLD.gif")),
				toolkit.getImage(Tank.class.getClassLoader().getResource("images/TankLU.gif")),
				toolkit.getImage(Tank.class.getClassLoader().getResource("images/TankRU.gif")),
				toolkit.getImage(Tank.class.getClassLoader().getResource("images/TankRD.gif"))
		};
		
		imgs.put("D", tankImages[0]);
		imgs.put("L", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("R", tankImages[3]);
		imgs.put("LD", tankImages[4]);
		imgs.put("LU", tankImages[5]);
		imgs.put("RU", tankImages[6]);
		imgs.put("RD", tankImages[7]);
		
	}
	
	boolean isEnemy;
	boolean live = true;
	int step = 30+random.nextInt(30);
	int lastX, lastY;
	int life;
	BloodBar bar;
	
	
	public int getScore() {
		if (isEnemy)
		return SCORE;
		else return -200;
	}
	
	public int getLife() {
		return life;
	}

	public void gotFired() {
		this.life -= 20 ;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public boolean isEnemy() {
		return isEnemy;
	}

	int x, y;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	TankClient tc;
	
	boolean bL = false, bU = false, bR = false, bD = false;
		
	Direction dir = Direction.STOP;
	String directionString = "D";
	
	Direction fireDir = Direction.R;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
		this.lastX = x;
		this.lastY = y;
	}
	
	public Tank(int x, int y, TankClient tc, boolean isEnemy) {
		this(x,y);
		this.tc = tc;
		this.isEnemy = isEnemy;
		if (isEnemy) lifeLimit = 20;
		else lifeLimit = 100;
		life = lifeLimit;
		bar = new BloodBar(x, y, WIDTH, HEIGHT, tc);
	}
	
	public Tank(int x, int y, TankClient tc, boolean isEnemy, Direction dir) {
		this(x,y);
		this.tc = tc;
		this.isEnemy = isEnemy;
		this.dir = dir;
		if (isEnemy) lifeLimit = 20;
		else lifeLimit = 100;
		life = lifeLimit;
		bar = new BloodBar(x, y, WIDTH, HEIGHT, tc);
	}
	
	
	public void draw(Graphics g) {
		Color c = g.getColor();//save the original color of the graphics 
		g.drawImage(imgs.get(directionString), x, y,null);
		g.setColor(c);
		if (!isEnemy) {
			bar.updatePos(x, y);
			bar.draw(g);
		}
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
		if (dir != Direction.STOP) {
			directionString = dir.toString();
		}
		

	}
	
	public void fire() {
		if (!live) return;
		int width = imgs.get(directionString).getWidth(null);
		int height = imgs.get(directionString).getHeight(null);
		Missile missile = new Missile(x+width/2-Missile.WIDTH/2,y+height/2-Missile.HEIGHT/2,fireDir,tc,isEnemy);
		tc.addMissile(missile);
		//return missile;
	}
	
	public void superFire() {
		if (!live) return;
		int width = imgs.get(directionString).getWidth(null);
		int height = imgs.get(directionString).getHeight(null);
		for (int i = 0; i < 8; i++) {
			Direction tDir = Direction.values()[i];
			Missile missile = new Missile(x+width/2-Missile.WIDTH/2,y+height/2-Missile.HEIGHT/2,tDir,tc,isEnemy);
			tc.addMissile(missile);
		}

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
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		locateDirection();
	}
	
	void move() {
		lastX = x;
		lastY = y;
		
		if (isEnemy){
			if (step <= 0) {
				Direction[] directions = Direction.values();
				dir = directions[random.nextInt(directions.length-1)];
				if (dir != Direction.STOP) {
					directionString = dir.toString();
				}
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
		boundCheck();
	}
	
	public void boundCheck() {
		int width = imgs.get(directionString).getWidth(null);
		int height = imgs.get(directionString).getHeight(null);
		if (x < 0) 
			x = 0;
		if (y < 23) 
			y = 23;
		if (x > TankClient.GAMEWIDTH - width) 
			x = TankClient.GAMEWIDTH-width;
		if (y > TankClient.GAMEHEIGHT - height) 
			y = TankClient.GAMEHEIGHT - height;
	}
	
	Rectangle getRectangle() {
		return new Rectangle(x+7,y+7,WIDTH,HEIGHT);
	}
	
	boolean collidWithWall(Wall w) {
		if (live&&getRectangle().intersects(w.getRect())) {
			//this.dir = Direction.STOP;
			this.x = lastX;
			this.y = lastY;
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
	
	boolean collidWithTank(Tank w) {
		if (w.getX()< x || w.getY() < y)
		if (live&&getRectangle().intersects(w.getRectangle())) {
			this.dir = Direction.STOP;
			w.dir = Direction.STOP;
			this.lastPosition();
			w.lastPosition();
			return true;
		}
		return false;
	}
	
	public boolean collidWithTanks() {
		if (!live) return false;
		Iterator<Tank> it = tc.getTanks();
		while (it.hasNext()) {
			Tank tempTank = it.next();
			if (this.collidWithTank(tempTank)) {
				return true;
			}
		}
		return false;
	}
	
	void locateDirection() {
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
	
	public void lastPosition(){
		this.x = lastX;
		this.y = lastY;
	}
	
	class BloodBar {
		int x, y, width, height;
		TankClient tc;
		Color co = Color.red;
		
		public BloodBar(int x, int y, int width, int height, TankClient tc) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.tc = tc;
		}
		
		public void setColor(Color c) {
			this.co = c;
		}
		
		
		public void updatePos(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void draw (Graphics g) {
			Color c = g.getColor();
			g.setColor(co);
			g.drawRect(x, y-10, width, 10);
			g.fillRect(x, y-10, width*life/lifeLimit, 10);
			g.setColor(c);
		}
		
	}
}
