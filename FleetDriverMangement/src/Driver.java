public class Driver extends Person {

    private String licenseNumber;
    private boolean available = true;

    public Driver(int id, String name, String licenseNumber){
        super(id, name);
        this.licenseNumber = licenseNumber;
    }

    public boolean isAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public String getLicenseNumber(){
        return licenseNumber;
    }

    @Override
    public String toString() {
        return "Driver: " + getName() + " (ID: " + getId() + "), License: " + licenseNumber;
    }
}