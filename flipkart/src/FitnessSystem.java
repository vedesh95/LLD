import javax.naming.ContextNotEmptyException;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

public class FitnessSystem {
    private HashMap<String, Center> centers;
    private HashMap<String, User> users;

    FitnessSystem(){
        centers = new HashMap<>();
        users = new HashMap<>();
    }

    public void registerCenter(String email, String name, String location) {
        User user = User.createUser(email, name, location);
        users.put(user.getEmail(), user);
    }

    public List<WorkoutSlot> viewWorkoutSlotsAvailability(WorkoutType type, Date date) {
        List<WorkoutSlot> slots= new ArrayList<>();
        for(Center center : centers.values()) {
            slots.addAll(center.getWorkoutTypeSlots().getOrDefault(type, new ArrayList<WorkoutSlot>()).stream().filter(x  -> x.isSeatAvailable()).toList());
        }
        return slots;
    }

    public boolean bookSlot(String email, String name, WorkoutType type, Time startTime, Time endTime, Date date) {
        User user = users.get(email);
        Center center = centers.get(name);
        if (user == null || center == null) {
            return false; // User not registered // Center not registered
        }
        List<WorkoutSlot> slots = center.getWorkoutTypeSlots().getOrDefault(type, new ArrayList<>());
        for (WorkoutSlot slot : slots) {
            if (slot.isSeatAvailable() && slot.getStartTime().toString().equals(startTime.toString()) && slot.getEndTime().toString().equals(endTime.toString()) && slot.getDate().equals(date)) {
                slot.addUser(user.getEmail());
                return true; // Slot booked successfully
            }
        }
        return false;
    }
}
