/*
This class merges sorted linked lists, which given in a array of them firsts Nodes.
This class uses Runnable task class and synchronization provided by JAVA Phaser class.
 */
package org.sortedListsMerge;

import java.util.concurrent.*;

public class SolutionPhaser implements TestSolution{

    public static ListNode[] runnables;
    private Phaser phaser;
    private final int THREADS_NUMBER = 4;

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        runnables = lists;
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);

        int interval = 1;
        while (interval < lists.length) {

            phaser = new Phaser(1);
            // Sending merge tasks of a contiguous lists pairs
            for (int i = 0; i + interval < lists.length; i = i + (interval << 1)) {
                executorService.submit(new Merge2ListsPh(i, lists[i], lists[i + interval], phaser));
            }
            // Waiting on the phaser object
            phaser.arriveAndAwaitAdvance();
            phaser.arriveAndDeregister();

            interval <<= 1;
        }
        executorService.shutdown();
        return lists[0];
    }
}

// Merges two sorted linked lists in a one
class Merge2ListsPh implements Runnable {

    private final int i;
    private ListNode first, second;
    private final Phaser phaser;


    Merge2ListsPh(int i, ListNode first, ListNode second, Phaser phaser) {
        this.i = i;
        this.first = first;
        this.second = second;
        this.phaser = phaser;

        phaser.register();
    }

    @Override
    public void run() {

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

        SolutionPhaser.runnables[i] = output.next;
        // Calls to the phaser object about a completed task
        phaser.arrive();
    }
}
