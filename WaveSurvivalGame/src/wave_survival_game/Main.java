package wave_survival_game;

public class Main {
	public static void main(String[] args) {
		Window window = new Window();
		Back back = new Back();
		Draw draw = new Draw();
		
		// to window
		window.setDraw(draw);
		// to window
		window.setBack(back);
		
		// to back
		back.setDraw(draw);
		// to back
		back.setWindow(window);
		
		// thread start
		window.startLoop();
		back.startLoop();
		
		window.add(draw);
		window.setVisible(true);
	}
}