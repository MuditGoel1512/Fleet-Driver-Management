import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        FleetManager fm = new FleetManager();
        Scanner sc = new Scanner(System.in);

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
                    System.out.println("Enter Driver Name: ");
                    String DriverName = sc.nextLine();
                    System.out.println("Enter Driver's License Number: ");
                    String License = sc.nextLine();
                    fm.addDriver(new Driver(driverId, DriverName, License));
                    break;

                case 2:
                    System.out.println("Enter Vehicle ID: ");
                    int vehicleId = sc.nextInt();
                    System.out.println("Enter Vehicle Model: ");
                    String Model = sc.nextLine();
                    fm.addVehicle(new Vehicle(vehicleId, Model));
                    break;

                case 3:
                    System.out.println("Enter Driver Id: ");
                    int dId = sc.nextInt();
                    System.out.println("Enter Vehicle Id: ");
                    int vId = sc.nextInt();
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
                    System.out.println("Enter Destination Location: ");
                    String dest2 = sc.nextLine();
                    System.out.println("Enter Distance: ");
                    int dist2 = sc.nextInt();
                    fm.addRoute(src, dest2, dist2);
                    break;

                case 7:
                    System.out.print("Enter Start Location: ");
                    String start = sc.nextLine();
                    System.out.print("Enter End Location: ");
                    String end = sc.nextLine();
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
