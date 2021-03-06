package by.it.group873601.kravchenko.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/*
Задача на программирование: наибольшая невозростающая подпоследовательность

Дано:
    целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] не больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]>=A[i[j+1]].

    В первой строке выведите её длину k,
    во второй - её индексы i[1]<i[2]<…<i[k]
    соблюдая A[i[1]]>=A[i[2]]>= ... >=A[i[n]].

    (индекс начинается с 1)

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    5 3 4 4 2

    Sample Output:
    4
    1 3 4 5
*/


public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        //тут реализуйте логику задачи методами динамического программирования (!!!)
        int result = 0;

        //алгоритм снизу вверх
        int[] memoization = new int[n];
        int[] prev = new int[n];//то, через какую подзадачу этот оптимальный ответ реализовался
        for (int i = 0; i < n; i++) {
            memoization[i] = 1;
            prev[i] = -1;//индекс пред элемента для соотвт наибольшей возрастающей последовательности
            //сначала -1, т.к. нет пред

            for (int j = 0; j <= i - 1; j++) {
                if (m[j] >= m[i] && (memoization[j] + 1) > memoization[i]) {
                    memoization[i] = memoization[j] + 1; //заполняем согласно реккурентному соотношению
                    //  memoization[i] = длина кратчайшей подпоследовательности заканчивающая в [i] элементе

                    prev[i] = j;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            result = Math.max(result, memoization[i]);
        }

        int[] indexResults = new int[result];

        int resIndex = 0;
        for (int i = 1; i < n; i++)
            if (memoization[i] > memoization[resIndex])
                resIndex = i; //  индекс, в котором реализуется максимум в массиве мемоизации (последний индекс)

        int j = resIndex - 1; //заполняем конечный массив индексов справа налево
        while (resIndex >= 0) {
            indexResults[j] = resIndex + 1;
            j--;
            resIndex = prev[resIndex];
        }
        System.out.println(Arrays.toString(indexResults));

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}
