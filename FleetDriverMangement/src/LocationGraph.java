import java.util.*;

public class LocationGraph {
    
    private Map<String, List<Edge>> adjacencyList = new HashMap<>();

    class Edge{
        String destination;
        int distance;

        Edge(String destination, int distance){
            this.destination = destination;
            this.distance = distance;
        }
    }

    public void addLocation(String location){
        adjacencyList.putIfAbsent(location, new ArrayList<>());
    }

    public void addEdge(String source, String destination, int distance){
        adjacencyList.get(source).add(new Edge(destination, distance));
    }

    public List<String> findShortestPath(String start, String end) {
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distance::get));

        for (String location : adjacencyList.keySet()) {
            distance.put(location, Integer.MAX_VALUE);
        }
        distance.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            String current = pq.poll();
            for (Edge edge : adjacencyList.get(current)) {
                int newDist = distance.get(current) + edge.distance;
                if (newDist < distance.get(edge.destination)) {
                    distance.put(edge.destination, newDist);
                    parent.put(edge.destination, current);
                    pq.add(edge.destination);
                }
            }
        }
        
        List<String> path = new ArrayList<>();
        String current = end;
        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
