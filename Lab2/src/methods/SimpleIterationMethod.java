package methods;

import functions.Equations;

public class SimpleIterationMethod {
    public static boolean isPossible(double x0) {
        return Math.abs(Equations.functions.apply(x0).get(4)) < 1;
    }

    public static double solve(double x0, double epsilon) {
        double x = Equations.functions.apply(x0).get(3);
        if (Math.abs(x - x0) < epsilon) return x;
        if (isPossible(x)) return solve(x, epsilon);
        throw new UnsupportedOperationException("Метод простой итерации не сходится");
    }
}
