/*
This class merges sorted linked lists, which given in a array of them firsts Nodes.
This class uses fixed Executors thread pool and ArrayDeque of Futures for multithreading processing.
 */

package org.sortedListsMerge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class SolutionMultithreading implements TestSolution {

    private final int THREADS_NUMBER = 4;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        List<Future<ListNode>> futures;
        List<Merge2Lists> tasks = new ArrayList<>(lists.length);
        try {
            int interval = 1;
            while (interval < lists.length) {

                // Creating the merge tasks of a contiguous lists pairs
                for (int i = 0; i + interval < lists.length; i = i + (interval * 2)) {
                    tasks.set(i, new Merge2Lists(lists[i], lists[i + interval]));
                }

                futures = executorService.invokeAll(tasks);
                //tasks.clear();

                // Retrieving heads of sorted linked lists with preserving order
                int count = 0;
                for (int i = 0; i + interval < lists.length; i = i + (interval * 2))
                    lists[i] = futures.get(i).get();

                interval *= 2;
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        executorService.shutdown();
        return lists[0];
    }
}

// Merges two sorted linked lists in a one
class Merge2Lists implements Callable<ListNode> {

    private ListNode first, second;

    Merge2Lists(ListNode first, ListNode second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ListNode call() {
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
