import numpy as np
import math

size = 0
matrix = list()

def get_matrix():
    global size
    size = int(input("Введите размер матрицы: "))
    print("Введите значения матрицы")
    for i in range(size):
        matrix.append(list(map(float, input().split())))


def matrix_is_correct() -> bool:
    for i in range(size):
        if len(matrix[i]) != size:
            return False
        for j in range(size):
            if matrix[i][j] != matrix[j][i]:
                return False
    return True


def get_e_matrix():
    e = np.zeros((size, size))
    for i in range(size):
        e[i][i] = 1
    return e


def cholesky():
    t = np.zeros((size, size))
    tr_t = np.zeros((size, size))
    t[0][0] = math.sqrt(matrix[0][0])
    for j in range(1, size):
        t[0][j] = matrix[0][j] / t[0][0]
    for i in range(1, size):
        t[i][i] = matrix[i][i]
        for k in range(i):
            t[i][i] -= math.pow(t[k][i], 2)
        t[i][i] = math.sqrt(t[i][i])
        for j in range(i + 1, size):
            t[i][j] = matrix[i][j]
            for k in range(i):
                t[i][j] -= t[k][i] * t[k][j]
            t[i][j] /= t[i][i]
    for i in range(size):
        for j in range(size):
            tr_t[i][j] = t[j][i]
    return t, tr_t


def inversed_matrix(t, tr_t):
    y = np.linalg.solve(tr_t, get_e_matrix())
    x = np.linalg.solve(t, y)
    return x


if __name__ == "__main__":
    get_matrix()
    if matrix_is_correct():
        t, tr_t = cholesky()
        print("\nОбратная матрица:\n", inversed_matrix(t, tr_t))
        print(t)
    else:
        print("Матрица не подходит")