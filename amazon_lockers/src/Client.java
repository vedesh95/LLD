import assignment.AssignmentStrategy;
import locker.LockerDetails;
import locker.Size;

import static java.lang.Thread.sleep;
import locker.*;

public class Client {
    public static void main(String[] args) {
        LockerSystem lockerSystem = new LockerSystem();
        AssignmentStrategy.Item item1 = new AssignmentStrategy.Item("item1", Size.SMALL);
        AssignmentStrategy.Item item2 = new AssignmentStrategy.Item("item2", Size.SMALL);
        AssignmentStrategy.Item item3 = new AssignmentStrategy.Item("item3", Size.MEDIUM);

        LockerDetails ld1 = lockerSystem.drop(item1);
//        comment out to demo the case when no locker is available for the item
//        locker.LockerDetails ld2 = lockerSystem.drop(item2);
        LockerDetails ld3 = lockerSystem.drop(item3);

        lockerSystem.collect(ld1.lockerId, ld1.accessCode);
        lockerSystem.printAvailableLockers();
    }
}