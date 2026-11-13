package jhd.Interpolators;
import java.util.Arrays;
//chatGPT Luna wrote this

public class CubicSplineInterpolator {
    private final double[] x;
//    private final double[] y;
    private final double[] a;
    private final double[] b;
    private final double[] c;
    private final double[] d;
    private final int n;
    
    public CubicSplineInterpolator(double[] x, double[] y) {
        if (x == null || y == null || x.length != y.length) {
            throw new IllegalArgumentException("X and Y arrays must be non-null and have the same length.");
        }
        if (x.length < 3) {
            throw new IllegalArgumentException("At least 3 data points are required for a cubic spline.");
        }
        
        // Ensure x is strictly increasing
        for (int i = 0; i < x.length - 1; i++) {
            if (x[i] >= x[i + 1]) {
                throw new IllegalArgumentException("X array must be sorted in strictly increasing order.");
            }
        }
        
        this.x = x.clone();
//        this.y = y.clone();
        this.n = x.length - 1;

        this.a = new double[n + 1];
        this.b = new double[n];
        this.c = new double[n + 1];
        this.d = new double[n];

        // Coefficients 'a' are simply the y values
        System.arraycopy(y, 0, a, 0, y.length);

        calculateCoefficients();
    }

    private void calculateCoefficients() {
        double[] h = new double[n];
        for (int i = 0; i < n; i++) {
            h[i] = x[i + 1] - x[i];
        }

        double[] alpha = new double[n];
        for (int i = 1; i < n; i++) {
            alpha[i] = (3.0 / h[i]) * (a[i + 1] - a[i]) - (3.0 / h[i - 1]) * (a[i] - a[i - 1]);
        }

        // Solving the tridiagonal system using Thomas algorithm
        double[] l = new double[n + 1];
        double[] mu = new double[n + 1];
        double[] z = new double[n + 1];

        l[0] = 1.0;
        mu[0] = 0.0;
        z[0] = 0.0;

        for (int i = 1; i < n; i++) {
            l[i] = 2.0 * (x[i + 1] - x[i - 1]) - h[i - 1] * mu[i - 1];
            mu[i] = h[i] / l[i];
            z[i] = (alpha[i] - h[i - 1] * z[i - 1]) / l[i];
        }

        // Natural boundary condition: second derivative at ends is zero
        l[n] = 1.0;
        z[n] = 0.0;
        c[n] = 0.0;

        // Back substitution to find coefficients b, c, d
        for (int j = n - 1; j >= 0; j--) {
            c[j] = z[j] - mu[j] * c[j + 1];
            b[j] = (a[j + 1] - a[j]) / h[j] - h[j] * (c[j + 1] + 2.0 * c[j]) / 3.0;
            d[j] = (c[j + 1] - c[j]) / (3.0 * h[j]);
        }
    }

    /**
     * Evaluates the interpolated y-value at a given x-coordinate.
     */
    public double interpolate(double targetX) {
        if (targetX < x[0] || targetX > x[n]) {
            throw new IllegalArgumentException("Target X value is out of bounds.");
        }

        // Find the correct interval using binary search
        int index = Arrays.binarySearch(x, targetX);
        if (index < 0) {
            index = -index - 2;
        }
        if (index >= n) {
            index = n - 1; // Handle edge case at the exact boundary
        }

        double dx = targetX - x[index];
        // Evaluate cubic polynomial: S_i(x) = a_i + b_i*dx + c_i*dx^2 + d_i*dx^3
        return a[index] + b[index] * dx + c[index] * dx * dx + d[index] * dx * dx * dx;
    }

//    // Example Usage
//    public static void main(String[] args) {
//    	
//		MuMassCalculator mmc = new MuMassCalculator();
//		//Avoid absorption edge
//		double edge = mmc.getAtomAbsEdge("AL", "K");
//		double[] xData = mmc.getMevArray("AL", edge, 100000.0);
//		double[] yData = new double[xData.length];
//		
//		for (int i = 0; i < xData.length; i++) {
//			yData[i] = mmc.getAtomMuMass("AL", xData[i], "TotAttn");
//		}
//		for (int i = 0; i < xData.length; i++) {
//			yData[i] = Math.log(yData[i]);
//			xData[i] = Math.log(xData[i]);
//		}
//
//        CubicSplineInterpolator spline = new CubicSplineInterpolator(xData, yData);
//        
//     		for (double tp = xData[0]; tp < xData[xData.length - 1]; tp += .1) {
//            double interpolatedValue = spline.interpolate(tp);
//    		System.out.println( Math.exp(tp) + "," + Math.exp(interpolatedValue));//+"," + mmcMuMass);
//        }
//    }
}
