package tree;

public class BinaryTreePruning_814 {

    public TreeNode pruneTree(TreeNode root) {
        
        //Handling Null case
        if(root==null)
        {
         return null;   
        }
        //altering left side value using recursion
        root.left=pruneTree(root.left);
        //altering right side value recursion
        root.right=pruneTree(root.right);
        
        /*validation of the base case and altering it with null so recursive approach return null in this case and the left side got removed from the tree*/
        if(root.val==0 && root.left==null && root.right==null)
            return null;
        
        //returning the tree removing the sub tree in former recursion
        return root;
        
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
