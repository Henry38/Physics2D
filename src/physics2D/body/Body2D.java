package physics2D.body;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import physics2D.data.Force;
import viewer2D.geometry.Shape2D;
import math2D.Point2D;
import math2D.Vecteur2D;

public class Body2D extends Shape2D {
	
	private double density;
	private double masse;
	//private double friction;
	
	private ArrayList<Force> listForce;
	
	private Vecteur2D acceleration;
	private Vecteur2D velocity;
	private double angularAcceleration;
	private double angularVelociy;
	
	/** Constructeur */
	public Body2D(double[] xpoints, double[] ypoints, int npoints, double density) {
		super(xpoints, ypoints, npoints);
		
		this.density = Math.max(0.0, density);
		this.masse = computeArea() * getDensity();
		this.listForce = new ArrayList<Force>();
		
		this.acceleration = new Vecteur2D();
		this.velocity = new Vecteur2D();
		this.angularAcceleration = 0.0;
		this.angularVelociy = 0.0;
	}
	
	/** Retourne la densite */
	public double getDensity() {
		return density;
	}
	
	/** Retourne la masse inertielle */
	public double getMasse() {
		return masse;
	}
	
	/** Retourne le moment d'inertie */
	public double getMomentInertiel() {
		return 1.0; 
	}
	
	/** Retourne vrai si le corps est statique */
	public boolean isStaticBody() {
		return (getMasse() == 0);
	}
	
	public ArrayList<Force> getListForce() {
		return listForce;
	}
	
	public void addForce(Force force) {
		listForce.add(force);
	}
	
	public void removeForce(Vecteur2D force) {
		listForce.remove(force);
	}
	
	public void clearForce() {
		listForce.clear();
	}
	
	/** Calcul et retourne la boundingBox, repere monde */
	public Rectangle2D.Double computeBoundingBox() {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		for (int i = 0; i < npoints; i++) {
			Point2D point = getPoint2D(i);
			minX = Math.min(minX, point.getX());
			minY = Math.min(minY, point.getY());
			maxX = Math.max(maxX, point.getX());
			maxY = Math.max(maxY, point.getY());
		}
		return new Rectangle2D.Double(minX, minY, maxX-minX, maxY-minY);
	}
	
	/** Retourne vrai si le point (x, y) est a l'interieur du polygone, repere monde */
	public boolean isInside(double x, double y) {
		if (!computeBoundingBox().contains(x, y)) {
			return false;
		}
		boolean inside = false;
		int j = getNPoint() - 1;
		for (int i = 0; i < getNPoint(); i++) {
			Point2D iPoint = getPoint2D(i);
			Point2D jPoint = getPoint2D(j);
			if ( ((iPoint.getY() > y) != (jPoint.getY() > y)) &&
					(x < (jPoint.getX()-iPoint.getX()) * (y-iPoint.getY()) / (jPoint.getY()-iPoint.getY()) + iPoint.getX()) ) {
				inside = !inside;
			}
			j = i;
		}
		return inside;
	}
	
	/** Calcul et retourne l'air du polygone */
	public double computeArea() {
		double area = 0.0;
		int j = getNPoint() - 1;
		for (int i = 0; i < getNPoint(); i++) {
			Point2D iPoint = points[i];
			Point2D jPoint = points[j];
			area += (jPoint.getX() + iPoint.getX()) * (jPoint.getY() - iPoint.getY());
			j = i;
		}
		return Math.abs(area / 2.0);
	}
	
	@Override
	public void setPoint(double[] xpoints, double[] ypoints, int npoints) {
		super.setPoint(xpoints, ypoints, npoints);
		this.masse = computeArea() * getDensity();
	}
	
	/** Met a jour les forces qui agissent sur le Body2D */
	public void computeForce(Vecteur2D gravity) {
		if (getMasse() > 0) {
			// Ajout du vecteur poids
			Vecteur2D force = new Vecteur2D(gravity);
			force.mult(getMasse());
			Force poid = new Force(getBarycenter(), force);
			listForce.add(poid);
		}
	}
	
	public void updatePosition(double dt) {
		if (!isStaticBody()) {
			// Calcul du nouveau vecteur acceleration
			double ax = 0.0;
			double ay = 0.0;
			double mz = 0.0;
			for (Force force : listForce) {
				ax += force.getVecteur().getDx();
				ay += force.getVecteur().getDy();
				Vecteur2D vect = new Vecteur2D(getBarycenter(), getPoint2D(force.getPoint()));
				mz += Vecteur2D.produit_vectoriel(vect, force.getVecteur());
			}
			ax /= getMasse();
			ay /= getMasse();
			acceleration.set(ax, ay);
			mz /= getMomentInertiel();
			angularAcceleration = mz;
			
			// Calcul du nouveau vecteur vitesse
			double dvx = acceleration.getDx() * dt;
			double dvy = acceleration.getDy() * dt;
			velocity.add(dvx, dvy);
			angularVelociy += angularAcceleration * dt;
			
			// Calcul de la nouvelle position
			double dx = velocity.getDx() * dt;
			double dy = velocity.getDy() * dt;
			translate(dx, dy);
			rotate(angularVelociy * dt);
		}
	}
}
