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
}
