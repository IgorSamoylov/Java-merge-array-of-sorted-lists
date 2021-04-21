package org.example;

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
    public void Test1() {
        Solution3Multithreading solution0 = new Solution3Multithreading();
        outputList = solution0.mergeKLists(TestArray);
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