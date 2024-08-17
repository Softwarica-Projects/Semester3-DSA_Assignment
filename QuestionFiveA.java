
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionFiveA {
    static final int MAX_ITERATIONS = 1000;
    static final int NUM_CITIES = 8;

    public static void main(String[] args) {
        int[][] distances = generateDistances(NUM_CITIES);
        List<Integer> bestTour = generateRandomTour(NUM_CITIES);
        double bestLength = evaluate(distances, bestTour);
        int count = 0;

        while (count < MAX_ITERATIONS) {
            System.out.println("Best length so far: " + bestLength + " Tour: " + tourToString(bestTour) + " Loop count: " + count);
            count++;
            List<Integer> newTour = new ArrayList<>(bestTour);
            mutateTour(newTour);
            double newLength = evaluate(distances, newTour);
            if (newLength < bestLength) {
                bestTour = newTour;
                bestLength = newLength;
            }
        }
        System.out.println("Final best length: " + bestLength + " Tour: " + tourToString(bestTour) + " Iterations: " + count);
    }

    public static int[][] generateDistances(int numCities) {
        Random random = new Random();
        int[][] distances = new int[numCities][numCities];

        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i != j) {
                    distances[i][j] = random.nextInt(100) + 1; // Random distance between 1 and 100
                } else {
                    distances[i][j] = 0; // Distance to itself is 0
                }
            }
        }
        return distances;
    }

    public static List<Integer> generateRandomTour(int numCities) {
        List<Integer> tour = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour);
        return tour;
    }

    public static double evaluate(int[][] distances, List<Integer> tour) {
        double length = 0;

        for (int i = 0; i < tour.size(); i++) {
            int from = tour.get(i);
            int to = tour.get((i + 1) % tour.size());
            length += distances[from][to];
        }

        return length;
    }

    public static void mutateTour(List<Integer> tour) {
        Random random = new Random();
        int i = random.nextInt(tour.size());
        int j = random.nextInt(tour.size());
        while (i == j) {
            j = random.nextInt(tour.size());
        }
        // Swap the two cities
        Collections.swap(tour, i, j);
    }

    public static String tourToString(List<Integer> tour) {
        StringBuilder sb = new StringBuilder();
        for (int city : tour) {
            sb.append(city).append(" ");
        }
        return sb.toString().trim();
    }
}
