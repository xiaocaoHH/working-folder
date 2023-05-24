package dp;

public class HouseRobber_198 {

    public int rob(int[] nums) {
        
        // f(k) = max(f(k – 2) + Ak, f(k – 1))
        int prevMax = 0;
        int currMax = 0;
        for (int x : nums) {
            int temp = currMax;
            currMax = Math.max(prevMax + x, currMax);
            prevMax = temp;
        }
        return currMax;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
