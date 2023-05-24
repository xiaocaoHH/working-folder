package dp;

public class UniqueBinarySearchTrees_96 {

    // c(n+1) = sum(c(i)*c(n-i)) i={1,n)
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;  //empty tree
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j < i; ++j) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }
    
	public static void main(String[] args) {

	}

}
