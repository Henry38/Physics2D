package physics2D.data;

import java.awt.BasicStroke;
import java.awt.Color;

import physics2D.body.Body2D;
import physics2D.body.Rectangle;
import viewer2D.data.WorldModel;
import viewer2D.geometry.Shape2D;
import math2D.Vecteur2D;

public class World2D extends WorldModel implements Runnable {
	
	// rendering at 30fps
	private long framerate = 1000 / 30;
    // time the frame began. Edit the second value (30) to change the prefered FPS (i.e. change to 50 for 50 fps)
    private long frameStart;
    // number of frames counted this second
    private long frameCount = 0;
    // time elapsed during one frame
    private long elapsedTime;
    // accumulates elapsed time over multiple frames
    private long totalElapsedTime = 0;
    // the actual calculated framerate reported
    private long reportedFramerate = 0;
    
	private Vecteur2D gravity;
	
	/** Constructeur */
	public World2D(Vecteur2D gravity) {
		this.gravity = new Vecteur2D(gravity);
		createBody();
	}
	
	/** Constucteur */
	public World2D() {
		this(new Vecteur2D(0, -9.81));
	}
	
	private void createBody() {
		//// Rectangle
		Rectangle dBody1 = new Rectangle(4, 2, 10.0);
		//dBody1.addForce(new Force(new Point2D(0,-1), new Vecteur2D(10, 0)));
		//System.out.println("Rectangle");
		//System.out.println("Density = 10kg/m² <=> masse : " + dBody1.getMasse() + "kg");
		
		Rectangle sBody = new Rectangle(10, 2, 0);
		sBody.translate(0, -4);
		sBody.setColor(Color.gray);
		sBody.setStroke(new BasicStroke(3));
		
		add(dBody1);
		add(sBody);
	}
	
	public long getCurrentFPS() {
		return reportedFramerate; 
	}
	
	/** Retourne le vecteur gravite */
	public Vecteur2D getGravity() {
		return gravity;
	}
	
	/** Met a jour la position des objets du monde */
	public void updatePosition(double dt) {
		
		for (Shape2D shape : getListShape()) {
			Body2D body = (Body2D) shape;
			body.clearForce();
			body.computeForce(getGravity());
		}
		
		for (Shape2D shape : getListShape()) {
			Body2D body = (Body2D) shape;
			body.updatePosition(dt);
		}
	}
	
	@Override
	public void run() {
		while (true) {
			frameStart = System.currentTimeMillis();
			/////////////////
			// Data performed
			/////////////////
			updatePosition(framerate / 1000.0);
			//updatePosition(1/30.0);
			
            fireNeedRefresh();
			/////////////////
			// Finish
			/////////////////
			// calculate the time it took to render the frame
            elapsedTime = System.currentTimeMillis() - frameStart;
            // sync the framerate
            try {
                // make sure framerate milliseconds have passed this frame
                if (elapsedTime < framerate) {
                    Thread.sleep(framerate - elapsedTime);
                } else {
                    // don't starve the garbage collector
                    Thread.sleep(5);
                }
            }
            catch (InterruptedException e) {
                break;
            }
            frameCount += 1;
            totalElapsedTime += (System.currentTimeMillis() - frameStart);
            if (totalElapsedTime > 1000) {
                reportedFramerate = (long) ((double) frameCount / (double) totalElapsedTime * 1000.0);
                // show the framerate in the applet status window
                //System.out.println("fps: " + reportedFramerate);
                frameCount = 0;
                totalElapsedTime = 0;
            }
		}
	}
}
