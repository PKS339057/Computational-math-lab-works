import java.util.function.Function;

import static java.lang.Double.isNaN;

public class IntegralCalculator {
    public static String errorMessage = "";
    public static boolean hasDiscontinuity = false;
    public static double solveIntegral(double a, double b, int f, double epsilon, int methodIndex) {
        double difference = epsilon + 1;
        int numberOfRectangles = 1;
        double currentIteration, nextIteration;

        Function<Double, Double> func = Functions.getFunction(f);
        double position = (a + b) / 2;
        if (isNaN(func.apply(position))) {
            position = (a + b) / 2 + 1.e-8;
        }

        currentIteration = (b - a) * func.apply(position);
        while (difference > epsilon) {
            numberOfRectangles *= 2;
            nextIteration = 0;
            double widthOfRectangle = (b - a) / numberOfRectangles;
            for (int i = 0; i < numberOfRectangles; i++) {
                switch (methodIndex) {
                    case 1: {
                        position = a + i * widthOfRectangle;
                        break;
                    }
                    case 2: {
                        position = a + (i + 0.5) * widthOfRectangle;
                        break;
                    }
                    case 3: {
                        position = a + (i + 1) * widthOfRectangle;
                        break;
                    }
                    default: {
                        position = 0;
                    }
                }

                double heightOfRectangle = func.apply(position);
                if (isNaN(heightOfRectangle)) {
                    heightOfRectangle = func.apply(position + 1.e-8);
                }
                nextIteration += widthOfRectangle * heightOfRectangle;
            }
            difference = Math.abs(nextIteration - currentIteration);
            currentIteration = nextIteration;
        }
        return currentIteration;
    }

    public static void checkIfFunctionHasDiscontinuity(double a, double b, int f) {
        if (((f == 1) && ((a <= 0 && 0 <= b) || (b <= 0 && 0 <= a))) || (f == 5 && (a <= 0 || b <= 0))) {
            errorMessage = "Integrated function has discontinuity or does not defined in current interval";
            hasDiscontinuity = true;
        }
    }
}
