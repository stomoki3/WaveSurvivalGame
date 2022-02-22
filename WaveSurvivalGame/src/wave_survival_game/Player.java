package wave_survival_game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class Player extends JPanel implements Character {
	private int x;
	private int y;
	private int hp;
	private double rad;
	static Image img = Toolkit.getDefaultToolkit().getImage("images/player.png");
	
	private boolean f_flg, b_flg, l_flg, r_flg;
	
	// init
	public void init() {
		x = Window.WIDTH;
		y = Window.HEIGHT;
		hp = 5;
		rad = 270.0;
		
		f_flg = false;
		b_flg = false;
		l_flg = false;
		r_flg = false;
	}

	// move
	public void move() {
		if(hp <= 0) return;
		
		// forward move
		if(f_flg) {
			int x_tmp = (int)(Math.cos(Math.toRadians(rad)) * 10);
			int y_tmp = (int)(Math.sin(Math.toRadians(rad)) * 10);
			x += x_tmp;
			y += y_tmp;
		}
		
		// back move
		if(b_flg) {
			int x_tmp = (int)(Math.cos(Math.toRadians(rad)) * 10);
			int y_tmp = (int)(Math.sin(Math.toRadians(rad)) * 10);
			x -= x_tmp;
			y -= y_tmp;
		}
		
		// left move
		if(l_flg) {
			rad -= 5.0;
			if(rad < -360) rad = 0;
		}
		
		// right move
		if(r_flg) {
			rad += 5.0;
			if(rad > 360) rad = 0;
		}
	}

	// draw
	public void draw(Graphics _g) {
		if(hp <= 0) return;
		
		super.paintComponent(_g);
		Graphics2D g2d = (Graphics2D)_g;
		AffineTransform at = g2d.getTransform();
		
		// player
		at.setToRotation(Math.toRadians(rad + 90.0), x + 64, y + 64);
		g2d.setTransform(at);
		g2d.drawImage(Player.img, x, y, this);
		at.setToRotation(0, 0, 0);
		g2d.setTransform(at);
	}

	// getter
	public int getX() { return x; }
	public int getY() { return y; }
	public int getHp() { return hp; }
	
	public double getRad() { return rad; }

	// setter
	public void setX(int _x) { x = _x; }
	public void setY(int _y) { y = _y; }
	public void setHp(int _h) { hp = _h; }
	
	public void setRad(double _r) { rad = _r; }
	public void setFFlg(boolean _f) { f_flg = _f; }
	public void setBFlg(boolean _b) { b_flg = _b; }
	public void setLFlg(boolean _l) { l_flg = _l; }
	public void setRFlg(boolean _r) { r_flg = _r; }
}
