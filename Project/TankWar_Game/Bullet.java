import java.awt.Graphics;
import java.awt.event.KeyEvent;

import java.awt.*;

public class Bullet {
	private static final int BULLET_X__MOVE_SPEED = 10;
	private static final int BULLET_Y__MOVE_SPEED = 10;
	public static final int BULLET_WIDTH = 8;
	public static final int BULLET_HEGIHT = 8;
	private int x, y;
	private boolean live = true;	
	Tank.Mode mode = null;
	public boolean isaLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}


	private Tank.Direction bulletMoveDir;
	
	public Bullet(int x, int y, Tank.Direction dir, Tank.Mode mode) {
		this.x = x;
		this.y = y;
		this.bulletMoveDir = dir;
		this.mode = mode;
	}
	
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		if (this.mode == Tank.Mode.FRIEND) g.setColor(Color.RED);
		else g.setColor(Color.GREEN);
		g.fillOval(x, y, BULLET_WIDTH, BULLET_HEGIHT);
		g.setColor(c);
		if (this.x <= 0 || y <= 0 || x >= TankClient.WINDOW_WIDTH || y >= TankClient.WINDOW_HEIGHT) setLive(false);
		move();
	}
	
	public void move(){
		switch(bulletMoveDir) {
		case DIR_L:
			x -= BULLET_X__MOVE_SPEED;
			break;
		case DIR_UL:
			x -= BULLET_X__MOVE_SPEED;
			y -= BULLET_Y__MOVE_SPEED;
			break;
		case DIR_UP:
			y -= BULLET_Y__MOVE_SPEED;
			break;
		case DIR_UR:
			x += BULLET_X__MOVE_SPEED;
			y -= BULLET_Y__MOVE_SPEED;
			break;
		case DIR_R:
			x += BULLET_X__MOVE_SPEED;
			break;
		case DIR_DR:
			x += BULLET_X__MOVE_SPEED;
			y += BULLET_Y__MOVE_SPEED;
			break;
		case DIR_D:
			y += BULLET_Y__MOVE_SPEED;
			break;
		case DIR_DL:
			x -= BULLET_Y__MOVE_SPEED;
			y += BULLET_Y__MOVE_SPEED;
			break;
		case DIR_STOP:
			break;
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, 10, 10);
	}
	
	public boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isAlive() && t.getMode() != this.mode) {
			this.live = false;
			t.setAlive(false);
			Explode e = new Explode(x, y);
			TankClient.getInstance().handleExplode(e);
			return true;
		}
		return false;
	}
	
	public void hitTanks(java.util.List<Tank> tanks) {
		for (int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			hitTank(t);
		}
	}
	
	public void hitWall(Wall w) {
		if (this.isaLive() && this.getRect().intersects(w.getRect())) {
			this.live = false;
		}
	}
	
	public void hitWalls(java.util.List<Wall> walls) {
		for (int i=0; i<walls.size(); i++) {
			this.hitWall(walls.get(i));
		}
	}
	
	public static void main(String[] args) {

	}

}
