package leo.tankWar;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import leo.tankWar.Tank.Direction;

public class TankClient extends Frame{
	
	public static final int GAMEWIDTH = 800, GAMEHEIGHT = 600;
	
	Image image = null; 
	
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explosion> explosions = new ArrayList<Explosion>();
	Tank myTank = new Tank(0,0,this,false);
	List<Tank> tanks = new ArrayList<Tank>();
	List<Wall> walls = new ArrayList<Wall>();
	
	public TankClient() {
		super();
		tanks.add(myTank);
		for(int i = 0; i < 10; i++) {
			tanks.add(new Tank(700,80+i*50, this, true, Direction.L));
		}
		
		for(int i = 0; i < 5; i++) {
			tanks.add(new Tank(0,300+i*50, this, true, Direction.U));
		}
		
		for(int i = 0; i < 4; i++) {
			walls.add(new Wall(190,100+i*120,390, 30, this));
		}
	}
	
	public void addMissile(Missile missile) {
		this.missiles.add(missile);
	}
	
	public void addExplosion(Explosion e) {
		this.explosions.add(e);
	}
	
	
	public void update(Graphics g) {
		if (image == null) {
			image = this.createImage(GAMEWIDTH, GAMEHEIGHT);
		}
		Graphics newG = image.getGraphics();
		Color c = newG.getColor();
		newG.setColor(Color.GREEN);
		newG.fillRect(0, 0, GAMEWIDTH, GAMEHEIGHT);
		newG.setColor(Color.BLACK);
		newG.drawString("Remaining Missiles: "+missiles.size(), 10, 35);
		newG.drawString("Remaining Explosions: "+explosions.size(), 10, 55);
		newG.setColor(c);
		paint(newG);
		g.drawImage(image, 0, 0, null);
	}
	
	public Iterator<Tank> getTanks() {
		return tanks.listIterator();
	}
	
	public Iterator<Wall> getWalls() {
		return walls.listIterator();
	}

	
	public void paint(Graphics g) {

		for(int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks();
			m.collidWithWalls();
			if (m.dead()) {
				missiles.remove(m);
				i--;
			}
			else m.draw(g);
		}
		
		for(int i = 0; i < explosions.size(); i++) {
			Explosion m = explosions.get(i);
			if (m.isDead()) {
				explosions.remove(m);
				i--;
			}
			else m.draw(g);
		}
		
		for(int i = 0; i < tanks.size(); i++) {
			Tank m = tanks.get(i);
			m.collidWithWalls();
			m.collidWithTanks();
			if (!m.isLive()) {
				tanks.remove(m);
				i--;
			}
			else m.draw(g);
		}
		
		for(int i = 0; i < walls.size(); i++) {
			Wall m = walls.get(i);
			m.draw(g);
		}
		
	}
	
	
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class KeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
	}

	public void launchFrame() {
		this.setLocation(400,300);
		this.setSize(GAMEWIDTH,GAMEHEIGHT);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.setTitle("Leo-Tank War");
		this.setResizable(false);
		this.setBackground(Color.green);
		this.addKeyListener(new KeyListener());
		setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	

}
