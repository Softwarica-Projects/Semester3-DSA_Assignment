class QuestionFourB {
    static int maxSum;

    public static int findLargestSubtreeSum(Tree.Node root) {
        maxSum = Integer.MIN_VALUE;
        calculateSubtreeSum(root, true);
        return maxSum;
    }

    private static int calculateSubtreeSum(Tree.Node node, boolean isFromRoot) {
        if (node == null) {
            return 0;
        }

        // Calculate the sum of the left and right subtrees
        int leftSum = calculateSubtreeSum(node.left, false);
        int rightSum = calculateSubtreeSum(node.right, false);

        //If the current division of node is from root tree then whichever the max sum from subtree will be the largest subarray
        int currentSum = !isFromRoot ? (node.data + leftSum + rightSum) : Math.max(leftSum, rightSum);
        maxSum = Math.max(maxSum, currentSum);
        return currentSum;
    }

    public static void main(String[] args) {
        Tree.Node root = new Tree.Node(1);
        root.left = new Tree.Node(4);
        root.right = new Tree.Node(3);
        root.left.left = new Tree.Node(2);
        root.left.right = new Tree.Node(4);
        root.right.left = new Tree.Node(2);
        root.right.right = new Tree.Node(5);
        root.right.right.left = new Tree.Node(4);
        root.right.right.right = new Tree.Node(6);
        int result = findLargestSubtreeSum(root);
        System.out.println(result);
    }
}