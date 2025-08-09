import parkinglot.ParkingLotManager;
import parkinglot.VehicleType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        Path path = Paths.get("inp.txt");
        ParkingLotManager parkingLotManager = new ParkingLotManager();
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                List<String> parts = Arrays.asList(line.split(" "));

                if(line.contains("create_parking_lot")){
                    String s = parkingLotManager.createParkingLot(parts.get(1), Integer.parseInt(parts.get(2)), Integer.parseInt(parts.get(3)));
                    print(s);
                } else if(line.contains("free_count")){
                    List<String> list = parkingLotManager.displayNumberOfFreeParkingSpots(getVehicleType(parts.get(2)));
                    print(list);
                } else  if(line.contains("occupied_slots")){
                    List<String> list = parkingLotManager.displayOccupiedParkingSpots(getVehicleType(parts.get(2)));
                    print(list);
                } else if(line.contains("free_slots")){
                    List<String> list = parkingLotManager.displayFreeParkingSpots(getVehicleType(parts.get(2)));
                    print(list);
                } else if(line.contains("unpark")){
                    String ticketId = parts.get(1);
                    System.out.println(parkingLotManager.unparkVehicle(ticketId));
                } else if(line.contains("park_vehicle")){
                    String vehicleId = parts.get(2);
                    VehicleType type = getVehicleType(parts.get(1));
                    String color = parts.get(3);
                    System.out.println(parkingLotManager.parkVehicle(vehicleId, type, color));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static VehicleType getVehicleType(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return VehicleType.CAR;
            case "bike":
                return VehicleType.BIKE;
            case "truck":
                return VehicleType.TRUCK;
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
    public static void print(String s){
        System.out.println(s);
    }
    public static void print(List<String> list){
        for (String s : list) {
            System.out.println(s);
        }
    }
}
