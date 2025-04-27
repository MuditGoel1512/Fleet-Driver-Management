public class Vehicle {
    
    private int id;
    private String model;
    private int distanceDriven;
    private boolean serviceStatus = false;
    private boolean available = true;

    public Vehicle(int id, String model){
        this.id = id;
        this.model = model;
    }

    public boolean isAvailable(boolean available){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }
}
