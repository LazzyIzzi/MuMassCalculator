package jhd.Interpolators;

public final class XcomPairInterpolator {
    private XcomPairInterpolator() {}

    /**ChatGTP6 Luna
     * Interpolates one pair-production channel between its threshold and
     * its first standard-grid point above threshold.
     *
     * @param energyMeV photon energy, in MeV
     * @param thresholdMeV pair-production threshold, in MeV
     * @param gridMeV standard-grid energies above threshold, ascending
     * @param sigma tabulated cross sections at gridMeV, same units throughout
     * @return interpolated cross section, in the units of sigma
     */
    public static double betweenThresholdAndFirstGrid(
            double energyMeV,
            double thresholdMeV,
            double[] gridMeV,
            double[] sigma) {
    	
        if (gridMeV == null || sigma == null || gridMeV.length != sigma.length
                || gridMeV.length < 3) {
            throw new IllegalArgumentException(
                    "Provide at least three matching energy/cross-section values.");
        }

        if (energyMeV <= thresholdMeV) {
            return 0.0;
        }

        if (energyMeV > gridMeV[0]) {
            throw new IllegalArgumentException(
                    "This method handles only threshold <= energy <= first grid point.");
        }

        int n = gridMeV.length;
        double[] x = new double[n + 1];
        double[] p = new double[n + 1];

        // Physical standard-grid points, transformed as in XCOM.
        for (int i = 0; i < n; i++) {
            if (!(gridMeV[i] > thresholdMeV)
                    || (i > 0 && !(gridMeV[i] > gridMeV[i - 1]))
                    || !(sigma[i] > 0.0)
                    || !Double.isFinite(sigma[i])) {
                throw new IllegalArgumentException(
                        "Energies must be strictly ascending above threshold; "
                                + "cross sections must be positive and finite.");
            }

            double q = (gridMeV[i] - thresholdMeV) / gridMeV[i];
            x[i + 1] = Math.log(gridMeV[i]);
            p[i + 1] = Math.log(sigma[i]) - 3.0 * Math.log(q);
        }

        // XCOM's extrapolated transformed value at the threshold.
        // The first three points are the three lowest-energy grid values.
        p[0] = 3.006275 * p[1]
             - 2.577757 * p[2]
             + 0.571482 * p[3];
        x[0] = Math.log(thresholdMeV);

        // Fit the spline across the threshold point and all supplied grid points.
        double[][] coeff = naturalSplineCoefficients(x, p);

        // The requested interval is the first spline segment:
        // threshold -> first standard-grid point.
        double dx = Math.log(energyMeV) - x[0];
        double transformed = ((coeff[3][0] * dx + coeff[2][0]) * dx
                              + coeff[1][0]) * dx + coeff[0][0];

        double q = (energyMeV - thresholdMeV) / energyMeV;
        return q * q * q * Math.exp(transformed);
    }

    /**
     * Natural cubic spline coefficients in the form
     * y(x[i] + dx) = a[i] + b[i]dx + c[i]dx^2 + d[i]dx^3.
     * This follows the coefficient construction used by XCOM's SCOF routine.
     */
    private static double[][] naturalSplineCoefficients(double[] x, double[] y) {
        int n = x.length;
        double[] h = new double[n - 1];
        double[] c = new double[n];
        double[] diagonal = new double[n];
        double[] a = y.clone();
        double[] b = new double[n - 1];
        double[] d = new double[n - 1];

        double previousSlope = 0.0;
        for (int i = 0; i < n - 1; i++) {
            h[i] = x[i + 1] - x[i];
            if (!(h[i] > 0.0)) {
                throw new IllegalArgumentException("Spline energies must ascend.");
            }

            double slope = (y[i + 1] - y[i]) / h[i];
            c[i] = slope - previousSlope;
            previousSlope = slope;
        }

        // Natural-spline endpoint conditions.
        c[0] = 0.0;
        c[n - 1] = 0.0;

        double r = 0.0;
        double s = 0.0;
        for (int i = 1; i <= n - 2; i++) {
            c[i] += r * c[i - 1];
            diagonal[i] = 2.0 * (x[i - 1] - x[i + 1]) - r * s;
            s = h[i];
            r = s / diagonal[i];
        }

        for (int i = n - 2; i >= 1; i--) {
            c[i] = (h[i] * c[i + 1] - c[i]) / diagonal[i];
        }

        for (int i = 0; i < n - 1; i++) {
            double deltaC = c[i + 1] - c[i];
            d[i] = deltaC / h[i];
            double threeC = 3.0 * c[i];
            b[i] = (y[i + 1] - y[i]) / h[i]
                    - (threeC + deltaC) * h[i];
            c[i] = threeC;
        }

        return new double[][] { a, b, c, d };
    }

    // XCOM Version 3.1 threshold values, expressed in MeV.
    public static final double NUCLEAR_FIELD_THRESHOLD_MEV = 1.022007;
    public static final double ELECTRON_FIELD_THRESHOLD_MEV = 2.044014;
}
