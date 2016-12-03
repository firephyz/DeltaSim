import java.awt.Color;
import java.awt.Graphics;

public class TempCell extends SimCell {
	
	private int x;
	private int y;
	
	private double old_temp;
	private double temp;
	private double next_temp;
	
	private double change;
	private double next_change;
	
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
		
//		if(left_cell == null) left_cell = (TempCell)sim.getCell(Simulation.CELL_RES_X - 1, y);
//		if(right_cell == null) right_cell = (TempCell)sim.getCell(0, y);
//		if(upper_cell == null) upper_cell = (TempCell)sim.getCell(x, Simulation.CELL_RES_Y - 1);
//		if(lower_cell == null) lower_cell = (TempCell)sim.getCell(x, 0);
		
		double left_cell_temp = left_cell == null ? 0 : left_cell.getTemp();
		double right_cell_temp = right_cell == null ? 0 : right_cell.getTemp();
		double upper_cell_temp = upper_cell == null ? 0 : upper_cell.getTemp();
		double lower_cell_temp = lower_cell == null ? 0 : lower_cell.getTemp();

		next_temp = 2 * temp - old_temp + Simulation.DELTA_T * Simulation.DELTA_T * (((left_cell_temp + right_cell_temp - 2 * temp) / (Simulation.CELL_RES_X * Simulation.CELL_RES_X)) 
					   								   			  + ((upper_cell_temp + lower_cell_temp - 2 * temp) / (Simulation.CELL_RES_Y * Simulation.CELL_RES_Y)));
	}

	@Override
	public void update() {
		old_temp = temp;
		temp = next_temp;
		change = next_change;
	}

	@Override
	public void paint(Graphics g) {
		
		int red = 0;
		int green = 0;
		
		if(temp < 0) red = (int)(255 * -temp / 100);
		if(temp > 0) green = (int)(255 * temp / 100);
		
		if(red > 255) red = 255;
		if(green > 255) green = 255;
		
		int blue = 0;
		if(temp == 0.125) blue = 255;
		
		g.setColor(new Color(red, green, blue));
		
		int x_draw = Display.DISPLAY_WIDTH * x / Simulation.CELL_RES_X;
		int y_draw = Display.DISPLAY_HEIGHT * y / Simulation.CELL_RES_Y;
		int x_width = Display.DISPLAY_WIDTH / Simulation.CELL_RES_X;
		int y_width = Display.DISPLAY_HEIGHT / Simulation.CELL_RES_Y;
		
		g.fillRect(x_draw, y_draw, x_width, y_width);
	}
	
	public double getTemp() {return this.temp;}
	public void setTemp(double temp) {this.temp = temp;}
	public static TempCell getTempCell(int x, int y) {return (TempCell)sim.getCell(x, y);}
	
	public static void addDrop(int x, int y, int mode) {
		
		double mag = 1000 * mode;
		
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x, y), mag));
//		
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x - 1, y - 1), mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x - 1, y    ), mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x - 1, y + 1), mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x    , y - 1), mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x    , y + 1), mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x + 1, y - 1), mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x + 1, y    ), mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x + 1, y + 1), mag));
//		
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x - 2, y)    , mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x + 2, y)    , mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x    , y - 2), mag));
//		getTempCell(x, y).setTemp(spreadFunc(calcDist(x, y, x    , y + 2), mag));
		
		for(int i = -2; i <= 2; ++i) {
			for(int j = -2; j <= 2; ++j) {
				getTempCell(x + i, y + j).setTemp(spreadFunc(calcDist(x, y, x + i, y + j), mag));
			}
		}
	}
	
	private static double spreadFunc(double dist, double mag) {
		return mag * Math.exp(-(dist * dist));
	}
	
	private static double calcDist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}
