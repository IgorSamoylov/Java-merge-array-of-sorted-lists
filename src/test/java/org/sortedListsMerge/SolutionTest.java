package org.sortedListsMerge;

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
    public void testMultithreading() {
        tester(new SolutionMultithreading());
    }
    @Test
    public void testFixedThreadArray() {
        tester(new SolutionFixedThreadArray());
    }
    @Test
    public void testPhaser() {
        tester(new SolutionPhaser());
    }


    public void tester(TestSolution solution) {

        outputList = solution.mergeKLists(TestArray);
        Assertions.assertNotNull(outputList);
        Assertions.assertEquals(totalNodes, countNodes(outputList));

    }



    private int countNodes(ListNode ln) {
        int count = 0;
        while(ln != null) {
            count++;
            ln = ln.next;
        }

        return count;
    }

    @AfterAll
    static void tearDown() {
        App.printList(outputList);
    }
}