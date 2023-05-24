package tree;

import java.util.ArrayList;
import java.util.List;

public class SerializeAndDeserializeBST_449 {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        
        if(root==null)
            return null;
        
        String pre = pre_traverse(root);
        String in = in_traverse(root);
        
        return pre + "#" + in;
    }
    
    String pre_traverse(TreeNode root)
    {
        if(root==null)
            return "";        
        String str = String.valueOf(root.val)+":";
        str = str + pre_traverse(root.left);
        str = str + pre_traverse(root.right);
        return str;
    }
    
    String in_traverse(TreeNode root)
    {
        if(root==null)
            return "";
        String str = in_traverse(root.left);
        str = str + String.valueOf(root.val)+":";
        str = str + in_traverse(root.right);
        return str;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        
        if(data==null || data.isEmpty())
            return null;
        
        String[] str = data.split("#");
        List<String> pre = new ArrayList<String>();
        List<String> in = new ArrayList<String>();
        
        String part = str[0];
        String[] items = part.split(":");
        for(String item : items)
        {
            if(!item.isEmpty())
            {
                pre.add(item);
            }
        }
        part = str[1];
        items = part.split(":");
        for(String item:items)
        {
            if(!item.isEmpty())
            {
                in.add(item);
            }
        } 
        
        return build(pre,in);
    }
    
    TreeNode build(List<String> po, List<String> io)
    {
        if(po.size()==0)
            return null;
        
        String curr=po.get(0);
        List<String> po_left = new ArrayList<String>();
        List<String> po_right = new ArrayList<String>();
        List<String> io_left = new ArrayList<String>();
        List<String> io_right = new ArrayList<String>();
        
        int counter = 0;
        int key = -1;
        for(int i=0; i< io.size(); i++)
        {
            if(!io.get(i).equals(curr))
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
        TreeNode root = new TreeNode(Integer.valueOf(curr),left,right);
        return root;        
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
