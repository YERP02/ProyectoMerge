/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormimerge;


import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecuteService {
    
    private int tam;
    private int vec[];
    Double aux = 0.0;
    long startTime = System.nanoTime();
    private static final int tamaño = 8;

    public ExecuteService(int vec[]){
        //this.tam = tam;
        this.vec = vec;
    }

    public int[] Service(int[] vec){
        int[] vector = {};
        ExecutorService executorService = Executors.newFixedThreadPool(tamaño);
        Future<int[]> futureSortedArray = executorService.submit(() -> MergeSort(vec));

        try{
            vector = futureSortedArray.get();
            return vector;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            
            NuevoM();
        }
        return vector;
    }

    public void NuevoM(){
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  

        double milliseconds = duration / 1_000_000.0;
        aux = milliseconds;

        //System.out.println("Tiempo de ejecución de EXECUTOR: " + milliseconds + " milisegundos");
    }

    double time(){
        return aux;
    }

    public static int[] MergeSort(int vec[]){
    if(vec.length <= 1) return vec;
    int mitad = vec.length/2;
    int izq[] = Arrays.copyOfRange(vec, 0, mitad);
    int der[] =  Arrays.copyOfRange(vec, mitad, vec.length);
    return Combinar(MergeSort(izq), MergeSort(der));
    }

    public static int[] Combinar(int izq[], int der[]){
    int i=0;
    int j=0;
    int[] v = new int[izq.length + der.length];
    for(int k=0;k<v.length;k++){
        if(i>=izq.length){
            v[k]=der[j];
            j++;
            continue;
        }
        if(j>=der.length){
            v[k]=izq[i];
            i++;
            continue;
        }
        if(izq[i]<der[j]){
            v[k]=izq[i];
            i++;
        }else{
            v[k]=der[j];
            j++;
        }
    }
    return v;
}
}
