package dp;

public class CoinChange2 {

// 完全背包问题
public int change(int amount, int[] coins) 
{
    if (coins == null) 
    {
        return 0;
    }
    int[] dp = new int[amount + 1];
    dp[0] = 1;
    for (int coin : coins)  // every coin will be considered multiple times
    {
        for (int i = coin; i <= amount; i++)   //将逆序遍历改为正序遍历
        {
            dp[i] += dp[i - coin];
        }
    }
    return dp[amount];
}


//背包问题
//有一个容量为 N 的背包，要用这个背包装下物品的价值最大，这些物品有两个属性：体积 w 和价值 v。
// W 为背包总体积
// N 为物品数量
// weights 数组存储 N 个物品的重量
// values 数组存储 N 个物品的价值

	public int knapsack_1(int W, int N, int[] weights, int[] values) 
	{
	    int[][] dp = new int[N + 1][W + 1];
	    for (int i = 1; i <= N; i++) 
	    {
	        int w = weights[i - 1], v = values[i - 1];
	        for (int j = 1; j <= W; j++) 
	        {
	            if (j >= w) 
	            {
	                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w] + v);
	            } 
	            else 
	            {
	                dp[i][j] = dp[i - 1][j];
	            }
	        }
	    }
	    return dp[N][W];
	}
	
	
//	空间优化
	public int knapsack_2(int W, int N, int[] weights, int[] values) 
	{
	    int[] dp = new int[W + 1];
	    for (int i = 1; i <= N; i++) 
	    {
	        int w = weights[i - 1], v = values[i - 1];
	        for (int j = W; j >= 1; j--) 
	        {
	            if (j >= w) 
	            {
	                dp[j] = Math.max(dp[j], dp[j - w] + v);
	            }
	        }
	    }
	    return dp[W];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
