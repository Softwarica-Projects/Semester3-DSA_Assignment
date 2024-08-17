
public class Tree {
    public static class Node {
        Node left;
        Node right;
        int data;

        public Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    public static Node addLeft(Node node, int data) {
        var newNode = new Node(data);
        node.left = newNode;
        return newNode;

    }

    public static Node addRight(Node node, int data) {
        var newNode = new Node(data);
        node.right = newNode;
        return newNode;
    }

}