package parkinglot;

import java.util.*;

public class ParkingLot {
    private ArrayList<ParkingFloor> floors;
    private String name;
    ParkingLot(String name, int floors, int slots) {
        this.name = name;
        this.floors = new ArrayList<>();
        for (int i = 0; i < floors; i++) {
            this.floors.add(new ParkingFloor(i, slots)); // Assuming each floor has 10 slots
        }
    }

    public Map<Integer, Integer > getFreeCountForVehicleType(VehicleType vehicleType) {
        Map<Integer, Integer> freeCounts = new HashMap<>();
        for (ParkingFloor floor : this.floors) {
            freeCounts.put(floor.getFloorNumber(), floor.getFreeCountForVehicleType(vehicleType));
        }
        return freeCounts;
    }
    public Map<Integer, List<Integer> > getFreeSlotsForVehicleType(VehicleType vehicleType) {
        Map<Integer, List<Integer>> freeSlots = new HashMap<>();
        for (ParkingFloor floor : this.floors) {
            freeSlots.put(floor.getFloorNumber(), floor.getFreeSlotsForVehicleType(vehicleType));
        }
        return freeSlots;
    }
    public Map<Integer, List<Integer> > getOccupiedSlotsForVehicleType(VehicleType vehicleType) {
        Map<Integer, List<Integer>> occupiedSlots = new HashMap<>();
        for (ParkingFloor floor : this.floors) {
            occupiedSlots.put(floor.getFloorNumber(), floor.getOccupiedSlotsForVehicleType(vehicleType));
        }
        return occupiedSlots;
    }
    public FloorSlotId park(Vehicle vehicle) {
        for (ParkingFloor floor : this.floors) {
            if (floor.getFreeCountForVehicleType(vehicle.getType()) > 0) {
                int slotId = floor.park(vehicle);
                if(slotId!=-1){
                    return new FloorSlotId(floor.getFloorNumber()+1, slotId);
                }
            }
        }
        return new FloorSlotId(-1, -1);
    }
    public void unpark(Vehicle vehicle, int floorId, int slotId) {
        ParkingFloor floor = floors.get(floorId);
        floor.unpark(vehicle, slotId);
    }

    public String getVehicleId(int floorId, int slotId) {
        if(floorId < 0 || floorId >= floors.size() || slotId < 0 || slotId >= floors.get(floorId).getSlots().size()) {
            return null; // Invalid floor or slot ID
        }
        if(floors.get(floorId).getSlots().get(slotId).isOccupied()){
            return floors.get(floorId).getSlots().get(slotId).getVehicleId();
        }
        return null;
    }

    public String getName() {
        return name;
    }
    public List<ParkingFloor> getFloors() {
        return floors;
    }
}
