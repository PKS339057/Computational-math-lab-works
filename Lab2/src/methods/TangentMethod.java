package methods;

import functions.Equations;

import java.util.List;

public class TangentMethod {
    public static boolean isPossible(double x0) {
        List<Double> results = Equations.functions.apply(x0);
        return results.get(0) * results.get(2) > 0;
    }
    public static double solve(double x0, double epsilon) {
        List<Double> results = Equations.functions.apply(x0);
        double x = x0 - results.get(0) / results.get(1);
        if (Math.abs(x - x0) < epsilon) return x;
        if (isPossible(x)) return solve(x, epsilon);
        throw new UnsupportedOperationException("Метод касательных не сходится");
    }
}
