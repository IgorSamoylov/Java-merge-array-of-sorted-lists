/*
This class merges sorted linked lists, which given in a array of them firsts Nodes.
This class uses Runnable methods and synchronization provided by JAVA Phaser class.
 */
package org.sortedListsMerge;

import java.util.concurrent.*;

public class SolutionPhaser implements TestSolution{

    public static ListNode[] runnables;
    private Phaser phaser;

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        runnables = lists;

        int interval = 1;

        while (interval < lists.length) {

            phaser = new Phaser(1);

            for (int i = 0; i + interval < lists.length; i = i + (interval << 1)) { // Bit shift multiplies the value by two
                Runnable runnable = new Merge2ListsPh(i, lists[i], lists[i + interval], phaser);
            // May creates too many threads if input array is long and causes heap memory overflow error
                Thread thread = new Thread(runnable);

                thread.start();
            }

            phaser.arriveAndAwaitAdvance();
            phaser.arriveAndDeregister();

            interval <<= 1;
        }

        return lists[0];
    }
}


class Merge2ListsPh implements Runnable {

    private int i;
    private ListNode first, second;
    private Phaser phaser;


    Merge2ListsPh(int i, ListNode first, ListNode second, Phaser phaser) {
        this.i = i;
        this.first = first;
        this.second = second;
        this.phaser = phaser;

        phaser.register();
    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getName() + " started!");

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
        int arrived = phaser.arrive();
        //System.out.println(Thread.currentThread().getName() + " is arrived!" + arrived);
    }
}
