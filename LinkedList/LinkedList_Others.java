class Solution {
    /* Leetcode 234. Palindrome Linked List
    Given a singly linked list, determine if it is a palindrome.
    */
    /* 方法一：利用stack，先进后出的性质：
    找到终点，过程中将前半部分链表入栈
    继续向后遍历，出栈，对比元素是否一致
    */
    public boolean isPalindrome(ListNode head) {
        if (head==null || head.next==null) return true;
        
        Stack<Integer> stack = new Stack<Integer>(); 
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null) {
            stack.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }
        System.out.println(slow.val);
        System.out.println(stack);
        if (fast!=null && fast.next==null) slow = slow.next;
        while (!stack.empty()) {
            if (stack.pop()!=slow.val) return false;
            slow = slow.next;
        }
        return true;
    }
    /* 方法二：
    先找到中点，将后半部分的链表反转，对比前后两部分是否一致
    */
    public boolean isPalindrome(ListNode head) {
        if (head==null || head.next==null) return true;
        
        ListNode fast = head;
        ListNode slow = head;
        while (fast!=null && fast.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        if (fast!=null) slow = slow.next;
        
        ListNode dummy = null;
        while (slow!=null) {
            ListNode temp = slow.next;
            slow.next = dummy;
            dummy = slow;
            slow = temp;
        }
        ListNode mid = dummy;
        
        while (mid!=null) {
            if (head.val!=mid.val) return false;
            mid = mid.next;
            head = head.next;
        }       
        return true;      
    }

    /* Leetcode 160. Intersection of Two Linked Lists
    Write a program to find the node at which the intersection of two singly linked lists begins.
    */
    // 方法1: 两个指针分别遍历，一个先A后B，一个先B后A，两指针指向节点相等即为相交处
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA==null || headB==null) return null;
        
        ListNode Afirst = headA;
        ListNode Bfirst = headB;
        
        while (Afirst!=null || Bfirst!=null) {
            if (Afirst==null) Afirst= headB;
            if (Bfirst==null) Bfirst = headA;
            if (Afirst==Bfirst) return Afirst;
            Afirst = Afirst.next;
            Bfirst = Bfirst.next;
        }
        return null;
    }
    /* 方法2:
    比较两个linkedlist的长短差x，把长的往后移x位，再同时移动两个pointer直到他们相同
    这里用到result来存储tail的值和长度，因为如果tail不一样的话说明两个linkedlist没有intersection
    */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA==null || headB==null) return null;
        
        Result resultA = getTailAndSize(headA);
        Result resultB = getTailAndSize(headB);
        
        if (resultA.tail!=resultB.tail) return null;
        
        ListNode longer = resultA.size < resultB.size ? headB : headA;
        ListNode shorter = resultA.size < resultB.size ? headA : headB;
        
        longer = getKthNode(longer, Math.abs(resultA.size-resultB.size));
        
        while (shorter!=longer) {
            shorter = shorter.next;
            longer = longer.next;
        }
        return longer;
    }
    
    //Result contains the tail and size
    public class Result {
        public ListNode tail;
        public int size;
        public Result(ListNode tail, int size) {
            this.tail = tail;
            this.size = size;
        }
    }
    
    Result getTailAndSize(ListNode list) {
        if (list == null) {
            return null;
        }
        int size = 1;
        ListNode current = list;
        while (current.next != null){
            current = current.next;
            size++;
        }
        return new Result(current, size);
    }
    
    ListNode getKthNode(ListNode head, int size) {
        ListNode curr = head;
        while (size>0 && curr!=null) {
            curr = curr.next;
            size--;
        }
        return curr;
    }
}
