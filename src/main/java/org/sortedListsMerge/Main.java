package org.sortedListsMerge;


import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String... args) {

        Test(new Solution(),
                "Simple bruteforce solution with JAVA integrated sort methods"
        );
        Test(new SolutionDivideAndConquer(),
                "Advanced one-thread solution with divide and conquer method"
        );
        Test(new SolutionMultithread(),
                "Queue of Futures"
        );
        Test(new SolutionPhaser(),
                "Runnable and Phaser class synchronization"
        );
    }

    private static void Test(TestSolution solution, String name) {
        ListNode[] TestArray = new ListNode[Settings.LISTS_NUMBER];

        for (int i = 0; i < Settings.LISTS_NUMBER; ++i) {
            TestArray[i] = Util.createList();
        }

        long startTime = System.nanoTime();
        ListNode outputList = solution.mergeKLists(TestArray);
        long finishTime = System.nanoTime();
        long result = finishTime - startTime;
        System.out.println(name + " " + TimeUnit.NANOSECONDS.toMillis(result) + " milliseconds");
        //Util.printList(outputList);
    }
}




