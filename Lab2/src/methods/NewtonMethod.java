package methods;

import functions.SystemOfEquations;

import java.util.*;
import java.util.function.Function;

public class NewtonMethod {

    public static Double difference = 1.;
    public static List<Double> solve(int system_id, List<Function<List<Double>, Double>> functions, List<Double> initial_approximations, double epsilon) {
        List<Double> previousApproximations = initial_approximations;
        List<Double> currentApproximations = setNewIteration(system_id, functions, previousApproximations);
        while (difference > epsilon) {
            previousApproximations = currentApproximations;
            currentApproximations = setNewIteration(system_id, functions, previousApproximations);
        }
        return currentApproximations;
    }
    public static List<Double> setNewIteration(int system_id, List<Function<List<Double>, Double>> functions, List<Double> approximations) {

        Double[][] jacobian = getJacobian(system_id, approximations);

        Double[] B = new Double[functions.size()];
        for (int i = 0; i < functions.size(); i++) {
            B[i] = -functions.get(i).apply(approximations);
        }
        difference = 0.;
        Double[] delta = multiplyTwoMatrices(B, getInversedMatrix(jacobian));

        Double[] result = new Double[approximations.size()];
        for (int i = 0; i < approximations.size(); i++) {
            if (Math.abs(delta[i]) > difference) difference = Math.abs(delta[i]);
            result[i] = delta[i] + approximations.get(i);
        }
        return Arrays.stream(result).toList();
    }
    private static Double getDeterminant(Double[][] matrix) {
        double result;
        if (matrix.length == 1) {
            result = matrix[0][0];
        } else if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            result = 0;
            for (int i = 0; i < matrix[0].length; i++)
                result += Math.pow(-1, i) * matrix[0][i] * getDeterminant(getSubmatrix(matrix, 0, i));
        }
        return result;
    }

    private static Double[][] getInversedMatrix(Double[][] matrix) {
        int n = matrix.length;
        Double[][] result = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = Math.pow(-1, i + j) * getDeterminant(getSubmatrix(matrix, i, j));
            }
        }
        if (getDeterminant(matrix) == 0) throw new UnsupportedOperationException("Метод Ньютона не сходится в данной точке");
        double determinant = 1. / getDeterminant(matrix);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                double temp = result[i][j];
                result[i][j] = result[j][i] * determinant;
                result[j][i] = temp * determinant;
            }
        }
        return result;
    }

    private static Double[][] getSubmatrix(Double[][] matrix, int row, int column) {
        int n = matrix.length;
        Double[][] result = new Double[n - 1][n - 1];
        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            for (int j = 0; j < n; j++)
                if (j != column)
                    result[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
        }
        return result;
    }
    private static Double[] multiplyTwoMatrices(Double[] a, Double[][] b) {
        int n = a.length;
        Double[] result = new Double[n];
        for (int i = 0; i < n; i++) {
            result[i] = 0.;
            for (int k = 0; k < n; ++k)
                result[i] += a[k] * b[i][k];
        }
        for (int i = 0; i < n; i++) {
            result[i] = result[i];
        }
        return result;
    }
    public static Double[][] getJacobian(int system_id, List<Double> approximations) {
        Double[][] result = new Double[approximations.size()][approximations.size()];
        for (int variableIndex = 0; variableIndex < approximations.size(); variableIndex++) {
            result[variableIndex] = getJacobianRow(system_id, approximations, variableIndex);
        }
        Double[][] t_result = new Double[approximations.size()][approximations.size()];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                t_result[i][j] = result[j][i];
            }
        }
        return t_result;
    }
    private static Double[] getJacobianRow(int system_id, List<Double> approximations, int variableIndex) {
        Double[] result = new Double[approximations.size()];
        switch (system_id) {
            case 1: {
                result[0] = variableIndex == 0 ? Math.cos(approximations.get(variableIndex)) : 0.;
                result[1] = approximations.get(variableIndex == 0 ? 1 : 0) / 2;
                break;
            }
            case 2: {
                result[0] = variableIndex == 0 ? approximations.get(1) / Math.pow(Math.cos(approximations.get(0) * approximations.get(1) + 0.4), 2) - 2 * approximations.get(0)
                        : approximations.get(0) / Math.pow(Math.cos(approximations.get(0) * approximations.get(1) + 0.4), 2);
                result[1] = variableIndex == 0 ? 1.8 * approximations.get(0) : 4 * approximations.get(1);
                break;
            }
            case 3: {
                result[0] = variableIndex == 0 ? approximations.get(1) / Math.pow(Math.cos(approximations.get(0) * approximations.get(1)), 2) - 2 * approximations.get(0)
                        : approximations.get(0) / Math.pow(Math.cos(approximations.get(0) * approximations.get(1)), 2);
                result[1] = variableIndex == 0 ?  approximations.get(0) : 4 * approximations.get(1);
                break;
            }
            case 4: {
                switch (variableIndex) {
                    case 0: {
                        result[0] = 2 * approximations.get(0);
                        result[1] = 4 * approximations.get(0);
                        result[2] = 6 * approximations.get(0);
                        break;
                    }
                    case 1: {
                        result[0] = 2 * approximations.get(1);
                        result[1] = 2 * approximations.get(1);
                        result[2] = -4.;
                        break;
                    }
                    case 2: {
                        result[0] = 2 * approximations.get(2);
                        result[1] = -4.;
                        result[2] = 2 * approximations.get(2);
                        break;
                    }
                }
                break;
            }
        }
        return result;
    }
}
