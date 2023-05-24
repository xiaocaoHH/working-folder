package tree;

import java.util.ArrayList;
import java.util.List;

public class BalanceABinarySearchTree_1382 {

    // copy from the discusstion;
    public TreeNode balanceBST(TreeNode root) {
        if(root==null) return null;
        
        List<Integer> list=new ArrayList<>();
        inorder(root,list);
        return buildTree(list, 0, list.size()-1);        
    }
    
    private TreeNode buildTree(List<Integer> list, int start, int end)
    {
        if(start>end) return null;
        int mid=(start)+(end-start)/2;
        
        TreeNode curr=new TreeNode(list.get(mid));
        curr.left=buildTree(list,start,mid-1);
        curr.right=buildTree(list,mid+1,end);
        
        return curr;        
    }
    
    private void inorder(TreeNode root, List<Integer> list)
    {
        if(root==null) return;
        inorder(root.left,list);
        list.add(root.val);
        inorder(root.right,list);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
