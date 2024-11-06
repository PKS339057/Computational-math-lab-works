package InputTypes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FileInputType {
    public static List<List<Double>> getMatrix() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/tests/file1"));
            List<List<Double>> result = new ArrayList<>();
            int matrixSize = 0;
            try {
                matrixSize = Integer.parseInt(reader.readLine().trim());
            } catch (Exception e) {
                System.err.println("Ошибка чтения размера матрицы!");
                System.exit(0);
            }
            if (matrixSize < 1 || matrixSize > 20) {
                System.err.println("Размер матрицы не принадлежит интервалу [1, 20]");
                System.exit(0);
            }
            List<Double> row = new ArrayList<>();
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                try {
                    row = Stream.of(reader.readLine().replaceAll("\\s+$", "").split(" "))
                            .map(Double::parseDouble)
                            .collect(toList());
                } catch (Exception e) {
                    System.err.println("Ошибка чтения данных!");
                    System.exit(0);
                }
                if (row.size() == matrixSize + 1) {
                    result.add(row);
                } else {
                    System.err.println(String.format("Неверное количество элементов в строке %d!", rowIndex + 1));
                    System.exit(0);
                }
            }
            return result;
        } catch (Exception e) {
            System.err.println("Ошибка чтения файла!");
            System.exit(0);
        }
        return new ArrayList<>();
    }
}
