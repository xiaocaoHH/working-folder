package dp;

public class BestTimeToBuyAndSellStockII_122 {

	// Find the maximum profit you can achieve. You may complete as many transactions as you like
    public int maxProfit(int[] prices) {
        
        // copy from other places
        
        int profit = 0;
        for (int i = 1; i < prices.length; i++) 
        {
            if (prices[i] > prices[i - 1]) 
            {
                profit += (prices[i] - prices[i - 1]);
            }
        } 
        return profit;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
