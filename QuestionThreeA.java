import java.util.*;

public class QuestionThreeA {
    // Method to process friend requests
    public static List<String> processRequests(int numHouses, int[][] restrictions, int[][] requests) {
        // Initialize the arrays
        boolean[][] restricted = new boolean[numHouses][numHouses];
        boolean[][] friends = new boolean[numHouses][numHouses];
        // Mark restricted pairs
        for (int[] restriction : restrictions) {
            int house1 = restriction[0];
            int house2 = restriction[1];
            restricted[house1][house2] = true;
            restricted[house2][house1] = true;
        }
        // List to store results
        List<String> results = new ArrayList<>();
        // Process each request
        for (int[] request : requests) {
            int house1 = request[0];
            int house2 = request[1];
            // Check if the request would violate any restrictions
            boolean canBeApproved = true;
            if (restricted[house1][house2]) {
                canBeApproved = false;
            } else {
                // Check if adding this request would violate any indirect restrictions
                for (int i = 0; i < numHouses; i++) {
                    if (friends[house1][i] && friends[house2][i] && restricted[i][house2]) {
                        canBeApproved = false;
                        break;
                    }
                    if (friends[house2][i] && friends[house1][i] && restricted[i][house1]) {
                        canBeApproved = false;
                        break;
                    }
                }
            }
            if (canBeApproved) {
                results.add("approved");
                friends[house1][house2] = true;
                friends[house2][house1] = true;
            } else {
                results.add("denied");
            }
        }
        return results;
    }

    public static void main(String[] args) {
        // Example 1
        int numHouses1 = 3;
        int[][] restrictions1 = {{0, 1}};
        int[][] requests1 = {{0, 2}, {2, 1}};
        System.out.println(processRequests(numHouses1, restrictions1, requests1));

        // Example 2
        int numHouses2 = 5;
        int[][] restrictions2 = {{0, 1}, {1, 2}, {2, 3}};
        int[][] requests2 = {{0, 4}, {1, 2}, {3, 1}, {3, 4}};
        System.out.println(processRequests(numHouses2, restrictions2, requests2));
    }
}
