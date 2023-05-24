class Solution:
    def numberOfArithmeticSlices(self, nums):
        A = nums
        n = len(A)
        seq = [defaultdict(int) for _ in range(n)]
        for j in range(n):
            for i in range(j):
                seq[j][A[j] - A[i]] += seq[i][A[j] - A[i]] + 1

        # dp: we only care about length>2, so minus n*(n-1)//2
        return sum([sum(seq[j].values()) for j in range(n)]) - n*(n-1)//2