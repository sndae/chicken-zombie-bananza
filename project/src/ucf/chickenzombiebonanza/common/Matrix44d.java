package ucf.chickenzombiebonanza.common;

public class Matrix44d {
	
	private final double matrix[] = new double[16];
	
	public Matrix44d() {
		
	}
	
	public void set(int row, int col, double val) {
		matrix[(4*row) + col] = val;
	}
	
	public static Matrix44d lookAt(Vector3d lookAt, Vector3d up) {
		Vector3d right = up.crossProduct(lookAt);
		Matrix44d matrix = new Matrix44d();
		matrix.set(0, 0, right.u());
		matrix.set(1, 0, right.v());
		matrix.set(2, 0, right.w());
		matrix.set(0, 1, up.u());
		matrix.set(1, 1, up.v());
		matrix.set(2, 1, up.w());
		matrix.set(0, 2, lookAt.u());
		matrix.set(1, 2, lookAt.v());
		matrix.set(2, 2, lookAt.w());
		matrix.set(3, 3, 1);
		return matrix;
	}

	public double[] getMatrix() {
		return matrix;
	}
	
	public double[] getMatrixOpenGLFormat() {
		return new double[]{matrix[0],matrix[4],matrix[8],matrix[12],
		                    matrix[1],matrix[5],matrix[9],matrix[13],
		                    matrix[2],matrix[6],matrix[10],matrix[14],
		                    matrix[3],matrix[7],matrix[11],matrix[15]};
	}
	
	public static float[] doubleArrayToFloatArray(double[] array) {
		float[] farray = new float[array.length];
		for(int i = 0; i < farray.length; ++i) {
			farray[i] = (float)array[i];
		}
		return farray;
	}

}
