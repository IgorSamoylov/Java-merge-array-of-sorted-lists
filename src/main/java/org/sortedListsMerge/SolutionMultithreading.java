/*
This class merges sorted linked lists, which given in a array of them firsts Nodes.
This class uses fixed Executors thread pool and ArrayList of Futures for multithreading processing.
 */

package org.sortedListsMerge;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SolutionMultithreading implements TestSolution {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final ArrayList<Future<ListNode>> futures = new ArrayList<>();

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        int interval = 1;
        while (interval < lists.length) {


            for (int i = 0; i + interval < lists.length; i = i + (interval << 1)) { // Bit shift multiplies the value by two
                Callable<ListNode> callable = new Merge2Lists(lists[i], lists[i + interval]);

                futures.add(executorService.submit(callable));
            }

            int complete;
            do {
                complete = 0;
                for (Future<ListNode> future : futures) {
                    if (future.isDone()) complete++;
                }
            }
            while (complete == futures.size());

            int n = 0;
            for (int i = 0; i + interval < lists.length; i = i + (interval << 1)) {

                try {
                    lists[i] = futures.get(n).get();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                n++;
            }

            futures.clear();

            interval <<= 1;
        }
        executorService.shutdown();
        return lists[0];
    }
}


class Merge2Lists implements Callable<ListNode> {

    private ListNode first, second;

    Merge2Lists(ListNode first, ListNode second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ListNode call() {
        //System.out.println(Thread.currentThread().getName() + " started!");

        if (first == null && second == null) return null;

        ListNode tmp = new ListNode(0);
        ListNode output = tmp;

        while (first != null && second != null) {

            if (first.val < second.val) {
                tmp.next = first;
                tmp = first;

                first = first.next; // Iterate one step throw the first list

            } else {
                tmp.next = second;
                tmp = second;

                second = second.next; //Iterate one step throw the second list
            }
        }

       if (first == null) tmp.next = second;
       if (second == null) tmp.next = first;

        return output.next;
    }
}
