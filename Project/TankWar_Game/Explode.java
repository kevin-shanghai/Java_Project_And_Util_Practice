import java.awt.*;


public class Explode {
	
	private int x, y;
	private boolean isAlive = true;
	private int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};
	private int step = 0;
	 	
	public boolean isaLive() {
		return isAlive;
	}
	
	private TankClient tc = TankClient.getInstance();
	Explode(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	public void draw(Graphics g) {
		if(step == diameter.length) {
			isAlive = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step ++;
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
