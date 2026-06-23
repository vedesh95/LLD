package elevator;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;

// requirements:
// assume 10 floors 3 elevators
// hall calls
//- user can press up or down button on each floor
//- inside elevator users can specify one or more floors
// concurrent requests can be made by multiple users
// reject invalid requests
// request for current floor is no-op

enum RequestType {PICKUP_UP,PICKUP_DOWN, DESTINATION}
enum ElevatorStatus {IDLE, MOVING_UP, MOVING_DOWN}
class ElevatorRequest{
    private int floor;
    private RequestType type;
    public ElevatorRequest(int floor, RequestType type){
        this.floor=floor;
        this.type=type;
    }
    public int getFloor() {return this.floor;}
    public RequestType getType() {return this.type;}

    // we need to override equals and hashcode to ensure that two requests with the same floor and type are considered equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ElevatorRequest that = (ElevatorRequest) obj;
        return floor == that.floor && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(floor);
        result = 31 * result + type.hashCode();
        return result;
    }
}
class Elevator{
    private int id;
    private int currentFloor;
    private ElevatorStatus status;
    Set<ElevatorRequest> requests;
    public Elevator(int id){
        this.id=id;
        this.currentFloor= 0;
        this.status=ElevatorStatus.IDLE;
        this.requests = new java.util.HashSet<>();
    }
    public synchronized boolean addRequest(ElevatorRequest request){
        if(request.getFloor() == this.currentFloor) { return false; }
        if(this.requests.contains(request)) { return false; }
        return this.requests.add(request);
    }

    public synchronized void step(){
        List<ElevatorRequest> reqsToRemove = new java.util.ArrayList<>();
        for(ElevatorRequest request: this.requests){
            if(request.getFloor() == this.currentFloor) reqsToRemove.add(request);
        }
        for(ElevatorRequest request: reqsToRemove){
            this.requests.remove(request);
        }

        if(this.requests.isEmpty()) {
            this.status = ElevatorStatus.IDLE;
            return;
        }

        if(this.status == ElevatorStatus.MOVING_UP && checkRequestAboveCurrentFloor()) {
            this.status = ElevatorStatus.MOVING_UP;
            currentFloor++;
        } else {
            if(currentFloor==0){
                this.status = ElevatorStatus.MOVING_UP;
                currentFloor++;
                return;
            }
            this.status = ElevatorStatus.MOVING_DOWN;
            currentFloor--;
        }

    }

    public boolean checkRequestAboveCurrentFloor(){
        for(ElevatorRequest request: this.requests){
            if(request.getFloor() > this.currentFloor && request.getType() == RequestType.PICKUP_UP) {
                return true;
            }
        }
        return false;
    }

    public int getId() {return this.id;}
    public int getCurrentFloor() {return this.currentFloor;}
    public ElevatorStatus getStatus() {return this.status;}
    public void setCurrentFloor(int floor) {this.currentFloor=floor;}
    public void setStatus(ElevatorStatus status) {this.status=status;}
}

public class ElevatorSystem {
    private List<Elevator> elevators;
    ElevatorSystem(){
        this.elevators = new java.util.ArrayList<>();
        for(int i=0;i<3;i++){
            this.elevators.add(new Elevator(i));
        }
    }

    public Elevator addRequest(ElevatorRequest request){
        Elevator bestElevator = findBestElevator(request);
        if(bestElevator != null) bestElevator.addRequest(request);
        return bestElevator;
    }

    public Elevator addRequest(int elevatorId, ElevatorRequest request){
        if(elevatorId < 0 || elevatorId >= this.elevators.size()) return null;
        Elevator elevator = this.elevators.get(elevatorId);
        elevator.addRequest(request);
        return elevator;
    }

    public void step(){
        for(Elevator elevator: this.elevators){
            elevator.step();
        }
    }

    // find elevator moving in the same direction and closest to the request floor
    public Elevator findBestElevator(ElevatorRequest request) {
        Elevator bestElevator = null;
        int bestDistance = Integer.MAX_VALUE;
        for (Elevator elevator : this.elevators) {
            if (elevator.getStatus() == ElevatorStatus.MOVING_UP && request.getType() == RequestType.PICKUP_UP && elevator.getCurrentFloor() <= request.getFloor()) {
                int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestElevator = elevator;
                }
            } else if (elevator.getStatus() == ElevatorStatus.MOVING_DOWN && request.getType() == RequestType.PICKUP_DOWN && elevator.getCurrentFloor() >= request.getFloor()) {
                int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestElevator = elevator;
                }
            }
        }

        if (bestElevator != null) {
            return bestElevator;
        }

        // check for idle elevators
        for (Elevator elevator : this.elevators) {
            if (Objects.equals(elevator.getStatus().toString(), ElevatorStatus.IDLE.toString())){
                elevator.setStatus(ElevatorStatus.MOVING_UP);
                return elevator;
            }
        }

        // if no elevator found, return the closest
        bestElevator = null;
        bestDistance = Integer.MAX_VALUE;
        for (Elevator elevator : this.elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
            if (distance < bestDistance) {
                bestDistance = distance;
                bestElevator = elevator;
            }
        }
        return bestElevator;
    }
}

class ElevatorSimulator {
    public static void main(String[] args) throws InterruptedException {
        // spin a thread to step the elevators every second
        ElevatorSystem elevatorSystem = new ElevatorSystem();
        new Thread(() -> {
            // simulate for 5 secs
            for(int i=0;i<5;i++) {
                elevatorSystem.step();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // simulate some requests
        Elevator e;
        e=elevatorSystem.addRequest(new ElevatorRequest(3, RequestType.PICKUP_UP));
        System.out.println(e==null? null: e.getId() + " " + e.getCurrentFloor() + " " + e.getStatus());
        e=elevatorSystem.addRequest(new ElevatorRequest(5, RequestType.PICKUP_DOWN));
        System.out.println(e==null? null: e.getId() + " " + e.getCurrentFloor() + " " + e.getStatus());

        // simulate some destination requests
        e=elevatorSystem.addRequest(0,new ElevatorRequest(7, RequestType.DESTINATION));
        System.out.println(e==null? null: e.getId() + " " + e.getCurrentFloor() + " " + e.getStatus());
        e=elevatorSystem.addRequest(1,new ElevatorRequest(2, RequestType.DESTINATION));
        System.out.println(e==null? null: e.getId() + " " + e.getCurrentFloor() + " " + e.getStatus());

        // simulate internal req even if elevator is moving in opposite direction
        e=elevatorSystem.addRequest(0,new ElevatorRequest(1, RequestType.DESTINATION));
        System.out.println(e==null? null: e.getId() + " " + e.getCurrentFloor() + " " + e.getStatus());
        Thread.sleep(2000); // wait for 2 seconds to let elevators move

        // idle lift should pick up request
        e=elevatorSystem.addRequest(new ElevatorRequest(1, RequestType.PICKUP_UP));
        System.out.println(e==null? null: e.getId() + " " + e.getCurrentFloor() + " " + e.getStatus());
    }
}
