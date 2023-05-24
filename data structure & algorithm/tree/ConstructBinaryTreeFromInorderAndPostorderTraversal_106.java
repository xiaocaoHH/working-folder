package tree;

import java.util.ArrayList;
import java.util.List;

public class ConstructBinaryTreeFromInorderAndPostorderTraversal_106 {

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        
        List<Integer> po = new ArrayList<Integer>();
        for(int i=0; i<postorder.length; i++)
            po.add(postorder[i]);
        
        List<Integer> io = new ArrayList<Integer>();
        for(int i=0; i<inorder.length; i++)
            io.add(inorder[i]);
        
        return build(io, po);
    }
    
    public TreeNode build(List<Integer> io, List<Integer> po)
    {
        if(po.size()==0)
            return null;
        
        int curr=po.get(po.size()-1);
        List<Integer> po_left = new ArrayList<Integer>();
        List<Integer> po_right = new ArrayList<Integer>();
        List<Integer> io_left = new ArrayList<Integer>();
        List<Integer> io_right = new ArrayList<Integer>();
        
        int counter = 0;
        int key = -1;
        for(int i=0; i< io.size(); i++)
        {
            if(io.get(i)!=curr)
            {
                counter++;
                io_left.add(io.get(i));
            }
            else
            {
                key = i;
                break;
            }
        
        }
        
        for(int i=key+1; i< io.size(); i++)
        {
            io_right.add(io.get(i));
        }
        
        for(int i=0; i<po.size()-1; i++)
        {
            if(counter>0)
            {
                po_left.add(po.get(i));
                counter--;
            }
            else
            {
                po_right.add(po.get(i));
            }
        }

        TreeNode left = build(io_left,po_left);
        TreeNode right = build(io_right, po_right);
        TreeNode root = new TreeNode(curr,left,right);
        return root;
        
    }
    
}
