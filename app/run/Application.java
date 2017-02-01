package run;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class Application {
	
	/** Lancement de l'application 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException */
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				final MainWindow fen = new MainWindow();
				fen.addKeyListener(new KeyListener() {
					public void keyTyped(KeyEvent ev) {}
					
					public void keyReleased(KeyEvent ev) {}
					
					public void keyPressed(KeyEvent ev) {
						if (ev.getKeyCode() == KeyEvent.VK_ESCAPE) {
							fen.dispose();
							System.exit(0);
						}
					}
				});
				fen.setFocusable(true);
				fen.setVisible(true);
			}
		});
		
	}
}
