import java.util.*;

public class FleetManager {
    private Map<Integer, Driver> drivers;
    private Map<Integer, Vehicle> vehicles;
    private List<Assignment> assignments;
    private Map<String, List<Route>> routes;
    private List<Driver> freeDrivers; // Added missing variable
    
    public FleetManager() {
        drivers = new HashMap<>();
        vehicles = new HashMap<>();
        assignments = new ArrayList<>();
        routes = new HashMap<>();
        freeDrivers = new ArrayList<>(); // Initialize freeDrivers
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
        
        // Create and store the assignment
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
        // Initialize the list for this location if it doesn't exist
        if (!routes.containsKey(location)) {
            routes.put(location, new ArrayList<>());
            System.out.println("Location " + location + " added successfully!");
        } else {
            System.out.println("Location " + location + " already exists.");
        }
    }
    
    public void addRoute(String source, String destination, int distance) {
        // Check if both locations exist
        if (!routes.containsKey(source)) {
            System.out.println("Source location '" + source + "' does not exist. Please add it first.");
            return;
        }
        
        if (!routes.containsKey(destination)) {
            System.out.println("Destination location '" + destination + "' does not exist. Please add it first.");
            return;
        }
        
        // Add bidirectional routes
        routes.get(source).add(new Route(destination, distance));
        routes.get(destination).add(new Route(source, distance));
        
        System.out.println("Route added successfully: " + source + " <-> " + destination + " (" + distance + " km)");
    }
    
    public void findShortestPath(String start, String end) {
        // Print the result of findShortestPathAsString to console
        System.out.println(findShortestPathAsString(start, end));
    }
    
    // Add this method to support the GUI view
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

    // For GUI support of findShortestPath
    public String findShortestPathAsString(String start, String end) {
        StringBuilder result = new StringBuilder();
        
        // Check if locations exist
        if (!routes.containsKey(start)) {
            return "Start location '" + start + "' does not exist.";
        }
        if (!routes.containsKey(end)) {
            return "End location '" + end + "' does not exist.";
        }
        
        // Implement Dijkstra's algorithm for finding shortest path
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(
            Comparator.comparingInt(location -> distances.getOrDefault(location, Integer.MAX_VALUE))
        );
        
        // Initialize distances
        for (String vertex : routes.keySet()) {
            if (vertex.equals(start)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            queue.add(vertex);
        }
        
        // Find shortest path
        while (!queue.isEmpty()) {
            String current = queue.poll();
            
            // If we've reached the destination, we're done
            if (current.equals(end)) {
                break;
            }
            
            // Skip unreachable vertices
            if (distances.get(current) == Integer.MAX_VALUE) {
                continue;
            }
            
            // Check all neighbors
            for (Route route : routes.get(current)) {
                String neighbor = route.getDestination();
                int alt = distances.get(current) + route.getDistance();
                
                if (alt < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, alt);
                    previous.put(neighbor, current);
                    
                    // Update queue (inefficient but simple approach)
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        
        // Build the path
        if (!previous.containsKey(end) && !start.equals(end)) {
            return "No path exists from " + start + " to " + end;
        }
        
        List<String> path = new ArrayList<>();
        String current = end;
        
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        
        // Reverse the path (it's built backward)
        Collections.reverse(path);
        
        // Format the output
        result.append("Best route from ").append(start).append(" to ").append(end).append(":\n");
        result.append("Path: ");
        
        for (int i = 0; i < path.size(); i++) {
            result.append(path.get(i));
            if (i < path.size() - 1) {
                result.append(" -> ");
            }
        }
        
        result.append("\nTotal distance: ").append(distances.get(end)).append(" km");
        
        return result.toString();
    }
    
    // Helper method to check if a driver exists
    public boolean driverExists(int driverId) {
        return drivers.containsKey(driverId);
    }
    
    // Helper method to check if a vehicle exists
    public boolean vehicleExists(int vehicleId) {
        return vehicles.containsKey(vehicleId);
    }
    
    // Helper method to check if a location exists
    public boolean locationExists(String location) {
        return routes.containsKey(location);
    }
}
