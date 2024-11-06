import InputTypes.FileInputType;
import InputTypes.RandomMatrix;
import InputTypes.UserInputType;
import methods.GaussZeidelMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int inputType = 0;
        List<List<Double>> matrix = new ArrayList<>();

        System.out.println("Выберите тип ввода данных:\n" +
                "\t1. Пользовательский ввод данных\n" +
                "\t2. Чтение данных из файла\n" +
                "\t3. Генерация случайной матрицы");
        try {
            inputType = Integer.parseInt(reader.readLine().trim());
        } catch (Exception e) {
            System.err.println("Ошибка ввода типа данных!");
            System.exit(0);
        }
        switch (inputType) {
            case 1: {
                System.out.println("Вы выбрали пользовательский тип данных");
                matrix = UserInputType.getMatrix();
                break;
            }
            case 2: {
                System.out.println("Вы выбрали чтение данных из файла");
                matrix = FileInputType.getMatrix();
                break;
            }
            case 3: {
                System.out.println("Вы выбрали генерацию случайной матрицы");
                matrix = RandomMatrix.getMatrix();
                break;
            }
            default: {
                System.err.println("Введённого типа данных не существует!");
                System.exit(0);
            }
        }
        double epsilon = 0.;
        System.out.print("Введите максимальную допустимую погрешность измерений: ");
        try {
            epsilon = Double.parseDouble(reader.readLine().trim());
        } catch (Exception e) {
            System.err.println("Ошибка ввода погрешности!");
            System.exit(0);
        }
        if (epsilon <= 0) {
            System.err.println("Погрешность меньше либо равна нулю!");
            System.exit(0);
        }
        List<Double> result = GaussZeidelMethod.solveByGaussSeidel(matrix.size(), matrix, epsilon);

        System.out.println("\nВаша матрица:");
        for (int i = 0; i < matrix.size(); i++) {
            System.out.println("\t" + matrix.get(i));
        }

        System.out.println("\nБыло выполнено " + GaussZeidelMethod.numberOfIterations + " итераций");
        System.out.println("Столбец решений:");
        for (int i = 0; i < matrix.size(); i++) {
            System.out.println(String.format("\tx%d = %.9f", i + 1, result.get(i)));
        }
        System.out.println("Столбец погрешностей:");
        for (int i = 0; i < matrix.size(); i++) {
            System.out.println(String.format("\te%d = %.9f", i + 1, GaussZeidelMethod.listOfDifferences.get(i)));
        }
    }
}