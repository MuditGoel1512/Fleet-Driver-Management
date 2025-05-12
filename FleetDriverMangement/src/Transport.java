public abstract class Transport {
    private int id;
    private String model;

    public Transport(int id, String model) {
        this.id = id;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }
}
