class Solution {
    /*
    In a complete binary tree every level, except possibly the last, 
    is completely filled, and all nodes in the last level are as far left as possible. 
    It can have between 1 and 2^h nodes inclusive at the last level h.
    */

    /* Leetcode 222. Count Complete Tree Nodes
    Given a complete binary tree, count the number of nodes.

    https://www.youtube.com/watch?v=rvZfvo-r5WU
    完全二叉树只有最后一层的节点可能是不满的，所以对于树中的任意一个节点来说，其左右子节点的高度只有两种情况：
        1. 高度相等，说明该节点左子树是一个满二叉树，其节点个数为2h−1 ，仍需对其右子树递归求解节点个数
        2. 右节点高度比左节点高度小1，说明右节点是满二叉树，节点个数为2h−1−1 ,仍需对其左子树递归求解节点个数
    但是这个involve bit manipulation

    以下是二分法的code：
    计算height，计算最后一排会有多少个node，2^height-1 + number of leaves = node count
    https://www.youtube.com/watch?v=rvZfvo-r5WU
    */
    public int countNodes(TreeNode root) {
        if (root==null) return 0;
        int depth = 0;
        TreeNode pointer = root;
        while (pointer.left!=null) {
            depth += 1;
            pointer = pointer.left;
        }
        
        int left=0, right=(int)Math.pow(2, depth)-1;
        while (left<=right) {
            int mid = left + (right-left)/2;
            if (exist(root, mid, depth)) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return (int)Math.pow(2, depth)-1+left; 
    }
    
    public boolean exist(TreeNode root, int idx, int depth) {
        if (root==null) return false;
        
        TreeNode pointer = root;
        int left = 0, right = (int)Math.pow(2, depth)-1;
        for (int i=0; i<depth; i++) {
            int mid = left + (right-left)/2;
            if (idx>mid) {
                pointer = pointer.right;
                left = mid+1;
            }
            else {
                pointer = pointer.left;
                right = mid-1;
            }
        }
        return pointer!=null;
    }
}