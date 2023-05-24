package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphValidTree_261 {

	
	// 多条线  from the same starting point，一个visit，visit是true时分为两种情况，一种是环，一种是parent （结合684）
    public boolean validTree(int n, int[][] edges) {
        if (edges.length == 0) {
            return n == 1;
        }
        if (n != edges.length + 1) {
            return false;
        }
        
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        Set<Integer> visited = new HashSet<>();
        if (hasCycle(0, -1, graph, visited)) {
            return false;
        }
        return visited.size() == n;
    }
    
    private boolean hasCycle(int node, int parent, Map<Integer, Set<Integer>> graph, Set<Integer> visited) {
        visited.add(node);
        for (int neighbor : graph.get(node)) {
            if (neighbor != parent)
            {
                if(visited.contains(neighbor)) {
                    return true;
                }
                if (hasCycle(neighbor, node, graph, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
