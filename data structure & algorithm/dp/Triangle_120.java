package dp;

import java.util.List;

public class Triangle_120 {

	
	// currow.set(j,currow.get(j)+Math.min(prevrow.get(j),prevrow.get(j+1)))
	 public int minimumTotal(List<List<Integer>> triangle) {

    if(triangle.size()==0)
        return 0;
    for(int i = triangle.size()-2;i>=0;i--)
    {
        List<Integer> prevrow = triangle.get(i+1);
        List<Integer> currow = triangle.get(i);
        for(int j=0;j<currow.size();j++)
            currow.set(j,currow.get(j)+Math.min(prevrow.get(j),prevrow.get(j+1)));
    }
    return triangle.get(0).get(0);
}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
