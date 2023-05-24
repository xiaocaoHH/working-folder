package tree;

import java.util.ArrayList;
import java.util.List;

public class CheckCompletenessOfABinaryTree_958 {

    public boolean isCompleteTree(TreeNode root) {
        List<ANode> nodes = new ArrayList();
        if(root==null)
            return true;
        nodes.add(new ANode(root, 1));
        int i = 0;
        while (i < nodes.size()) {
            ANode anode = nodes.get(i);
            if (anode.node.left != null) {
                nodes.add(new ANode(anode.node.left, anode.code * 2));
            }
            if(anode.node.right!=null)
            {
               nodes.add(new ANode(anode.node.right, anode.code * 2 + 1));
            }
            i++;
        }

        return nodes.get(i-1).code == nodes.size();
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
