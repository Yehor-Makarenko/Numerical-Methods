package Lab1;

import java.util.Scanner;

public class ModifiedNewtonMethod {
  private static final Function f = new Function() {
    @Override
    public double getValue(double x) {
      return Math.pow(x, 4) + 10 * Math.pow(x, 3) + 48.16 * Math.pow(x, 2) + 108.08 * x + 70.76;
    }
  };

  private static final Function fDer1 = new Function() {
    @Override
    public double getValue(double x) {      
      return 4 * Math.pow(x, 3) + 30 * Math.pow(x, 2) + 96.32 * x + 108.08;
    }
  };

  private static final Function fDer2 = new Function() {
    @Override
    public double getValue(double x) {      
      return 12 * Math.pow(x, 2) + 60 * x + 96.32;
    }
  };

  private static final int MAX_ITERATIONS = 100;
  private static final double E = 0.0001;

  private static double left, right, x0, dx0, currX, delta, q; 

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);       
    int numberOfIterations;

    System.out.println("Left bound: ");
    left = input.nextDouble();
    System.out.println("Right bound: ");
    right = input.nextDouble();
    System.out.println("x0: ");
    x0 = input.nextDouble();
    input.close();

    if (x0 < left || x0 > right) {
      System.out.println("Wrong x0");
      return;
    }     

    if (!checkFirstCondition() || !checkSecondCondition()) {
      System.out.println("Cannot use Newton method");
      return;
    }
    
    currX = x0;
    dx0 = fDer1.getValue(x0);
    numberOfIterations = (int)(Math.floor(Math.log(Math.log(delta / E) / Math.log(1 / q)) / Math.log(2) + 1) + 1);  
    numberOfIterations = Math.min(numberOfIterations, MAX_ITERATIONS);

    for (int i = 0; i < numberOfIterations; i++) {
      currX = currX - f.getValue(currX) / dx0;
    }
    
    double y = f.getValue(currX);

    System.out.println("Result:\n\tx: " + currX + "\n\ty: " + y);
    System.out.println(numberOfIterations);
  }

  private static boolean checkFirstCondition() {   
    System.out.println("f(a)*f(b)<0: " + (f.getValue(left) * f.getValue(right) < 0));
    System.out.println("f(x0)*f''(x0)>0': " + (f.getValue(x0) * fDer2.getValue(x0) > 0));
    return (f.getValue(left) * f.getValue(right) < 0) && (f.getValue(x0) * fDer2.getValue(x0) > 0);
  }

  private static boolean checkSecondCondition() {   
    delta = Math.max(x0 - left, right - x0);     
    double m1 = MinMaxAbsFunction.getMinAbs(fDer1, left, right);
    double m2 = MinMaxAbsFunction.getMaxAbs(fDer2, left, right);
    q = (m2 * delta) / (2 * m1);
    System.out.println("q: " + q);  
    return q < 1;
  }
}
