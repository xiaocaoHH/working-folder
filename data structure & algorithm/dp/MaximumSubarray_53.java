package dp;

public class MaximumSubarray_53 {

	  public int maxSubArray(int[] nums) {
		    int n = nums.length, maxSum = nums[0];
		    for(int i = 1; i < n; ++i) {
		      if (nums[i - 1] > 0) nums[i] += nums[i - 1]; [nums: local maximum]
		      maxSum = Math.max(nums[i], maxSum);
		    }
		    return maxSum;
		  }
	  
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
