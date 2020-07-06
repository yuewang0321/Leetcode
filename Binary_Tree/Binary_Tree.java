class Solution {
    /* Leetcode 104. Maximum Depth of Binary Tree
    Given a binary tree, find its maximum depth.
    The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
    拆分求解left and right subtree的maxDepth, 再合并二者最大的值
    */
    public int maxDepth(TreeNode root) {
        if (root==null) return 0;
        
        int depth = 1;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        depth += Math.max(left, right);
        
        return depth;
    }

    /* Leetcode 110. Balanced Binary Tree
    Given a binary tree, determine if it is height-balanced.
    For this problem, a height-balanced binary tree is defined as:
        a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
    利用高度判断，当不平衡时，置高度为-1
    */
    public boolean isBalanced(TreeNode root) {
        int height = getHeight(root);
        return height!=-1;
    }
    
    public int getHeight(TreeNode root) {
        if (root==null) return 0;
        int depth=1;
        int left = getHeight(root.left);
        int right = getHeight(root.right);
        
        if (Math.abs(left-right)>1 || left==-1 || right==-1) return -1;
        depth += Math.max(left, right);
        return depth;
    }

    /* Leetcode 236. Lowest Common Ancestor of a Binary Tree
    Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
    p和q的分布情况有以下几种：
        1.其中有一个是root -> 返回root
        2.全在左子树 -> 返回左子树root
        3.全在右子树 -> 返回右子树root
        4.一个在左子树、一个在右子树 -> 返回root
        5.这两个点不在这棵树里 -> 返回null
    */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root==p || root==q || root==null) return root;
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        if (left!=null && right!=null) return root;
        if (left==null && right!=null) return right;
        if (left!=null && right==null) return left;
        
        return null;
    }

    /* Leetcode 112. Path Sum
    Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that 
    adding up all the values along the path equals the given sum.
    计算当前sum-root.val剩余是否为0，如果大于0继续往left and right找，如果==0返回true
    最后return left||right
    */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root==null) return false;
        if(root.left == null && root.right == null && sum - root.val == 0) return true;

        boolean left = hasPathSum(root.left, sum-root.val);
        boolean right = hasPathSum(root.right, sum-root.val);
        
        return (left||right);
    }

    /* Binary Tree Maximum Path Sum II
    给定一个二叉树，从根节点root出发，求最大路径和，可以在任一点结束
    如果是求从root到leaf的最大路径，就用分治法，从上到下，每个节点的最大路径是其左子树和右子树的最大路径的最大值:
    root.val + Math.max(maxleft,maxright)
    如果二叉树上的节点值有负数，那么最大路径就有可能不到leaf就结束了，所以在计算节点最大路径时，如果其左右子树最大路径的最大值为负数，则该节点到leaf的最大路径长度应该为0：
    root.val + Math.max(0,Math.max(maxleft,maxright))
    */
    public int maxPathSum2(TreeNode root){
        if(root == null) return 0;

        int maxleft = maxPathSum2(root.left);
        int maxright = maxPathSum2(root.right);

        return root.val + Math.max(0,Math.max(maxleft,maxright));
    }

    /* Leetcode 124. Binary Tree Maximum Path Sum
    Given a non-empty binary tree, find the maximum path sum.
    For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree 
    along the parent-child connections. The path must contain at least one node and does not need to go through the root.
    跟LCA问题思考方式类似，考虑某一点root的最大路径的位置可能有如下三种情况：
        1.都在左子树中(root.left:any->any)
        2.都在右子树中(root.right:any->any)
        3.跨过root节点左右子树中都有(root:any->any)
    对三种情况取个最大，就是该root节点的最大路径长度
    对于第三种情况，可以分为三个子问题：
    A:从左子节点出发的最大路径长度（root.left->any）
    root
    B:从右子节点出发的最大路径长度（root.right->any）
    对三个子问题求和，就是跨过此root节点的最大路径长度
    这里需要同时计算any->any和root->any，所以需要定义一个ResultType存储每个节点的any->any和root->any
    */
    class ResultType{
        int any2any;
        int root2any;
        ResultType(int any2any, int root2any) {
            this.any2any = any2any;
            this.root2any = root2any;
        }
    }
    public ResultType helper(TreeNode root) {
        if (root==null) {
            return new ResultType(Integer.MIN_VALUE,Integer.MIN_VALUE);
        }
        
        ResultType left = helper(root.left);
        ResultType right = helper(root.right);
        
        //root到左边或右边
        int root2any = Math.max(0, Math.max(left.root2any, right.root2any)) + root.val;
        
        //包含完全在左边、完全在右边和跨过root三种情况
        
        //完全在左边和完全在右边的情况
        int any2any = Math.max(left.any2any, right.any2any);
        //include root,分别在两边的情况 root+left+right
        any2any = Math.max(any2any, Math.max(0, left.root2any)+Math.max(0, right.root2any)+root.val);
        
        return new ResultType(any2any, root2any);
    }
    public int maxPathSum(TreeNode root) {
        return helper(root).any2any;      
    }

    // 第二种方法
    int maxVal = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxPathDown(root);
        return maxVal;
    }
    public int maxPathDown(TreeNode root) {
        if (root==null) return 0;
        
        int left = Math.max(0, maxPathDown(root.left));
        int right = Math.max(0, maxPathDown(root.right));
        // update maxVal=left+right+val
        // 如果left/right有负数的话则为0
        maxVal = Math.max(maxVal,left+right+root.val);
        
        return Math.max(left,right)+root.val;
    }

    /* Leetcode 687. Longest Univalue Path
    Given a binary tree, find the length of the longest path where each node in the path has the same value. 
    This path may or may not pass through the root.
    The length of path between two nodes is represented by the number of edges between them.
    */
    int longest = 0;
    public int longestUnivaluePath(TreeNode root) {
        if (root==null) return 0;
        helper(root, root.val);
        return longest;    
    }
    public int helper(TreeNode root, int value) {
        if (root==null) return 0;
        
        int left = helper(root.left, root.val);
        int right = helper(root.right, root.val);
        //longest = 左边+右边   如果某一边没有相同元素则为0
        longest = Math.max(left+right, longest);
        // 如果现在的val==prevval, then we need to pick a side which is longer
        if (root.val == value) return Math.max(left, right)+1;
        else return 0;
    }

    /* Leetcode 543. Diameter of Binary Tree
    Given a binary tree, you need to compute the length of the diameter of the tree. 
    The diameter of a binary tree is the length of the longest path between any two nodes in a tree. 
    This path may or may not pass through the root.

   求过每一个点的最长路径，取全局最大值
   过某一个点的最长路径=左子树的最大高度+右子树的最大高度
   计算某一个节点的最大高度需要计算其左子树的右子树的最大高度，然后取最大值+1
   两个函数整合成一个，同时更新全局最大路径长度和节点高度，代码如下：
    */
    int longest2 = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        if (root==null) return 0;
        helper(root);
        return longest2;
    }
    public int helper(TreeNode root) {
        if (root==null) return 0;
        int left = helper(root.left);
        int right = helper(root.right);
        longest2 = Math.max(longest2, left+right);
        return Math.max(left, right)+1;
    }

    /* Leetcode 538. Convert BST to Greater Tree
    Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is 
    changed to the original key plus sum of all keys greater than the original key in BST.
    
    Iterate through the tree as right, root, left, and update node values.
    */
    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if (root==null) return root;
        root.right = convertBST(root.right);
        sum += root.val;
        root.val = sum;
        root.left = convertBST(root.left);
        return root;
    }
    
    /* Leetcode 572. Subtree of Another Tree
    Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s.
    A subtree of s is a tree consists of a node in s and all of this node's descendants. 
    The tree s could also be considered as a subtree of itself.
    如果遇到相同val则check是不是subtree，否则check left and right
    */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s==null) return false;
        
        if (checkSame(s, t)) return true;
        else {
            boolean left = isSubtree(s.left, t);
            boolean right = isSubtree(s.right, t);
            return left || right;
        }
    }
    public boolean checkSame(TreeNode s, TreeNode t) {
        if (s==null && t==null) return true;
        if (s==null || t==null) return false;
        if (s.val!=t.val) return false;
        else {
            boolean left = checkSame(s.left, t.left);
            boolean right = checkSame(s.right, t.right);
            return left && right;
        }
    }

    /* Leetcode 100. Same Tree
    Given two binary trees, write a function to check if they are the same or not.
    
    compare root value, if == then compare left and right
    */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        
        if (p==null && q==null) return true;
        if (p==null || q==null) return false;
        
        if (p.val==q.val) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
        return false;
    }
}
