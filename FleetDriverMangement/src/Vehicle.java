public class Vehicle extends Transport {
    
    private int distanceDriven;
    private boolean serviceStatus = false;
    private boolean available = true;

    public Vehicle(int id, String model){
        super(id, model);
    }

    public boolean isAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public int getDistanceDriven() {
        return distanceDriven;
    }

    public void setDistanceDriven(int distanceDriven) {
        this.distanceDriven = distanceDriven;
    }

    public boolean isServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(boolean serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    @Override
    public String toString() {
        return "Vehicle: " + getModel() + " (ID: " + getId() + ")";
    }
}
