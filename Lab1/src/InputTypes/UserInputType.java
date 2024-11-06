package InputTypes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class UserInputType {
    public static List<List<Double>> getMatrix() {
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
        System.out.println(String.format("Введите %d строк по %d элементов в каждой", matrixSize, matrixSize + 1));
        List<Double> row = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
            try {
                row = Stream.of(reader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Double::parseDouble)
                        .collect(toList());
            } catch (Exception e) {
                System.err.println("Ошибка ввода данных!");
                System.exit(0);
            }
            if (row.size() == matrixSize + 1) {
                result.add(row);
            } else {
                System.err.println("Неверное количество элементов в строке!");
                System.exit(0);
            }
        }
        return result;
    }
}
