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
		
		TempCell left_cell = (TempCell)sim.getCell(x - 1, y);
		TempCell right_cell = (TempCell)sim.getCell(x + 1, y);
		TempCell upper_cell = (TempCell)sim.getCell(x, y - 1);
		TempCell lower_cell = (TempCell)sim.getCell(x, y + 1);
		
		if(left_cell == null) left_cell = (TempCell)sim.getCell(Simulation.CELL_RES_X - 1, y);
		if(right_cell == null) right_cell = (TempCell)sim.getCell(0, y);
		if(upper_cell == null) upper_cell = (TempCell)sim.getCell(x, Simulation.CELL_RES_Y - 1);
		if(lower_cell == null) lower_cell = (TempCell)sim.getCell(x, 0);
		
		double left_cell_temp = left_cell.getTemp();
		double right_cell_temp = right_cell.getTemp();
		double upper_cell_temp = upper_cell.getTemp();
		double lower_cell_temp = lower_cell.getTemp();

		next_temp = ((left_cell_temp + right_cell_temp - 2 * temp) * (Simulation.DELTA_T * Simulation.DELTA_Y * Simulation.DELTA_Y / Simulation.DELTA_X / Simulation.DELTA_X))
					+ ((upper_cell_temp + lower_cell_temp - 2 * temp) * (Simulation.DELTA_T * Simulation.DELTA_X * Simulation.DELTA_X / Simulation.DELTA_Y / Simulation.DELTA_Y)) 
					+ (Simulation.DELTA_X * Simulation.DELTA_X * Simulation.DELTA_Y * Simulation.DELTA_Y * temp);
	}

	@Override
	public void update() {
		temp = next_temp;
	}

	@Override
	public void paint(Graphics g) {
		
		int color = (int)(255 * temp / 100);
		
		if(color > 255) color = 255;
		if(color < 0) color = 0;
		
		g.setColor(new Color(color, color, color));
		
		int x_draw = Display.DISPLAY_WIDTH * x / Simulation.CELL_RES_X;
		int y_draw = Display.DISPLAY_HEIGHT * y / Simulation.CELL_RES_Y;
		int x_width = Display.DISPLAY_WIDTH / Simulation.CELL_RES_X;
		int y_width = Display.DISPLAY_HEIGHT / Simulation.CELL_RES_Y;
		
		g.fillRect(x_draw, y_draw, x_width, y_width);
	}
	
	public double getTemp() {return this.temp;}
	public void setTemp(double temp) {this.temp = temp;}
	public static TempCell getTempCell(int x, int y) {return (TempCell)sim.getCell(x, y);}
}
