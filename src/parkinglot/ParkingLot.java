package parkinglot;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

enum ParkingSpotType {MOTORCYCLE, CAR, LARGE}
enum VehicleType {MOTORCYCLE, CAR, LARGE}
enum ParkingSpotStatus {AVAILABLE, OCCUPIED}

class ParkingSpot{
    private Integer id;
    private ParkingSpotType type;
    private ParkingSpotStatus status;
    public ParkingSpot(Integer id, ParkingSpotType type) {
        this.id = id;
        this.type = type;
        this.status = ParkingSpotStatus.AVAILABLE;
    }

    public ParkingSpotType getType() { return type; }
    public ParkingSpotStatus getStatus() { return status; }
    public void occupy() { status = ParkingSpotStatus.OCCUPIED; }
    public void vacate() { status = ParkingSpotStatus.AVAILABLE; }
}

class Ticket {
    private Integer id;
    private Timestamp timestamp;
    private Integer parkingSpotId;
    private Boolean isUsed;
    public Ticket(Integer id,Timestamp timestamp, Integer parkingSpotId){
        this.id = id;
        this.timestamp = timestamp;
        this.parkingSpotId = parkingSpotId;
        this.isUsed = false;
    }

    public Integer getParkingSpotId() { return parkingSpotId; }
    public Timestamp getTimestamp() { return timestamp; }
    public Integer getId() {return this.id;};
    public void setUsed() {this.isUsed = true;}
    public Boolean isUsed() {return this.isUsed;}
}

interface ParkingStrategy{
    Integer parkVehicle(VehicleType vehicleType);
}

class SimpleStrategy implements ParkingStrategy{
    private ParkingLot lot;
    public SimpleStrategy(ParkingLot lot) {this.lot=lot;}
    @Override
    public Integer parkVehicle(VehicleType vehicleType) {
        Integer slotId = this.lot.getAvailableParkingSpotId(vehicleType);
        if(slotId==null) return null;
        this.lot.removeSlotIdfromAvailableSlotsIds(slotId);
        this.lot.occupySlot(slotId);
        Ticket ticket = new Ticket(this.lot.generateTicketId(), Timestamp.from(Instant.now()),slotId);
        this.lot.addTicket(ticket);
        return ticket.getId();
    }
}

public class ParkingLot {
    private HashMap<Integer, ParkingSpot> parkingSpots = new HashMap<>();
    private HashMap<Integer, Ticket> tickets = new HashMap<>();
    private Set<Integer> availableSlots = new HashSet<>();
    private ParkingStrategy parkingStrategy = new SimpleStrategy(this);
    private final int rate = 1;

    public Integer parkVehicle(VehicleType vehicleType) {
        return parkingStrategy.parkVehicle(vehicleType);
    }

    public void freeParkingSpot(Integer spotId){
        vacateSlot(spotId);
        addSlotIdfromAvailableSlotsIds(spotId);
    }

    public Integer calculateFare(Integer ticketId) throws Exception {
        if(this.tickets.get(ticketId)==null) throw new Exception("Invalid parking ticket");
        if(this.tickets.get(ticketId).isUsed()) throw new Exception("parkinglot.parkinglot.Ticket is already used");
        freeParkingSpot(this.tickets.get(ticketId).getParkingSpotId());
        int diff = Timestamp.from(Instant.now()).getNanos() - this.tickets.get(ticketId).getTimestamp().getNanos();
        return diff*rate;
    }

    public Integer generateTicketId(){ return this.tickets.isEmpty() ? 0 : this.tickets.size()+1; }
    public void addTicket(Ticket ticket) {tickets.put(ticket.getId(),ticket);}

    public void removeSlotIdfromAvailableSlotsIds(Integer id) {availableSlots.remove(id);}
    public void addSlotIdfromAvailableSlotsIds(Integer id) {availableSlots.add(id);}
    public void occupySlot(Integer id) {this.parkingSpots.get(id).occupy();}
    public void vacateSlot(Integer id) {this.parkingSpots.get(id).vacate();}

    public Integer getAvailableParkingSpotId(VehicleType vehicleType){
        for(Integer slotId: availableSlots){
            if(this.parkingSpots.get(slotId).getType().toString().equals(vehicleType.toString()) && this.parkingSpots.get(slotId).getStatus()==ParkingSpotStatus.AVAILABLE){
                return slotId;
            }
        }
        return null;
    }

    public void initialize(){
        this.parkingSpots.put(1, new ParkingSpot(1, ParkingSpotType.MOTORCYCLE));
        this.parkingSpots.put(2, new ParkingSpot(2, ParkingSpotType.CAR));
        this.parkingSpots.put(3, new ParkingSpot(3, ParkingSpotType.LARGE));
        this.availableSlots.add(1);
        this.availableSlots.add(2);
        this.availableSlots.add(3);
    }
}

class ParkingLotSystem {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.initialize();

        Integer ticketId1 = parkingLot.parkVehicle(VehicleType.MOTORCYCLE);
        System.out.println("parkinglot.parkinglot.Ticket ID for Motorcycle: " + ticketId1);

        Integer ticketId2 = parkingLot.parkVehicle(VehicleType.CAR);
        System.out.println("parkinglot.parkinglot.Ticket ID for Compact Car: " + ticketId2);

        Integer ticketId3 = parkingLot.parkVehicle(VehicleType.LARGE);
        System.out.println("parkinglot.parkinglot.Ticket ID for Large Vehicle: " + ticketId3);

        try {
            Integer fare1 = parkingLot.calculateFare(ticketId1);
            System.out.println("Fare for parkinglot.parkinglot.Ticket ID " + ticketId1 + ": " + fare1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Integer fare2 = parkingLot.calculateFare(ticketId2);
            System.out.println("Fare for parkinglot.parkinglot.Ticket ID " + ticketId2 + ": " + fare2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Integer fare3 = parkingLot.calculateFare(ticketId3);
            System.out.println("Fare for parkinglot.parkinglot.Ticket ID " + ticketId3 + ": " + fare3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Integer ticketId4 = parkingLot.parkVehicle(VehicleType.CAR);
        System.out.println("parkinglot.parkinglot.Ticket ID for Compact Car: " + ticketId4);

    }
}

