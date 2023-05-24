package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmallestStringStartingFromLeaf_988 {

    String tar = "";
    Map<Integer, String> map = new HashMap<Integer,String>();
    
    public String smallestFromLeaf(TreeNode root) {
        List<Integer> s = new ArrayList<Integer>();
        
        map.put(0,"a");
        map.put(1,"b");
        map.put(2,"c");
        map.put(3,"d");
        map.put(4,"e");
        map.put(5,"f");
        map.put(6,"g");
        map.put(7,"h");
        map.put(8,"i");
        map.put(9,"j");
        map.put(10,"k");
        map.put(11,"l");
        map.put(12,"m");
        map.put(13,"n");
        map.put(14,"o");
        map.put(15,"p");
        map.put(16,"q");
        map.put(17,"r");
        map.put(18,"s");
        map.put(19,"t");
        map.put(20,"u");
        map.put(21,"v");
        map.put(22,"w");
        map.put(23,"x");
        map.put(24,"y");
        map.put(25,"z");
        
        if(root==null)
            return tar;
        
        s.add(root.val);
        search(root,s);
        return tar;
        
    }
    
    public void update(List<Integer> s)
    {
        StringBuilder builder = new StringBuilder();
        for(int i=s.size()-1; i>=0; i--)
        {
            builder.append(map.get(s.get(i)));
        }
        
        String str = builder.toString();
        if(tar.isEmpty())
        {
            tar = str;
        }
        else if(str.compareTo(tar)<0)
        {
            tar = str;
        }
    }
    
    public void search(TreeNode root, List<Integer> s)
    {
        
        if(root.left==null && root.right==null)
        {
            update(s);
            return;
        }
        
        if(root.left!=null)
        {
            s.add(root.left.val);
            search(root.left, s);
            s.remove(s.size()-1);
        }
        
        if(root.right!=null)
        {
            s.add(root.right.val);
            search(root.right, s);
            s.remove(s.size()-1);
        }
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
