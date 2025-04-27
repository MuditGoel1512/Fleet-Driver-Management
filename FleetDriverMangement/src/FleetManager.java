import java.util.*;

public class FleetManager {
    private Map<Integer, Driver> drivers = new HashMap<>();
    private Map<Integer, Vehicle> vehicles = new HashMap<>();
    private Queue<Driver> freeDrivers = new LinkedList<>();
    private Queue<Assignment> delieveryQueue = new LinkedList<>();
    private LocationGraph cityMap = new LocationGraph();

    public void addDriver(Driver driver){
        drivers.put(driver.getId(), driver);
        freeDrivers.add(driver);
    }

    public void addVehicle(Vehicle vehicle){
        vehicles.put(vehicle.getId(), vehicle);
    }

    public void assignDelivery(int driverId, int vehicleId, String destination, int distance){
        Driver driver = drivers.get(driverId);
        Vehicle vehicle = vehicles.get(vehicleId);

        if (driver != null && vehicle != null && driver.isAvailable() && vehicle.isAvailable()) {
            driver.setAvailable(false);
            vehicle.setAvailable(false);
            Assignment assignment = new Assignment(driver, vehicle, destination, distance);
            delieveryQueue.add(assignment);
            System.out.println("Delievery assigned Successfully ");
        }else{
            System.out.println("Driver or Vehicle not available.");
        }
    }

    public void viewAssignments(){
        if (delieveryQueue.isEmpty()) {
            System.out.println("No Assignments yet !!!");
        }else{
            for (Assignment assignment : delieveryQueue) {
                System.out.println("Driver: " + assignment.getDriver().getName() +
                                   " | Vehicle: " + assignment.getVehicle().getModel() +
                                   " | Destination: " + assignment.getDestination() +
                                   " | Distance: " + assignment.getDistance() + " km");
            }
        }
    }

    public void addLocation(String location){
        cityMap.addLocation(location);
    }

    public void addRoute(String source, String destination, int distance){
        cityMap.addEdge(source, destination, distance);
        cityMap.addEdge(destination, source, distance);
    }

    public void findShortestPath(String start, String end){
        List<String> path = cityMap.findShortestPath(start, end);
        System.out.println("Best Route: " + String.join("->", path));
    }
}
