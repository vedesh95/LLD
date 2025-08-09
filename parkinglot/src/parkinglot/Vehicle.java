package parkinglot;

public class Vehicle {
    private String id;
    private VehicleType type;
    private String color;

    public Vehicle(String id, VehicleType type, String color) {
        this.id = id;
        this.type = type;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
