package org.sortedListsMerge;


public class App {
    public static void main(String... args) {

        Test(new Solution(), "Simple bruteforce solution with JAVA integrated sort methods", 32);
        Test(new SolutionDivideAndConquer(), "Advanced one-thread solution with divide and conquer method", 32);
        Test(new SolutionMultithreading(), "Queue of Futures", 32);
        Test(new SolutionPhaser(), "Runnable and Phaser class synchronization", 32);
    }

    // Creates linked list of ListNodes with random size
    public static ListNode createList() {
        ListNode currentNode = new ListNode();
        ListNode firstNode = currentNode;
        ListNode secondNode;
        int randomLength = (int) (Math.random() * 100);
        int currentMaxValue = 0;
        for (int i = 0; i < randomLength; ++i) {
            secondNode = new ListNode();

            currentMaxValue += (int) (Math.random() * 100);
            currentNode.val = currentMaxValue;

            currentNode.next = secondNode;
            currentNode = secondNode;
        }
        currentNode.val = currentMaxValue + 5; // Set max value for the last node

        //System.out.println("New LIST");
        //printList(firstNode);

        return firstNode;
    }

    public static void printList(ListNode inputListNode) {
        while (inputListNode != null) {
            System.out.println(inputListNode.val);
            inputListNode = inputListNode.next;
        }

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




