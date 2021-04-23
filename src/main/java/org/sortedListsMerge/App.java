package org.sortedListsMerge;


public class App {
    public static void main(String... args) {

        Test(new Solution(), "Simple bruteforce solution with JAVA integrated sort methods");
        Test(new Solution3(), "Advanced one-thread solution with divide and conquer method");
        Test(new SolutionFixedThreadArray(), "Fixed Thread Array");
        Test(new SolutionMultithreading(), "ArrayList of Threads");
        Test(new SolutionPhaser(), "Runnable and Phaser class synchronization");
    }

    // Creates linked list of ListNodes with random size
    public static ListNode createList() {
        ListNode currentNode = new ListNode();
        ListNode firstNode = currentNode;
        ListNode secondNode;
        int randomLength = (int) (java.lang.Math.random() * 100);
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

    private static void Test(TestSolution solution, String name) {
        ListNode[] TestArray = new ListNode[16];

        for (int i = 0; i < 16; ++i) {
            TestArray[i] = createList();
        }


        long startTime = System.nanoTime();
        ListNode outputList = solution.mergeKLists(TestArray);
        long finishTime = System.nanoTime();
        long result = (finishTime - startTime) / 1000_000;
        System.out.println(name + " " + result + " milliseconds");
        printList(outputList);

    }
}




