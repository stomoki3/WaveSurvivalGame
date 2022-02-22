package wave_survival_game;

import java.util.ArrayList;
import java.util.List;

public class Back implements Runnable {
	// thread
	Thread thread = null;
	
	// enemy list
	private List<Enemy> enemy_list = new ArrayList<Enemy>();
	static final int ENEMY_MAX = 5;
	
	private int respown_pos;
	
	// window set
	private Window window = null;
	public void setWindow(Window _w) { window = _w; }
	
	// player set
	private Player player = null;
	public void setPlayer(Player _p) { player = _p; }
	
	// wall
	private List<Wall> wall_list = new ArrayList<Wall>();
	public void setWall(Wall _w) { wall_list.add(_w); }
	
	// draw set
	private Draw draw = null;
	public void setDraw(Draw _d) { draw = _d; }
	
	// wave
	private boolean[] wave_enemy = new boolean[Back.ENEMY_MAX];
	private int wave_tmp;
	
	// thread start
	synchronized void startLoop() {
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
			
			// wave
			wave_tmp = 1;
			
			// enemy
			respown_pos = 0;
			for(int i = 0; i < ENEMY_MAX; i++) {
				enemy_list.add(new Enemy());
				
				enemy_list.get(i).init(respown_pos);
				respown_pos += 200;
				if(respown_pos >= 1000) respown_pos = 0;
				
				// to draw
				draw.setEnemy(enemy_list.get(i));
				
				// to window
				window.setEnemy(enemy_list.get(i));
			}
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
		System.out.println("back thread...");
		
		while(thread != null) {
			try {
				Thread.sleep(25);
				
				if(Window.wave >= 1) {
					// enemy
					for(int i = 0; i < ENEMY_MAX; i++) {
						enemy_list.get(i).move(player, wall_list);
						enemy_list.get(i).collision(player, wall_list);
						
						if(enemy_list.get(i).getHp() <= 0) wave_enemy[i] = true;
					}
					
					int tmp = 0;
					for(int i = 0; i < ENEMY_MAX; i++) {
						if(wave_enemy[i]) {
							tmp++;
						}
					}
					if(tmp >= 5) {
						if(wave_tmp > 1) {
							for(int i = 0; i < ENEMY_MAX; i++) {
								enemy_list.get(i).init(respown_pos);
								respown_pos += 200;
								if(respown_pos >= 1000) respown_pos = 0;
								
								wave_enemy[i] = false;
							}
							
							wave_tmp--;
						} else if(wave_tmp == 1) {
							for(int i = 0; i < ENEMY_MAX; i++) {
								enemy_list.get(i).init(respown_pos);
								respown_pos += 200;
								if(respown_pos >= 1000) respown_pos = 0;
								
								wave_enemy[i] = false;
							}
							
							Window.wave++;
							wave_tmp = Window.wave;
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
