package Lab2;

public class SquareRootMethod {
  private static int size = 4;
  private static double[][] A = {
    {2, 1, -1, 0},
    {1, 3, 0, 1},
    {-1, 0, 2, 1},
    {0, 1, 1, 4}
  };
  private static double[] b = {1, -3, -2, -5};

  public static void main(String[] args) {
    double[][] S = new double[size][size];    
    double[][] InverseA = new double[size][size];       
    double[] d = new double[size];
    double[] y = new double[size];
    double[] x = new double[size];
    double[] yInv = new double[size];        
    double determinant = 1;
    double aNorm = 0, invANorm = 0, condA;

    // Find S
    for (int i = 0; i < size; i++) {    
      double sdSum = 0;
      for (int j = 0; j < i; j++) {
        sdSum += S[j][i]*S[j][i]*d[j];        
      }
      d[i] = Math.signum(A[i][i] - sdSum);
      S[i][i] = Math.sqrt(Math.abs(A[i][i] - sdSum));

      for (int j = i + 1; j < size; j++) {
        double sndSum = 0;
        for (int k = 0; k < i; k++) {
          sndSum += S[k][i] * d[k] * S[k][j];
        }      
        S[i][j] = (A[i][j] - sndSum) / (d[i] * S[i][i]);
      }
    }
    
    // Find y
    for (int i = 0; i < size; i++) {
      double ySum = 0;
      for (int j = 0; j < i; j++) {
        ySum += S[j][i] * y[j];
      }
      y[i] = (b[i] - ySum) / S[i][i] * d[i];
    }

    // FInd x
    for (int i = size - 1; i >= 0; i--) {
      double xSum = 0;
      for (int j = i + 1; j < size; j++) {
        xSum += S[i][j] * x[j];
      }
      x[i] = (y[i] - xSum) / S[i][i];
    }
    System.out.println("x=(" + x[0] + ", " + x[1] + ", " + x[2] + ", " + x[3] + ")");

    // Find determinant
    for (int i = 0; i < size; i++) {
      determinant *= d[i] * S[i][i] * S[i][i];
    }
    System.out.println("\nDet(A): " + determinant);

    // Find Inv(A)
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        double ySum = 0;        
        for (int k = 0; k < j; k++) {
          ySum += S[k][j] * yInv[k];
        }
        yInv[j] = i == j ? (1 - ySum) / S[j][j] * d[j] : -ySum / S[j][j] * d[j];
      }

      for (int j = size - 1; j >= 0; j--) {
        double xSum = 0;
        for (int k = j + 1; k < size; k++) {
          xSum += S[j][k] * InverseA[k][i];
        }
        InverseA[j][i] = (yInv[j] - xSum) / S[j][j];
      }
    }
    
    System.out.print("\nInverse matrix:");
    for (int i = 0; i < size; i++) {
      System.out.println("\t");
      for (int j = 0; j < size; j++) {
        System.out.print(InverseA[i][j] + " ");
      }
    }   

    System.out.println("\n\nA * Inv(A)");
    multiplyAndPrintMatrix(A, InverseA);

    // Find cond(A)
    for (int i = 0; i < size; i++) {
      double aSum = 0, invASum = 0;
      for (int j = 0; j < size; j++) {
        aSum += Math.abs(A[j][i]);
        invASum += Math.abs(InverseA[j][i]);
      }
      aNorm = Math.max(aNorm, aSum);
      invANorm = Math.max(invANorm, invASum);
    }
    condA = aNorm * invANorm;
    System.out.println("\nCond(A): " + condA);
  }  

  private static void multiplyAndPrintMatrix(double[][] M1, double[][] M2) {
    double[][] Res = new double[4][4];
    for(int i = 0; i < size; i++) {    
      for(int j = 0; j < size; j++) {                
        for(int k = 0; k < size; k++) {      
          Res[i][j] += M1[i][k] * M2[k][j];      
        }
        System.out.printf("%.2f ", Res[i][j]);
      }
      System.out.println();
      }    
  }
}
