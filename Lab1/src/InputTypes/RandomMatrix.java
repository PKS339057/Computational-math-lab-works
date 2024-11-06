package InputTypes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMatrix {
    public static List<List<Double>> getMatrix() {
        double rangeMin = -5.;
        double rangeMax = 5.;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<List<Double>> result = new ArrayList<>();
        int matrixSize = 0;
        System.out.print("Выберите размер матрицы в интервале [1, 20]: ");
        try {
            matrixSize = Integer.parseInt(reader.readLine().trim());
        } catch (Exception e) {
            System.err.println("Ошибка ввода размера матрицы!");
            System.exit(0);
        }
        if (matrixSize < 1 || matrixSize > 20) {
            System.err.println("Введённый размер матрицы не принадлежит интервалу [1, 20]");
            System.exit(0);
        }
        for (int i = 0; i < matrixSize; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < matrixSize; j++) {
                Random random = new Random();
                row.add(rangeMin + (rangeMax - rangeMin) * random.nextDouble());
            }
            row.add(i, rowSum(row) + 1);
            result.add(row);
        }
        return result;
    }
    private static double rowSum(List<Double> row) {
        double result = 0;
        for (double element : row) {
            result += Math.abs(element);
        }
        return result;
    }
}
