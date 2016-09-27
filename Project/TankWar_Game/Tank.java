import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.Random;




public class Tank {
	private int x, y;
	private static final int TANK_X_MOVE_SPEED = 5;
	private static final int TANK_Y_MOVE_SPEED = 5;
	private static final int TANK_WIDTH = 30;
	private static final int TANK_HEIGHT = 30;
	enum Direction { DIR_L, DIR_UL, DIR_DL, DIR_UP, DIR_R, DIR_UR, DIR_DR, DIR_D, DIR_STOP };
	enum Mode { FRIEND, ENEMY };
	private Direction tankMoveDir = Direction.DIR_STOP;
	private Direction barrelDir = Direction.DIR_UP;
	private boolean bLflag = false, bULflag = false, bDLflag = false, bUpflag = false, bRflag = false, bURflag = false, bDRflag = false, bDflag = false;
	private boolean isAlive = true;
	private Tank.Mode mode = null;
	private static Random random = new Random();
	private static final Direction[] DIRS = Direction.values();
	private int enemyTankDirChangeCounter = 0;
	private int previousX = x;
	private int previousY = y;
	
	public Tank(int x, int y, Tank.Mode mode){
		this.x = x;
		this.y = y;
		previousX = x;
		previousY = y;
		this.mode = mode;
	} 
	
	
	public void draw(Graphics g) {
		if ( !isAlive ) return;
		Color c = g.getColor();
		if (this.mode == Tank.Mode.FRIEND) 
			g.setColor(Color.RED);
		else {
			g.setColor(Color.GREEN);
			if (enemyTankDirChangeCounter ++ >= 20) {
				changeEnemyTankMovedir();
				enemyTankDirChangeCounter = 0;
				if (random.nextInt(30) > 25) fire();
			}
			previousX = x;
			previousY = y;
			
		}
			
		g.fillOval(x,  y, TANK_WIDTH, TANK_HEIGHT);
		g.setColor(c);
		move();
		drawBarrel(g);
	}
	

	
	public void handleKeyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		switch (keyCode){
		case KeyEvent.VK_LEFT: 
			bLflag= true;
			break;
		case KeyEvent.VK_RIGHT:
			bRflag = true;
			break;
		case KeyEvent.VK_UP:
			bUpflag = true;
			break;
		case KeyEvent.VK_DOWN:
			bDflag = true;
			break;
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		}
		setMoveDirection();
	}
	
	
	public void handleKeyRleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode){
		case KeyEvent.VK_LEFT: 
			bLflag= false;
			break;
		case KeyEvent.VK_RIGHT:
			bRflag = false;
			break;
		case KeyEvent.VK_UP:
			bUpflag = false;
			break;
		case KeyEvent.VK_DOWN:
			bDflag = false;
			break;
		}
		setMoveDirection();
	}
	
	public void move(){
		this.previousX = x;
		this.previousY = y;
		if(tankMoveDir !=  Direction.DIR_STOP) barrelDir = tankMoveDir;
		if ( x<= 10 ) x = 10;
		if ( y<= 40) y = 40;
		if ( x+Tank.TANK_WIDTH  >= TankClient.WINDOW_WIDTH) x = TankClient.WINDOW_WIDTH - Tank.TANK_WIDTH;
		if ( y+Tank.TANK_HEIGHT>= TankClient.WINDOW_HEIGHT) y= TankClient.WINDOW_HEIGHT - Tank.TANK_HEIGHT;
		switch(tankMoveDir) {
		case DIR_L:
			x -= TANK_X_MOVE_SPEED;
			break;
		case DIR_UL:
			x -= TANK_X_MOVE_SPEED;
			y -= TANK_Y_MOVE_SPEED;
			break;
		case DIR_UP:
			y -= TANK_Y_MOVE_SPEED;
			break;
		case DIR_UR:
			x += TANK_X_MOVE_SPEED;
			y -= TANK_Y_MOVE_SPEED;
			break;
		case DIR_R:
			x += TANK_X_MOVE_SPEED;
			break;
		case DIR_DR:
			x += TANK_X_MOVE_SPEED;
			y += TANK_Y_MOVE_SPEED;
			break;
		case DIR_D:
			y += TANK_Y_MOVE_SPEED;
			break;
		case DIR_DL:
			x -= TANK_X_MOVE_SPEED;
			y += TANK_Y_MOVE_SPEED;
			break;
		case DIR_STOP:
			break;
		}

		
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, 30, 30);
	}
	
	private void setMoveDirection() {
		if (bLflag && !bUpflag && !bDflag && !bRflag) tankMoveDir = Direction.DIR_L;
		else if (bLflag && bUpflag && !bDflag && !bRflag) tankMoveDir = Direction.DIR_UL;
		else if(!bLflag && bUpflag && !bRflag && !bDflag) tankMoveDir = Direction.DIR_UP;
		else if(!bLflag && bUpflag && bRflag && !bDflag) tankMoveDir = Direction.DIR_UR;
		else if(!bLflag && !bUpflag && bRflag && !bDflag) tankMoveDir = Direction.DIR_R;
		else if(!bLflag && !bUpflag && bRflag && bDflag) tankMoveDir = Direction.DIR_DR;
		else if(!bLflag && !bUpflag && !bRflag && bDflag) tankMoveDir = Direction.DIR_D;
		else if(bLflag && !bUpflag && !bRflag && bDflag) tankMoveDir = Direction.DIR_DL;
		else if(!bLflag && !bUpflag && !bRflag && !bDflag) tankMoveDir = Direction.DIR_STOP;
		else tankMoveDir = Direction.DIR_STOP;
		
	}
	
	private void drawBarrel(Graphics g) {
		switch (barrelDir) {
		case DIR_L:
			g.drawLine(x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT / 2, x, y + Tank.TANK_HEIGHT / 2);
			break;
		case DIR_UL:
			g.drawLine(x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT / 2, x, y);
			break;
		case DIR_DL:
			g.drawLine(x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT / 2, x, y + Tank.TANK_HEIGHT);
			break;
		case DIR_UP:
			g.drawLine(x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT / 2, x + Tank.TANK_WIDTH / 2, y);
			break;
		case DIR_R:
			g.drawLine(x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT / 2, x + Tank.TANK_WIDTH, y + Tank.TANK_HEIGHT / 2);
			break;
		case DIR_UR:
			g.drawLine(x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT / 2, x + Tank.TANK_WIDTH, y);
			break;
		case DIR_D:
			g.drawLine(x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT / 2, x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT);
			break;
		case DIR_DR:
			g.drawLine(x + Tank.TANK_WIDTH / 2, y + Tank.TANK_HEIGHT / 2, x + Tank.TANK_WIDTH , y + Tank.TANK_HEIGHT);
			break;
		}
	}
	
	public void fire() {
		if ( !this.isAlive() ) return;
		int x = this.x + TANK_WIDTH / 2 - Bullet.BULLET_WIDTH / 2 ;
		int y = this.y + TANK_HEIGHT / 2 - Bullet.BULLET_HEGIHT / 2 ;
		Bullet tempBullet = new Bullet(x, y, barrelDir, this.mode);
		TankClient.getInstance().fireOneBullet(tempBullet);
	}
	
	
	public void checkCollisionWithWall(Wall w) {
		if ( this.isAlive && this.getRect().intersects(w.getRect())) {
			stayOldPosition();
		}
	}
	
	public void checkCollisitionWithWalls(java.util.List<Wall> walls) {
		for (int i=0; i<walls.size(); i++) {
			checkCollisionWithWall(walls.get(i));
		}
	}
	
	private void changeEnemyTankMovedir() {
		int temp = random.nextInt(DIRS.length);
		tankMoveDir = DIRS[temp];
	}
	
	private void stayOldPosition() {
		this.x = previousX;
		this.y = previousY;
	}
	
	public void checkCollisionWithTank(Tank t) {
		if ( t.getMode() != this.mode && this.getRect().intersects(t.getRect()) && t.isAlive()) {
			t.setAlive(false);
			this.isAlive = false;
			TankClient.getInstance().handleExplode(new Explode(x, y));
		}
		if (t.getMode() == this.mode && this.getRect().intersects(t.getRect()) && t.isAlive() && this.isAlive()) {
			this.stayOldPosition();
			t.stayOldPosition();	
		}
	}
	
	public void checkCollisionWithTanks(java.util.List<Tank> tanks) {
		for (int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if ( this != t ) {
				checkCollisionWithTank(t);
			}
		}
	}
	
	public Tank.Mode getMode() {
		return this.mode;
	}
	
	
	public static void main(String[] args) {

	}

}
