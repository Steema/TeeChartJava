package com.steema.teechart.functions;
/**
 *
 * <p>Title: Polynom class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Polynom {

    static public int maxDegree = 20;
    private int polyDegree = 5;
    private double[] phi;

    public Polynom() {
        super();
        if (polyCoeff == null) {
            polyCoeff = new double[maxDegree];
        }
        if (phi == null) {
            phi = new double[maxDegree];
        }
    }

    public double[] polyCoeff;

    /**
     * The number of degrees a polynomy will have. <br>
     * <b>Warning: </b><br>
     * With more degrees, more adjustment is achieved, however, values greater
     * than 10 can slow the process.<br>
     *
     * @return int
     */
    public int getPolyDegree() {
        return polyDegree;
    }

    /**
     * Sets the number of degrees a polynomy will have. <br>
     * <b>Warning: </b><br>
     * With more degrees, more adjustment is achieved, however, values greater
     * than 10 can slow the process.<br>
     *
     * @param value int
     */
    public void setPolyDegree(int value) {
        if (polyDegree != value) {
            polyDegree = Math.min(value, maxDegree);
        }
    }

    /**
     * Gaussian fitting algorithm.
     *
     * @param error double
     * @param pmtx double[][]
     * @param y double[]
     * @param x double[]
     * @return double
     */
    private double gaussFit(double error, double[][] pmtx, double[] y,
                            double[] x) {
        int change = 0;
        int maxindex;
        double maxel, tmp, val;

        for (int i = 0; i < polyDegree - 1; i++) {
            maxindex = i;
            maxel = Math.abs(pmtx[i][i]);

            for (int j = i + 1; j < polyDegree; j++) {
                if (Math.abs(pmtx[j][i]) > maxel) {
                    maxel = Math.abs(pmtx[j][i]);
                    maxindex = j;
                }
            }

            if (maxindex != i) {
                for (int j = i; j < polyDegree; j++) {
                    tmp = pmtx[i][j];
                    pmtx[i][j] = pmtx[maxindex][j];
                    pmtx[maxindex][j] = tmp;
                }
                tmp = y[i];
                y[i] = y[maxindex];
                y[maxindex] = tmp;
                change++;
            }

            if (Math.abs(pmtx[i][i]) < error) {
                return 0.0;
            }

            for (int j = i + 1; j < polyDegree; j++) {
                val = pmtx[j][i] / pmtx[i][i];
                for (int k = i + 1; k < polyDegree; k++) {
                    pmtx[j][k] -= val * pmtx[i][k];
                }
                y[j] -= val * y[i];
            }
        }

        if (Math.abs(pmtx[polyDegree - 1][polyDegree - 1]) < error) {
            return 0.0;
        }
        double result = 1.0;
        for (int i = polyDegree - 1; i >= 0; i--) {
            val = 0.0;
            for (int j = i + 1; j < polyDegree; j++) {
                val += pmtx[i][j] * x[j];
            }
            x[i] = (y[i] - val) / pmtx[i][i];
            result *= pmtx[i][i];
        }
        if (change % 2 == 1) {
            result *= -1.0;
        }
        return result;
    }

    private double[] fkt(double x) {
        double[] tmpPhi=new double[polyDegree];
        tmpPhi[0] = 1.0;

        for (int t = 1; t < polyDegree; t++) {
            tmpPhi[t] = tmpPhi[t - 1] * x;
        }
        return tmpPhi;
    }

    /// <param name="polyDegree">The polynomial degree</param>
    /// <param name="polyCoeff">Calculated polynomial coefficients</param>
    /**
     * Calculate the polynomial fit
     *
     * @param numPoints int is the number of points to fit
     * @param x double[] x values
     * @param y double[] y values
     */
    public void polyFit(int numPoints, double[] x, double[] y) {
        double[] b = new double[maxDegree];

        double[][] f = new double[polyDegree][];
        for (int i = 0; i < polyDegree; i++) {
            f[i] = new double[numPoints + 1];
            // reset to 0.0
            for (int tt = 0; tt <= numPoints; tt++) {
                f[i][tt] = 0.0;
            }
        }

        double[][] m = new double[maxDegree][];
        for (int i = 0; i < maxDegree; i++) {
            m[i] = new double[maxDegree];
        }

        try {
            // 1. Prepare the approximation
            for (int t = 0; t < numPoints; t++) {
                phi=fkt(x[t]);
                for (int tt = 0; tt < polyDegree; tt++) {
                    f[tt][t] = phi[tt];
                }
            }

            // 2. Construct matrix system
            for (int tt = 0; tt < polyDegree; tt++) {
                for (int t = 0; t < polyDegree; t++) {
                    m[t][tt] = 0.0;
                    for (int l = 0; l < numPoints; l++) {
                        m[t][tt] += f[t][l] * f[tt][l];
                    }
                    m[tt][t] = m[t][tt];
                }
            }

            // 3. Solve
            for (int t = 0; t < polyDegree; t++) {
                b[t] = 0.0;
                for (int l = 0; l < numPoints; l++) {
                    b[t] += f[t][l] * y[l];
                }
            }

            if (gaussFit(1.0E-15, m, b, polyCoeff) == 0) {
                // raise exception
            }
        } finally {
            f = null;
            m = null;
            b = null;
        }
    }

    /**
     * Evaluate function by using polyDegree polynomial with polyCoeff at
     * value x.<br>
     *
     * @param x double
     * @return double
     */
    public double polyEval(double x) {
        double result = 0.0;
        phi=fkt(x);
        for (int t = 0; t < polyDegree; t++) {
            result += polyCoeff[t] * phi[t];
        }
        return result;
    }
}

