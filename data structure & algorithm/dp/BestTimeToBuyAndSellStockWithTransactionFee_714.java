package dp;

public class BestTimeToBuyAndSellStockWithTransactionFee_714 {

    public int maxProfit(int[] p, int fee) {
        
        // Find the maximum profit you can achieve. You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction.
        // copy from the discussion
        int n = p.length;
        
        int unhold = 0;  // unhold
        int hold = -p[0];  // hold

        for(int i=1; i<n; i++)
        { 
            unhold = Math.max(unhold, hold + p[i] - fee);
            hold = Math.max(hold, unhold-p[i]);
        }
        
        return unhold;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
