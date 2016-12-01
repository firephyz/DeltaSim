import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation {
	
	private Display disp;
	
	private ArrayList<SimCell> cells;
	
	public static int CELL_RES_X = 100;
	public static int CELL_RES_Y = 100;
	
	public static double DELTA_T = 0.01;
	public static double DELTA_X = 0.1;
	public static double DELTA_Y = 0.1;

	public Simulation() {
		
		SimCell.sim = this;
		disp = new Display();
		cells = new ArrayList<>();
	}
	
	public void start() {
		// Start the simulation thread
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				simLoop();
			}
		}, 0, 100);
	}
	
	public void simLoop() {
		
		for(SimCell c : cells) {
			c.processInteraction();
		}
		
		for(SimCell c : cells) {
			c.update();
		}
	}
	
	public void addSimCell(SimCell cell) {
		// Add to the simulation list
		cells.add(cell);
		
		// Add to the draw list
		disp.addElementToPaint(cell);
	}
	
	public SimCell getCell(int x, int y) {
		
		if(x < 0 || y < 0 || x >= Simulation.CELL_RES_X || y >= Simulation.CELL_RES_Y) {
			return null;
		}
		
		return cells.get(Simulation.CELL_RES_X * y + x);
	}
}
