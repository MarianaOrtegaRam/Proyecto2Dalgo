import java.util.*;

public class Dijkstra {
    public static void main(String[] args) {
        // Example graph
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        graph.put(1, new HashMap<>());
        graph.put(2, new HashMap<>());
        graph.put(3, new HashMap<>());
        graph.put(-1, new HashMap<>());
        graph.put(-2, new HashMap<>());
        graph.put(-3, new HashMap<>());

        graph.get(1).put(2, 3);
        graph.get(1).put(-2, 1);
        graph.get(1).put(3, 1);
        graph.get(1).put(-3, 4);

        graph.get(-1).put(2, 2);
        graph.get(-1).put(-2, 1);
        graph.get(-1).put(3, 5);
        graph.get(-1).put(-3, 8);

        graph.get(2).put(1, 3);
        graph.get(2).put(-1, 2);
        graph.get(2).put(3, 5);
        graph.get(2).put(-3, 7);

        graph.get(-2).put(1, 1);
        graph.get(-2).put(-1, 1);
        graph.get(-2).put(3, 4);
        graph.get(-2).put(-3, 5);

        graph.get(3).put(2, 5);
        graph.get(3).put(-2, 4);
        graph.get(3).put(1, 1);
        graph.get(3).put(-1, 5);

        graph.get(-3).put(2, 7);
        graph.get(-3).put(-2, 5);
        graph.get(-3).put(1, 4);
        graph.get(-3).put(-1, 8);

        int start = -3;
        int end = 3;

        List<Integer> path = dijkstra(graph, start, end);
        int weight = 0;
        for (int i = 1; i < path.size(); i++) {
            weight += graph.get(path.get(i - 1)).get(path.get(i));
        }

        System.out.println("Shortest path from " + start + " to " + end + ": " + path);
        System.out.println("Total weight: " + weight);
    }

    public static List<Integer> dijkstra(Map<Integer, Map<Integer, Integer>> graph, int start, int end) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        for (int node : graph.keySet()) {
            if (node == start) {
                distances.put(node, 0);
            } else {
                distances.put(node, Integer.MAX_VALUE);
            }
            previous.put(node, null);
        }

        while (!visited.containsAll(distances.keySet())) {
            int current = getLowestDistanceNode(distances, visited);
            visited.add(current);

            for (int neighbor : graph.get(current).keySet()) {
                if (!visited.contains(neighbor)) {
                    int newDistance = distances.get(current) + graph.get(current).get(neighbor);
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, current);
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        Integer current = end;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    public static int getLowestDistanceNode(Map<Integer, Integer> distances, Set<Integer> visited) {
        int lowestDistance = Integer.MAX_VALUE;
        int lowestDistanceNode = -1;

        for (int node : distances.keySet()) {
            if (!visited.contains(node) && distances.get(node) < lowestDistance) {
                lowestDistance = distances.get(node);
                lowestDistanceNode = node;
            }
        }

        return lowestDistanceNode;
    }
}