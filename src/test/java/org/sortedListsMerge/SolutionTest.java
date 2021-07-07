package org.sortedListsMerge;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SolutionTest {
    private static ListNode outputList;
    private ListNode[] TestArray = new ListNode[16];
    private int totalNodes = 0;


    SolutionTest() {

        for (int i = 0; i < 16; ++i) {
            TestArray[i] = App.createList();
            totalNodes += countNodes(TestArray[i]);
        }
    }
    @Test
    public void simpleBrutforce() { tester(new Solution());}

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


    private void tester(@NotNull TestSolution solution) {

        outputList = solution.mergeKLists(TestArray);
        Assertions.assertNotNull(outputList);
        Assertions.assertEquals(totalNodes, countNodes(outputList));
        Assertions.assertTrue(testAscendingOrder(outputList));

    }



    private int countNodes(ListNode ln) {
        int count = 0;
        while(ln != null) {
            count++;
            ln = ln.next;
        }

        return count;
    }

    private boolean testAscendingOrder(@NotNull ListNode ln) {
        while(ln.next != null) {
            if (!(ln.val <= ln.next.val)) return false;
            ln = ln.next;
        }
        return true;
    }

    @AfterAll
    static void tearDown() {
        App.printList(outputList);
    }
}