# -*- coding: utf-8 -*-
"""Untitled5.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1c88uP2hj1-OO0NURhiD0jevePcfaFUBH
"""

class Solution:
    def searchMatrix(self, mat: List[List[int]], target: int) -> bool:
        m=len(mat)
        n=len(mat[0])
        
        for i in range(m):
            if mat[i][0]<=target and mat[i][-1]>=target:
                lo=0
                hi=n-1
                while (lo<=hi):
                    mid=(lo+hi)//2
                    
                    if mat[i][mid]==target:
                        return True
                    elif mat[i][mid]<target:
                        lo = mid + 1
                    else:
                        hi = mid - 1
                        
        return False
                
            