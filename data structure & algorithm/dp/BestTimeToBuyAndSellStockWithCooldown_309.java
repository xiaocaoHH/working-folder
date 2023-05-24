package dp;

public class BestTimeToBuyAndSellStockWithCooldown_309 {

    public int maxProfit(int[] prices) 
    {        
        int len = prices.length;
        if (len < 2) return 0;
        int[] hold   = new int[len];    //第i天hold股票的最大profit
        int[] unHold = new int[len];    //第i天不hold股票的最大profit

        hold[0] = -prices[0];  // the maximum money I have if I hold it.
        hold[1] = Math.max(hold[0], -prices[1]);
        unHold[1] = Math.max(unHold[0], hold[0] + prices[1]); // the maximum profit

        for (int i = 2; i < len; i++){
            hold[i]   = Math.max(hold[i - 1], unHold[i - 2] - prices[i]);    // 买
            unHold[i] = Math.max(unHold[i - 1], hold[i - 1] + prices[i]);    // 卖
        }

        return unHold[len - 1];
    }

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
