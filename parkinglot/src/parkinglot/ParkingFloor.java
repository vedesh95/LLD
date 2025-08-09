package parkinglot;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingFloor {
    private int floorNumber;
    private ArrayList<ParkingSlot> slots;
    private HashMap<VehicleType, TreeSet<Integer>> freeSlots;
    private HashMap<VehicleType, TreeSet<Integer>> occupiedSlots;

    public ParkingFloor(int floorNumber, int numberOfSlots) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
        for (int i = 0; i < numberOfSlots; i++){
            slots.add(new ParkingSlot(i)); // Slot IDs start from 1
        }
        this.freeSlots = new HashMap<>();
        this.occupiedSlots = new HashMap<>();
        freeSlots.put(VehicleType.TRUCK, new TreeSet<>());
        freeSlots.put(VehicleType.BIKE, new TreeSet<>());
        freeSlots.put(VehicleType.CAR, new TreeSet<>());
        occupiedSlots.put(VehicleType.TRUCK, new TreeSet<>());
        occupiedSlots.put(VehicleType.BIKE, new TreeSet<>());
        occupiedSlots.put(VehicleType.CAR, new TreeSet<>());
        setParkingSlots(1,2, numberOfSlots - 3); // Assuming 1 Truck, 2 Bikes, and the rest Cars
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    private void setParkingSlots(int trucks, int bikes, int cars){
        int slotNumber=0;
        for(int i=0; i<trucks; i++){
            slots.get(slotNumber).setSlotType(VehicleType.TRUCK);
            freeSlots.get(VehicleType.TRUCK).add(slotNumber);
            slotNumber++;
        }
        for(int i=0; i<bikes; i++){
            slots.get(slotNumber).setSlotType(VehicleType.BIKE);
            freeSlots.get(VehicleType.BIKE).add(slotNumber);
            slotNumber++;
        }
        for(int i=0; i<cars; i++){
            slots.get(slotNumber).setSlotType(VehicleType.CAR);
            freeSlots.get(VehicleType.CAR).add(slotNumber);
            slotNumber++;
        }
    }

    public int getFreeCountForVehicleType(VehicleType vehicleType) {
        return freeSlots.get(vehicleType).size();
    }
    public List<Integer> getFreeSlotsForVehicleType(VehicleType vehicleType) {
        return freeSlots.keySet().stream()
                .filter(type -> type == vehicleType)
                .flatMap(type -> freeSlots.get(type).stream())
                .collect(Collectors.toList());
    }
    public List<Integer> getOccupiedSlotsForVehicleType(VehicleType vehicleType) {
        return occupiedSlots.keySet().stream()
                .filter(type -> type == vehicleType)
                .flatMap(type -> occupiedSlots.get(type).stream())
                .collect(Collectors.toList());
    }
    public int park(Vehicle vehicle) {
        if(freeSlots.get(vehicle.getType()).isEmpty()) {
            return -1; // No free slots available for this vehicle type
        }
        int slotId = freeSlots.get(vehicle.getType()).first();
        slots.get(slotId).setOccupied(true);
        slots.get(slotId).setVehicleId(vehicle.getId());
        freeSlots.get(vehicle.getType()).remove(slotId);
        occupiedSlots.get(vehicle.getType()).add(slotId);
        return slotId;
    }
    public void unpark(Vehicle vehicle, int slotId) {
        slots.get(slotId).setOccupied(false);
        slots.get(slotId).setVehicleId(null);
        freeSlots.get(vehicle.getType()).add(slotId);
        occupiedSlots.get(vehicle.getType()).remove(slotId);

    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public ArrayList<ParkingSlot> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<ParkingSlot> slots) {
        this.slots = slots;
    }

    public HashMap<VehicleType, TreeSet<Integer>> getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(HashMap<VehicleType, TreeSet<Integer>> freeSlots) {
        this.freeSlots = freeSlots;
    }

    public HashMap<VehicleType, TreeSet<Integer>> getOccupiedSlots() {
        return occupiedSlots;
    }

    public void setOccupiedSlots(HashMap<VehicleType, TreeSet<Integer>> occupiedSlots) {
        this.occupiedSlots = occupiedSlots;
    }


}
