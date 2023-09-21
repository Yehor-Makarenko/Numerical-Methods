package Lab1;

public class MinMaxAbsFunction {
  public static double getMinAbs(Function f, double left, double right) {
    double step;
    double currX = left;
    double min = Math.abs(f.getValue(currX));

    if (right - left > 10) {
      step = 0.01;
    } else {
      step = (right - left) / 1000;
    }

    currX += step;
    while (currX < right) {
      double newValue = Math.abs(f.getValue(currX));
      if (newValue < min) {
        min = newValue;
      }
      currX += step;
    }

    double newValue = Math.abs(f.getValue(right));
    if (newValue < min) {
      min = newValue;
    }

    return min;
  }

  public static double getMaxAbs(Function f, double left, double right) {
    double step;
    double currX = left;
    double max = Math.abs(f.getValue(currX));

    if (right - left > 10) {
      step = 0.01;
    } else {
      step = (right - left) / 1000;
    }

    currX += step;
    while (currX < right) {
      double newValue = Math.abs(f.getValue(currX));
      if (newValue > max) {
        max = newValue;
      }
      currX += step;
    }

    double newValue = Math.abs(f.getValue(right));
    if (newValue > max) {
      max = newValue;
    }

    return max;
  }
}
