public class Driver {

    private String name;
    private int id;
    private String licenseNumber;
    private boolean available = true;

    public Driver(int id, String name, String licenseNumber){
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public boolean isAvailable(boolean available){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

}