package tree;

import java.util.ArrayList;
import java.util.List;

public class ConstructBinaryTreeFromPreorderAndInorderTraversal_105 {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        
        List<Integer> po = new ArrayList<Integer>();
        for(int i=0; i<preorder.length; i++)
            po.add(preorder[i]);
        
        List<Integer> io = new ArrayList<Integer>();
        for(int i=0; i<inorder.length; i++)
            io.add(inorder[i]);
        
        return build(po, io);
    }
    
    public TreeNode build(List<Integer> po, List<Integer> io)
    {
        if(po.size()==0)
            return null;
        
        int curr=po.get(0);
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
        
        for(int i=1; i<po.size(); i++)
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

        TreeNode left = build(po_left,io_left);
        TreeNode right = build(po_right, io_right);
        TreeNode root = new TreeNode(curr,left,right);
        return root;
        
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
