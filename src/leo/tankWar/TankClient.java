package leo.tankWar;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame{
	
	public static final int WIDTH = 800, HEIGHT = 600;
	
	Tank myTank = new Tank(50,50);
	Image image = null; 

	public void update(Graphics g) {
		if (image == null) {
			image = this.createImage(WIDTH, HEIGHT);
		}
		Graphics newG = image.getGraphics();
		Color c = newG.getColor();
		newG.setColor(Color.GREEN);
		newG.fillRect(0, 0, WIDTH, HEIGHT);
		newG.setColor(c);
		paint(newG);
		g.drawImage(image, 0, 0, null);
	}	
	
	public void paint(Graphics g) {
		myTank.draw(g);
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
		this.setSize(800,600);
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
