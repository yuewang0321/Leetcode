class Solution {
    // TreeNode data structure
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode (int val) {
            this.val = val;
        }
    }

    /* DFS是搜索算法的一种。它沿着树的深度遍历树的节点，尽可能深的搜索树的分支。
    当节点v的所有边都己被探寻过，搜索将回溯到发现节点v的那条边的起始节点。这一过程一直进行到已发现从源节点可达的所有节点为止。
    如果还存在未被发现的节点，则选择其中一个作为源节点并重复以上过程，整个进程反复进行直到所有节点都被访问为止。
    */

    /* Recursive
    Recursivly do dfs on left and right subtree. 
    return null when root is null
    Starting from the root, add root to result, then do dfs for left and right subtree.
    */
    public void DFSRecursionHelper(TreeNode root, List<Integer> results) {
        if (root==null) return;

        results.add(root.val);
        DFSRecurtionHelper(root.left, results);
        DFSRecurtionHelper(root.right, results);
    }

    public list<Integer> DFSRecursion(TreeNode root) {
        List<Integer> results = new ArrayList();
        DFSRecursionHelper(root, results);
        return results;
    }


    /* Iterative
    Use stack
    Add right subtree to stack, and then add left subtree. In this way we can go through all left subtrees before right.
    */
    public List<Integer> DFSwithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> results = new ArrayList<>();

        if (root==null) return results;

        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode temp = stack.pop();
            results.add(temp.val);
            if (temp.right!=null) stack.push(temp.right);
            if (temp.left!=null) stack.push(temp.left);
        }
        return results;
    }
}
