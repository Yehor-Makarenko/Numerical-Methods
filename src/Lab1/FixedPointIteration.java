package Lab1;

import java.util.Scanner;

public class FixedPointIteration {
  private static final Function f = new Function() {
    @Override
    public double getValue(double x) {
      return Math.pow(x, 4) + 10 * Math.pow(x, 3) + 48.16 * Math.pow(x, 2) + 108.08 * x + 70.76;
    }
  };

  private static final Function phi1 = new Function() {
    @Override
    public double getValue(double x) {
      double a = (-(Math.pow(x, 4) + 48.16 * Math.pow(x, 2) + 108.08 * x + 70.76) / 10);
      return Math.signum(a) * Math.pow(Math.abs(a), (double)1 / 3);
    }
  };

  private static final Function phi1Der = new Function() {
    @Override
    public double getValue(double x) {           
      return -(4 * Math.pow(x, 3) + 96.32 * x + 108.08) / (3 * Math.pow(10, (double)1 / 3) * Math.pow(Math.pow(x, 4) + 48.16 * Math.pow(x, 2) + 108.08 * x + 70.76, (double)2 / 3));
    }
  };

  private static final Function phi2 = new Function() {
    @Override
    public double getValue(double x) {
      return -Math.pow(-10 * Math.pow(x, 3) - 48.16 * Math.pow(x, 2) - 108.08 * x - 70.76, (double)1 / 4);      
    }
  };

  private static final Function phi2Der = new Function() {
    @Override
    public double getValue(double x) {           
      return (30 * Math.pow(x, 2) + 96.32 * x + 108.08) / (4 * Math.pow(-10 * Math.pow(x, 3) - 48.16 * Math.pow(x, 2) - 108.08 * x - 70.76, (double)3 / 4));
    }
  };

  private static final int MAX_ITERATIONS = 100;
  private static final double E = 0.0001;
  private static Function mainPhi, mainPhiDer;
  private static double left, right, x0, q, x1, x2, x3;  

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);   
    int i = 0; 

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

    if (right > -1.4) {
      mainPhi = phi1;
      mainPhiDer = phi1Der;
    } else {
      mainPhi = phi2;
      mainPhiDer = phi2Der;
    }

    if (!checkFirstCondition() || !checkSecondCondition()) {
      System.out.println("Cannot use fixed-point iteration");
      return;
    }

    x1 = x0;
    x2 = mainPhi.getValue(x1);
    x3 = mainPhi.getValue(x2);

    while (!canFinish() && i < MAX_ITERATIONS) {
      x1 = x2;
      x2 = x3;
      x3 = mainPhi.getValue(x3);
      i++;
    }
    
    double y = f.getValue(x3);

    System.out.println("Result:\n\tx: " + x3 + "\n\ty: " + y);
  }

  private static boolean checkFirstCondition() {
    q = MinMaxAbsFunction.getMaxAbs(mainPhiDer, left, right);
    System.out.println("q: " + q);
    return q < 1;
  }

  private static boolean checkSecondCondition() {
    double delta = Math.max(x0 - left, right - x0);    
    System.out.println("|phi(x0)-x0|: " + Math.abs(mainPhi.getValue(x0) - x0));
    System.out.println("(1-q)*delta: " + (1 - q) * delta);
    return Math.abs(mainPhi.getValue(x0) - x0) <= (1 - q) * delta;
  }

  private static boolean canFinish() {
    return Math.pow(x3 - x2, 2) / Math.abs(2 * x2 - x3 - x1) < E;
  }
}
