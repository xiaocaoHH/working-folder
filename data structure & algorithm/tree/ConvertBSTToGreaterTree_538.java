package tree;

public class ConvertBSTToGreaterTree_538 {

    int sum = 0;
    int first = 0;
    public TreeNode convertBST(TreeNode root) {
        
        if(root==null)
            return root;
        
        convertBST(root.right);
        
        if(first==0 && ((root.left==null && root.right==null) || root.right==null))
        {
            sum = root.val;
            first=1;
        }
        else
        {
            sum = sum + root.val;
            root.val = sum;
        }
        
        convertBST(root.left);
        
        return root;
    }
    
    public int get(TreeNode curr)
    {
        if(curr==null)
            return 0;
        
        return curr.val + get(curr.left) + get(curr.right);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
