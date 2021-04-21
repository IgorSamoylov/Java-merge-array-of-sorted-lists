package org.example;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

    class Solution {

        public List<ListNode> myArray = new CopyOnWriteArrayList<>();

        public ListNode mergeKLists(ListNode[] lists) {
            if(lists.length == 0) return null;


            for(int k = 0; k < lists.length; ++k) {

                ListNode currentListNode = lists[k];
                while (currentListNode != null) {
                    myArray.add(currentListNode);
                    currentListNode = currentListNode.next;
                }
            }

            Collections.sort(myArray, new SortClass());

            for(int i = 0; i < myArray.size() - 1; ++i){
                myArray.get(i).next = myArray.get(i + 1);
            }

            if (myArray.size() == 0) return null;

            return myArray.get(0);


        }
    }

    class SortClass implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            ListNode listNode1 = (ListNode) o1;
            ListNode listNode2 = (ListNode) o2;

            if(listNode1.val > listNode2.val) return 1;
            if(listNode1.val < listNode2.val) return -1;

            return 0;
        }
    }

