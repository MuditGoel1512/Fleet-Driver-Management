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

    public boolean isAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public int getId(){
        return id;
    }

    public String getModel(){
        return model;
    }
}
