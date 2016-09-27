import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.*;

//
public class TankClient extends Frame {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	private Tank myTank = new Tank(50, 50, Tank.Mode.FRIEND);
	Image offScreenImage = null;
	
	private LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	private LinkedList<Explode> explodes = new LinkedList<Explode>();
	private LinkedList<Tank> enemyTanks = new LinkedList<Tank>();
	private LinkedList<Wall> walls = new LinkedList<Wall>();
	
	private static TankClient tankClient = new TankClient();
	private int gameExitCounter = 0;
	
	public TankClient() {
		for (int i=0; i<10; i++) {
			enemyTanks.add(new Tank(100 + i*40, 100 + i*50, Tank.Mode.ENEMY));
		}
		
		walls.add(new Wall(500, 100, 300, 20, Color.yellow));
		walls.add(new Wall(200, 300, 20, 400, Color.magenta));
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawString("missiles count: " + bullets.size(), 10, 50);
		g.drawString("explodes count: " + explodes.size(), 10, 70);
		g.drawString("enemy count: " + enemyTanks.size(), 10, 90);
		if (myTank.isAlive())
			myTank.draw(g);
		else {
//			bullets.clear();
//			explodes.clear();
//			enemyTanks.clear();
//			g.drawString("****************GAME OVER****************", 100, 100);
//			if (gameExitCounter++ == 100)	System.exit(ABORT);
//			return;
		}
		for (int i=0; i<enemyTanks.size(); i++) {
			Tank enemyTank = enemyTanks.get(i);
			if (enemyTank.isAlive()) {
				enemyTank.checkCollisionWithTank(myTank);
				enemyTank.checkCollisionWithTanks(enemyTanks);
				enemyTank.checkCollisitionWithWalls(walls);
			}
			
			if (enemyTank.isAlive()) {
				enemyTank.draw(g);
			}
			else {
				enemyTanks.remove(i);
			}	
		}
		
		for (int i=0; i<walls.size(); i++) {
			walls.get(i).draw(g);
		}
		
		for (int i=0; i<bullets.size(); i++) {
			Bullet currentBullet = bullets.get(i);
			if (currentBullet.isaLive()) {
				currentBullet.hitWalls(walls);
				currentBullet.hitTanks(enemyTanks);
				bullets.get(i).draw(g);
			}
			else {
				bullets.remove();
			}
		}

		for(int j=0; j<explodes.size(); j++) {
				Explode currentExplode = explodes.get(j);
				if (currentExplode.isaLive()) currentExplode.draw(g);
				else {
						explodes.remove();
					}
			}
		
	}

	public static TankClient getInstance() {
		return tankClient;
	}
	
	public void fireOneBullet(Bullet b) {
		bullets.add(b);
	}
	
	public void handleExplode(Explode explode) {
		explodes.add(explode);
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLUE);
		gOffScreen.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private void lanchFrame(){
		this.setTitle("Tank var");
		this.setLocation(400, 300);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setVisible(true);
		this.setBackground(Color.DARK_GRAY);
		new Thread(new PaintActor()).start();
		this.addKeyListener(new KeyMonitor());
		
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankClient tc = TankClient.getInstance();
		tc.lanchFrame();	
	}
	
	private class PaintActor implements Runnable{
		public void run()
		{
			while (true){
				repaint();
				try {
					Thread.sleep(50);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			myTank.handleKeyPressed(e);	
		}
		
		public void keyReleased(KeyEvent e) {
			myTank.handleKeyRleased(e);
		}
	}
}

