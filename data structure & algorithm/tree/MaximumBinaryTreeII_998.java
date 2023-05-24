package tree;

public class MaximumBinaryTreeII_998 {

    // 一直往右走
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        
        if(root==null)
            return new TreeNode(val);
        
        if(root.val<val)
        {
            TreeNode curr = new TreeNode(val);
            curr.left = root;
            return curr;
        }
        
        search(null, root, val);
        return root;
    }
    
    public void search(TreeNode parent, TreeNode root, int val)
    {
        
        if(root.val<val)
        {
            TreeNode curr = new TreeNode(val);            
            parent.right = curr;
            curr.left = root;   
            return;
        }
        
        if(root.left!=null && root.right!=null)
        {
            search(root, root.right, val);
        }
        else if(root.left!=null)
        {
            TreeNode curr = new TreeNode(val);
            root.right = curr;
        }
        else if(root.right!=null)
        {
            search(root, root.right, val);
        }
        else
        {
            TreeNode curr = new TreeNode(val);
            root.right = curr;
        }
        return;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
