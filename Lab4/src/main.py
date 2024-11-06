import math

import numpy as np
import matplotlib.pyplot as plt

A, B, C, D = list(), list(), list(), list()


def get_a_matrix(dots_size):
    size = dots_size - 2
    result = []
    for i in range(size):
        result.append([0] * size)
    for i in range(size):
        result[i][i] = 4
        if i > 0:
            result[i][i - 1] = 1
        try:
            result[i][i + 1] = 1
        except IndexError:
            pass
    return result


def get_b_matrix(Y, H):
    return [6 / (H[i] ** 2) * (Y[i - 1] - 2 * Y[i] + Y[i + 1]) for i in range(1, len(Y) - 1)]


def get_m_coefficients(X, Y):
    dots_quantity = len(Y)
    H = [X[i + 1] - X[i] for i in range(dots_quantity - 1)]
    A, B = get_a_matrix(dots_quantity), get_b_matrix(Y, H)
    M = [0] + solve(A, B) + [0]
    return M


# Метод прогонки
def solve(A, B):
    n = len(B) - 1
    P, Q = [0] * n, [0] * n
    for i in range(n):
        alpha = A[i][i - 1] if i > 0 else 0
        beta = -A[i][i]
        gamma = A[i][i + 1]
        sigma = B[i]
        P[i] = gamma / (beta - alpha * P[i - 1]) if i > 0 else gamma / beta
        Q[i] = (alpha * Q[i - 1] - sigma) / (beta - alpha * P[i - 1]) if i > 0 else - sigma / beta
    result = [0] * (n + 1)
    result[n] = (alpha * Q[n - 1] - sigma) / (beta - alpha * P[n - 1])
    for i in range(n - 1, -1, -1):
        result[i] = P[i] * result[i + 1] + Q[i]
    return result


def interpolate_by_spline(X, Y, x):
    const_size = len(X) - 1
    M = get_m_coefficients(X, Y)

    for i in range(const_size):
        h = X[i + 1] - X[i]
        A.append((M[i + 1] - M[i]) / (6 * h))
        B.append(M[i] / 2)
        C.append((Y[i + 1] - Y[i]) / h - (M[i + 1] + 2 * M[i]) * h / 6)
        D.append(Y[i])

    for i in range(const_size):
        if X[i] <= x <= X[i + 1]:
            x -= X[i]
            return A[i] * pow(x, 3) + B[i] * pow(x, 2) + C[i] * x + D[i]


def visualize_result(x_axis, y_axis, x, y):
    for i in range(len(x_axis) - 1):
        X = np.linspace(x_axis[i], x_axis[i + 1], 1000)
        Y = [A[i] * pow(x - x_axis[i], 3) + B[i] * pow(x - x_axis[i], 2) + C[i] * (x - x_axis[i]) + D[i] for x in X]
        plt.plot(X, Y, "r")
        plt.plot(x_axis, y_axis, 'bo')
        plt.plot(x, y, 'go')
    plt.show()


if __name__ == '__main__':
    print("Метод интерполяции кубическими сплайнами\n")
    x_axis = list(map(float, input("Введите координаты x: ").rstrip().split()))
    y_axis = list(map(float, input("Введите координаты y: ").rstrip().split()))

    x = float(input("Введите координату x неизвестной точки: ").strip())

    result = interpolate_by_spline(x_axis, y_axis, x)

    print("\nРезультат: ", result)

    visualize_result(x_axis, y_axis, x, result)
