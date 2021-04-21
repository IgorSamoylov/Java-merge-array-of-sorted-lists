package org.example;


public class App {
    public static void main(String[] args) throws Exception {
        Solution3Multithreading solution0 = new Solution3Multithreading();
        Solution3Multithreading1 solution = new Solution3Multithreading1();

        ListNode[] TestArray = new ListNode[16];


        for (int i = 0; i < 16; ++i) {
            TestArray[i] = createList();
        }

        long startTime = System.nanoTime();
        ListNode outputList = solution.mergeKLists(TestArray);
        long finishTime = System.nanoTime();
        long result = finishTime - startTime;
        System.out.println("Solution with the primitive array of Future : " + result + " nanoseconds");
        printList(outputList);

        for (int i = 0; i < 16; ++i) {
            TestArray[i] = createList();
        }

        Thread.sleep(1000);


        startTime = System.nanoTime();
        outputList = solution0.mergeKLists(TestArray);
        finishTime = System.nanoTime();
        result = finishTime - startTime;
        System.out.println("Solution with the ArrayList of Future: " + result + " nanoseconds");
        printList(outputList);

    }

    // Creates linked list of ListNodes with random size
    public static ListNode createList() {
        ListNode currentNode = new ListNode();
        ListNode firstNode = currentNode;
        ListNode secondNode;
        int randomLength = (int)(java.lang.Math.random() * 100);
        for (int i = 0; i < randomLength; ++i) {
            secondNode = new ListNode();
            currentNode.val = i;
            currentNode.next = secondNode;
            currentNode = secondNode;
        }
        currentNode.val = randomLength;

        return firstNode;
    }

    public static void printList(ListNode inputListNode) {
        while (inputListNode != null) {
            System.out.println(inputListNode.val);
            inputListNode = inputListNode.next;
        }

    }
}
