package by.it.group873602.sheiko.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии,
            - за сам массив отрезков - сортировка на месте
            - рекурсионные вызовы должны проводится на основе 3-разбиения

        2) при поиске подходящих отрезков для точки реализуйте метод бинарного поиска,
        помните при реализации, что поиск множественный
        (т.е. отрезков, подходящих для точки, может быть много)

    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/


public class C_QSortOptimized {

    //отрезок
    private class Segment{
        int start;
        int stop;

        Segment(int start, int stop){
            if (start > stop){
                this.start = stop;
                this.stop = start;
            }
            else{
                this.start = start;
                this.stop = stop;
            }
        }

        public int compareTo(Segment o) {
            //подумайте, что должен возвращать компаратор отрезков
            return Integer.compare(this.start, o.start);
        }
    }

    void quickSort(Segment[] segments, int left, int right){
        Segment v = segments[right];
        if (right <= left){
            return;
        }

        // между {l и p} и {q и r} равен v
        // между {p и i} меньше v
        // между {j и q} и больше v

        int i = left;
        int j = right - 1;
        int p = left - 1;
        int q = right;
        while (i <= j) {
            while (segments[i].compareTo(v) < 0) {
                i++;
            }
            while (segments[j].compareTo(v) > 0) {
                j--;
            }
            if (i >= j)
                break;
            swap(segments, i, j);
            if (segments[i].compareTo(v) == 0){
                p++;
                swap(segments, p, i);
            }
            i++;
            if (segments[j].compareTo(v) == 0){
                q--;
                swap(segments, q, j);
            }
            j--;
        }
        swap(segments, i, right);
        j = i - 1;
        i++;
        for (int k = left; k <= p; k++, j--) {
            swap(segments, k, j);
        }
        for (int k = right - 1; k >= q; k--, i++) {
            swap(segments, k, i);
        }
        quickSort(segments, left, j);
        quickSort(segments, i, right);
    }

    void swap(Segment[] segment, int left, int right){
        Segment tmp = segment[left];
        segment[left] = segment[right];
        segment[right] = tmp;
    }



    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments=new Segment[n];
        //число точек
        int m = scanner.nextInt();
        int[] points=new int[m];
        int[] result=new int[m];

        //читаем сами отрезки
        for (int i = 0; i < n; i++) {
            //читаем начало и конец каждого отрезка
            segments[i]=new Segment(scanner.nextInt(),scanner.nextInt());
        }
        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением быстрой сортировки
        //в классе отрезка Segment реализуйте нужный для этой задачи компаратор

        quickSort(segments, 0, segments.length - 1);

        //меньше старта середины - влевой части
        //больше конца середины - в правой части

        for (int i = 0; i < points.length; i++){
            int counter = 0;
            for (int j = 0; j < segments.length; j++) {
                if (points[i] >= segments[j].start && points[i] <= segments[j].stop){
                    counter++;
                }
            }
            result[i] = counter;
        }






        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
