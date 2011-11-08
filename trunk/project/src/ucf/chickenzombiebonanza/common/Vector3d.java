package ucf.chickenzombiebonanza.common;

public class Vector3d {

	private double u, v, w;
	
	public Vector3d(double u, double v, double w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}
	
	public double u() {
		return u;
	}
	
	public double v() {
		return v;
	}
	
	public double w() {
		return w;
	}
}
