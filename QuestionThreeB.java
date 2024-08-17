import java.util.*;

///[Linked List Reverse Node]
public class QuestionThreeB {
    // Method to rearrange passengers
    public static List<Integer> optimizeBoarding(List<Integer> head, int k) {
        int n = head.size();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i += k) {
            // Determine the end index of the current segment
            int end = Math.min(i + k, n);
            // Create a sublist for the current segment
            List<Integer> segment = head.subList(i, end);
            // Reverse the segment if it has exactly k elements
            if (segment.size() == k) {
                for (int j = segment.size() - 1; j >= 0; j--) {
                    result.add(segment.get(j));
                }
            } else {
                // Add remaining passengers as is
                result.addAll(segment);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> head1 = Arrays.asList(1, 2, 3, 4, 5);
        int k1 = 2;
        System.out.println(optimizeBoarding(head1, k1));

        List<Integer> head2 = Arrays.asList(1, 2, 3, 4, 5);
        int k2 = 3;
        System.out.println(optimizeBoarding(head2, k2));
    }
}
