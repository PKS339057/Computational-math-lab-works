import math
import matplotlib.pyplot as plt
import numpy as np


class Result:

    def first_function(x: float, y: float):
        return math.sin(x)

    def second_function(x, y):
        return (x * y) / 2

    def third_function(x: float, y: float):
        return y - (2 * x) / y

    def fourth_function(x: float, y: float):
        return x + y

    def default_function(x: float, y: float):
        return 0.0

    def get_function(n: int):
        match n:
            case 1: return Result.first_function
            case 2: return Result.second_function
            case 3: return Result.third_function
            case 4: return Result.fourth_function
            case _: return Result.default_function

    def get_x(h, a, b):
        return [a + i * h for i in range(round((b - a) / h) + 1)]

    def get_y(f, x, y_a, h):
        result = [y_a]
        for i in range(len(x) - 1):
            result.append(result[i] + h * f(x[i], result[i]))
        return result

    def solveByEuler(f, a, y_a, b, epsilon):
        func = Result.get_function(f)
        number_of_steps = 1
        h = b - a
        x = Result.get_x(h, a, b)
        previous_y = Result.get_y(func, x, y_a, h)

        number_of_steps *= 2
        while True:
            h = (b - a) / number_of_steps
            x = Result.get_x(h, a, b)
            current_y = Result.get_y(func, x, y_a, h)
            if abs(current_y[-1] - previous_y[-1]) > epsilon:
                previous_y = current_y
                number_of_steps *= 2
            else:
                plt.plot(x, current_y, 'b-')
                plt.title("Ломаная Эйлера")
                plt.show()
                print("\nКоличество шагов:", number_of_steps)
                return current_y[-1]


if __name__ == '__main__':
    function_text = "Введите номер функции:\n" \
                    "\t1. y' = sin(x)\n" \
                    "\t2. y' = xy / 2\n" \
                    "\t3. y' = y - 2x / y\n" \
                    "\t4. y' = x + y\n"

    f = int(input(function_text).strip())
    a = float(input("Введите значение x для начального условия: ").strip())
    y_a = float(input("Введите значение y для начального условия: ").strip())
    b = float(input("Введите точку окончания решения ОДУ: ").strip())
    h = float(input("Введите точность решения: ").strip())

    result = Result.solveByEuler(f, a, y_a, b, h)

    print("Ответ:", str(result))
