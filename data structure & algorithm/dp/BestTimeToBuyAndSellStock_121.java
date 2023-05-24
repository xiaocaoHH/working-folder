package dp;

public class BestTimeToBuyAndSellStock_121 {

	// You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        for (int i = 0; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        return maxProfit;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
