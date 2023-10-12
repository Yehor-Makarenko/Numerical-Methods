package Lab2;

public class SeidelMethod {
  private static final double E = 0.00000000000000000000000001;
  private static int size = 4;
  private static double[][] A = {
    {2, 1, -1, 0}, // 2 = 2
    {1, 3, 0, 1}, // 3 > 2
    {-1, 0, 2, 1}, // 2 = 2
    {0, 1, 1, 4} // 4 > 2
  };
  private static double[] b = {1, -3, -2, -5};

  public static void main(String[] args) {
    double[] x0 = new double[size];
    double[]x1 = new double[size];    
    int iterationsCounter = 0;

    do {    
      double xi, xSum1, xSum2;
      for (int i = 0; i < size; i++) {
        xSum1 = 0;
        xSum2 = 0;
        for (int j = 0; j < i; j++) {
          xSum1 += A[i][j] * x1[j] / A[i][i];
        }
        for (int j = i + 1; j < size; j++) {
          xSum2 += A[i][j] * x1[j] / A[i][i];
        }
        xi = -xSum1 - xSum2 + b[i] / A[i][i];
        x0[i] = x1[i];
        x1[i] = xi;
      }      
      iterationsCounter++;
    } while (getVectorNorm(subtractVectors(x1, x0)) > E);

    System.out.println("Iterations: " + iterationsCounter);
    System.out.println("\nx=(" + x1[0] + ", " + x1[1] + ", " + x1[2] + ", " + x1[3] + ")");
  }

  private static double[] subtractVectors(double[] v1, double[] v2) {
    double[] res = new double[size];
    for (int i = 0; i < size; i++) {
      res[i] = v1[i] - v2[i];
    }
    return res;
  }

  private static double getVectorNorm(double[] v) {
    double res = 0;
    for (int i = 0; i < size; i++) {
      res += v[i]*v[i];
    }
    return Math.sqrt(res);
  }
}
