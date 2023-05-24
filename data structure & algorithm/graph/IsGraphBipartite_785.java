package graph;

public class IsGraphBipartite_785 {
    boolean [] visited;
    boolean [] color;
    boolean isBipartite;
    public boolean isBipartite(int[][] graph) 
    {
        visited = new boolean[graph.length];
        color = new boolean[graph.length];
        isBipartite = true;
        for (int i = 0; i < graph.length; ++i) 
        {
            if (!visited[i]) 
            {
                paint(i, graph);
            }
        }
        return isBipartite;
    }
    
    private void paint(int v, int [][] graph) {
        visited[v] = true;
        for (int w : graph[v]) 
        {
            if (!visited[w]) 
            {
                color[w] = !color[v];
                paint(w, graph);
            } else if (color[w] == color[v]) 
            {
                isBipartite = false;
                return;
            }
        }
    }


    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
