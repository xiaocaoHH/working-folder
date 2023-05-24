package dp;

public class MinimumPathSum_64 {

    public int minPathSum(int[][] grid) {
        
    if(grid == null || grid.length==0)
        return 0;
 
    int m = grid.length;
    int n = grid[0].length;
 
    int[][] dp = new int[m][n];
    dp[0][0] = grid[0][0];    
 
    // initialize top row
    for(int i=1; i<n; i++){
        dp[0][i] = dp[0][i-1] + grid[0][i];
    }
 
    // initialize left column
    for(int j=1; j<m; j++){
        dp[j][0] = dp[j-1][0] + grid[j][0];
    }
 
    // fill up the dp table
    for(int i=1; i<m; i++){
        for(int j=1; j<n; j++){
            if(dp[i-1][j] > dp[i][j-1]){
                dp[i][j] = dp[i][j-1] + grid[i][j];
            }else{
                dp[i][j] = dp[i-1][j] + grid[i][j];
            }
        }
    }
    return dp[m-1][n-1];
    }
    
    
    public int minPathSum_2(int[][] grid) 
    {
        if (grid.length == 0 || grid[0].length == 0) 
        {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        int[] dp = new int[n];
        for (int i = 0; i < m; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                if (j == 0) {
                    dp[j] = dp[j];        // 只能从上侧走到该位置
                } else if (i == 0) {
                    dp[j] = dp[j - 1];    // 只能从左侧走到该位置
                } else {
                    dp[j] = Math.min(dp[j - 1], dp[j]);
                }
                dp[j] += grid[i][j];
            }
        }
        return dp[n - 1];
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
