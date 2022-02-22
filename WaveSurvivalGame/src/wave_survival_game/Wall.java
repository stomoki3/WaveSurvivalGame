package wave_survival_game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Wall extends JPanel {
	private int x;
	private int y;
	private int hp = 0;
	
	static Image img = Toolkit.getDefaultToolkit().getImage("images/wall.png");
	static Image img_1 = Toolkit.getDefaultToolkit().getImage("images/wall_1.png");
	static Image img_2 = Toolkit.getDefaultToolkit().getImage("images/wall_2.png");
	
	// init
	public void init(int _x, int _y) {
		x = _x;
		y = _y;
		hp = 3;
	}
	
	// draw
	public void draw(Graphics _g) {
		if(hp <= 0) return;
		
		super.paintComponent(_g);
		Graphics2D g2d = (Graphics2D)_g;
		
		switch(hp) {
		case 3:
			g2d.drawImage(img, x, y, this);
			break;
			
		case 2:
			g2d.drawImage(img_1, x, y, this);
			break;
			
		case 1:
			g2d.drawImage(img_2, x, y, this);
			break;
		}
	}
	
	// getter
	public int getX() { return x; }
	public int getY() { return y; }
	public int getHp() { return hp; }
	
	// setter
	public void setHp(int _h) { hp = _h; }
}
