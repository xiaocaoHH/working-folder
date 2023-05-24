package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ReconstructItinerary_332 {

    Map<String, PriorityQueue<String>> hm = null;
    List<String> result = new LinkedList<String>();
    
    public List<String> findItinerary(List<List<String>> tickets) {
        
        hm = new HashMap<String, PriorityQueue<String>>();
        result = new ArrayList<String>();
        
        String first = null;
        String second = null;
        PriorityQueue curr = null; 
        
        for(List<String> list : tickets)
        {
            first = list.get(0);
            second = list.get(1);
            
            if(hm.containsKey(first))
            {
                hm.get(first).add(second);
            }
            else
            {
                curr = new PriorityQueue<String>();
                curr.add(second);
                hm.put(first, curr);
            }
        }
        
        dfs("JFK");
        Collections.reverse(result);
        return result;       
        
    }
    
    public void dfs(String s)
    {
        PriorityQueue<String> q = hm.get(s);
 
        String value = null;
	while (q != null && !q.isEmpty()) {
            // pull out
            value = q.poll();
	    dfs(value);
        }
        
        result.add(s);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
