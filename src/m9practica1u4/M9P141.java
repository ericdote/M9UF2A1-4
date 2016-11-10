package m9practica1u4;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class M9P141 extends RecursiveTask<Integer> {

    private final int[] array;
    private final int inici, fin;

    public M9P141(int[] array, int inici, int fin) {
        this.array = array;
        this.inici = inici;
        this.fin = fin;
    }

    public static void main(String[] args) {
        int sous[] = new int[20000];
        for (int i = 1; i < 20000; i++) {
            sous[i - 1] = (int) (Math.random() * (0 - (50000 + 0)) + (50000));
        }
        int procesos = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(procesos);
        M9P141 tasca = new M9P141(sous, 0, sous.length - 1);
        Integer resultat = pool.invoke(tasca);
        System.out.println("El sou mes alt es: " + resultat);
    }

    @Override
    protected Integer compute() {
        int souMaxim = array[0];
        if (fin - inici <= 1) {
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > souMaxim) {
                    souMaxim = array[i];
                }
            }
            return souMaxim;
        } else {
            //Dividim les tasques en tasques més petites per agilitzar la tasca.           
            int mitat = inici + (fin - inici) / 2;
            M9P141 forkJoin1 = new M9P141(array, inici, mitat);
            M9P141 forkJoin2 = new M9P141(array, mitat + 1, fin);
            invokeAll(forkJoin1, forkJoin2);
            return Math.max(forkJoin1.join(), forkJoin2.join());
        }
    }
}
