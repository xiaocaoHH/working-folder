package tree;

public class MaximumSumBSTInBinaryTree_1373 {

    int max = 0;    
    public int maxSumBST(TreeNode root) 
    {
        find(root);
        return max;
    }
    
    private ValidateBST find(TreeNode node) 
    {
        if (node == null) return new ValidateBST(true);
        
        ValidateBST left = find(node.left);
        ValidateBST right = find(node.right);
        ValidateBST cur = new ValidateBST();
        
        int curValue = node.val;
        
        //getting lower bound -> min(cur, left.lower, right.lower)
        cur.lower = Math.min(curValue, Math.min(left.lower, right.lower));
        
        //getting upper bound -> max(cur, left.upper, right.upper)
        cur.upper = Math.max(curValue, Math.max(left.upper, right.upper));
        
        //isBST -> left.upper < curValue < right.lower
        cur.isBST = left.isBST && right.isBST && curValue>left.upper && curValue<right.lower;
        
        if (cur.isBST) 
        {
            cur.sum = curValue + left.sum + right.sum;
            max = Math.max(max, cur.sum);
        }        
        return cur;
    }
    
    private class ValidateBST {
        int sum;
        boolean isBST;
        
        //we will be doing post order traversal, so the leaf nodes left.upper should be min and right.lower should be max
        int lower = Integer.MAX_VALUE;
        int upper = Integer.MIN_VALUE;
        
        public ValidateBST()
        {            
        }
        
        public ValidateBST(boolean isBST) 
        {
           this.isBST = isBST;
        }
    }

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
	}

}
