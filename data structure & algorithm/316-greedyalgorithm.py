import numpy as np

class Solution:
    def removeDuplicateLetters(self, s: str) -> str:
        cnt = [0]*26
        pos = 0
        for index in range(len(s)):
            cnt[ord(s[index])-ord('a')] += 1
        
        for index in range(len(s)):
            if s[index] < s[pos]:
                pos = index
            
            cnt[ord(s[index]) - ord('a')] -= 1
            if cnt[ord(s[index]) - ord('a')]==0:
                break
        result = ""
        if len(s)==0:
            result = ""
        else:
            result = s[pos]+self.removeDuplicateLetters(s[pos+1:].replace(s[pos],""))

        return result
        