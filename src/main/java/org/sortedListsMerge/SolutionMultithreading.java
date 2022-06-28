/*
This class merges sorted linked lists, which given in a array of them firsts Nodes.
This class uses fixed Executors thread pool and ArrayDeque of Futures for multithreading processing.
 */

package org.sortedListsMerge;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.*;

public class SolutionMultithreading implements TestSolution {

    private final int THREADS_NUMBER = 4;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        Deque<Future<ListNode>> futures = new ArrayDeque<>();
        Merge2Lists[] tasks = new Merge2Lists[lists.length];

        try {
            int interval = 1;
            while (interval < lists.length) {
                // Creating the merge tasks of a contiguous lists pairs
                for (int i = 0; i + interval < lists.length; i = i + interval * 2) {
                    futures.add(executorService.submit(new Merge2Lists(lists[i], lists[i + interval])));
                }
                // TODO
                while(true) {
                    futures.
                }
                // Retrieving heads of sorted linked lists with preserving order
                for (int i = 0; i + interval < lists.length; i = i + interval * 2)
                    lists[i] = futures.pollFirst().get();

                interval *= 2;
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        } finally {
            executorService.shutdown();
        }

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
        ListNode result = tmp;

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

        return result.next;
    }
}
