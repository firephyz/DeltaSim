
public class DeltaSim {

	public DeltaSim() {
		Simulation sim = new Simulation();
		
		for(int y = 0; y < Simulation.CELL_RES_Y; ++y) {
			for(int x = 0; x < Simulation.CELL_RES_X; ++x) {
				sim.addSimCell(new TempCell(x, y, y));
			}
		}
		
		sim.start();
	}
	
	public static void main(String[] args) {
		new DeltaSim();
	}
}
