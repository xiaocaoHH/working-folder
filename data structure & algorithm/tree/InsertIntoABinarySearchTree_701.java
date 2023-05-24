package tree;

public class InsertIntoABinarySearchTree_701 {

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(root == null)
        {
            root = new TreeNode(val);
            return root;
        }        
        search(null,root,val,0);
        return root;
    }
    
    public void search(TreeNode parent, TreeNode root, int val, int branch)
    {
        if(root==null)        
        {
            TreeNode in = new TreeNode(val);
            if(branch==-1)
            {
                parent.left = in;
            }
            else
            {
                parent.right = in;
            }
            return;
        }
        
        if(root.val < val)
        {
            search(root,root.right,val, 1);
        }
        
        if(root.val > val)
        {
            search(root,root.left,val, -1);
        }
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
