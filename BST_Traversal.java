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
    /* Iterative
    用一个stack存储root, left, right, 每次pop root，insert root.left and root.right，得到的结果是reverse postorder
    最后再reverse list
    */
    public List<Integer> postorderTraversal_iter(TreeNode root) {
        if (root==null) return new ArrayList<Integer>();
        
        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<Integer> ans = new ArrayList<Integer>();
        
        stack.push(root);
        while (!stack.empty()) {
            TreeNode top = stack.pop();
            ans.add(top.val);
            if (top.left!=null) stack.push(top.left);
            if (top.right!=null) stack.push(top.right);
        }
        Collections.reverse(ans);
        return ans;  
    }
}




    /*
    分治法相比于遍历法的优点：
        1. 无需全局变量存储结果，无需helper函数
        2. 可并行 


    三种traversal的iterative实现
    整体思路：
    三种traveresal的iterative思路核心思想是一致的 -- 
        1. 将binary tree分为“左”（包括一路向左，经过的所有实际左+根）、“右”（包括实际的右）两种节点
        2. 使用同样的顺序将“左”节点入栈
        3. 在合适的时机转向（转向后，“右”节点即成为“左”节点）、访问节点、或出栈
    比如{1,2,3}，当cur位于节点1时，1、2属于“左”节点，3属于“右”节点。DFS的非递归实现本质上是在协调入栈、出栈和访问，
    三种操作的顺序。上述统一使得我们不再需要关注入栈顺序，仅需要关注出栈和访问（第3点），随着更详细的分析，你将更加体会到这种简化带来的好处。

    将对节点的访问定义为results.add(node.val);，分析如下：

    Preorder: root, left, right 按照上面提到的思路，可以简化为左->右
    从root节点开始访问，依次向下访问左节点(cur指向当前节点)，此时立即将这些“左”节点输出到结果中，同时把他们压入栈，便于后续访问其右节点：
    */
    while (cur != null) {
        results.add(cur.val);
        stack.push(cur);
        cur = cur.left;
    }
    // 上面循环结束意味着我们已经访问过所有的“左”节点，现在需要将这些节点出栈，转到其“右”节点，此时右节点也变成了“左”节点，需要对其进行上面的处理。
    if (!stack.empty()) {
        cur = stack.pop();
        // 转向
        cur = cur.right;
    }
    // 完整代码：
    public List<Integer> Preorder_nonRecursion(TreeNode root){
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> results = new ArrayList<>();
        while(cur != null || !stack.empty()){//停止条件：栈和cur都为空
            while (cur != null) {
                results.add(cur.val);//左节点加入结果集
                stack.push(cur);//左节点入栈
                cur = cur.left;
            }
            if(!stack.empty()){
                cur = stack.pop();
                cur = cur.right;
            }
        }
        return results;
    }

    /* Inorder: left, root, right   
    prorder与inorder的区别只在于对“左”节点的处理上，preorder遍历是先访问实际根，再访问左节点，
    而inorder是先访问实际左节点，再访问实际根节点，所以需要将inorder改为出栈时才访问这个节点的值。
    */
    while (cur != null) {
        stack.push(cur);//左节点入栈
        cur = cur.left;
      }
    if(!stack.empty()){
        cur = stack.pop();
        results.add(cur.val);//左节点加入结果集
        cur = cur.right;
    }
    // 完整代码
    public List<Integer> Inorder_nonRecursion(TreeNode root){
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> results = new ArrayList<>();
        while(cur != null || !stack.empty()){//停止条件：栈和cur都为空
            while (cur != null) {
                stack.push(cur);//左节点入栈
                cur = cur.left;
            }
            if(!stack.empty()){
                cur = stack.pop();
              	results.add(cur.val);//左节点加入结果集
                cur = cur.right;
            }
        }
        return results;
    }
