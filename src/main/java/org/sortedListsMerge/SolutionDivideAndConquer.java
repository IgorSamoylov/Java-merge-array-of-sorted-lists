package org.sortedListsMerge;


public class SolutionDivideAndConquer implements TestSolution {

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        int interval = 1;
        while(interval < lists.length) {
            for (int i = 0; i + interval < lists.length; i = i + interval * 2) {
                lists[i] = merge2Lists(lists[i], lists[i + interval]);
            }
            interval *= 2;
        }

        return lists[0];
    }

    private ListNode merge2Lists(ListNode first, ListNode second) {

        ListNode tmp = new ListNode(0);
        ListNode output = tmp;

        while(first != null && second != null) {

            if(first.val < second.val) {
                tmp.next = first;
                tmp = first;
                first = first.next;}
            else {
                tmp.next = second;
                tmp = second;
                second = second.next;
            }

            if (first == null) tmp.next = second;
            if (second == null) tmp.next = first;

        }
    return output.next;
    }
}
