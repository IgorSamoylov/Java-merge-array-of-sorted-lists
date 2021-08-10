package org.sortedListsMerge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

    class Solution implements TestSolution {

        public List<ListNode> myArray = new ArrayList<>();

        public ListNode mergeKLists(ListNode[] lists) {
            if(lists.length == 0) return null;

            for (ListNode list: lists) {

                ListNode currentListNode = list;
                while (currentListNode != null) {
                    myArray.add(currentListNode);
                    currentListNode = currentListNode.next;
                }
            }
            myArray.sort(new SortClass());

            for(int i = 0; i < myArray.size() - 1; ++i){
                myArray.get(i).next = myArray.get(i + 1);
            }

            if (myArray.size() == 0) return null;

            return myArray.get(0);
        }
    }

    class SortClass implements Comparator<ListNode> {
        @Override
        public int compare(ListNode listNode1, ListNode listNode2) {
            return Integer.compare(listNode1.val, listNode2.val);
        }
    }

