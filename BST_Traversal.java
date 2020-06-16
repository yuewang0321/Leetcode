class Solution {
    /* Leetcode 144. Binary Tree Preorder Traversal
    Given a binary tree, return the preorder traversal of its nodes' values.
    */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root==null) return new ArrayList<Integer>();
        
        List<Integer> pre = new ArrayList<Integer>();
        pre.add(root.val);
        if (root.left!=null) pre.addAll(preorderTraversal(root.left));
        if (root.right!=null) pre.addAll(preorderTraversal(root.right));
        
        return pre;
    }

    /* Leetcode 94. Binary Tree Inorder Traversal
    Given a binary tree, return the inorder traversal of its nodes' values.
    */
    public List<Integer> inorderTraversal(TreeNode root) {
        
        if (root==null) return new ArrayList<Integer>();
        
        List<Integer> in = new ArrayList<Integer>();
        if (root.left!=null) in.addAll(inorderTraversal(root.left));
        in.add(root.val);
        if (root.right!=null) in.addAll(inorderTraversal(root.right));
        
        return in;
    }

    /* Leetcode 145. Binary Tree Postorder Traversal
    Given a binary tree, return the post traversal of its nodes' values.
    */
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root==null) return new ArrayList<Integer>();
        
        List<Integer> post = new ArrayList<Integer>();
        if (root.left!=null) post.addAll(postorderTraversal(root.left));
        if (root.right!=null) post.addAll(postorderTraversal(root.right));
        post.add(root.val);
        
        return post;       
    }
}
