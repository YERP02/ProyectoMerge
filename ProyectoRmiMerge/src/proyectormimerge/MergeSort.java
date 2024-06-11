/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormimerge;

import java.util.Arrays;


public class MergeSort {
    
    double aux= 0;
    String ArregloS = "";
    String ArregloA = "";

    void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];


        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }


    void sort(int arr[], int l, int r) {
        if (l < r) {

            int m = l + (r - l) / 2;

            sort(arr, l, m);
            sort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

   static String printArray(int arr[]) {
        StringBuilder sb = new StringBuilder();
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            sb.append(arr[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    void main(int arr[]) {
        /*Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
            ArregloS += arr[i]+ ",";
        }*/
        
        
        //System.out.println("Arreglo creado:");
        //printArray(arr);
        
        MergeSort ob = new MergeSort();
        
        long startTime = System.nanoTime();
        ob.sort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();
        
        //System.out.println("\nArreglo Organizado");
        ArregloA = printArray(arr);
        
        //System.out.println("Tiempo de Secuencial: " + (endTime - startTime) + " millisegundos");
        aux= endTime - startTime;
        aux=aux/1_000_000;
    }
    
    double time(){
        
        double aux2 = 0;
        String tiempo = null;
        aux2 = aux;
        //tiempo=aux2;
        
        return aux2;
    }
    
    String Arreglo(){
        String Arreglo = null;
        Arreglo = ArregloS;
        return Arreglo;
    }
    
    String Arreglo2(){
        String Arreglo = null;
        Arreglo = ArregloA;
        return Arreglo;
    }
}
