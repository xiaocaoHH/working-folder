package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FlowerPlantingWithNoAdjacent_1042 {

    int[] answer;
    public int[] gardenNoAdj(int N, int[][] paths) {
       
        if(paths==null || paths.length==0)
        {
            answer = new int[N];
            Arrays.fill(answer, 1);
            return answer;
        }

        
        // reorganize
        Map<Integer,List<Integer>> g = reorganize(paths);

        answer = new int[N];        
        search(g);
        
        return answer;
    }
    
    public void search(Map<Integer, List<Integer>> g)
    {
        List<Integer> curr;
        int key;
        for(Map.Entry e:g.entrySet())
        {
            key = (int)e.getKey();
            curr = (List<Integer>) e.getValue();
            
            Set<Integer> colors = new HashSet<Integer>();
            colors.add(1);colors.add(2);colors.add(3);colors.add(4);            
            
            if(answer[key-1]==0)
            {
                for(Integer i:curr)
                {
                    if(answer[i-1]!=0)
                    {
                        colors.remove(answer[i-1]);
                    }
                }                
                Iterator<Integer> iter=colors.iterator();  
                answer[key-1] = iter.next();
            }            
        }
        
        // deal with vertices without edges
        for(int i=0; i<answer.length; i++)
        {
            if(answer[i]==0)
                answer[i]=1;
        }
    }
    
    
    public Map<Integer,List<Integer>> reorganize(int[][] paths)
    {
        Map<Integer,List<Integer>> graph = new HashMap<Integer, List<Integer>>();
        List<Integer> list;
        int curr;
        for(int i=0; i<paths.length; i++)
        {
            // add one direction
            if(graph.containsKey(paths[i][0]))
            {
                list=graph.get(paths[i][0]);
                curr = paths[i][1];
                list.add(curr);
            }
            else
            {
                list= new ArrayList<Integer>();
                curr = paths[i][1];
                list.add(curr);
                graph.put(paths[i][0],list);                
            }
            
            // add another direction
            if(graph.containsKey(paths[i][1]))
            {
                list=graph.get(paths[i][1]);
                curr = paths[i][0];
                list.add(curr);
            }
            else
            {
                list= new ArrayList<Integer>();
                curr = paths[i][0];
                list.add(curr);
                graph.put(paths[i][1],list);                
            }
        }
        
        return graph;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
