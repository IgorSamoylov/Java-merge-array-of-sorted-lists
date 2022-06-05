package org.sortedListsMerge;


public class App {

    private static final int LISTS_NUMBER = 320;
    private static final int LISTS_MAX_LENGTH = 1000;
    private static final int LISTS_MAX_VALUE = 100;

    public static void main(String... args) {

        Test(new Solution(), "Simple bruteforce solution with JAVA integrated sort methods", LISTS_NUMBER);
        Test(new SolutionDivideAndConquer(), "Advanced one-thread solution with divide and conquer method", LISTS_NUMBER);
        Test(new SolutionMultithreading(), "Queue of Futures", LISTS_NUMBER);
        Test(new SolutionPhaser(), "Runnable and Phaser class synchronization", LISTS_NUMBER);
    }

    // Creates linked list of ListNodes with random size
    public static ListNode createList() {
        ListNode currentNode = new ListNode();
        ListNode firstNode = currentNode;
        ListNode secondNode;
        int randomLength = (int) (Math.random() * LISTS_MAX_LENGTH);
        int currentMaxValue = 0;
        for (int i = 0; i < randomLength; ++i) {
            secondNode = new ListNode();

            currentMaxValue += (int) (Math.random() * LISTS_MAX_VALUE);
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

    private static void Test(TestSolution solution, String name, int listsNumber) {
        ListNode[] TestArray = new ListNode[listsNumber];

        for (int i = 0; i < listsNumber; ++i) {
            TestArray[i] = createList();
        }

        long startTime = System.nanoTime();
        ListNode outputList = solution.mergeKLists(TestArray);
        long finishTime = System.nanoTime();
        long result = (finishTime - startTime) / 1000_000;
        System.out.println(name + " " + result + " milliseconds");
        //printList(outputList);

    }
}




