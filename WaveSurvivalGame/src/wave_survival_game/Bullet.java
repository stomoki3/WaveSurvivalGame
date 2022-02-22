package wave_survival_game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class Bullet extends JPanel {
	private int x;
	private int y;
	private double rad;
	private boolean flg;
	
	// init
	public void init(int _x, int _y, double _r) {
		x = _x;
		y = _y;
		rad = _r;
		flg = true;
	}
	
	// collision
	public void collision(Enemy _e) {
		if(!flg) return;
		
		if(5000 >= ((x - _e.getX()) * (x - _e.getX())) + ((y - _e.getY()) * (y - _e.getY()))) {
			int tmp = _e.getHp();
			tmp--;
			_e.setHp(tmp);
			
			flg = false;
		}
	}
	
	// move
	public void move() {
		if(!flg) return;
	
		x += (int)(Math.cos(Math.toRadians(rad)) * 30);
		y += (int)(Math.sin(Math.toRadians(rad)) * 30);
		
		if(x < -100 || x > Window.WIDTH + 1700 || y < -100 || y > Window.HEIGHT + 1100) flg = false;
	}
	
	// draw
	public void draw(Graphics _g) {
		if(!flg) return;
		
		super.paintComponent(_g);
		Graphics2D g2d = (Graphics2D)_g;
		AffineTransform at = g2d.getTransform();
		at.setToScale(1.0, 1.0);
		g2d.setTransform(at);
		g2d.fillOval(x, y, 30, 30);
	}
}
