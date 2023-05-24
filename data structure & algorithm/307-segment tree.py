class SegmentTree:
    def __init__(self, nums):
        n = len(nums)
        self.tree = [0] * (4 * n)
        self.buildTree(nums, 0, n - 1, 0)

    def buildTree(self, nums, left, right, index = 0):
        if left == right:
            self.tree[index] = nums[left]
            return

        mid = (left + right) // 2
        self.buildTree(nums, left, mid, 2 * index + 1)
        self.buildTree(nums, mid + 1, right, 2 * index + 2)
        self.tree[index] = self.tree[2 * index + 1] + self.tree[2 * index + 2]

    # Find sum of nums[i..j]
    def sumRange(self, left, right, i, j, index = 0):
        if right < i or left > j:
            return 0

        if i <= left and right <= j:
            return self.tree[index]

        mid = (left + right) // 2

        return self.sumRange(left, mid, i, j, 2 * index + 1) + self.sumRange(mid + 1, right, i, j, 2 * index + 2)

    # Update nums[pos] = value
    def update(self, left, right, pos, value, index = 0):
        if pos < left or pos > right:
            return

        if left == right:
            self.tree[index] = value
            return

        mid = (left + right) // 2
        if pos <= mid:
            self.update(left, mid, pos, value, 2 * index + 1)
        else:
            self.update(mid + 1, right, pos, value, 2 * index + 2)

        self.tree[index] = self.tree[2 * index + 1] + self.tree[2 * index + 2]


class NumArray:
    def __init__(self, nums: List[int]):
        self.n = len(nums)
        self.it = SegmentTree(nums)
        self.nums = nums

    def update(self, index: int, val: int) -> None:
        self.nums[index] = val
        self.it.update(0, self.n - 1, index, val, 0)

    def sumRange(self, left: int, right: int) -> int:
        return self.it.sumRange(0, self.n - 1, left, right, 0)