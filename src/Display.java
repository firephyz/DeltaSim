import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Display extends JFrame {
	
	private JFrame myThis;
	private MyPanel canvas;
	
	private BufferedImage buffer;
	private ArrayList<ElementToPaint> things_to_draw;
	
	private Simulation sim;
	
	public static int DISPLAY_WIDTH = 500;
	public static int DISPLAY_HEIGHT = 500;
	public static int FRAMERATE = 30;
	
	private int mouse_click_x;
	private int mouse_click_y;
	
	public Display(Simulation sim) {
		
		mouse_click_x = -1;
		mouse_click_y = -1;
		myThis = this;
		this.sim = sim;
		
		buffer = new BufferedImage(DISPLAY_WIDTH, DISPLAY_HEIGHT, BufferedImage.TYPE_INT_RGB);
		things_to_draw = new ArrayList<>();
		
		this.setTitle("DeltaSim");
		this.setUndecorated(true);
		this.setSize(DISPLAY_WIDTH + 2, DISPLAY_HEIGHT + 2);
		this.getContentPane().setBackground(Color.WHITE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		// Setup the panel on which we will draw
		canvas = new MyPanel();
		canvas.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		canvas.setLocation(1, 1);
		canvas.setVisible(true);
		this.add(canvas);
		
		// Start the framerate thread
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				
				if(canvas.getLocation().getX() == 0) {
					canvas.setLocation(1, 1);
				}
				
				updateBuffer(buffer.getGraphics());
				
				canvas.paintComponent(canvas.getGraphics());
			}
			
		}, 0, 1000 / FRAMERATE);
		
		// Listens for the enter key to quit the program
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
				else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					sim.toggleShouldSim();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		// Gets the initial mouse position when you start to move the window
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent m) {}

			@Override
			public void mouseEntered(MouseEvent m) {}

			@Override
			public void mouseExited(MouseEvent m) {}

			@Override
			public void mousePressed(MouseEvent m) {
				
				if(m.getButton() == MouseEvent.BUTTON1 && m.isControlDown()) {
					mouse_click_x = m.getX();
					mouse_click_y = m.getY();
				}
				else if (m.getButton() == MouseEvent.BUTTON1) {
					TempCell.getTempCell(m.getX() * Simulation.CELL_RES_X / Display.DISPLAY_WIDTH ,
										 m.getY() * Simulation.CELL_RES_Y / Display.DISPLAY_HEIGHT).setTemp(100);
				}
				else if(m.getButton() == MouseEvent.BUTTON3) {
					System.out.println(TempCell.getTempCell(m.getX() * Simulation.CELL_RES_X / Display.DISPLAY_WIDTH ,
										 					m.getY() * Simulation.CELL_RES_Y / Display.DISPLAY_HEIGHT).getTemp());
				}
			}

			@Override
			public void mouseReleased(MouseEvent m) {}
			
		});
		
		// Moves the window with the mouse when control is pressed
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent m) {

				if(SwingUtilities.isLeftMouseButton(m)) {
					if(m.isControlDown()) {
						int delta_x = m.getX() - mouse_click_x;
						int delta_y = m.getY() - mouse_click_y;
						
						if(delta_x != 0 || delta_y != 0) {
							
							int new_x = myThis.getLocation().x + delta_x;
							int new_y = myThis.getLocation().y + delta_y;
								
							myThis.setLocation(new_x, new_y);
						}
					}
					else {
						TempCell.getTempCell(m.getX() * Simulation.CELL_RES_X / Display.DISPLAY_WIDTH ,
								 			 m.getY() * Simulation.CELL_RES_Y / Display.DISPLAY_HEIGHT).setTemp(100);
					}
				}
			}

			@Override
			public void mouseMoved(MouseEvent m) {}
			
		});
	}

	public void updateBuffer(Graphics g) {
		
		synchronized(things_to_draw) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
			for(ElementToPaint e : things_to_draw) {
				e.paint(g);
			}
		}
	}
	
	public void addElementToPaint(ElementToPaint e) {
		
		synchronized(things_to_draw) {
			things_to_draw.add(e);
		}
	}
	
	class MyPanel extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(buffer, 0, 0, null);
		}
	}
}
