package tree;

public class LongestUnivaluePath_687 {

    // copy from the discussion
    // The length of the path between two nodes is represented by the number of edges between them.
	
	
    int maxpath = 0;
    public int longestUnivaluePath(TreeNode root) {        
        dfs(root, null);
        return maxpath;
    }
    
    private int dfs(TreeNode root, TreeNode parent) {
        if (root == null) return 0;
        
        int left = dfs(root.left, root);
        int right = dfs(root.right, root);
        
        // check if the current node is the inversion point:
        maxpath = Math.max(maxpath, left + right);
        
        // check if current node contribute to either left or right sub paths:
        if (parent != null && root.val == parent.val) {
            return Math.max(left, right) + 1;
        } else {
            return 0;
        }
    
        
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
