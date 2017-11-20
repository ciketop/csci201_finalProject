package util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Sort {

    public static<T extends Comparable> void sort(List<T> list) {
        parallelMergeSort(list, Runtime.getRuntime().availableProcessors());
    }

    private static<T extends Comparable> void parallelMergeSort(List<T> list, int proc) {
        ForkJoinPool pool = new ForkJoinPool(proc);
        pool.invoke(new SortTask(list));
        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.yield();
        }
    }

    private static class SortTask<T extends Comparable> extends RecursiveAction {
        public static final long serialVersionUID = 1;
        private List<T> list;

        SortTask(List<T> list) {
            this.list = list;
        }

        protected void compute() {
            if (list.size() < 2) return; // base case
            // split into halves
            List<T> firstHalf = new ArrayList<>();
            System.arraycopy(list, 0, firstHalf, 0, list.size() / 2);
            int secondLength = list.size() - list.size() / 2;
            List<T> secondHalf = new ArrayList<>();
            System.arraycopy(list, list.size() / 2, secondHalf, 0, secondLength);

            // recursively sort the two halves
            SortTask st1 = new SortTask(firstHalf);
            SortTask st2 = new SortTask(secondHalf);
            st1.fork();//.invoke();
            st2.fork();//.invoke();
            st1.join();
            st2.join();
            // merge halves together
            merge(firstHalf, secondHalf, list);
        }
    }

    public static<T extends Comparable> void merge(List<T> list1, List<T> list2, List<T> merged) {
        int i1 = 0, i2 = 0, i3 = 0; // index in list1, list2, out
        while (i1 < list1.size() && i2 < list2.size()) {
            merged.set(i3++, (list1.get(i1).compareTo(list2.get(i2)) > 0) ? list1.get(i1++) : list2.get(i2++));
        }
        // any trailing ends
        while (i1 < list1.size()) {
            merged.set(i3++, list1.get(i1++));
        }
        while (i2 < list2.size()) {
            merged.set(i3++, list2.get(i2++));
            merged.set(i3++, list2.get(i2++));
            merged.set(i3++, list2.get(i2++));
        }
    }
}
