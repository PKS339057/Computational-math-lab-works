package functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Equations {
    public static Function<Double, List<Double>> functions;
    public static Function<Double, List<Double>> getEquations(int n) {
        return switch (n) {
            case 1 -> Equations::getFirstEquations;
            case 2 -> Equations::getSecondEquations;
            case 3 -> Equations::getThirdEquations;
            case 4 -> Equations::getFourthEquations;
            case 5 -> Equations::getFifthEquations;
            default -> Equations::getDefaultEquation;
        };
    }
    private static List<Double> getFirstEquations(double x) {
        return List.of(Math.exp(-x) - x, -Math.exp(-x) - 1, Math.exp(-x), Math.exp(-x), -Math.exp(-x));
    }
    private static List<Double> getSecondEquations(double x) {
        return List.of(Math.pow(x, 3) + 2 * x - 1, 3 * Math.pow(x, 2) + 2, 6 * x, (1 - Math.pow(x, 3)) / 2., -3 * Math.pow(x, 2) / 2);
    }
    private static List<Double> getThirdEquations(double x) {
        return List.of(Math.log(x) + Math.pow(x, 4), 4 * Math.pow(x, 3) + 1 / x, 12 * Math.pow(x, 2) - 1 / Math.pow(x, 2),
                -Math.pow(Math.log(x), 1 / 4.), -1 / (4 * x * Math.pow(Math.log(x), 3 / 4.)));
    }
    private static List<Double> getFourthEquations(double x) {
        return List.of(x - 5 - Math.log(x), (x - 1) / x, 1 / Math.pow(x, 2), 5 + Math.log(x), 1 / x);
    }
    private static List<Double> getFifthEquations(double x) {
        return List.of(Math.log(x) + Math.pow(x, 3) - Math.pow(2, x), 3 * Math.pow(x, 2) + 1 / x - Math.pow(2, x) * Math.log(2),
                -1 / Math.pow(x, 2) + 6 * x - Math.pow(2, x) * Math.pow(Math.log(2), 2), Math.pow(Math.pow(2, x) - Math.log(x), 1 / 3.),
                (Math.pow(2, x) * x * Math.log(2) - 1) / (3 * x * Math.pow(Math.pow(2, x) - Math.log(x), 2 / 3.)));
    }
    private static List<Double> getDefaultEquation(double x) {
        return new ArrayList<>();
    }
}

