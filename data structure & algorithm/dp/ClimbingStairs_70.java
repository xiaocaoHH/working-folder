package dp;

public class ClimbingStairs_70 {

	public int climbStairs(int n) {
	    if (n <= 2) 
	    {
	        return n;
	    }
	    // dp[i] = dp[i - 1] + dp[i - 2]
	    int pre2 = 1, pre1 = 2;
	    for (int i = 2; i < n; i++) {
	        int cur = pre1 + pre2;
	        pre2 = pre1;
	        pre1 = cur;
	    }
	    return pre1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
