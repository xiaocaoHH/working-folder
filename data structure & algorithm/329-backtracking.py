import numpy as np
class Solution:
    def longestIncreasingPath(self, matrix: List[List[int]]) -> int:

        height = len(matrix)
        width = len(matrix[0])

        result = [0]
        for i in range(height):
            for j in range(width):
                visit = np.zeros((height,width), dtype='int')
                curr_len = 1
                self.search(matrix, height, width, curr_len, result, visit, i, j)
        return result[0]

    def search(self, matrix, height, width, curr_len, result, visit, i, j):
        if curr_len>result[0]:
            result[0] = curr_len

        if visit[i][j]==1:
            return

        up = i-1
        if up>=0 and matrix[up][j]>matrix[i][j]:
            visit[i][j]=1
            self.search(matrix, height, width, curr_len+1, result, visit, up, j)
            visit[i][j]=0

        down = i+1
        if down<height and matrix[down][j]>matrix[i][j]:
            visit[i][j]=1
            self.search(matrix, height, width, curr_len+1, result, visit, down, j)
            visit[i][j]=0

        left = j-1
        if left>=0 and matrix[i][left]>matrix[i][j]:
            visit[i][j]=1
            self.search(matrix, height, width, curr_len+1, result, visit, i, left)
            visit[i][j]=0

        right = j+1
        if right<width and matrix[i][right]>matrix[i][j]:
            visit[i][j]=1
            self.search(matrix, height, width, curr_len+1, result, visit, i, right)
            visit[i][j]=0

