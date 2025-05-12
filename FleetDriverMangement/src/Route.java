public class Route {
    private String destination;
    private int distance;
    
    public Route(String destination, int distance) {
        this.destination = destination;
        this.distance = distance;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Route to " + destination + " (" + distance + " km)";
    }
}
