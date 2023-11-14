import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
    int[] arr;
    QuickSort(int n) {
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        StdRandom.shuffle(arr);
    }

    // lo is the pivot element here
    public static int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;

        while (true) {
            while (a[lo] > a[++i])
                if (i == hi) break;
            while (a[lo] < a[--j])
                if (j == lo) break;

            if (i >= j) break;
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
        int tmp = a[j];
        a[j] = a[lo];
        a[lo] = tmp;
        return j;
    }
    public static void qsort(int[] a) {
        StdRandom.shuffle(a);
        qsort(a, 0, a.length-1);
    }
    public static void qsort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int pivot = partition(a, lo, hi);
        qsort(a, lo, pivot - 1);
        qsort(a, pivot + 1, hi);
    }

    public static void printArr(int[]a) {
        for (int j : a) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        QuickSort q = new QuickSort(10);
        printArr(q.arr);
        qsort(q.arr);
        printArr(q.arr);
    }
}
