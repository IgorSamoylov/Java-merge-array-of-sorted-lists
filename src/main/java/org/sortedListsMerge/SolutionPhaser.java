/*
This class merges sorted linked lists, which given in a array of them firsts Nodes.
This class uses Runnable task class and synchronization provided by JAVA Phaser class.
 */
package org.sortedListsMerge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class SolutionPhaser implements TestSolution{
    private final int THREADS_NUMBER = 4;
    private ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);
    private Phaser phaser = new Phaser(1);

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        int interval = 1;
        while (interval < lists.length) {
            // Sending merge tasks of a contiguous lists pairs
            for (int i = 0; i + interval < lists.length; i = i + interval * 2) {
                executorService.submit(new Merge2ListsPh(lists, i,i + interval, phaser));
            }
            // Waiting on phaser object
            phaser.arriveAndAwaitAdvance();

            interval *= 2;
        }

        phaser.arriveAndDeregister();
        executorService.shutdown();
        return lists[0];
    }
}

// Merges two sorted linked lists in a one
class Merge2ListsPh implements Runnable {

    private final ListNode[] lists;
    private final int targetIndex;
    private ListNode firstNode, secondNode;
    private final Phaser phaser;


    Merge2ListsPh(ListNode[] lists, int first, int second, Phaser phaser) {
        this.lists = lists;
        this.targetIndex = first;
        this.firstNode = lists[first];
        this.secondNode = lists[second];
        this.phaser = phaser;

        phaser.register();
    }

    @Override
    public void run() {

        ListNode tmp = new ListNode(0);
        ListNode output = tmp;

        // Merging
        while (firstNode != null && secondNode != null) {
            if (firstNode.val < secondNode.val) {
                tmp.next = firstNode;
                tmp = firstNode;
                firstNode = firstNode.next; // Iterate one step throw the first list
            } else {
                tmp.next = secondNode;
                tmp = secondNode;
                secondNode = secondNode.next; //Iterate one step throw the second list
            }
        }
        // Appending tails
        if (firstNode == null) tmp.next = secondNode;
        if (secondNode == null) tmp.next = firstNode;

        lists[targetIndex] = output.next;

        // Calls to the phaser object about a completed task
        phaser.arriveAndDeregister();
    }
}
