package tree;

import java.util.LinkedList;
import java.util.Queue;

public class CousinsInBinaryTree_993 {

    class BigNode{
        TreeNode node;
        int val;
        int parent;
        BigNode(TreeNode node, int parent, int val)
        {
            this.node = node;
            this.parent = parent;    // location of node's parent
            this.val = val;   // location of node
        }
    }    
    BigNode xn = null;
    BigNode yn = null;
    boolean sign = false;    
    public boolean isCousins(TreeNode root, int x, int y) {        
        if(root==null || (root.left==null && root.right==null))
           return false;
        BigNode node = new BigNode(root,0, 1);
        Queue<BigNode> queue = new LinkedList<BigNode>();
        queue.add(node);
        queue.add(null);        
        while(!queue.isEmpty() && queue.peek()!=null)
        {
            BigNode curr = queue.poll();             
            while(curr!=null)
            {
            	// each layer
                if(curr.node.val==x)
                    xn = curr;
                
                if(curr.node.val==y)
                    yn = curr;
                
                if(curr.node.left!=null)
                {
                    BigNode left = new BigNode(curr.node.left, curr.val ,2*curr.val);
                    queue.add(left);
                }                
                if(curr.node.right!=null)
                {
                    BigNode right = new BigNode(curr.node.right, curr.val,2*curr.val+1);
                    queue.add(right);
                }                 
                curr = queue.poll();
            }
            
            if(xn!=null && yn!=null && xn.parent!=yn.parent)
            {
                sign = true;
                break;
            }
            else if(xn==null && yn==null)
            {/*do nothing*/}
            else
            {
            	// not in the same level
                break;
            }
            queue.add(null);           
        }
        return sign;   
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
