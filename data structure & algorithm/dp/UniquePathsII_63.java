package dp;

public class UniquePathsII_63 {

	   public int uniquePathsWithObstacles(int[][] obstacleGrid) {
	        int m = obstacleGrid.length;
	        int n = obstacleGrid[0].length;        
	        int[][] db = new int[m][n];
	        // initialize;
	        for(int i=0; i<n; i++)
	        {
	            // row
	            if(obstacleGrid[0][i] == 1)
	            {
	                db[0][i] = 0;
	                break;
	            }
	            else
	            {
	                db[0][i] = 1;
	            }
	        }
	                            
	        for(int i=0; i<m; i++)
	        {
	            // row
	            if(obstacleGrid[i][0] == 1)
	            {
	                db[i][0] = 0;
	                break;
	            }
	            else
	            {
	                db[i][0] = 1;
	            }
	        }
	                            
	                            
	        for(int i=1; i<m; i++)
	        {
	            for(int j=1; j<n; j++)
	            {
	                if(obstacleGrid[i][j] == 1)
	                {
	                    db[i][j] = 0;
	                }
	                else
	                {
	                    db[i][j] = db[i-1][j] + db[i][j-1];
	                }
	            }
	                                
	        }
	        return db[m-1][n-1];
	        
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
