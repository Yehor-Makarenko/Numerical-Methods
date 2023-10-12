package Lab1;

public class DichotomyMethod {
  private static final Function f = new Function() {
    @Override
    public double getValue(double x) {
      return Math.pow(x, 4) + 10 * Math.pow(x, 3) + 48.16 * Math.pow(x, 2) + 108.08 * x + 70.76;
    }
  };

  public static void main(String[] args) {
    double left = -3.6;
    double right = -3.2;
    double currX = -3.4;
    final double E = 0.0001;
    int n0 = (int)Math.round(Math.log((right - left) / E) / Math.log(2)) + 1;

    for (int i = 0; i < n0; i++) {
      currX = (right + left) / 2;      
      if (f.getValue(currX) > 0) {
        left = currX;
      } else {
        right = currX;
      }
    }

    System.out.println(currX);
    System.out.println(n0);
  }
}
