package physics2D.controler;

import java.util.EventListener;

import physics2D.body.Body2D;

public interface World2DListener extends EventListener {
	public void shapeAdded(Body2D body);
	public void shapeRemoved(Body2D body);
	public void needRefresh();
}
