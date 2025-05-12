public class Assignment {
    
    private Driver driver;
    private Vehicle vehicle;
    private String destination;
    private int distance;

    public Assignment(Driver driver, Vehicle vehicle, String destination, int distance){
        this.driver = driver;
        this.vehicle = vehicle;
        this.destination = destination;
        this.distance = distance;
    }

    public Driver getDriver(){
        return driver;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public String getDestination(){
        return destination;
    }

    public int getDistance(){
        return distance;
    }
    
    @Override
    public String toString() {
        return driver.toString() + 
               ", " + vehicle.toString() + 
               ", Destination: " + destination + 
               ", Distance: " + distance + " km";
    }
}
