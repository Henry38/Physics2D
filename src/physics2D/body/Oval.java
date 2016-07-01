package physics2D.body;

public class Oval extends Body2D {
	
	private double axisA;
	private double axisB;
	
	/** Constructeur */
	public Oval(double axisA, double axisB, int npoints, double density) {
		super(new double[] {}, new double[] {}, 0, density);
		this.axisA = axisA;
		this.axisB = axisB;
		
		double xpoints[] = new double[npoints];
		double ypoints[] = new double[npoints];
		double step = (2 * Math.PI) / npoints;
		double radian = 0.0;
		for (int i = 0; i < npoints; i++) {
			xpoints[i] = axisA * Math.cos(radian);
			ypoints[i] = axisB * Math.sin(radian);
			radian += step;
		}
		setPoint(xpoints, ypoints, npoints);
	}
	
	/** Retourne le demi-grand axe */
	public double getAxisA() {
		return axisA;
	}
	
	/** Retourne le demi-petit axe */
	public double getAxisB() {
		return axisB;
	}
	
	@Override
	public double getMomentInertiel() {
		double axisA = getAxisA();
		double axisB = getAxisB();
		return (getMasse() / 5.0) * (Math.pow(axisA, 2) + Math.pow(axisB, 2));
	}
	
	@Override
	public double computeArea() {
		return (Math.PI * getAxisA() * getAxisB()); 
	}
}
