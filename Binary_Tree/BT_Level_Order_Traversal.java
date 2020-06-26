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

    /* Leetcode 297. Serialize and Deserialize Binary Tree
    serialize，用queue层序遍历即可
    deserialize也要用queue，将root节点放入队列，然后数组中的前两个元素是其左右孩子，依次加入队列。
    */
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder res = new StringBuilder();
        
        if(root == null) return res.toString();
        queue.add(root);
        
        while(!queue.isEmpty()){
            TreeNode temp = queue.poll();
            if(temp != null){
                res.append(temp.val+",");
            }
            else {
                res.append("null,");
                continue;
            }
            queue.add(temp.left);
            queue.add(temp.right);
        }
        res.subSequence(0,res.length()-1);
        return res.toString();
    }
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.length() == 0) return null;
        
        String[] nums = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        
        TreeNode root = new TreeNode(Integer.parseInt(nums[0]));
        queue.add(root);

        for(int i = 1; i < nums.length;i++){
            TreeNode parent = queue.poll();
            if(!nums[i].equals("null")){
                parent.left = new TreeNode(Integer.parseInt(nums[i]));
                queue.add(parent.left);
            }
            if(!nums[++i].equals("null")){
                parent.right = new TreeNode(Integer.parseInt(nums[i]));
                queue.add(parent.right);
            }
        }
        return root;
    }
}