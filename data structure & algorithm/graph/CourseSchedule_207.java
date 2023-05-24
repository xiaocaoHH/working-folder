package graph;

public class CourseSchedule_207 {

// 有向图，拓扑排序； 选取有优先权的nodes
	
//	class Node:
//	    def __init__(self):
//	        self.out_nodes = []
//	        self.indegree = 0
//
//	class Solution(object):
//	    def canFinish(self, numCourses, prerequisites):
//	        """
//	        :type numCourses: int
//	        :type prerequisites: List[List[int]]
//	        :rtype: bool
//	        """
//	        
//	        if not prerequisites:
//	            return True;
//	        
//	        dic={}
//	        for course in range(numCourses):
//	            dic[course] = Node()
//	            
//	        for pair in prerequisites:
//	            dic[pair[1]].out_nodes.append(pair[0])
//	            dic[pair[0]].indegree+=1
//	            
//	        q = deque()
//	        for course in dic.keys():
//	            if dic[course].indegree==0:
//	                q.append(dic[course])
//	                
//	        counter = 0        
//	        while q:
//	            item = q.pop()
//	            counter+=1
//	            
//	            for adj in item.out_nodes:
//	                    dic[adj].indegree-=1
//	                    if dic[adj].indegree==0:
//	                        q.append(dic[adj])
//	                        
//	        if counter==numCourses:
//	            return True
//	        else:
//	            return False    
	            		
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
