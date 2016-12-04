import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation {
	
	private Display disp;
	
	private ArrayList<SimCell> cells;
	
	public static int CELL_RES_X = 500;
	public static int CELL_RES_Y = 500;
	
	public static double DELTA_T = 0.1;
	public static double DELTA_X = 1;
	public static double DELTA_Y = 1;
	
	private boolean should_sim;

	public Simulation() {
		
		SimCell.sim = this;
		disp = new Display(this);
		cells = new ArrayList<>();
		should_sim = false;
	}
	
	public void start() {
		// Start the simulation thread
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				simLoop();
			}
		}, 0, 20);
	}
	
	public void simLoop() {
		
		double new_max = Double.MIN_VALUE;
		double new_min = Double.MAX_VALUE;
		
		if(should_sim) {
			for(SimCell c : cells) {
				c.processInteraction();
			}
			
			for(SimCell c : cells) {
				c.update();
			}
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
	
	public void toggleShouldSim() {
		should_sim = !should_sim;
	}
}
