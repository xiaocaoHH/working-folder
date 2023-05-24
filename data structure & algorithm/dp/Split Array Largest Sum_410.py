# it is dp if it is needed to get the results of later parts.
class Solution:
    def splitArray(self, nums: List[int], m: int) -> int:
        
        @lru_cache(maxsize = None)
        def dp(i,m):
            #Base Case: No partion
            if m== 1:
                return sum(nums[i:])
            # res will have the minimum value of a row
            # lsum will have the sum of nums[i:j+1]
            # maxsum will have the maxsum of a dfs(imagine the max of one branch from root)
            res, lsum = math.inf, 0
            # len(nums)-m+1 because dry run couple of test cases to see relation
			
            for j in range(i,len(nums)-m+1):          
                lsum+=nums[j]
                #If lsum is greater than res, we break because this is already bigger than res and its dfs might return even bigger sum, and res has to smallest of all the maxsums 
                if lsum > res: break
                # Recurrence relation - to find max of one dfs(imagine the max of one branch from root)
                maxsum = max(lsum, dp(j+1,m-1))
                # The min of all branches(maxsum) from a root
                res = min(res,maxsum)               
            return res
        
        return dp(0,m)