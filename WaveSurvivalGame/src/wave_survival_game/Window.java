package wave_survival_game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Window extends JFrame implements Runnable, KeyListener {
	// thread
	Thread thread = null;
	
	// window width height
	static final int WIDTH = 1200;
	static final int HEIGHT = 700;
	
	// draw set
	private Draw draw = null;
	public void setDraw(Draw _d) { draw = _d; }
	
	// back set
	private Back back = null;
	public void setBack(Back _b) { back = _b; }
	
	// enemy set
	private List<Enemy> enemy_list = new ArrayList<Enemy>();
	public void setEnemy(Enemy _e) { enemy_list.add(_e); }
	
	// player
	private Player player = null;
	
	// bullet
	private List<Bullet> bullet_list = new ArrayList<Bullet>();
	static final int BULLET_MAX = 30;
	private int current_bullet_number;
	private boolean bullet_reloading_flg;
	private int reloading_time;
	
	// wall
	private List<Wall> wall_list = new ArrayList<Wall>();
	static final int WALL_MAX = 3;
	private int current_wall_number;
	
	// wave
	static int wave;
	
	// constructor
	public Window() {
		super("Title");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Window.WIDTH, Window.HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		addKeyListener(this);
	}
	
	// thread start
	synchronized void startLoop() {
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
			
			// player
			player = new Player();
			player.init();
			
			// bullet
			for(int i = 0; i < Window.BULLET_MAX; i++) {
				bullet_list.add(new Bullet());
				
				// to draw
				draw.setBullet(bullet_list.get(i));
			}
			current_bullet_number = 0;
			bullet_reloading_flg = false;
			reloading_time = 0;
			
			// wall
			for(int i = 0; i < Window.WALL_MAX; i++) {
				wall_list.add(new Wall());
				
				current_wall_number = 0;
				
				// to draw
				draw.setWall(wall_list.get(i));
				
				// to back
				back.setWall(wall_list.get(i));
			}
			
			// to draw
			draw.setPlayer(player);
			
			// to back
			back.setPlayer(player);
			
			// wave
			wave = 0;
		}
	}
	
	// thread stop
	synchronized void stopLoop() {
		if(thread != null) {
			thread = null;
		}
	}
	
	// main loop
	public void run() {
		System.out.println("window thread...");
		
		while(thread != null) {
			try {
				Thread.sleep(25);
				
				if(wave >= 1) {
					// player
					player.move();
					
					
					// bullet
					for(Bullet b:bullet_list) {
						b.move();
						
						for(Enemy e:enemy_list) {
							b.collision(e);
						}
					}
					if(bullet_reloading_flg) {
						reloading_time++;
						
						if(reloading_time >= 30) {
							bullet_reloading_flg = false;
							reloading_time = 0;
							current_bullet_number = 0;
						}
					}
				}
				
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// key
	public void keyTyped(KeyEvent _e) {
		
	}
	
	public void keyPressed(KeyEvent _e) {
		switch(_e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setFFlg(true);
			break;
			
		case KeyEvent.VK_S:
			player.setBFlg(true);
			break;
			
		case KeyEvent.VK_A:
			player.setLFlg(true);
			break;
			
		case KeyEvent.VK_D:
			player.setRFlg(true);
			break;
			
		case KeyEvent.VK_SPACE:
			// bullet
			// reloading
			if(bullet_reloading_flg) break;
			
			bullet_list.get(current_bullet_number).init(player.getX() + 64, player.getY() + 64, player.getRad());
			
			current_bullet_number++;
			if(current_bullet_number >= 30) {
				bullet_reloading_flg = true;
			}
			// to draw
			draw.setCBN(current_bullet_number);
			break;
			
		case KeyEvent.VK_B:
			if(current_wall_number >= Window.WALL_MAX) break;
			
			wall_list.get(current_wall_number).init(player.getX() + 64, player.getY() + 64);
			current_wall_number++;
			break;
			
		case KeyEvent.VK_ENTER:
			if(wave == 0) {
				wave = -1;
			}
			break;
		}
	}
	
	public void keyReleased(KeyEvent _e) {
		switch(_e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setFFlg(false);
			break;
			
		case KeyEvent.VK_S:
			player.setBFlg(false);
			break;
			
		case KeyEvent.VK_A:
			player.setLFlg(false);
			break;
			
		case KeyEvent.VK_D:
			player.setRFlg(false);
			break;
		}
	}
}
