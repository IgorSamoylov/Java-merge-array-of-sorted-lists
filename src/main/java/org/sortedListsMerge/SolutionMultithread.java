/*
This class merges sorted linked lists, which given in a array of them firsts Nodes.
This class uses fixed Executors thread pool and ArrayDeque of Futures for multithreading processing.
 */

package org.sortedListsMerge;

import java.util.*;
import java.util.concurrent.*;

public class SolutionMultithread implements TestSolution {
    private final int THREADS_NUMBER = 4;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);
    private final Queue<Merge2Lists> tasks = new LinkedList<>();
    private final Queue<ListNode> nodeDeque = new LinkedList<>();

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        Collections.addAll(nodeDeque, lists);

        while(nodeDeque.size() != 1) {
            while (nodeDeque.size() >= 2) {
                tasks.add(new Merge2Lists(nodeDeque.poll(), nodeDeque.poll()));
            }
            try {
                List<Future<ListNode>> futures = executorService.invokeAll(tasks);
                tasks.clear();
                while (!futures.isEmpty())
                    nodeDeque.add(futures.remove(0).get());
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }
        executorService.shutdown();

        return nodeDeque.poll();
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
