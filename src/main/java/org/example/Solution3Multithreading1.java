package org.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Solution3Multithreading1 {

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        int interval = 1;
        while (interval < lists.length) {

            ExecutorService executorService = Executors.newFixedThreadPool(4);
            Future<ListNode>[] futures = new Future[lists.length];

            int n = 0;
            for (int i = 0; i + interval < lists.length; i = i + (interval << 1)) {
                Callable<ListNode> callable = new Merge2Lists2(lists[i], lists[i + interval]);

                futures[i] = executorService.submit(callable);
                n++;
            }


            int complete;
            do {
                complete = 0;
                for (Future<ListNode> future : futures) {
                    if (future != null && future.isDone()) complete++;
                }
            }
            while (complete == n);

            for (int i = 0; i + interval < lists.length; i = i + (interval << 1)) {

                try {
                    if (futures[i] != null) lists[i] = futures[i].get();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            executorService.shutdown();

            interval <<= 1;
        }
        return lists[0];
    }
}


class Merge2Lists2 implements Callable<ListNode> {

    private ListNode first, second;

    Merge2Lists2(ListNode first, ListNode second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ListNode call() {
        //System.out.println(Thread.currentThread().getName() + " started!");

        ListNode tmp = new ListNode(0);
        ListNode output = tmp;

        while (first != null && second != null) {

            if (first.val < second.val) {
                tmp.next = first;
                tmp = tmp.next;
                first = first.next;
            } else {
                tmp.next = second;
                tmp = tmp.next;
                second = second.next;
            }
        }
        if (first == null) tmp.next = second;
        if (second == null) tmp.next = first;

        return output.next;
    }
}

