package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//多条线 from the same starting point，一个visit，
public class EvaluateDivision_399 {

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for(int i = 0; i<values.length; i++){
            List<String> equation = equations.get(i);
            String a = equation.get(0);
            String b = equation.get(1);
            
            graph.putIfAbsent(a, new HashMap<String, Double>());
            graph.get(a).putIfAbsent(b, values[i]);
            
            if(values[i] == 0){
                continue;
            }
            
            graph.putIfAbsent(b, new HashMap<String, Double>());
            graph.get(b).putIfAbsent(a, 1.0/values[i]);
        }
        
        double [] res = new double[queries.size()];
        for(int i = 0; i<queries.size(); i++){
            List<String> query = queries.get(i);
            res[i] = dfs(query.get(0), query.get(1), 1.0, graph, new HashSet<String>());
        }
        
        return res;
    }
    
    private double dfs(String s, String e, double cur, Map<String, Map<String, Double>> graph, Set<String> visited){
        if(!graph.containsKey(s) || !visited.add(s)){
            return -1.0;
        }
        
        if(s.equals(e)){
            return cur;
        }
        
        Map<String, Double> neighbors = graph.get(s);
        for(Map.Entry<String, Double> entry : neighbors.entrySet()){
            double temp = dfs(entry.getKey(), e, cur*entry.getValue(), graph, visited);
            if(temp != -1.0){
                return temp;
            }
        }
        
        return -1.0;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
