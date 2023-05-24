package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class FindLargestValueInEachTreeRow_515 {

    public List<Integer> largestValues(TreeNode root) {
        
        List<Integer> lst = new ArrayList<Integer>();
        if(root==null)
            return lst;
        
        PriorityQueue<Integer> pq;        
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);
        q.add(null);       
        
        while(q.peek()!=null)
        {
            TreeNode curr = q.poll();
            pq = new PriorityQueue<Integer>(Collections.reverseOrder());
            
            while(curr!=null)
            {
                pq.add(curr.val);

                if(curr.left!=null)
                {
                    q.add(curr.left);
                }
                if(curr.right!=null)
                {
                    q.add(curr.right);
                }
                curr = q.poll();
            }
            
            lst.add(pq.peek());         
            q.add(null);

        }
        return lst;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
