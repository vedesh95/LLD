import assignment.AssignmentStrategy;
import assignment.SimpleAssignmentStrategy;
import locker.Locker;
import locker.LockerDetails;
import locker.Size;
import locker.State;

import java.util.HashMap;
import java.util.Map;

public class LockerSystem {
    Map<String, Locker> lockerMap;
    AssignmentStrategy assignmentStrategy;
    LockerSystem(){
        lockerMap = new HashMap<>();
        assignmentStrategy = new SimpleAssignmentStrategy();
        initialize();
    }


    public LockerDetails drop(AssignmentStrategy.Item item){
        Locker locker = lockerMap.get(assignmentStrategy.assign(lockerMap, item));
        if(locker == null){
            throw new RuntimeException("No locker available for the item");
        }
        locker.drop();
        return new LockerDetails(locker);
    }

    public void collect(String lockerId, String accessCode){
        Locker locker = lockerMap.get(lockerId);
        if (locker == null) {
            throw new RuntimeException("Invalid locker ID");
        }
        locker.collect(accessCode);
    }

    public void initialize() {
        lockerMap.put("1", new Locker("1", Size.LARGE));
        lockerMap.put("2", new Locker("2", Size.MEDIUM));
        lockerMap.put("3", new Locker("3", Size.SMALL));
    }

    public void printAvailableLockers() {
        for (Locker locker : lockerMap.values()) {
            if (locker.getState() == State.EMPTY) {
                System.out.println("locker.Locker ID: " + locker.getId() + ", locker.Size: " + locker.getSize());
            }
        }
    }
}
