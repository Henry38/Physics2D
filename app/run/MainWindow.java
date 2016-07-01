package run;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import physics2D.data.World2D;
import viewer2D.graphic.Viewer2D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	private Viewer2D viewer;
	
	/** Constructeur */
	public MainWindow() {
		super("World2D");
		
		World2D world = new World2D();
		viewer = new Viewer2D(world, 640, 480);
		
		Thread t = new Thread(world);
		t.setDaemon(true);
		t.start();
		
		panneau = new JPanel(new BorderLayout());
		panneau.setLayout(new BorderLayout());
		panneau.add(viewer, BorderLayout.CENTER);
		
		setContentPane(panneau);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
