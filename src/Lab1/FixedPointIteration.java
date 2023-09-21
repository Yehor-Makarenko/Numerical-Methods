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

  private static final Function phiDer1 = new Function() {
    @Override
    public double getValue(double x) {           
      return -(4 * Math.pow(x, 3) + 96.32 * x + 108.08) / (3 * Math.pow(10, (double)1 / 3) * Math.pow(Math.pow(x, 4) + 48.16 * Math.pow(x, 2) + 108.08 * x + 70.76, (double)2 / 3));
    }
  };

  private static final Function phi1 = new Function() {
    @Override
    public double getValue(double x) {
      double a = (-(Math.pow(x, 4) + 48.16 * Math.pow(x, 2) + 108.08 * x + 70.76) / 10);
      return Math.signum(a) * Math.pow(Math.abs(a), (double)1 / 3);
    }
  };

  private static final Function phiDer1 = new Function() {
    @Override
    public double getValue(double x) {           
      return -(4 * Math.pow(x, 3) + 96.32 * x + 108.08) / (3 * Math.pow(10, (double)1 / 3) * Math.pow(Math.pow(x, 4) + 48.16 * Math.pow(x, 2) + 108.08 * x + 70.76, (double)2 / 3));
    }
  };

  private static Function mainPhi, mainPhiDer;

  private static final double e = 0.0001;
  private static double left, right, x0, q, prevX, currX, newX;  

  public static void main(String[] args) {
    mainPhi = phi1;
    mainPhiDer = phiDer1;
    Scanner input = new Scanner(System.in);    

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
      System.out.println("Cannot use fixed-point iteration");
      return;
    }

    prevX = x0;
    currX = mainPhi.getValue(prevX);
    newX = mainPhi.getValue(currX);

    while (!canFinish()) {
      prevX = currX;
      currX = newX;
      newX = mainPhi.getValue(newX);
    }
    
    double y = f.getValue(newX);

    System.out.println("Result:\n\tx: " + newX + "\n\ty: " + y);
  }

  private static boolean checkFirstCondition() {
    q = MinMaxAbsFunction.getMaxAbs(mainPhiDer, left, right);
    System.out.println(q);
    return q < 1;
  }

  private static boolean checkSecondCondition() {
    double delta = Math.max(x0 - left, right - x0);    
    System.out.println(Math.abs(mainPhi.getValue(x0) - x0));
    System.out.println((1 - q) * delta);
    return Math.abs(mainPhi.getValue(x0) - x0) <= (1 - q) * delta;
  }

  private static boolean canFinish() {
    return Math.pow(newX - currX, 2) / Math.abs(2 * currX - newX - prevX) < e;
  }
}
