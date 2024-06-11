/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormimerge;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;

class ForkJoin extends RecursiveAction {
    private final int[] arr;
    private final int low;
    private final int high;

    public ForkJoin(int[] arr, int low, int high) {
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (low < high) {
            int mid = (low + high) / 2;
            ForkJoin leftTask = new ForkJoin(arr, low, mid);
            ForkJoin rightTask = new ForkJoin(arr, mid + 1, high);
            invokeAll(leftTask, rightTask);
            merge(arr, low, mid, high);
        }
    }

    private void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low, j = mid + 1, k = 0;

        while (i <= mid && j <= high) {
            if (arr[i] <= arr[j])
                temp[k++] = arr[i++];
            else
                temp[k++] = arr[j++];
        }

        while (i <= mid)
            temp[k++] = arr[i++];

        while (j <= high)
            temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, low, temp.length);
    }
}

class ForkJoinSortTest {
    
    double elapsedTime;
    double aux=0;
    String sortedArrayString;

    public ForkJoinSortTest(int[] arr) {
        ForkJoin sortTask = new ForkJoin(arr, 0, arr.length - 1);
        ForkJoinPool pool = new ForkJoinPool();
        
        long startTime = System.nanoTime();
        pool.invoke(sortTask);
        long endTime = System.nanoTime();

        elapsedTime = endTime - startTime;
        aux=elapsedTime/1_000_000;
        
        sortedArrayString = Arrays.toString(arr);
    }

    double Time() {
        double aux2 = 0;
        String tiempo = null;
        aux2 = aux;
        //tiempo=aux2;
        
        return aux2;
    }

    String getSortedArrayString() {
        return sortedArrayString;
    }

    public void main(int[] arr) {
        ForkJoinSortTest sortTest = new ForkJoinSortTest(arr);

        //System.out.println("Sorted Array: " + sortTest.getSortedArrayString());
        //System.out.println("Elapsed Time (nanoseconds): " + sortTest.getElapsedTime());
    }
}
