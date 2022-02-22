package wave_survival_game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class Enemy extends JPanel implements Character {
	private int x;
	private int y;
	private int hp;
	private double rad, rad_sub;
	static Image img = Toolkit.getDefaultToolkit().getImage("images/enemy.png");
	
	// player or wall までの距離
	private int distance;
	// 次の攻撃までの
	private int attack_interval;
	// 攻撃中
	private boolean attack_flg;
	// wall を標的にしている時
	private boolean wall_attack_flg;
	// player or wall どちらを標的としているか
	private int player_or_wall;
	
	// init
	public void init(int _pos) {
		int tmp = new Random().nextInt(4);
		switch(tmp) {
		case 0:
			x = -50 + _pos;
			y = -50;
			break;
			
		case 1:
			x = (Window.WIDTH + 1000) + 50;
			y = -50 + _pos;
			break;
			
		case 2:
			x = (Window.WIDTH + 1000) + 50 - _pos;
			y = (Window.HEIGHT + 800) + 50;
			break;
			
		case 3:
			x = -50;
			y = (Window.HEIGHT + 800) + 50 - _pos;
			break;
		}
		
		hp = 3;
		rad = 270.0;
		rad_sub = 270.0;
		
		wall_attack_flg = false;
		attack_flg = false;
		player_or_wall = -1;
	}
	
	// collision vs player and wall
	public void collision(Player _p, List<Wall> _wl) {
		int x_tmp = _p.getX() - x;
		int y_tmp = _p.getY() - y;
		int vs_player = (x_tmp * x_tmp) + (y_tmp * y_tmp);
		
		if(!wall_attack_flg) {
			distance = vs_player;
			player_or_wall = -1;
			
			for(int i = 0; i < Window.WALL_MAX; i++) {
				if(_wl.get(i).getHp() <= 0) continue;
				
				int vs_wall = ((_wl.get(i).getX() - x) * (_wl.get(i).getX() - x)) + ((_wl.get(i).getY() - y) * (_wl.get(i).getY() - y));
				if(vs_player < vs_wall) {
					distance = vs_player;
					player_or_wall = -1;
				} else {
					distance = vs_wall;
					player_or_wall = i;
					wall_attack_flg = true;
				}
			}
		} else {
			distance = ((_wl.get(player_or_wall).getX() - x) * (_wl.get(player_or_wall).getX() - x)) + 
					((_wl.get(player_or_wall).getY() - y) * (_wl.get(player_or_wall).getY() - y));
		}

		if(5000 >= distance) {
			attack_interval--;
			
			if(attack_interval <= 0 && player_or_wall < 0) {
				int tmp = _p.getHp();
				tmp--;
				_p.setHp(tmp);
				
				attack_flg = true;
				attack_interval = 30;
			} else if(attack_interval <= 0 && player_or_wall >= 0) {
				int tmp = _wl.get(player_or_wall).getHp();
				tmp--;
				_wl.get(player_or_wall).setHp(tmp);
				if(_wl.get(player_or_wall).getHp() <= 0) { wall_attack_flg = false; }
				
				attack_flg = true;
				attack_interval = 30;
			}
		} else {
			attack_interval = 10;
			attack_flg = false;
		}
	}

	// move
	public void move(Player _p, List<Wall> _wl) {
		if(hp <= 0) {
			x = -100;
			y = -100;
			
			return;
		}
		
		if(player_or_wall < 0) {
			rad = Math.atan2(_p.getY() - y, _p.getX() - x);
		} else {
			rad = Math.atan2(_wl.get(player_or_wall).getY() - y, _wl.get(player_or_wall).getX() - x);
		}
		
		rad_sub = Math.toDegrees(rad);
		if(rad_sub > 0) rad_sub -= 5.0;
		if(rad_sub < 0) rad_sub += 5.0;
		
		if(!attack_flg) {
			x += Math.cos(rad) * 3;
			y += Math.sin(rad) * 3;
		}
		
	}

	// draw
	public void draw(Graphics _g) {
		if(hp <= 0) return;
		
		super.paintComponent(_g);
		Graphics2D g2d = (Graphics2D)_g;
		AffineTransform at = g2d.getTransform();
		
		// enemy
		at.setToRotation(Math.toRadians(rad_sub + 90.0), x + 64, y + 64);
		g2d.setTransform(at);
		g2d.drawImage(Enemy.img, x, y, this);
		at.setToRotation(0, 0, 0);
		g2d.setTransform(at);
	}

	// getter
	public int getX() { return x; }
	public int getY() { return y; }
	public int getHp() { return hp; }
	
	// setter
	public void setX(int _x) { x = _x; }
	public void setY(int _y) { y = _y; }
	public void setHp(int _hp) { hp = _hp; }
}
