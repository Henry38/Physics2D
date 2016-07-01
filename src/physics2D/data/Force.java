package physics2D.data;

import math2D.Point2D;
import math2D.Vecteur2D;

public class Force {
	
	private Point2D pointApp;
	private Vecteur2D vectForce;
	
	/** Constructeur */
	public Force(Point2D pointApp, Vecteur2D vectForce) {
		this.pointApp = pointApp;
		this.vectForce = vectForce;
	}
	
	/** Retourne le point d'application de la force */
	public final Point2D getPoint() {
		return pointApp;
	}
	
	/** Retourne */
	public final Vecteur2D getVecteur() {
		return vectForce;
	}
}
