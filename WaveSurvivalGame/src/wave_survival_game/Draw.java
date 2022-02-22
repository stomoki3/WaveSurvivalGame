package wave_survival_game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Draw extends JPanel {	
	// player set
	private Player player = null;
	public void setPlayer(Player _p) { player = _p; }
	
	static Image hp_bullet_bar_img = Toolkit.getDefaultToolkit().getImage("images/hp_bullet_bar.png");
	
	// bullet set
	private List<Bullet> bullet_list = new ArrayList<Bullet>();
	public void setBullet(Bullet _b) { bullet_list.add(_b); }
	
	private int current_bullet_number;
	public void setCBN(int _cbn) { current_bullet_number = _cbn; }
	
	// wall
	private List<Wall> wall_list = new ArrayList<Wall>();
	public void setWall(Wall _w) { wall_list.add(_w); }
	
	// enemy set
	private List<Enemy> enemy_list = new ArrayList<Enemy>();
	public void setEnemy(Enemy _e) { enemy_list.add(_e); }
	
	// stage
	static Image stage_img = Toolkit.getDefaultToolkit().getImage("images/stage.png");
	
	// loading
	private int loading_count = 0;
	
	// wave
	static Image wave_img = Toolkit.getDefaultToolkit().getImage("images/wave.png");
	
	public void paintComponent(Graphics _g) {
		super.paintComponent(_g);
		Graphics2D g2d = (Graphics2D)_g;
		
		// stage
		g2d.drawImage(stage_img, 0, 0, this);
		g2d.drawImage(stage_img, 700, 0, this);
		
		// タイトル表示
		// wave
		if(Window.wave <= 0) {
			Font font = new Font("MS Pゴシック", Font.BOLD, 80);
			g2d.setFont(font);
			g2d.drawString("Wave Survival Game", 180, 300);
			g2d.drawString("Enter To Start", 310, 400);
			if(Window.wave == -1) {
				if(loading_count <= 30) {
					g2d.drawString("Loading .", 350, 500);
				} else if(loading_count >= 31 && loading_count <= 80) {
					g2d.drawString("Loading . .", 350, 500);
				} else if(loading_count >= 81) {
					g2d.drawString("Loading . . .", 350, 500);
				}
				
				loading_count++;
				if(loading_count >= 100) {
					Window.wave = 1;
				}
			}
		} else if(Window.wave >= 1) {
			// wave
			g2d.drawImage(wave_img, 30, 30, this);
			Font font = new Font("MS Pゴシック", Font.BOLD, 30);
			g2d.setFont(font);
			g2d.drawString("WAVE : " + Window.wave, 60, 70);
			
			// player
			player.draw(_g);
			// hp
			g2d.drawImage(hp_bullet_bar_img, 80, Window.HEIGHT + 450, this);
			font = new Font("MS Pゴシック", Font.BOLD, 50);
			g2d.setFont(font);
			g2d.drawString("HP : " + player.getHp(), 130, Window.HEIGHT + 510);
			// bullet
			g2d.drawImage(hp_bullet_bar_img, Window.WIDTH + 850, Window.HEIGHT + 450, this);
			g2d.setFont(font);
			g2d.drawString((30 - current_bullet_number) + " / 30", Window.WIDTH + 850 + 30, Window.HEIGHT + 510);
			
			// bullet
			for(Bullet b:bullet_list) {
				b.draw(_g);
			}
			
			// wall
			for(Wall w:wall_list) {
				w.draw(_g);
			}
			
			// enemy
			for(Enemy e:enemy_list) {
				e.draw(_g);
			}
		}
	}
}