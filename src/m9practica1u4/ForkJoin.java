package m9practica1u4;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;



public class ForkJoin extends RecursiveTask<Integer> {

    private final int [] array;
    private final int inici, finale;

    public ForkJoin(int [] arr, int ini, int fin) {
        this.array = arr;
        this.inici = ini;
        this.finale = fin;
    }

    /**
     * En el compute() es calcula el sou maxim emmagatzemat en l'array.
     * @return 
     */
    @Override
    protected Integer compute() {
        int sueldoMax = array[0];
        if (finale - inici <= 1) {
            
            for (int i = 0; i < array.length - 1; i++) {
                if(array[i] > sueldoMax){
                    sueldoMax = array[i];
                }
            }
            
         return sueldoMax;
        }else {
        //Dividir el problema en parts més petites
         int mitat = inici + (finale - inici) / 2;
         ForkJoin forkJoin1 = new ForkJoin(array, inici, mitat);
         ForkJoin forkJoin2 = new ForkJoin(array, mitat + 1, finale);
         invokeAll(forkJoin1, forkJoin2);
         return Math.max(forkJoin1.join(), forkJoin2.join());
        }
    }





    public static void main(String[] args) {
        int sueldos[] = new int[20000];

        for (int i = 1; i < 20000; i++) {
            sueldos[i - 1] = (int) (Math.random() * (0 - (50000 + 0)) + (50000));
        }


        int NumberOfProcessors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(NumberOfProcessors);
        ForkJoin tasca = new ForkJoin(sueldos, 0, sueldos.length - 1);
        Integer result = pool.invoke(tasca);
        
        System.out.println("Resultat és: " + result);
        
    }

}
