import java.util.*;

///[Sliding Window]
public class QuestionTwoB {
    public static boolean canSitTogether(int[] nums, int indexDiff, int valueDiff) {
        // Iterate through each seat in the array
        for (int i = 0; i < nums.length; i++) {
            // Check seats in the range from i+1 to i+indexDiff
            for (int j = i + 1; j <= i + indexDiff && j < nums.length; j++) {
                // Check if the difference between the seat numbers is within the valueDiff
                if (Math.abs(nums[i] - nums[j]) <= valueDiff) {
                    return true; // Found a valid pair
                }
            }
        }
        return false; // No valid pair found
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 5, 4, 9};
        int indexDiff = 2;
        int valueDiff = 1;

        boolean result = canSitTogether(nums, indexDiff, valueDiff);
        System.out.println(result); // Output: true
    }
}
