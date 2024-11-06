package methods;

import java.util.*;

public class GaussZeidelMethod {
    public static String errorMessage;
    public static boolean isMethodApplicable = true;
    public static int[][] matrixOfDominantElements;
    public static int numberOfIterations;
    public static List<Double> listOfDifferences = new ArrayList<>();

    public static List<Double> solveByGaussSeidel(int n, List<List<Double>> matrix, double epsilon) {
        if (hasDiagonalDominance(n, matrix)) {
            matrix = convertToDiagonalDominance(n, matrix);
            return solveEquation(n, matrix, epsilon);
        } else {
            isMethodApplicable = false;
            errorMessage = "Для решения матрицы методом Гаусса-Зейделя отсутствует диагональное преобладание";
            System.err.println(errorMessage);
            System.exit(0);
        }
        return null;
    }
    private static boolean hasDiagonalDominance(int n, List<List<Double>> matrix) {
        matrixOfDominantElements = getMatrixOfDominantElements(n, matrix);
        for (int row = 0, column = 0; row < n; row++, column++) {
            if ((getRowSum(n, row) != 1) || (getColumnSum(n, column) != 1)) return false;
        }
        return true;
    }
    private static int[][] getMatrixOfDominantElements(int n, List<List<Double>> matrix) {
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            double rowSum = 0.;
            for (int j = 0; j < n; j++) {
                rowSum += Math.abs(matrix.get(i).get(j));
            }
            for (int j = 0; j < n; j++) {
                if (2 * Math.abs(matrix.get(i).get(j)) > rowSum) {
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }
    private static List<List<Double>> convertToDiagonalDominance(int n, List<List<Double>> matrix) {
        List<Double>[] result = new List[n];
        matrix.toArray(result);
        for (int i = 0; i < n; i++) {
            if (matrixOfDominantElements[i][i] == 1) continue;
            for (int j = 0; j < n; j++) {
                if (matrixOfDominantElements[i][j] == 1) {
                    List<Double> temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;

                    int[] temp2 = matrixOfDominantElements[i];
                    matrixOfDominantElements[i] = matrixOfDominantElements[j];
                    matrixOfDominantElements[j] = temp2;
                }
            }
        }
        return List.of(result);
    }
    private static List<Double> solveEquation(int n, List<List<Double>> matrix, double epsilon) {
        List<Double> previousOccurence = getZeroOccurence(n, matrix);
        List<Double> currentOccurence = getNextOccurence(n, matrix, previousOccurence);
        numberOfIterations = 1;
        while (!differenceIsLessThanEpsilon(previousOccurence, currentOccurence, epsilon)) {
            previousOccurence = currentOccurence;
            currentOccurence = getNextOccurence(n, matrix, previousOccurence);
            numberOfIterations++;
        }
        return currentOccurence;
    }
    private static List<Double> getZeroOccurence(int n, List<List<Double>> matrix) {
        List<Double> result = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            result.add(0.);
        }
        return result;
    }
    private static List<Double> getNextOccurence(int n, List<List<Double>> matrix, List<Double> previousOccurence) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Double currentResult = matrix.get(i).get(n);
            for (int j = 0; j < n; j++) {
                try {
                    currentResult -= result.get(j) * matrix.get(i).get(j);
                } catch (IndexOutOfBoundsException exception) {
                    currentResult -= previousOccurence.get(j) * matrix.get(i).get(j);
                }
            }
            currentResult = currentResult / matrix.get(i).get(i) + previousOccurence.get(i);
            result.add(currentResult);
        }
        return result;
    }
    private static int getRowSum(int n, int row) {
        int result = 0;
        for (int column = 0; column < n; column++) {
            result += matrixOfDominantElements[row][column];
        }
        return result;
    }
    private static int getColumnSum(int n, int column) {
        int result = 0;
        for (int row = 0; row < n; row++) {
            result += matrixOfDominantElements[row][column];
        }
        return result;
    }
    private static boolean differenceIsLessThanEpsilon(List<Double> previousOccurence, List<Double> currentOccurence, double epsilon) {
        listOfDifferences = new ArrayList<>();
        for (int i = 0; i < previousOccurence.size(); i++) {
            listOfDifferences.add(Math.abs(currentOccurence.get(i) - previousOccurence.get(i)));
            if (listOfDifferences.get(i) > epsilon) return false;
        }
        return true;
    }
}