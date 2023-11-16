package Lab3;

import java.util.Scanner;

public class ModifiedNewtonMethod {
  private static final double x0 = 0.13;
  private static final double y0 = 0.99;
  private static double z0;
  private static double e; 
  private static final double[][] A = {
    {2 * x0, -2 * y0 / 9},
    {x0 / 2, 2 * y0}
  };

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);   
    double ADet = A[0][0] * A[1][1] - A[0][1] * A[1][0];
    if (ADet == 0) {
      System.out.println("A0 determinant is 0");
      input.close();
      return;
    }
    double[][] AInv = {
      {A[1][1] / ADet, -A[0][1] / ADet},
      {-A[1][0] / ADet, A[0][0] / ADet}
    };
    System.out.println("Enter z0:");
    z0 = input.nextDouble();
    System.out.println("Enter epsilon:");
    e = input.nextDouble();
    input.close();

    double l = 2;
    double m = getMatrixNorm(AInv);
    double delta = Math.max(Math.abs(getF1(new double[] {x0, y0})), Math.abs(getF2(new double[] {x0, y0})));
    double h = m*m*l*delta*4;

    if (h > 0.5) {
      System.out.println("The process is not convergent");
      return;
    }

    double[] res = {x0, y0};
    double[] zCurr = getZK(AInv, getFx(res));
    double[] zPrev;
    int iterations = 0;

    while (getVectorNorm(zCurr) >= e) {      
      res = subVectors(res, zCurr);
      zPrev = zCurr;
      zCurr = getZK(AInv, getFx(res));
      if (getVectorNorm(zCurr) - getVectorNorm(zPrev) >= 0) {
        System.out.println("The process is not convergent");
        return;
      }
      iterations++;
    }

    System.out.println("Iterations: " + iterations);
    System.out.println("Result: x = " + res[0] + ", y = " + res[1] + ", z = " + z0);
    System.out.println("f1(x, y, z) = " + getF1(res));
    System.out.println("f2(x, y, z) = " + getF2(res));
  }

  private static double getF1(double[] x) {
    return x[0] * x[0]  - (x[1] * x[1]) / 9 + z0 * z0;
  }

  private static double getF2(double[] x) {
    return (x[0] * x[0]) / 4  + x[1] * x[1] + (z0 * z0) / 9 - 1;
  }

  private static double[] getFx(double[] x) {
    return new double[] {getF1(x), getF2(x)};
  }

  private static double[] getZK(double[][] AInv, double[] fx) {
    double[] zk = {0, 0};
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        zk[i] += AInv[i][j] * fx[j];
      }
    }
    return zk;
  }

  private static double getMatrixNorm(double[][] matrix) {
    return Math.max(Math.abs(matrix[0][0]) + Math.abs(matrix[1][0]), Math.abs(matrix[0][1]) + Math.abs(matrix[1][1]));
  }

  private static double getVectorNorm(double[] v) {
    double res = 0;
    for (int i = 0; i < 2; i++) {
      res += v[i]*v[i];
    }
    return Math.sqrt(res);
  }

  private static double[] subVectors(double[] x1, double[] x2) {    
    double[] res = new double[2];
    for (int i = 0; i < 2; i++) {
      res[i] = x1[i] - x2[i];
    }
    return res;
  }
}
