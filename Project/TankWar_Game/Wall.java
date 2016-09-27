import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Wall {
	private int x;
	private int y;
	private int w;
	private int h;
	private Color c = Color.black;
	public Wall(int x, int y, int w, int h, Color c) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.c = c;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(this.c);
		g.fillRect(x, y, w, h);
		g.setColor(this.c);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	
	public static void main(String[] args) {
	}

}
