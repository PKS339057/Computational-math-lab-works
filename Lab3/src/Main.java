import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Введите номер функции:\n" +
                "\t1. 1 / x\n" +
                "\t2. sin(x) / x\n" +
                "\t3. x^2 + 2\n" +
                "\t4. 2 * x + 2\n" +
                "\t5. log(x)");
        int f = Integer.parseInt(bufferedReader.readLine().trim());

        System.out.print("Введите левую границу: ");
        double a = Double.parseDouble(bufferedReader.readLine().trim());

        System.out.print("Введите правую границу: ");
        double b = Double.parseDouble(bufferedReader.readLine().trim());

        System.out.print("Введите предельную погрешность: ");
        double epsilon = Double.parseDouble(bufferedReader.readLine().trim());

        IntegralCalculator.checkIfFunctionHasDiscontinuity(a, b, f);

        if(!IntegralCalculator.hasDiscontinuity) {
            System.out.println("Результаты:\n" +
                    "\tМетод левых прямоугольников: " + IntegralCalculator.solveIntegral(a, b, f, epsilon, 1) + "\n" +
                    "\tМетод средних прямоугольников: " + IntegralCalculator.solveIntegral(a, b, f, epsilon, 2) + "\n" +
                    "\tМетод правых прямоугольников: " + IntegralCalculator.solveIntegral(a, b, f, epsilon, 3));
        } else {
            System.err.println(IntegralCalculator.errorMessage);
        }

        bufferedReader.close();
    }
}