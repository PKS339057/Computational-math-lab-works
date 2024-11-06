import java.util.function.Function;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.sin;

public class Functions {
    private static double firstFunction(double x) {
        return 1 / x;
    }
    private static double secondFunction(double x) {
        return sin(x) / x;
    }
    private static double thirdFunction(double x) {
        return x * x + 2;
    }
    private static double fourthFunction(double x) {
        return 2 * x + 2;
    }
    private static double fifthFunction(double x) {
        return log(x);
    }
    public static Function<Double, Double> getFunction(int n) {
        switch (n) {
            case (1): return Functions::firstFunction;
            case (2): return Functions::secondFunction;
            case (3): return Functions::thirdFunction;
            case (4): return Functions::fourthFunction;
            case (5): return Functions::fifthFunction;
            default: throw new UnsupportedOperationException("Function " + n + " not defined.");
        }
    }
}