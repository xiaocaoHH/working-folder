package graph;

import java.util.LinkedList;
import java.util.List;

public class PossibleBipartition_886 {

    private boolean[] marked;
    private boolean[] colors;
    private boolean isTwoColorable;
    
    private List<Integer>[] initialGraph(int N, int[][] dislikes) {
        List<Integer>[] graph = (List<Integer>[]) new LinkedList[N + 1];
        for (int i = 0; i <= N; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] edge : dislikes) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        return graph;
    }
    
    public boolean possibleBipartition(int N, int[][] dislikes) {
        List<Integer>[] graph = initialGraph(N, dislikes);
        marked = new boolean[N + 1];
        colors = new boolean[N + 1];
        isTwoColorable = true;
        for (int s = 1; s <= N; s++) {
            if (!marked[s]) dfs(graph, s);
            if (!isTwoColorable) return false;
        }
        return true;
    }

    private void dfs(List<Integer>[] G, int v) {
        marked[v] = true;
        for (int w : G[v]) {
            if (!marked[w]) {
                colors[w] = !colors[v];
                dfs(G, w);
            }
            else if (colors[v] == colors[w]) {
                isTwoColorable = false;
                return;
            }
        }
    }

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
