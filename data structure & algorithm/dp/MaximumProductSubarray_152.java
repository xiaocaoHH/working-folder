package dp;

public class MaximumProductSubarray_152 {

	// nums could be positive or negative, so consider two directions
	// dp[i][0] = Math.max(Math.max(dp[i - 1][0] * nums[i].., dp[i - 1][1] * nums[i]), nums[i]);
	// dp[i][1] = Math.min(Math.min(dp[i - 1][0] * nums[i], dp[i - 1][1] * nums[i]), nums[i]);
	    
	    public int maxProduct(int[] nums) {
	        int[][] dp = new int[nums.length][2];
	        dp[0][0] = nums[0];
	        dp[0][1] = nums[0];
	        for(int i = 1; i < dp.length; i++) {
	            dp[i][0] = Math.max(Math.max(dp[i - 1][0] * nums[i], dp[i - 1][1] * nums[i]), nums[i]);
	            dp[i][1] = Math.min(Math.min(dp[i - 1][0] * nums[i], dp[i - 1][1] * nums[i]), nums[i]);
	        }
	        
	        int max = nums[0];
	        for(int i = 0; i < dp.length; i++) {
	            max = Math.max(max, dp[i][0]);
	        }
	        return max;
	    }
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
