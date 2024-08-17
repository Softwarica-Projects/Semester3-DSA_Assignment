public class QuestionFiveB {
    //Sliding Window
    public static int longestUphill(int[] nums, int k) {
        int maxLength = 0;
        int start = 0;

        for (int end = 1; end < nums.length; end++) {
            if (end > start && (nums[end] <= nums[end - 1] || nums[end] - nums[end - 1] > k)) {
                start = end;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        int[] altitudes = {4, 2, 1, 3, 4, 5, 8, 15};
        int maxGain = 3;
        int longest = longestUphill(altitudes, maxGain);
        System.out.println("Longest hike: " + longest);
    }
}