import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;
public class QuestionSeven extends JFrame{

    private Map<String, Point> cityPositions;
    private Map<String, Integer> cityIndexMap;
    private int[][] distances;

    private String sourceCity;
    private String destinationCity;
    String[] cities = {"L1", "L2","L3","L4","L5","L6"};

    private List<Integer> shortestPath;

    public QuestionSeven() {
        setTitle("Route Optimization");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeData();

        setLayout(new BorderLayout(10, 10));

        GraphPanel graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        JPanel pathPanel = new JPanel(new BorderLayout());
        pathPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        pathPanel.setBackground(Color.LIGHT_GRAY);

        JLabel pathLabel = new JLabel("");
        pathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pathPanel.add(pathLabel, BorderLayout.CENTER);
        add(pathPanel, BorderLayout.EAST);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel startLabel = new JLabel("Source City:");
        inputPanel.add(startLabel);

        JComboBox<String> startComboBox = new JComboBox<>(cityIndexMap.keySet().toArray(new String[0]));
        startComboBox.addActionListener(e -> {
            sourceCity = (String) startComboBox.getSelectedItem();
            graphPanel.repaint();
        });
        sourceCity=cities[0];
        startComboBox.setToolTipText("Select the source city");
        inputPanel.add(startComboBox);

        JLabel endLabel = new JLabel("Destination City:");
        inputPanel.add(endLabel);

        JComboBox<String> endComboBox = new JComboBox<>(cityIndexMap.keySet().toArray(new String[0]));
        endComboBox.addActionListener(e -> {
            destinationCity = (String) endComboBox.getSelectedItem();
            graphPanel.repaint();
        });
        endComboBox.setToolTipText("Select the destination city");
        inputPanel.add(endComboBox);
        destinationCity=cities[0];
        

        JButton optimizeButton = new JButton("Generate Route");
        optimizeButton.addActionListener(e -> {
            if (sourceCity != null && destinationCity != null && !sourceCity.equals(destinationCity)) {
                findShortestPath(sourceCity, destinationCity);
                pathLabel.setText("Shortest Path: " + shortestPathToString());
                graphPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Please select source and destination cities.");
            }
        });
        inputPanel.add(optimizeButton);
        add(inputPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void initializeData() {
        cityPositions = new HashMap<>();
        cityPositions.put(cities[0], new Point(200, 100));
        cityPositions.put(cities[1], new Point(100, 300));
        cityPositions.put(cities[2], new Point(400, 150));
        cityPositions.put(cities[3], new Point(300, 400));
        cityPositions.put(cities[4], new Point(500, 300));
        cityPositions.put(cities[5], new Point(600, 200));



        cityIndexMap = new HashMap<>();
        for (int i = 0; i < cities.length; i++) {
            cityIndexMap.put(cities[i], i);
        }

        distances = new int[cities.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
            distances[i][i] = 0;
        }

        connectCity(cities[0], cities[1], 200);
        connectCity(cities[1], cities[3], 50);
        connectCity(cities[3], cities[5], 150);
        connectCity(cities[5], cities[4], 50);
        connectCity(cities[2], cities[5], 55);
        connectCity(cities[2], cities[1], 200);
    }

    private void connectCity(String city1, String city2, int distance) {
        int index1 = cityIndexMap.get(city1);
        int index2 = cityIndexMap.get(city2);
        distances[index1][index2] = distance;
        distances[index2][index1] = distance;
    }

    private void findShortestPath(String sourceCity, String destinationCity) {
        int startIndex = cityIndexMap.get(sourceCity);
        int endIndex = cityIndexMap.get(destinationCity);
        shortestPath = dijkstraAlgo(startIndex, endIndex);
    }

    private List<Integer> dijkstraAlgo(int start, int end) {
        int numCities = distances.length;
        int[] minDistances = new int[numCities];
        boolean[] visited = new boolean[numCities];
        int[] previous = new int[numCities];

        Arrays.fill(minDistances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        minDistances[start] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(i -> minDistances[i]));
        queue.add(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            visited[u] = true;

            for (int v = 0; v < numCities; v++) {
                if (!visited[v] && distances[u][v] != Integer.MAX_VALUE) {
                    int alt = minDistances[u] + distances[u][v];
                    if (alt < minDistances[v]) {
                        minDistances[v] = alt;
                        previous[v] = u;
                        queue.add(v);
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        int current = end;
        while (current != -1) {
            path.add(current);
            current = previous[current];
        }
        Collections.reverse(path);
        return path;
    }

    private String shortestPathToString() {
        if (shortestPath == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < shortestPath.size(); i++) {
            sb.append(getCityName(shortestPath.get(i)));
            if (i < shortestPath.size() - 1) {
                sb.append(" -> ");
            }
        }
        return new String(sb);
    }

    private class GraphPanel extends JPanel {

        private static final int NODE_RADIUS = 20;
        private static final Color NODE_COLOR = Color.BLUE;
        private static final Color EDGE_COLOR = Color.BLACK;
        private static final Color PATH_COLOR = Color.RED;
        private static final Font EDGE_FONT = new Font("Arial", Font.PLAIN, 12);

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(EDGE_COLOR);
            g2d.setFont(EDGE_FONT);
            for (int i = 0; i < distances.length; i++) {
                for (int j = i + 1; j < distances.length; j++) {
                    if (distances[i][j] != Integer.MAX_VALUE) {
                        Point city1Pos = cityPositions.get(getCityName(i));
                        Point city2Pos = cityPositions.get(getCityName(j));
                        g2d.drawLine(city1Pos.x, city1Pos.y, city2Pos.x, city2Pos.y);

                        int centerX = (city1Pos.x + city2Pos.x) / 2;
                        int centerY = (city1Pos.y + city2Pos.y) / 2;

                        String distanceLabel = String.valueOf(distances[i][j]);
                        g2d.drawString(distanceLabel, centerX, centerY);
                    }
                }
            }

            g2d.setColor(NODE_COLOR);
            for (String city : cityPositions.keySet()) {
                Point cityPos = cityPositions.get(city);
                g2d.fillOval(cityPos.x - NODE_RADIUS, cityPos.y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
                g2d.drawString(city, cityPos.x - NODE_RADIUS, cityPos.y - NODE_RADIUS);
            }

            if (sourceCity != null && destinationCity != null) {
                g2d.setColor(Color.RED);
                Point sourceCityPos = cityPositions.get(sourceCity);
                Point destinationCityPos = cityPositions.get(destinationCity);
                g2d.drawOval(sourceCityPos.x - NODE_RADIUS - 5, sourceCityPos.y - NODE_RADIUS - 5,
                        2 * NODE_RADIUS + 10, 2 * NODE_RADIUS + 10);
                g2d.drawOval(destinationCityPos.x - NODE_RADIUS - 5, destinationCityPos.y - NODE_RADIUS - 5,
                        2 * NODE_RADIUS + 10, 2 * NODE_RADIUS + 10);
            }

            if (shortestPath != null) {
                g2d.setColor(PATH_COLOR);
                for (int i = 0; i < shortestPath.size() - 1; i++) {
                    Point city1Pos = cityPositions.get(getCityName(shortestPath.get(i)));
                    Point city2Pos = cityPositions.get(getCityName(shortestPath.get(i + 1)));
                    g2d.drawLine(city1Pos.x, city1Pos.y, city2Pos.x, city2Pos.y);
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(850, 850);
        }
    }

    private String getCityName(int index) {
        for (var entry : cityIndexMap.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuestionSeven::new);
    }
}