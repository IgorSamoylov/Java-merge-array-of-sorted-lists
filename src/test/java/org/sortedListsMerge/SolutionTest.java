package org.sortedListsMerge;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SolutionTest {
    private static ListNode outputList;
    private static final int numberLists = 32;
    private ListNode[] TestArray = new ListNode[numberLists];
    private int totalNodes = 0;


    SolutionTest() {

        for (int i = 0; i < numberLists; ++i) {
            TestArray[i] = App.createList();
            totalNodes += countNodes(TestArray[i]);
        }
    }
    @Test
    public void simpleBruteforce() { tester(new Solution());}

    @Test
    public void testDivideAndConquer() { tester(new SolutionDivideAndConquer());}

    @Test
    public void testMultithreading() {
        tester(new SolutionMultithreading());
    }

    @Test
    public void testPhaser() {
        tester(new SolutionPhaser());
    }


    private void tester(TestSolution solution) {

        outputList = solution.mergeKLists(TestArray);
        Assertions.assertNotNull(outputList);
        Assertions.assertEquals(totalNodes, countNodes(outputList));
        Assertions.assertTrue(testAscendingOrder(outputList));
    }

    // Counts the total number of nodes in a linked list
    private int countNodes(ListNode ln) {
        int count = 0;
        while(ln != null) {
            count++;
            ln = ln.next;
        }
        return count;
    }

    // Tests that nodes in linked list are ascending ordered
    private boolean testAscendingOrder(ListNode ln) {
        while(ln.next != null) {
            if (!(ln.val <= ln.next.val)) return false;
            ln = ln.next;
        }
        return true;
    }

    // Just print last output
    @AfterAll
    static void printResult() {
        App.printList(outputList);
    }
}