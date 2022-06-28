package org.sortedListsMerge;

public class Util {
    // Creates linked list of ListNodes with a random size
    public static ListNode createList() {
        ListNode currentNode = new ListNode();
        ListNode firstNode = currentNode;
        ListNode secondNode;
        int randomLength = (int) (Math.random() * Constants.LISTS_MAX_LENGTH);
        int currentMaxValue = 0;
        for (int i = 0; i < randomLength; ++i) {
            secondNode = new ListNode();

            currentMaxValue += (int) (Math.random() * Constants.LISTS_MAX_VALUE);
            currentNode.val = currentMaxValue;

            currentNode.next = secondNode;
            currentNode = secondNode;
        }
        currentNode.val = currentMaxValue + 5; // Set max value for the last node

        return firstNode;
    }

    public static void printList(ListNode inputListNode) {
        while (inputListNode != null) {
            System.out.print(inputListNode.val + " ");
            inputListNode = inputListNode.next;
        }
        System.out.println();
    }
}
