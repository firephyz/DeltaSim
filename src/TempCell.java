import java.awt.Color;
import java.awt.Graphics;

public class TempCell extends SimCell {
	
	private int x;
	private int y;
	
	private double temp;
	private double next_temp;
	
	public TempCell(int x, int y) {
		this(x, y, 0);
	}
	
	public TempCell(int x, int y, double temp) {
		this.x = x;
		this.y = y;
		this.temp = temp;
		this.next_temp = temp;
	}

	@Override
	public void processInteraction() {
		next_temp = temp - 1;
		
		if(next_temp < 0) next_temp = 0;
	}

	@Override
	public void update() {
		temp = next_temp;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(255, 255 - (int)(255 * (temp / 100)), 0));
		
		int x_draw = Display.DISPLAY_WIDTH * x / Simulation.CELL_RES_X;
		int y_draw = Display.DISPLAY_HEIGHT * y / Simulation.CELL_RES_Y;
		int x_width = Display.DISPLAY_WIDTH / Simulation.CELL_RES_X;
		int y_width = Display.DISPLAY_HEIGHT / Simulation.CELL_RES_Y;
		
		g.fillRect(x_draw, y_draw, x_width, y_width);
	}
}
