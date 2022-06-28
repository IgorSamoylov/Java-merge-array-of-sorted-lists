package org.sortedListsMerge;


public class Main {

    public static void main(String... args) {

        Test(new Solution(),
                "Simple bruteforce solution with JAVA integrated sort methods"
        );
        Test(new SolutionDivideAndConquer(),
                "Advanced one-thread solution with divide and conquer method"
        );
        Test(new SolutionMultithreading(),
                "List of Futures"
        );
        Test(new SolutionPhaser(),
                "Runnable and Phaser class synchronization"
        );
    }

    private static void Test(TestSolution solution, String name) {
        ListNode[] TestArray = new ListNode[Constants.LISTS_NUMBER];

        for (int i = 0; i < Constants.LISTS_NUMBER; ++i) {
            TestArray[i] = Util.createList();
        }

        long startTime = System.nanoTime();
        ListNode outputList = solution.mergeKLists(TestArray);
        long finishTime = System.nanoTime();
        long result = (finishTime - startTime) / 1000_000;
        System.out.println(name + " " + result + " milliseconds");
        Util.printList(outputList);
    }
}




