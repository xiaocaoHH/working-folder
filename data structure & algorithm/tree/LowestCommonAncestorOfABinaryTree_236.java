package tree;

import java.util.ArrayList;
import java.util.List;

public class LowestCommonAncestorOfABinaryTree_236 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        
        List<Integer> pre = new ArrayList<Integer>();
        List<Integer> in = new ArrayList<Integer>();
        
        pre_traverse(root, pre);
        in_traverse(root, in);

        List<Integer> lst = new ArrayList<Integer>();
        for(Integer item:pre)
        {
            if((item==p.val) || (item==q.val))
            {
                lst.add(item);
                break;
            }
            else
            {
                lst.add(item);
            }
        }
        
        Integer s = -1;
        Integer target = null;
        for(Integer item:in)
        {
            if((item==p.val) || (item==q.val))
            {
                s++;
                if(s==0)
                    continue;
                else
                    break;
            }
            else if(s==0 && lst.indexOf(item)!=-1)
            {
                target=item;
            }
        }

        return new TreeNode((target==null)?lst.get(lst.size()-1):target);
    }
    
    public void pre_traverse(TreeNode root, List<Integer> pre)
    {
        if(root==null)
            return;        
        pre.add(root.val);
        pre_traverse(root.left, pre);
        pre_traverse(root.right,pre);
    }
    
    public void in_traverse(TreeNode root, List<Integer> in)
    {
        if(root==null)
            return;
        in_traverse(root.left,in);
        in.add(root.val);
        in_traverse(root.right,in);
    }
}
