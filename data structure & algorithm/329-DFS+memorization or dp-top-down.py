# copy from solution

class Solution:        
    def longestIncreasingPath(self, matrix):
        if not matrix:
            return 0
        
        res = 0
        rows, cols = len(matrix), len(matrix[0])
        
        memo = [[0 for _ in range(cols)] for _ in range(rows)]
        dirs = [(1,0), (0,1),(-1,0), (0,-1)]
		
        for i in range(rows):
            for j in range(cols):
                res = max(res, self.dfs(i, j, matrix, memo, dirs))
        return res
    
    def dfs(self, i, j, matrix, memo, dirs):
        if memo[i][j] > 0:
            return memo[i][j]
        
        memo[i][j] = 1
        
        for x,y in dirs:
            newX, newY = i+x, j+y
            
            if newX >= 0 and newX < len(matrix) and newY >= 0 and newY < len(matrix[0]) and matrix[newX][newY] > matrix[i][j]:
                memo[i][j] = max(memo[i][j], 1+self.dfs(newX, newY, matrix, memo, dirs))
        
        return memo[i][j]
        