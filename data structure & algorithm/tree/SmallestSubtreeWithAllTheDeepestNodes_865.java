package tree;

public class SmallestSubtreeWithAllTheDeepestNodes_865 {

	   // copy from the discussion
    
	   //  The node we are finding need to satisfy two conditions:

	   //  It's on the longest path from root to leaves
	   //  The depth of its left subtree and right subtree are the same

	   //  Traverse the tree with post-order, the last one that satisfies these two conditions is the result.
	        
	    TreeNode result;
	    int max;
	    public TreeNode subtreeWithAllDeepest(TreeNode root) {
	        result = null;
	        max = -1;
	        getDepth(root, 0);
	        return result;
	    }
	    
	    private int getDepth(TreeNode node, int depth) {
	        if (node == null) {
	            return 0;
	        }
	        int left = getDepth(node.left, depth + 1);
	        int right = getDepth(node.right, depth + 1);
	        
	        if (left == right && depth + left >= max) {
	            result = node;
	            max = depth + left;
	        } 
	        return Math.max(left, right) + 1;
	    }
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
