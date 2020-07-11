class Solution {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */

    /* Leetcode 83. Remove Duplicates from Sorted List
    Given a sorted linked list, delete all duplicates such that each element appear only once.
    因为是sorted,所以只需要在遇到duplicate时让prev指向curr.next，最后return head
    */
    public ListNode deleteDuplicates(ListNode head) {
        if (head==null) return null;
        ListNode prev = head; 
        ListNode curr = head.next;
        while (curr!=null) {
            if (prev.val==curr.val) {
                prev.next = curr.next;
                curr = curr.next;
            }
            else {
                prev = prev.next;
                curr = curr.next;
            }
        }
        return head;
    }

    /* Leetcode 82. Remove Duplicates from Sorted List II
    Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
    Return the linked list sorted as well.
    因为删除某个节点node，需要让node的前序节点.next = node.next，删除全部重复的元素可能删掉head元素，
    因此需要构造一个dummy node，让其指向head的前序节点，也就是dummy.next = head。这样需要删除head的时候就可以令dummy.next = head.next。
    最后反回dummy.next
    */
    public ListNode deleteDuplicates(ListNode head) {
        //因为第一个可能有duplicate,所以创建一个dummy node
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        
        ListNode prev = dummy;
        ListNode curr = head;
        
        while (curr!=null) {
            if (curr.next!=null && curr.val==curr.next.val) {
                int val = curr.val;
                while (curr!=null && curr.val==val) {
                    curr = curr.next;
                }
                prev.next=curr;
            }
            else {
                prev = prev.next;
                curr = curr.next;
            }
        }
        return dummy.next;
    }

    /* Leetcode 206. Reverse Linked List
    Reverse a singly linked list.
    null   [1,] -> [2,] -> [3,] -> [4,] -> [,5,]
    ↑       ↑
    prev   curt
    
    1. 用temp记录下curt.next（因为后面要修改curt.next）
    null   [1,] -> [2,] -> [3,] -> [4,] -> [,5,]
    ↑       ↑      ↑
    prev   curt    temp
    
    2. 将curt.next指向其前序节点prev，此时原来的后续链断掉:
    null <- [1,]  [2,] -> [3,] -> [4,] -> [,5,]
    ↑       ↑      ↑
    prev   curt    temp
    3. 将prev移到curt位置，curt移动到原来的curt.next,即temp:
    null <- [1,]  [2,] -> [3,] -> [4,] -> [,5,]
            ↑      ↑       ↑
            prev   curt    temp
    ListNode temp = curt.next;
    curt.next = prev
    prev = curt;
    curt = temp;
    */
    public ListNode reverseList(ListNode head) {
        if (head==null) return head;
        
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr!=null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    /* Leetcode 92. Reverse Linked List II
    Reverse a linked list from position m to n. Do it in one-pass.
    翻转m和n之间的部分，分为三个步骤：
    1. 找到m-1和m的点
    2. 将m~n反转
    3. 把m-1.next指向n;把m.next指向n.next
    */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode rtn = new ListNode(-1);
        rtn.next = head;
        
        ListNode curr = head;
        ListNode prev = rtn;
        
        int count = 1;
        while (count<m) {
            prev = curr;
            curr = curr.next;
            count++;
        }
        
        
        ListNode track = curr;
        ListNode dummy = prev;
        
        while (count <= n) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
            count++;
        }  
//         System.out.println(prev.val);
//         System.out.println(track.val);
//         System.out.println(dummy.val);
//         System.out.println(curr.val);
        dummy.next = prev;
        track.next = curr;
        return rtn.next;
    }

    /* Leetcode 86. Partition List
    Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
    You should preserve the original relative order of the nodes in each of the two partitions.
    将链表排成两队，小于x的一队，大于等于x的一队，然后把两个链表连起来。
    链表的结构会发生变化，所以需要两个dummy node，一个用来指向小的队small，一个用来指向大的队big。
    */
    public ListNode partition(ListNode head, int x) {
        ListNode small = new ListNode(-1);
        ListNode big = new ListNode(-1);
        
        ListNode smallptr = small;
        ListNode bigptr = big;
        
        while (head!=null) {
            if (head.val<x) {
                smallptr.next = new ListNode(head.val);
                smallptr = smallptr.next;
            }
            else {
                bigptr.next = new ListNode(head.val);
                bigptr = bigptr.next;
            }
            head = head.next;
        }
        smallptr.next = big.next;
        return small.next;
    }

    /* Leetcode 21. Merge Two Sorted Lists
    Merge two sorted linked lists and return it as a new sorted list. 
    The new list should be made by splicing together the nodes of the first two lists.
    两个pointer分别指向l1和l2，比较大小，用新的链表连起来
    最后再把剩下的连上
    */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode rtn = new ListNode(-1);
        ListNode pointer = rtn;
        ListNode ptr1 = l1;
        ListNode ptr2 = l2;
        
        while (ptr1!=null && ptr2 !=null) {
            if (ptr1.val<=ptr2.val) {
                pointer.next = new ListNode(ptr1.val);
                pointer = pointer.next;
                ptr1 = ptr1.next;
            } else {
                pointer.next = new ListNode(ptr2.val);
                pointer = pointer.next;
                ptr2 = ptr2.next;
            }
        }
        if (ptr1!=null) pointer.next = ptr1;
        else if (ptr2!=null) pointer.next = ptr2;
        return rtn.next;     
    }

    /* Leetcode 148. Sort List
    Sort a linked list in O(n log n) time using constant space complexity.
    merge sort
    具体步骤:
    1. merge sort在链表中找中点，有两种方法：
        a. 遍历一遍，得到链表的长度n，则中间位置是n/2，再从头遍历一遍，到n/2的位置停止，找到中点
        b. 设置两个错位指针，一个slow一个fast，初始化都指向head，slow每次向右移动一位，fast每次向右移动两位，fast移动到末尾的时候，head指向中间，取到链表中点
    2. 对左右两段递归进行排序
    3. merge两段有序链表
    */
    //merge sort, split into left and right
    public ListNode sortList(ListNode head) {
        if (head==null || head.next==null) return head;
        
        ListNode fast = head;
        ListNode slow = head;
        ListNode breakpnt = slow;
        
        //find mid
        while (fast!=null && fast.next!=null) {
            fast = fast.next.next;
            breakpnt = slow;
            slow = slow.next;
        }
        breakpnt.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(slow);
        
        return merge(left, right);
    }
    public ListNode merge(ListNode left, ListNode right) {
        ListNode l = new ListNode(0), p = l;
        
        while (left!=null && right!=null) {
            if (left.val < right.val) {
                p.next = left;
                left = left.next;
            } else {
                p.next = right; 
                right = right.next;
            }
            p = p.next;
        }
        
        if (left!=null) p.next = left;
        if (right!=null) p.next = right;
        return l.next;
    }

    /* Leetcode 143. Reorder List
    Given a singly linked list L: L0→L1→…→Ln-1→Ln,
    reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
    分成左右两份，reverse右边，再merge
    example: [1,2,3,4]分成[1,2]和[3,4]
    merge [1,2], [4,3] --> [1,4,2,3]
    */
    public void reorderList(ListNode head) {   
        if (head==null) return;
        
        ListNode leftptr = head;
        ListNode rightptr = head;
        ListNode stop = leftptr;
        
        while (rightptr!=null && rightptr.next!=null) {
            stop = leftptr;
            leftptr = leftptr.next;
            rightptr = rightptr.next.next;
        }

        // stop.next = null;
        ListNode right = stop.next;
        stop.next=null;
        
        ListNode prev = null;
        ListNode curr = right;

        while (curr!=null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        right = prev;
        
        ListNode c = head;
        ListNode left = head.next;
        Boolean insertLeft = false;
        
        while (left!=null && right!=null) {
            if (insertLeft) {
                c.next = left;
                left = left.next;
                insertLeft = false;
            } else {
                c.next = right;
                right = right.next;
                insertLeft = true;
            }
            c = c.next;
        }
        
        if (left!=null) c.next = left;
        if (right!=null) c.next = right;
    }
    /*public void print(ListNode head) {
        while (head!=null) {
            System.out.println(head.val);
            head = head.next;
        }
        System.out.println("================");
    }*/

    /* Leetcode 24. Swap Nodes in Pairs
    Given a linked list, swap every two adjacent nodes and return its head.
    You may not modify the values in the list's nodes, only nodes itself may be changed.
    Given 1->2->3->4, you should return the list as 2->1->4->3.

    Use recursion to keep swaping
    */
    public ListNode swapPairs(ListNode head) {
        if ((head==null) || (head.next==null)) return head;
        
        ListNode temp = head.next;
        head.next = swapPairs(head.next.next);
        temp.next = head;
        return temp;
    }

    /* 25. Reverse Nodes in k-Group
    Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
    k is a positive integer and is less than or equal to the length of the linked list. 
    If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

    跟上面的思路一样，但是要检查是不是够k个，和需要extra pointer来指向第k个
    */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head==null) return head;
        ListNode check = head;
        for (int i=0; i<k-1; i++) {
            check = check.next;
            if (check==null) return head;
        }
        
        ListNode counter = head;
        ListNode prev = null;
        ListNode curr = head;
        for (int i=0; i<k; i++) {
            counter = counter.next;
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        head.next = reverseKGroup(counter, k);
        
        return prev;
    }

}
