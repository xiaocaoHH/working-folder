package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CompleteBinaryTreeInserter_919 {

    List<TreeNode> lst = new ArrayList<TreeNode>();
    public void CBTInserter(TreeNode root) {
        serialize(root);
    }
    
    public void serialize(TreeNode root)
    {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);
        
        TreeNode curr;
        while(!q.isEmpty())
        {
            curr = q.poll();
            lst.add(curr);
            if(curr.left!=null)
            {
                q.add(curr.left);
            }
                
            if(curr.right!=null)
            {
                q.add(curr.right);
            }
        }
    }
    
    public int insert(int v) {
        TreeNode root=new TreeNode(v);
        lst.add(root);
        if(lst.size()==1)
            return(0);

        int p =lst.size()/2-1;        
        if(lst.size()%2==0){            
            lst.get(p).left=root;
        }
        else
        {
            lst.get(p).right=root;
        }
        
        return(lst.get(p).val);
    }
    
    public TreeNode get_root() {
        return lst.get(0);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
