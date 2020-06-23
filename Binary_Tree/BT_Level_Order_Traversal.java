class Solution {
    /* Leetcode 102. Binary Tree Level Order Traversal
    Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
    BFS, use queue to store root.next and root.right
    */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> rtn = new ArrayList<>();
        
        if (root==null) return rtn;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = queue.size();
            for (int i=0; i<size; i++) {
                TreeNode treenode = queue.remove();
                temp.add(treenode.val);
                if (treenode.left!=null) queue.add(treenode.left);
                if (treenode.right!=null) queue.add(treenode.right);
            }
            rtn.add(temp);
        }
        return rtn;
    }

    /* Leetcode 107. Binary Tree Level Order Traversal II
    Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).
    
    Same as above, only difference is insert list at the beginning
    */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> rtn = new ArrayList<>();
        
        if (root==null) return rtn;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = queue.size();
            for (int i=0; i<size; i++) {
                TreeNode treenode = queue.remove();
                temp.add(treenode.val);
                if (treenode.left!=null) queue.add(treenode.left);
                if (treenode.right!=null) queue.add(treenode.right);
            }
            rtn.add(0, temp);
        }
        return rtn;
    }

    /* Leetcode 103. Binary Tree Zigzag Level Order Traversal
    Given a binary tree, return the zigzag level order traversal of its nodes' values. 
    (ie, from left to right, then right to left for the next level and alternate between).

    same as levelOrder, use zigzag to determine if need to reverse the list
    */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        boolean zigzag = false;
        List<List<Integer>> rtn = new ArrayList<>();
        
        if (root==null) return rtn;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = queue.size();
            for (int i=0; i<size; i++) {
                TreeNode treenode = queue.remove();
                temp.add(treenode.val);
                if (treenode.left!=null) queue.add(treenode.left);
                if (treenode.right!=null) queue.add(treenode.right);
            }
            if (zigzag) {
                Collections.reverse(temp); 
                zigzag = false;
            }
            else {
                zigzag = true;
            }
            rtn.add(temp);
        }
        return rtn;
    }

    
}
