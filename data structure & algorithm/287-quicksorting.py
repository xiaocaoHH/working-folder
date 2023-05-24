class Solution:
    def findDuplicate(self, nums: List[int]) -> int:
        
        self.sort(nums, 0, len(nums)-1)
        print(nums)
        
        for index in range(0, len(nums)-1, 1):
            if nums[index] == nums[index+1]:
                return nums[index]
            
    def sort(self, nums, start, end):
        
        if start < end:
            loc = self.partition(nums, start, end)
            self.sort(nums, start, loc-1)
            self.sort(nums, loc+1, end)
    
    def partition(self, nums, start, end):
        
        val = nums[start]
        while start < end:
            while start<end and nums[end] >= val:
                end = end -1
            
            nums[start] = nums[end]
                
            while start < end and nums[start] <= val:
                start = start + 1
            nums[end] = nums[start]
            
        nums[start] = val
            
        return start