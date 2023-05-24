package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 //多条线 from the same starting point，每条线独立，同过回溯visit, 但是weight是所有条线共有
public class FindTheCityWithTheSmallestNumberOfNeighborsAtAThresholdDistance_1334 {

	   public int findTheCity(int n, int[][] edges, int distanceThreshold) {
	        List<List<int[]>> adj = new ArrayList<>();
	        int[] res = new int[n];
	        int minim = Integer.MAX_VALUE;
	        int minimIndex = n-1;
	        for(int i=0;i<n;i++) {
	            adj.add(new ArrayList<>());
	        }
	        for(int i=0;i<edges.length;i++) {
	            adj.get(edges[i][0]).add(new int[]{edges[i][1], edges[i][2]});
	            adj.get(edges[i][1]).add(new int[]{edges[i][0], edges[i][2]});
	        }
	        for(int i=0;i<n;i++) {
	            dfsUtil(i, n, adj, distanceThreshold, res);
	        }
	        for(int i=0;i<n;i++) {
	            if(res[i]<=minim) {
	                minim = res[i];
	                minimIndex = i;
	            }
	        }
	        return minimIndex;
	    }
	    
	    
	    public void dfsUtil(int index, int n, List<List<int[]>> adj, int threshhold, int[] res) {
	        int[] weights = new int[n];
	        Arrays.fill(weights, Integer.MAX_VALUE);
	        dfs(index, 0, adj, threshhold, new int[n], weights);
	        
	        // count the cities;
	        for(int i=0;i<n;i++) {
	            res[index] += weights[i] <= threshhold ? 1 : 0;
	        }
	    }
	    
	    public void dfs(int index, int weight, List<List<int[]>> adj, int threshhold, int[] visited, int[] weights) {
		//已经访问过，取小的权重和才不会对其它结果有影响
	        if(weights[index]<=weight || weight > threshhold)
	            return;
	        weights[index] = weight;
	        for(int i=0;i<adj.get(index).size();i++) {
	            if(visited[adj.get(index).get(i)[0]]==0) {
	                visited[adj.get(index).get(i)[0]] = 1;
	                dfs(adj.get(index).get(i)[0], weights[index] + adj.get(index).get(i)[1], adj, threshhold, visited, weights);
	                visited[adj.get(index).get(i)[0]] = 0;
	            }
	        }
	    }
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
