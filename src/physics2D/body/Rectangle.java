package physics2D.body;

public class Rectangle extends Body2D {
	
	private double width;
	private double height;
	
	/** Constructeur */
	public Rectangle(double width, double height, double density) {
		super(new double[] {}, new double[] {}, 0, density);
		this.width = width;
		this.height = height;
		double xpoints[] = {-width/2, width/2, width/2, -width/2};
		double ypoints[] = {-height/2, -height/2, height/2, height/2};
		setPoint(xpoints, ypoints, 4);
	}
	
	/** Retourne la largeur */
	public double getWidth() {
		return width;
	}
	
	/** Retourne la hauteur */
	public double getHeight() {
		return height;
	}
	
	@Override
	public double getMomentInertiel() {
		double width = getWidth();
		double height = getHeight();
		return (getMasse() / 12.0) * (Math.pow(width, 2) + Math.pow(height, 2));
	}
	
	@Override
	public double computeArea() {
		return (getWidth() * getHeight()); 
	}
}
