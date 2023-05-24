package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AllNodesDistanceKInBinaryTree_863 {

    
    // copy from the solution
    Map<TreeNode, TreeNode> parent;
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        parent = new HashMap();
        dfs(root, null);
        
        Queue<TreeNode> queue = new LinkedList();
        queue.add(target);
        queue.add(null);

        if(K==0)
        {
           List<Integer> lst = new ArrayList<Integer>();
           lst.add(target.val);
           return lst;
        }
        
        Set<TreeNode> seen = new HashSet();
        seen.add(target);

        int dist = 0;
        while (queue.peek()!=null) {
            TreeNode node = queue.poll();
            
            while(node!=null)
            {
                if (node.left!=null && !seen.contains(node.left))
                {
                    seen.add(node.left);
                    queue.offer(node.left);
                }
                if (node.right!=null && !seen.contains(node.right)) 
                {
                    seen.add(node.right);
                    queue.offer(node.right);
                }
                TreeNode par = parent.get(node);
                if (par!=null && !seen.contains(par)) 
                {
                    seen.add(par);
                    queue.offer(par);
                }
                node = queue.poll();
            }           

            dist++;
            
            if (dist == K) 
            {
                List<Integer> ans = new ArrayList();
                for (TreeNode n: queue)
                    ans.add(n.val);
                return ans;
            }
            
            queue.add(null);
        }

        return new ArrayList<Integer>();
    }

    public void dfs(TreeNode node, TreeNode par) {
        if (node != null) {
            parent.put(node, par);
            dfs(node.left, node);
            dfs(node.right, node);
        }
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
