    def findMaximumXOR(self, nums: List[int]) -> int:
        sol = 0 
        for i in reversed(range(32)): # XOR from left, border=2^32
            prefixs = set([x >> i for x in nums]) # right shift (shrink) for i bits, keep left part
            sol <<= 1 # left shift (expand 0 on right) for each stage 
            candidate = sol + 1 # expected value to find on set()
            for p in prefixs:
                if candidate ^ p in prefixs: # apply rule: a^b=c so a^c=b to search targets
                    sol = candidate # update the largest solution we could obtain in current stage
                    break
        return sol