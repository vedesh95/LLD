package assignment;

import locker.Locker;
import locker.State;

import java.util.Map;

public class SimpleAssignmentStrategy implements AssignmentStrategy {
    @Override
    public String assign(Map<String, Locker> lockerMap, Item item) {
        for (Locker locker : lockerMap.values()) {
            if (locker.getState() == State.EMPTY && locker.getSize().ordinal() == item.getSize().ordinal()) {
                return locker.getId();
            }
        }
        return null;
    }
}
