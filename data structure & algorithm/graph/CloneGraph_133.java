package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {}

    public Node(int _val,List<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
};

public class CloneGraph_133 {

    HashMap<Node, Node> map = new HashMap<>();
    public Node cloneGraph(Node node) {
        map.put(node, new Node(node.val, new ArrayList<>()));
 
        for(Node neighbor: node.neighbors){
            if(map.containsKey(neighbor)){
                map.get(node).neighbors.add(map.get(neighbor)); 
            }else{
                map.get(node).neighbors.add(cloneGraph(neighbor));
            }
        }
 
    return map.get(node);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
