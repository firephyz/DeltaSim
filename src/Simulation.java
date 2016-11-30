import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation {
	
	private Display disp;
	
	private ArrayList<SimCell> cells;
	private ArrayList<SimCell> cells_to_add;
	
	public static int CELL_RES_X = 100;
	public static int CELL_RES_Y = 100;

	public Simulation() {
		
		disp = new Display();
		cells = new ArrayList<>();
		cells_to_add = new ArrayList<>();
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
		
		if(!cells_to_add.isEmpty()) {
			for(SimCell c : cells_to_add) {
				cells.add(c);
			}
			cells_to_add.clear();
		}
	}
	
	public void addSimCell(SimCell cell) {
		// Add to the simulation list
		cells_to_add.add(cell);
		
		// Add to the draw list
		disp.addElementToPaint(cell);
	}
}
