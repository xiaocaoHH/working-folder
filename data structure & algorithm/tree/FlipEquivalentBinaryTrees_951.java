package tree;

public class FlipEquivalentBinaryTrees_951 {

    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if(root1==null && root2==null)
            return true;
        else if(root1==null)
            return false;
        else if(root2 == null)
            return false;
        
        if(root1.val != root2.val)
            return false;
        
        return check(root1, root2);
    }
    
    public boolean check(TreeNode root1, TreeNode root2) 
    {
        if(root1==null && root2==null)
            return true;
        else if(root1==null)
            return false;
        else if(root2 == null)
            return false;
        
        int left1 = (root1.left==null)? -1: root1.left.val;
        int right1 = (root1.right==null)? -1: root1.right.val;

        int left2 = (root2.left==null)? -1: root2.left.val;
        int right2 = (root2.right==null)? -1: root2.right.val;
        
        if(left1==right2 && right1 == left2 && check(root1.left,root2.right) && check(root1.right, root2.left))
           return true;
        else if(left1 == left2 && right2==right2 && check(root1.left,root2.left) && check(root1.right, root2.right))
        {
            return true;
        }
        else
           return false;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
