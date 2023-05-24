package dp;

public class MaximumScoreFromPerformingMultiplicationOperations_1770 {

	// dp: top down method;
    public int maximumScore(int[] nums, int[] multipliers) {
      int si=0;
      int ei=nums.length-1;
        
      return helper(nums,multipliers,si,ei,0,new Integer[multipliers.length][multipliers.length]);
    }
    public int helper(int []nums,int []multipliers,int si,int ei,int idx,Integer[][] mat){
        if(idx==multipliers.length)
            return 0;
        if(mat[si][idx]!=null)  // save time to compute further [top -> down dp]
           return mat[si][idx];
        
        int score1=nums[si]*multipliers[idx]+helper(nums,multipliers,si+1,ei,idx+1,mat);
        int score2=nums[ei]*multipliers[idx]+helper(nums,multipliers,si,ei-1,idx+1,mat);
        mat[si][idx]=Math.max(score1,score2);
        return mat[si][idx];    
    }

}
