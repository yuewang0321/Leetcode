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
            root.left = deleteNode(root.left, key);
        }
        return root;
    }
    public TreeNode helper(TreeNode root) {
        while (root.right!=null) {
            root = root.right;
        }
        return root;
    }
}
