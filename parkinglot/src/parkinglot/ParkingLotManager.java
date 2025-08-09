package parkinglot;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotManager {
    private ParkingLot parkingLot;
    private HashMap<String, Vehicle> vehicleMap;

    public ParkingLotManager(){
        vehicleMap = new HashMap<>();
    }

    public String createParkingLot(String name, int floors, int slots) {
        this.parkingLot = new ParkingLot(name, floors, slots);
        return "Created parking lot with " + floors + " floors and " + slots + " slots per floor";
    }

    public List<String> displayOccupiedParkingSpots(VehicleType type) {
        List<String> occupiedSpots = new ArrayList<>();
        Map<Integer, List<Integer>> map = parkingLot.getOccupiedSlotsForVehicleType(type);
        for(Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int floorNumber = entry.getKey();
            List<Integer> slots = entry.getValue();
            occupiedSpots.add("Occupied slots for " + type + " on Floor " + floorNumber + ": " + slots.stream()
                .map(String::valueOf).collect(Collectors.joining(",")));
        }
        return occupiedSpots;
    }

    public List<String> displayFreeParkingSpots(VehicleType type) {
        List<String> freeSpots = new ArrayList<>();
        Map<Integer, List<Integer>> map = parkingLot.getFreeSlotsForVehicleType(type);
        for(Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int floorNumber = entry.getKey();
            List<Integer> slots = entry.getValue();
            freeSpots.add("Free slots for " + type + " on Floor " + floorNumber + ": " + slots.stream()
                .map(String::valueOf).collect(Collectors.joining(",")));
        }
        return freeSpots;
    }

    public List<String> displayNumberOfFreeParkingSpots(VehicleType type) {
        List<String> parkingSpots = new ArrayList<>();
        Map<Integer, Integer> freeCounts = parkingLot.getFreeCountForVehicleType(type);
        for(Map.Entry<Integer, Integer> entry : freeCounts.entrySet()) {
            int floorNumber = entry.getKey();
            int count = entry.getValue();
            parkingSpots.add("No. of free slots for " + type + " on Floor " + floorNumber + ": " + count);
        }
        return parkingSpots;
    }

    public String parkVehicle(String vehicleId, VehicleType type, String color) {
        Vehicle vehicle = new Vehicle(vehicleId, type, color);
        vehicleMap.put(vehicleId, vehicle);
        FloorSlotId slotId = parkingLot.park(vehicle);
        if (slotId.getFloorId() == -1) {
            return "Parking Lot Full";
        }
        return "Parked vehicle.  Ticket ID: " + parkingLot.getName() + "_" + slotId.getFloorId() + "_" + slotId.getSlotId();
    }

    public String unparkVehicle(String ticketId) {
        String[] parts = ticketId.split("_");
        Integer floorId = Integer.parseInt(parts[1])-1;
        Integer slotId = Integer.parseInt(parts[2])-1;

        String vehicleId = parkingLot.getVehicleId(floorId, slotId);
        if(vehicleId != null) {
            parkingLot.unpark(vehicleMap.get(vehicleId), floorId, slotId);
//            Unparked vehicle with Registration Number: WB-45-HO-9032 and Color: white
            return "Unparked vehicle with Registration Number: " + vehicleId + " and Color: " + vehicleMap.get(vehicleId).getColor();
        }
        return "Invalid Ticket";
    }


}
