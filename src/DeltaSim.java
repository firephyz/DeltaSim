
public class DeltaSim {

	public DeltaSim() {
		Simulation sim = new Simulation();
		
		for(int y = 0; y < Simulation.CELL_RES_Y; ++y) {
			for(int x = 0; x < Simulation.CELL_RES_X; ++x) {
				double temp = 0.0;
				
				if((x == Simulation.CELL_RES_X / 2 && y == 3 * Simulation.CELL_RES_Y / 4) ||
				   (x == Simulation.CELL_RES_X / 2 && y == 1 * Simulation.CELL_RES_Y / 4)) {
					temp = 6000000;
				}
				
				sim.addSimCell(new TempCell(x, y, temp));
			}
		}
		
		sim.start();
	}
	
	public static void main(String[] args) {
		new DeltaSim();
	}
}
