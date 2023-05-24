package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//有向图; 多条  from the same starting point，一个visit, [有向图要考虑入度出度，简化]
public class RedundantConnectionII_685 {

    public int[] findRedundantDirectedConnection(int[][] edges) {        
        final int n = edges.length;
        int[] indegree = new int[n + 1];
        int[] outdegree = new int[n + 1];
        final Map<Integer, Set<Integer>> adjList = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            adjList.put(i, new HashSet<>());
        }
        int edgeTo = -1;
        for (final int[] edge : edges) {
            outdegree[edge[0]]++;
            indegree[edge[1]]++;
            if (indegree[edge[1]] > 1) {
                edgeTo = edge[1];
            }
            adjList.get(edge[0]).add(edge[1]);
        }        
        int[] res = null;
        if (edgeTo != -1) {
            int root = -1;
            for (int i = 1; i <= n; i++) {
                if (indegree[i] == 0) root = i;
            }
            
            for (int i = n - 1; i >= 0; i--) {
                if (edges[i][1] == edgeTo) {
                    if (tryDeleteEdge(edges[i][0], edges[i][1], root, adjList)) {
                        res = edges[i];
                        break;
                    }
                }
            }
        } else {
            // [[1,2],[2,3],[3,4],[4,1],[1,5]]
            for (int i = n - 1; i >= 0; i--) {
                int v = edges[i][0];
                
                int w = edges[i][1];
                
                if(edges[i][1] == edges[0][0])
                {
                    res = edges[i];
                    break;
                }
            }
        }
        return res;
    }
    
    // has a circle (this solution has some problems.)
    private boolean tryDeleteEdge(final int v, final int w, final int root, final Map<Integer, Set<Integer>> adjList) {
        final Set<Integer> visited = new HashSet<>();
        dfs(root, v, w, adjList, visited);
        return visited.size() == adjList.size();
    }
    
    private void dfs(int v, int from, int to, Map<Integer, Set<Integer>> adjList, Set<Integer> visited) {
        visited.add(v);
        for (final int adj : adjList.get(v)) {
        	
        	// 不走 from to 
            if (!visited.contains(adj) && !(v == from && adj == to)) {
                dfs(adj, from, to, adjList, visited);
            }
        }
        
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
