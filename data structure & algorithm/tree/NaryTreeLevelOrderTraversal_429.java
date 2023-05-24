package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NaryTreeLevelOrderTraversal_429 {

    // simple BFS
    public List<List<Integer>> levelOrder(Node root) {
        
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        q.add(null);
        
        List<List<Integer>> lsts = new ArrayList<List<Integer>>();
        
        while(q.peek()!=null)
        {
            Node curr = q.poll();
            List<Integer> lst = new ArrayList<Integer>();            
            
            while(curr!=null)
            {
                lst.add(curr.val);
                List<Node> cs = curr.children;
                for(Node item : cs)
                {
                    q.add(item);
                }         
                curr = q.poll();
            }
            q.add(null);
            lsts.add(lst);
        }
        return lsts;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
