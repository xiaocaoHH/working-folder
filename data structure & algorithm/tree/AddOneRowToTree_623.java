package tree;

import java.util.LinkedList;
import java.util.Queue;

public class AddOneRowToTree_623 {

    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        
        if(depth==1)
        {
            TreeNode curr = new TreeNode(val);
            curr.left = root;
            return curr;
        }       
        
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);
        q.add(null);
        
        int counter = 1;
        while(q.peek()!=null)
        {
            TreeNode curr = q.poll();
            while(curr!=null)
            {
                if(counter==depth-1)
                {
                    TreeNode left = new TreeNode(val);
                    TreeNode right = new TreeNode(val);
                    
                    if(curr.left!=null)
                    {
                        left.left = curr.left;
                        curr.left = left;
                    }
                    else
                    {
                        curr.left = left;
                    }
                    if(curr.right!=null)
                    {
                        right.right = curr.right;
                        curr.right = right;
                    }
                    else
                    {
                        curr.right=right;
                    }
                }
                else
                {
                    if(curr.left!=null)
                    {
                        q.add(curr.left);
                    }
                
                    if(curr.right!=null)
                    {
                        q.add(curr.right);
                    }
                }                
                curr = q.poll();
            }
            q.add(null);
            if(counter==depth-1)
                break;
            counter++;
        }
        return root;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
