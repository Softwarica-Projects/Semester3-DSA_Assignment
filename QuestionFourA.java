import java.util.*;

///[Dijkstra's Algorithm in weighted graph]
public class QuestionFourA {
    static class Edge {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static int[][] modifyRoads(int n, int[][] roads, int source, int destination, int target) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        List<int[]> edgesWithNegatives = new ArrayList<>();
        for (int[] road : roads) {
            int from = road[0], to = road[1], weight = road[2];
            if (weight == -1) {
                edgesWithNegatives.add(road);
                graph.get(from).add(new Edge(to, Integer.MAX_VALUE));
                graph.get(to).add(new Edge(from, Integer.MAX_VALUE));
            } else {
                graph.get(from).add(new Edge(to, weight));
                graph.get(to).add(new Edge(from, weight));
            }
        }

        int originalDistance = dijkstra(graph, n, source, destination);
        if (originalDistance < target) {
            return new int[0][0];
        }
        for (int[] edge : edgesWithNegatives) {
            edge[2] = 1;
        }

        int newDistance = dijkstra(graph, n, source, destination);
        while (newDistance > target) {
            for (int[] edge : edgesWithNegatives) {
                edge[2] += (newDistance - target);
                if (edge[2] > 2 * 1000000000) {
                    return new int[0][0];
                }
            }
            newDistance = dijkstra(graph, n, source, destination);
        }

        return roads;
    }

    private static int dijkstra(List<List<Edge>> graph, int n, int source, int destination) {
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }
            if (dist[u] == Integer.MAX_VALUE) break;
            visited[u] = true;
            for (Edge edge : graph.get(u)) {
                int v = edge.to, weight = edge.weight;
                if (weight == Integer.MAX_VALUE) continue;
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }
        return dist[destination];
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] roads = {{4, 1, -1}, {2, 0, -1}, {0, 3, -1}, {4, 3, -1}};
        int source = 0;
        int destination = 1;
        int target = 5;
        int[][] result = modifyRoads(n, roads, source, destination, target);
        if (result.length == 0) {
            System.out.println("Not possible to achieve target time");
        } else {
            for (int[] road : result) {
                System.out.println(Arrays.toString(road));
            }
        }
    }
}
