package functions;

import java.util.*;
import java.util.function.*;

import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.tan;

public class SystemOfEquations {
    static double k = 0.4;
    static double a = 0.9;
    private static double first_function(List<Double> args) {
        return sin(args.get(0));
    }

    private static double second_function(List<Double> args) {
        return (args.get(0) * args.get(1)) / 2;
    }

    private static double third_function(List<Double> args) {
        return tan(args.get(0) * args.get(1) + k) - pow(args.get(0), 2);
    }

    private static double fourth_function(List<Double> args) {
        return a * pow(args.get(0), 2) + 2 * pow(args.get(1), 2) - 1;
    }

    private static double fifth_function(List<Double> args) {
        return pow(args.get(0), 2) + pow(args.get(1), 2) + pow(args.get(2), 2) - 1;
    }

    private static double six_function(List<Double> args) {
        return 2 * pow(args.get(0), 2) + pow(args.get(1), 2) - 4 * args.get(2);
    }


    private static double seven_function(List<Double> args) {
        return 3 * pow(args.get(0), 2) - 4 * args.get(1) + pow(args.get(2), 2);
    }

    private static double default_function(List<Double> args) {
        return 0.0;
    }
    public static List<Function<List<Double>, Double>> get_functions(int n) {
        switch (n) {
            case (1):
                return List.of(SystemOfEquations::first_function, SystemOfEquations::second_function);
            case (2):{
                SystemOfEquations.k = 0.4;
                SystemOfEquations.a = 0.9;
                return List.of(SystemOfEquations::third_function, SystemOfEquations::fourth_function);
            }
            case (3):{
                SystemOfEquations.k = 0.0;
                SystemOfEquations.a = 0.5;
                return List.of(SystemOfEquations::third_function, SystemOfEquations::fourth_function);
            }
            case (4):
                return List.of(SystemOfEquations::fifth_function, SystemOfEquations::six_function, SystemOfEquations::seven_function);
            default:
                return List.of(SystemOfEquations::default_function);
        }
    }
}