import functions.Equations;
import functions.SystemOfEquations;
import methods.NewtonMethod;
import methods.SimpleIterationMethod;
import methods.TangentMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {
    private static int typeOfEquation;
    private static int equationNumber, systemOfEquationsNumber;
    private static boolean equationIsChosen = false, systemOfEquationsIsChosen = false;
    private static double x0, epsilon;
    private static List<Double> X0;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        chooseTypeOfEquation();
        switch (typeOfEquation) {
            case 1:
                chooseEquation();
                break;
            case 2:
                chooseSystemOfEquation();
                break;
        }
        if (equationIsChosen) {
            Equations.functions = Equations.getEquations(equationNumber);
            setParameters();

            if (TangentMethod.isPossible(x0)) {
                try {
                    System.out.println("Значение, полученное методом касательных: " +
                            TangentMethod.solve(x0, epsilon));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("Метод касательных не применим для начального приближения");
            }
            if (SimpleIterationMethod.isPossible(x0)) {
                try {
                    System.out.println("Значение, полученное методом простой итерации: " +
                            SimpleIterationMethod.solve(x0, epsilon));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("Метод простой итерации не применим для начального приближения");
            }
        } else if (systemOfEquationsIsChosen) {
            List<Function<List<Double>, Double>> functions = SystemOfEquations.get_functions(systemOfEquationsNumber);
            setZeroOccurences(functions);
            setEpsilon();
            try {
                List<Double> result = NewtonMethod.solve(systemOfEquationsNumber, functions, X0, epsilon);
                System.out.println("Ответы:");
                for (int i = 0; i < result.size(); i++) {
                    System.out.println("\tx" + (i + 1) + " = " + result.get(i));
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void chooseTypeOfEquation() {
        System.out.println("Что вы хотите решить?\n" +
                "\t1. Нелинейное уравнение\n" +
                "\t2. Систему нелинейных уравнений");
        try {
            typeOfEquation = Integer.parseInt(reader.readLine().trim());
            if (typeOfEquation != 1 && typeOfEquation != 2) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.err.println("Элемента с данным индексом нет в списке!");
            chooseTypeOfEquation();
        } catch (Exception e) {
            System.err.println("Ошибка ввода!");
            chooseTypeOfEquation();
        }
    }
    private static void chooseEquation() {
        equationIsChosen = true;
        System.out.println("Выберите уравнение:\n" +
                "\t1. e^(-x) - x = 0\n" +
                "\t2. x^3 + 2x - 1 = 0\n" +
                "\t3. log(x) + x^4 = 0\n" +
                "\t4. x - 5 - log(x) = 0\n" +
                "\t5. log(x) + x^3 - 2^x = 0");
        try {
            equationNumber = Integer.parseInt(reader.readLine().trim());
            if (equationNumber < 1 || equationNumber > 5) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.err.println("Элемента с данным индексом нет в списке!");
            chooseEquation();
        } catch (Exception e) {
            System.err.println("Ошибка ввода!");
            chooseEquation();
        }
    }
    private static void chooseSystemOfEquation() {
        systemOfEquationsIsChosen = true;
        System.out.println("Выберите систему уравнений:\n" +
                "\t1. sin(x) = 0\n" +
                "\t   (x * y) / 2 = 0\n" +
                "\t2. tan(x * y + 0.4) - x^2 = 0\n" +
                "\t   0.9 * x^2 + 2 * y^2 - 1 = 0\n" +
                "\t3. tan(x * y) - x^2 = 0\n" +
                "\t   0.5 * x^2 + 2 * y^2 - 1 = 0\n" +
                "\t4. x^2 + y^2 + z^2 - 1 = 0\n" +
                "\t   2 * x^2 + y^2 - 4 * z = 0\n" +
                "\t   3 * x^2 - 4 * y + z^2 = 0");
        try {
            systemOfEquationsNumber = Integer.parseInt(reader.readLine().trim());
            if (systemOfEquationsNumber < 1 || systemOfEquationsNumber > 4) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.err.println("Элемента с данным индексом нет в списке!");
            chooseSystemOfEquation();
        } catch (Exception e) {
            System.err.println("Ошибка ввода!");
            chooseSystemOfEquation();
        }
    }
    private static void setParameters() {
        setZeroOccurence();
        setEpsilon();
    }
    private static void setZeroOccurence() {
        System.out.println("Введите x0:");
        try {
            x0 = Double.parseDouble(reader.readLine().trim());
        } catch (Exception e) {
            System.err.println("Ошибка ввода!");
            setZeroOccurence();
        }
    }
    private static void setEpsilon() {
        System.out.println("Введите предельную погрешность:");
        try {
            epsilon = Double.parseDouble(reader.readLine().trim());
            if (epsilon <= 0) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.err.println("Погрешность должна быть больше нуля!");
            setEpsilon();
        } catch (Exception e) {
            System.err.println("Ошибка ввода!");
            setEpsilon();
        }
    }
    private static void setZeroOccurences(List<Function<List<Double>, Double>> functions) {
        System.out.println("Введите " + functions.size() + " значения для начального приближения");
        try {
            X0 = Stream.of(reader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Double::parseDouble)
                    .collect(toList());
            if (X0.size() != functions.size()) throw new InputMismatchException("Всего должно быть " +
                    functions.size() + " элемента!");
        } catch (InputMismatchException e) {
            System.err.println(e.getMessage());
            setZeroOccurences(functions);
        } catch (Exception e) {
            System.err.println("Ошибка ввода!");
            setZeroOccurences(functions);
        }
    }
}