class Solution {
    /* Leetcode 19. Remove Nth Node From End of List
    Given a linked list, remove the n-th node from the end of list and return its head.
    两个指针，fast先走n+！步slow再出发，当fast==null时，slow指向倒数第n+1个节点，删掉slow后面的节点：slow.next = slow.next.next
    */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        for (int i=0; i<=n; i++) {
            fast = fast.next;
        }
        while (fast!=null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    /* Leetcode 141. Linked List Cycle
    Given a linked list, determine if it has a cycle in it.
    一个快指针一个慢指针，如果路径上有环，快慢指针一定会相遇
    初始化：slow = head;fast = head.next
    */
    public boolean hasCycle(ListNode head) {
        if (head==null) return false;
        
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast!=slow) {
            if (fast==null || slow==null || fast.next==null)
                return false;
            fast = fast.next.next;
            slow = slow.next;
        }
        return true;        
    }

    /* Leetcode 142. Linked List Cycle II
    Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
    slow从快慢指针相遇的地方出发，fast指针从初始地方出发，两个指针每次走一步，直到相遇，就是环的入口
    证明：
    l: length of the loop
    m: dist from the beginning to the start of the loop
    k: distance of meeting point of S and F from start of the loop when they meet for the first time (while detecting the loop)
    When S and F meet for the first time:
    Dist_S = m + p*l + k    Dist_F = m + q*l + k    (p<q)
    2Dist_S = Dist_F
    m+k = (q-2p)*l      =>  m+k是muptiple lengthof the loop
    在S和F meet以后，把F放在start of linkedlist, S和F每次走一步until they meet. 此时F走了m步，S是m+k from the start of the loop
    */
    public ListNode detectCycle(ListNode head) {
        if (head==null) return null;
        
        Boolean hasCycle = false;
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next!=null && slow!=null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast==null || slow==null)
                return null;
            if (slow==fast) {
                hasCycle=true;
                break;
            }
        }     
        if (!hasCycle) return null;
        
        fast = head;
        while (fast!=slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
    
    /* Leetcode 61. Rotate List
    Given a linked list, rotate the list to the right by k places, where k is non-negative.
    Input: 1->2->3->4->5->NULL, k = 2
    Output: 4->5->1->2->3->NULL
    1. 求链表长度len，如果k>len,k = k%len;
    2. 找到从后往前数第k个元素，也就是从前往后数第len-k个元素node，和末尾元素tail
    3. tail.next = dummy.next;dummy.next = node.next;node.next = null
    */
    public ListNode rotateRight(ListNode head, int k) {   
        if (head==null) return head;
        //mod k
        ListNode count = head;
        int len = 1;
        while (count.next!=null) {
            count = count.next;
            len++;
        }
        k = k%len;
        
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode node = dummy;
        //后len-k个应该跑到前面去
        for (int i=0; i<len - k; i++) {
            node = node.next;
        }
        count.next = dummy.next;
        dummy.next = node.next;
        node.next = null;
        
        return dummy.next;
    }

    /* Leetcode 138. Copy List with Random Pointer
    A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
    Return a deep copy of the list.
    The Linked List is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:
        val: an integer representing Node.val
        random_index: the index of the node (range from 0 to n-1) where random pointer points to, or null if it does not point to any node.
        hash_map
    先按next指针复制链表，把原链表老节点和新链表新节点的映射关系存入hash_map，再遍历一遍原链表，按照hash_map中的对应关系，把random pointer在对应的新节点中标出。
    空间复杂度O(n)
    */
    public Node copyRandomList(Node head) {
        if (head==null) return null;
        
        Map<Node, Node> map = new HashMap<Node, Node>();
        Node ptr = head;
        while (ptr!=null) {
            Node node = new Node(ptr.val);
            map.put(ptr, node);
            ptr = ptr.next; 
        }

        for (Map.Entry<Node, Node> entry: map.entrySet()) {
            entry.getValue().next = map.get(entry.getKey().next);
            if (entry.getKey().random!=null) {
                entry.getValue().random = map.get(entry.getKey().random);
            }
        }  
        return map.get(head);
    }
    // O(1) space complexity
    // https://leetcode.com/problems/copy-list-with-random-pointer/discuss/43491/A-solution-with-constant-space-complexity-O(1)-and-linear-time-complexity-O(N)
    public Node copyRandomList(Node head) {
        if (head==null) return null;
        
        Node head_pointer = head;
        
        while (head_pointer!=null) {
            Node temp = head_pointer.next;
            head_pointer.next = new Node(head_pointer.val);;
            head_pointer.next.next = temp;
            head_pointer = temp;
        }
        
        head_pointer = head;
        while (head_pointer!=null) {
            if (head_pointer.random!=null) head_pointer.next.random = head_pointer.random.next;
            head_pointer = head_pointer.next.next;
        }
        
        head_pointer = head;
        Node rtn = head.next;
        Node pointer = rtn;
        while (pointer.next!=null) {
            head_pointer.next = head_pointer.next.next;
            head_pointer = head_pointer.next;
            
            pointer.next = pointer.next.next;
            pointer = pointer.next;
        }
        head_pointer.next = head_pointer.next.next;
        return rtn;
    }


    /* Leetcode 23. Merge k Sorted Lists
    Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
    Input:
    [
    1->4->5,
    1->3->4,
    2->6
    ]
    Output: 1->1->2->3->4->4->5->6
    */
    /* 第一种方法：min heap，用priorityqueue实现
    每次弹出最小的元素，放到结果链表后面，然后将其next入堆，重复上述
    N：所有数的个数
    K：链表个数
    时间复杂度：O(NlogK) ，heap中最多有k各元素，插入操作时间复杂度是O(logk)
    空间复杂度：O(K）
    */
    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(new Comparator<ListNode>() {
           // @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        
        //把每个linkedlist的第一个head加到minheap里
        for (int i=0; i<k; i++) {
            ListNode head = lists[i];
            if (head!=null) minHeap.add(head);
        }
        
        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy;
        while (!minHeap.isEmpty()) {
            ListNode curr = minHeap.poll();
            //加最小listnode的next到minHeap里
            if (curr.next!=null) minHeap.add(curr.next);
            //连起来
            prev.next = curr;
            prev = prev.next;
        }
        return dummy.next;
    }

    /* 第二种方法：分治法
    merge k 个链表
    拆分成merge前k/2个链表得到list1和merge后k/2个链表得到list2， 合并list1和 list2，得到结果
    recursion
    时间复杂度：O(NlogK)
    */
    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        if(len ==0){
            return null;
        }
        return divideMergeKList(lists,0,len-1);
    }
    public ListNode divideMergeKList(ListNode[] lists,int start,int end) {
        if(start == end){
            return lists[start];
        }
        //拆分成两部分
        ListNode left = divideMergeKList(lists,start,start+(end-start)/2);
        ListNode right = divideMergeKList(lists,start+(end-start)/2+1,end);
        //合并两部分结果返回
        return MergeTwoList(left,right);
    }
    public ListNode MergeTwoList(ListNode left,ListNode right){
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        ListNode prev1 = left;
        ListNode prev2 = right;
        while(prev1 != null && prev2 != null){
            if(prev1.val < prev2.val){
                prev.next = prev1;
                prev1 = prev1.next;
                prev = prev.next;
            }
            else{
                prev.next = prev2;
                prev2 = prev2.next;
                prev = prev.next;
            }
        }
        if(prev1 == null){
            prev.next = prev2;
        }
        if(prev2 == null){
            prev.next = prev1;
        }
        return dummy.next;
    }

    /*
    方法三：两两合并
    1、2合并，3、4合并，….n, 向上递归合并
    时间复杂度：O(NlogK)
    如果是1、2合并，然后忽然3合并，…n
    时间复杂度O(NK)
    */
     //两两合并
    public ListNode mergeKListsOneByOne(ListNode[] lists){
        if(lists.length == 0) return null;

        List<ListNode> newlists = new ArrayList<>();
        for(int i = 0; i < lists.length;i++) {
            newlists.add(lists[i]);
        }

        while (newlists.size() > 1){
            List<ListNode> listTemp = new ArrayList<>();
            for(int i = 0;i+1 < newlists.size();i+=2){
                listTemp.add(MergeTwoList(newlists.get(i),newlists.get(i+1)));
            }
            //odd length, add the last one
            if(newlists.size() % 2 == 1){
                listTemp.add(newlists.get(newlists.size()-1));
            }
            newlists = listTemp;
        }
        return newlists.get(0);
    }
}
