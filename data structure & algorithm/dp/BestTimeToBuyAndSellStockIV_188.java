package dp;


public class BestTimeToBuyAndSellStockIV_188 {

	// 沿着背包问题想
	
    public int maxProfit(int k, int[] prices) 
    {        
        if(prices.length < 2)
            return 0;
         
        int dp[][] = new int[k + 1][prices.length];    
        
        int max = 0; 
        for(int i = 0; i < dp.length; i++)
        {            
            for(int j = 0; j < dp[0].length; j++)
            {                                   
                    if(i == 0 || j == 0)
                    {
                        dp[i][j] = 0;
                    }                
                    else
                    {
                        max = dp[i][j - 1];    // no transaction                 
                        for(int p = 0; p < j; p++)
                        {        
                        	// 第i次考虑第j个
                            max = Math.max(max, dp[i - 1][p] + prices[j] - prices[p]);                        
                        }  
                        dp[i][j] = max;  
                    }                   
            }            
        }        
        return dp[k][prices.length - 1];       
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
