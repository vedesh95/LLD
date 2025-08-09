package parkinglot;

public record FloorSlotId(int slotId, int floorId) {
    int getSlotId() {
        return slotId;
    }
    int getFloorId() {
        return floorId;
    }
}
