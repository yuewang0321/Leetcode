class Solution {
    /* Leetcode 450. Delete Node in a BST
    Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.
    Basically, the deletion can be divided into two stages:
        Search for a node to remove.
        If the node is found, delete the node.
    Note: Time complexity should be O(height of tree).
    首先要找到要删除的节点，然后用它左子树的最大值（或右子树的最小值）的值取代要删除节点的值，再将左子树的最大值（或右子树的最小值）节点删除。
    */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root==null) return null;
        if (root.val==key) {
            if(root.left == null){//如果左节点是空的
                return root.right;//返回右节点
            }
            else if (root.right == null){//左节点非空，右节点为空
                return root.left;//返回左节点
            }
            root.val = helper(root.left).val;
            root.left = deleteNode(root.left,root.val);
        }
        else if (root.val<key) {
            root.right = deleteNode(root.right, key);
        }
        else {
        return root;
    }
    public TreeNode helper(TreeNode root) {
        while (root.right!=null) {
            root = root.right;
        }
        return root;
    }

    /* Leetcode 96. Unique Binary Search Trees
    Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
    DP problem:
    https://www.youtube.com/watch?v=HWJEMKWzy-Q
    */
    public int numTrees(int n) {
        ArrayList<Integer> dp = new ArrayList<Integer>();
        dp.add(1);
        dp.add(1);  
        for (int i=2; i<=n; i++) {
            int count = 0;
            for (int j=0; j<i; j++) {
                count += dp.get(j) * dp.get(i-j-1);
            }
            dp.add(count); 
        }
        return dp.get(n);
    }

    /* Leetcode 95. Unique Binary Search Trees II
    Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
    遍历每一个节点k，以该节点为root的BST可以由其左、右子树所有可能的情况构成
    https://www.youtube.com/watch?v=GZ0qvkTAjmw
    */
    public List<TreeNode> generateTrees(int n) {
        return helper(1, n);
    }   
    public List<TreeNode> helper(int min, int max) {
        List<TreeNode> result = new ArrayList<TreeNode>();
        if (min>max) return result;
        
        for (int i=min; i<=max; i++) {
            List<TreeNode> leftSub = helper(min, i-1);
            List<TreeNode> rightSub = helper(i+1, max);
            
            if (leftSub.size()==0 && rightSub.size()==0) {
                TreeNode temp = new TreeNode(i);
                result.add(temp);
            }
            else if (rightSub.size()==0) {
                for (TreeNode left : leftSub) {
                    TreeNode temp = new TreeNode(i);
                    temp.left = left;
                    result.add(temp);
                }
            }
            else if (leftSub.size()==0) {
                for (TreeNode right : rightSub) {
                    TreeNode temp = new TreeNode(i);
                    temp.right = right;
                    result.add(temp);
                }
            }
            else {
                for (TreeNode left : leftSub) {
                    for (TreeNode right : rightSub) {
                        TreeNode temp = new TreeNode(i);
                        temp.left = left;
                        temp.right = right;
                        result.add(temp);
                    }
                }
            }
        }
        return result;
    }

    /* Leetcode 173. Binary Search Tree Iterator
    Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
    Calling next() will return the next smallest number in the BST.

    Use dfs to go through the tree, store nodes in queue
    Or Stack will also work: https://leetcode.com/problems/binary-search-tree-iterator/discuss/52526/Ideal-Solution-using-Stack-(Java)
    */

    class BSTIterator {
    
        public Queue<TreeNode> queue;
        public BSTIterator(TreeNode root) {
            queue = new LinkedList<TreeNode>();
            this.addToQueue(root);
        }
        
        /** @return the next smallest number */
        public int next() {
            return this.queue.poll().val;
        }
        
        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return (!this.queue.isEmpty());
        }
        
        public void addToQueue(TreeNode root) {
            if (root == null) return;
            if (root.left!=null)
                this.addToQueue(root.left);
            this.queue.add(root);
            if (root.right!=null)
                this.addToQueue(root.right);
        }
    }
}
