import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        FleetManager fm = new FleetManager();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("===== Fleet & Driver Management System =====");
        System.out.println("Please choose an interface:");
        System.out.println("1. Graphical User Interface (GUI)");
        System.out.println("2. Terminal/Command Line");
        System.out.print("Enter your choice (1 or 2): ");
        
        int interfaceChoice = sc.nextInt();
        sc.nextLine(); // Clear buffer
        
        if (interfaceChoice == 1) {
            // Launch GUI
            FleetManagementGUI gui = new FleetManagementGUI(fm);
            gui.setVisible(true);
            return;
        }
        // Continue with terminal interface
        runTerminalInterface(fm, sc);
    }
    
    private static void runTerminalInterface(FleetManager fm, Scanner sc) {
        while (true) {
            System.out.println("\n===== Fleet & Driver Management System =====");
            System.out.println("1. Add Driver");
            System.out.println("2. Add Vehicle");
            System.out.println("3. Assign Delivery");
            System.out.println("4. View Assignments");
            System.out.println("5. Add Location");
            System.out.println("6. Add Route between Locations");
            System.out.println("7. Find Best Route");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter Driver ID: ");
                    int driverId = sc.nextInt();
                    sc.nextLine(); // Clear buffer
                    System.out.println("Enter Driver Name: ");
                    String driverName = sc.nextLine();
                    System.out.println("Enter Driver's License Number: ");
                    String license = sc.nextLine();
                    fm.addDriver(new Driver(driverId, driverName, license));
                    break;

                case 2:
                    System.out.println("Enter Vehicle ID: ");
                    int vehicleId = sc.nextInt();
                    sc.nextLine(); // Clear buffer
                    System.out.println("Enter Vehicle Model: ");
                    String model = sc.nextLine();
                    fm.addVehicle(new Vehicle(vehicleId, model));
                    break;

                case 3:
                    System.out.println("Enter Driver Id: ");
                    int dId = sc.nextInt();
                    // Check if driver exists first
                    if (!fm.driverExists(dId)) {
                        System.out.println("Error: Driver with ID " + dId + " does not exist. Please add the driver first.");
                        sc.nextLine(); // Clear buffer
                        break;
                    }
                    
                    System.out.println("Enter Vehicle Id: ");
                    int vId = sc.nextInt();
                    // Check if vehicle exists first
                    if (!fm.vehicleExists(vId)) {
                        System.out.println("Error: Vehicle with ID " + vId + " does not exist. Please add the vehicle first.");
                        sc.nextLine(); // Clear buffer
                        break;
                    }
                    
                    sc.nextLine(); // Clear buffer
                    System.out.println("Enter the Destination place: ");
                    String dest = sc.nextLine();
                    System.out.println("Enter the Approx Distance: ");
                    int dist = sc.nextInt();
                    fm.assignDelivery(dId, vId, dest, dist);
                    break;
                    
                case 4:
                    fm.viewAssignments();
                    break;
                
                case 5:
                    System.out.println("Enter Location Name: ");
                    String location = sc.nextLine();
                    fm.addLocation(location);
                    break;
                
                case 6:
                    System.out.println("Enter Source Location: ");
                    String src = sc.nextLine();
                    // Check if source location exists
                    if (!fm.locationExists(src)) {
                        System.out.println("Error: Source location '" + src + "' does not exist. Please add it first.");
                        break;
                    }
                    
                    System.out.println("Enter Destination Location: ");
                    String dest2 = sc.nextLine();
                    // Check if destination location exists
                    if (!fm.locationExists(dest2)) {
                        System.out.println("Error: Destination location '" + dest2 + "' does not exist. Please add it first.");
                        break;
                    }
                    
                    System.out.println("Enter Distance: ");
                    int dist2 = sc.nextInt();
                    sc.nextLine(); // Add this line to consume the newline character
                    fm.addRoute(src, dest2, dist2);
                    break;

                case 7:
                    System.out.print("Enter Start Location: ");
                    String start = sc.nextLine();
                    // Check if start location exists
                    if (!fm.locationExists(start)) {
                        System.out.println("Error: Start location '" + start + "' does not exist. Please add it first.");
                        break;
                    }
                    
                    System.out.print("Enter End Location: ");
                    String end = sc.nextLine();
                    // Check if end location exists
                    if (!fm.locationExists(end)) {
                        System.out.println("Error: End location '" + end + "' does not exist. Please add it first.");
                        break;
                    }
                    
                    fm.findShortestPath(start, end);
                    break;

                case 8: 
                    System.out.println("Exiting... Thank You !!!");
                    return;

                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }
}
