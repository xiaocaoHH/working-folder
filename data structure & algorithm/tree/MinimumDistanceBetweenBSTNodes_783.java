package tree;

public class MinimumDistanceBetweenBSTNodes_783 {

    int prev =Integer.MAX_VALUE;
    int cur =0;
    int diff=Integer.MAX_VALUE;

    public int minDiffInBST(TreeNode root) {   
        inorder(root);
        return diff;
    }

    public void inorder(TreeNode root){    
        if(root!=null){        
            inorder(root.left);
            cur=root.val;        
            if(Math.abs(cur-prev)<diff)
                diff = Math.abs(cur-prev);
            prev=cur;        
            inorder(root.right);
        }
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
