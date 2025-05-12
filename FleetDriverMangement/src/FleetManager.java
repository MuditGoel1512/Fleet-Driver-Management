import java.util.*;

public class FleetManager {
    private Map<Integer, Driver> drivers;
    private Map<Integer, Vehicle> vehicles;
    private List<Assignment> assignments;
    private Map<String, List<Route>> routes;
    private List<Driver> freeDrivers;

    public FleetManager() {
        drivers = new HashMap<Integer, Driver>();
        vehicles = new HashMap<Integer, Vehicle>();
        assignments = new ArrayList<Assignment>();
        routes = new HashMap<String, List<Route>>();
        freeDrivers = new ArrayList<Driver>();
    }
    
    public void addDriver(Driver driver) {
        drivers.put(driver.getId(), driver);
        freeDrivers.add(driver);
        System.out.println("Driver " + driver.getName() + " added successfully!");
    }
    
    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getId(), vehicle);
        System.out.println("Vehicle " + vehicle.getModel() + " added successfully!");
    }
    
    public void assignDelivery(int driverId, int vehicleId, String destination, int distance) {
        Driver driver = drivers.get(driverId);
        Vehicle vehicle = vehicles.get(vehicleId);
        
        if (driver == null) {
            System.out.println("Driver with ID " + driverId + " does not exist.");
            return;
        }
        
        if (vehicle == null) {
            System.out.println("Vehicle with ID " + vehicleId + " does not exist.");
            return;
        }

        if (!routes.containsKey(destination)) {
            System.out.println("Destination '" + destination + "' does not exist. Please add it as a location first.");
            return;
        }
        
        Assignment assignment = new Assignment(driver, vehicle, destination, distance);
        assignments.add(assignment);
        
        System.out.println("Delivery assigned successfully!");
        System.out.println("Driver: " + driver.getName());
        System.out.println("Vehicle: " + vehicle.getModel());
        System.out.println("Destination: " + destination);
        System.out.println("Distance: " + distance + " km");
    }

    public void viewAssignments() {
        if (assignments.isEmpty()) {
            System.out.println("No assignments have been made yet.");
            return;
        }
        
        System.out.println("\n===== Current Assignments =====");
        for (Assignment assignment : assignments) {
            System.out.println("Driver: " + assignment.getDriver().getName() + 
                               " (ID: " + assignment.getDriver().getId() + ")");
            System.out.println("Vehicle: " + assignment.getVehicle().getModel() + 
                               " (ID: " + assignment.getVehicle().getId() + ")");
            System.out.println("Destination: " + assignment.getDestination());
            System.out.println("Distance: " + assignment.getDistance() + " km");
            System.out.println("---------------------------");
        }
    }
    
    public void addLocation(String location) {
        if (!routes.containsKey(location)) {
            routes.put(location, new ArrayList<Route>());
            System.out.println("Location " + location + " added successfully!");
        } else {
            System.out.println("Location " + location + " already exists.");
        }
    }
    
    public void addRoute(String source, String destination, int distance) {

        if (!routes.containsKey(source)) {
            System.out.println("Source location '" + source + "' does not exist. Please add it first.");
            return;
        }
        
        if (!routes.containsKey(destination)) {
            System.out.println("Destination location '" + destination + "' does not exist. Please add it first.");
            return;
        }
        
        routes.get(source).add(new Route(destination, distance));
        routes.get(destination).add(new Route(source, distance));
        
        System.out.println("Route added successfully: " + source + " <-> " + destination + " (" + distance + " km)");
    }
    
    public void findShortestPath(String start, String end) {
        System.out.println(findShortestPathAsString(start, end));
    }
    
    public String getAssignmentsAsString() {
        StringBuilder result = new StringBuilder();
        result.append("Current Assignments:\n\n");
        
        if (assignments.isEmpty()) {
            result.append("No assignments have been made yet.");
            return result.toString();
        }
        
        for (Assignment assignment : assignments) {
            result.append("Driver: ").append(assignment.getDriver().getName())
                  .append(" (ID: ").append(assignment.getDriver().getId()).append(")\n");
            result.append("Vehicle: ").append(assignment.getVehicle().getModel())
                  .append(" (ID: ").append(assignment.getVehicle().getId()).append(")\n");
            result.append("Destination: ").append(assignment.getDestination()).append("\n");
            result.append("Distance: ").append(assignment.getDistance()).append(" km\n");
            result.append("---------------------------\n");
        }
        
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public String findShortestPathAsString(String start, String end) {
        
        Map<String, Integer> locToIdx = new HashMap<String, Integer>();
        Map<Integer, String> idxToLoc = new HashMap<Integer, String>();
        int idx = 0;
        for (String loc : routes.keySet()) {
            locToIdx.put(loc, idx);
            idxToLoc.put(idx, loc);
            idx++;
        }

        if (!locToIdx.containsKey(start)) {
            return "Start location '" + start + "' does not exist.";
        }
        if (!locToIdx.containsKey(end)) {
            return "End location '" + end + "' does not exist.";
        }

        int n = routes.size();
        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Edge>();
        }

        for (String src : routes.keySet()) {
            int srcIdx = locToIdx.get(src);
            for (Route route : routes.get(src)) {
                String dest = route.getDestination();
                int destIdx = locToIdx.get(dest);
                int wt = route.getDistance();
                graph[srcIdx].add(new Edge(srcIdx, destIdx, wt));
            }
        }

        int[] dist = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        int srcIdx = locToIdx.get(start);
        int destIdx = locToIdx.get(end);
        dist[srcIdx] = 0;

        boolean[] vis = new boolean[n];
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
        pq.add(new Pair(srcIdx, 0));

        while (!pq.isEmpty()) {
            Pair curr = pq.remove();
            int u = curr.n;
            if (vis[u]) continue;
            vis[u] = true;

            for (Edge e : graph[u]) {
                int v = e.dest;
                int wt = e.wt;
                if (dist[u] + wt < dist[v]) {
                    dist[v] = dist[u] + wt;
                    prev[v] = u;
                    pq.add(new Pair(v, dist[v]));
                }
            }
        }

        if (dist[destIdx] == Integer.MAX_VALUE) {
            return "No path exists from " + start + " to " + end;
        }
        List<String> path = new ArrayList<String>();
        for (int at = destIdx; at != -1; at = prev[at]) {
            path.add(idxToLoc.get(at));
        }
        Collections.reverse(path);

        StringBuilder result = new StringBuilder();
        result.append("Best route from ").append(start).append(" to ").append(end).append(":\n");
        result.append("Path: ");
        for (int i = 0; i < path.size(); i++) {
            result.append(path.get(i));
            if (i < path.size() - 1) result.append(" -> ");
        }
        result.append("\nTotal distance: ").append(dist[destIdx]).append(" km");
        return result.toString();
    }

    static class Edge {
        int src, dest, wt;
        Edge(int src, int dest, int wt) {
            this.src = src;
            this.dest = dest;
            this.wt = wt;
        }
    }
    static class Pair implements Comparable<Pair> {
        int n, path;
        Pair(int n, int path) {
            this.n = n;
            this.path = path;
        }
        @Override
        public int compareTo(Pair p2) {
            return this.path - p2.path;
        }
    }

    public boolean driverExists(int driverId) {
        return drivers.containsKey(driverId);
    }
    
    public boolean vehicleExists(int vehicleId) {
        return vehicles.containsKey(vehicleId);
    }
    
    public boolean locationExists(String location) {
        return routes.containsKey(location);
    }
}
