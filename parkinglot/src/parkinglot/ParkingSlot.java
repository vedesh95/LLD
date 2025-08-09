package parkinglot;

public class ParkingSlot {
    private int id;
    private boolean isOccupied;
    private String vehicleId;
    private VehicleType slotType;

    ParkingSlot(int id) {
        this.id = id;
        this.isOccupied = false;
        this.vehicleId = null;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isOccupied() {
        return isOccupied;
    }
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
    public String getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    public VehicleType getSlotType() {
        return slotType;
    }
    public void setSlotType(VehicleType slotType) {
        this.slotType = slotType;
    }

}
