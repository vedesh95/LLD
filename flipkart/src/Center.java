import java.util.HashMap;
import java.util.List;

public class Center {
    private String id;
    private String name;
    private String address;
    private HashMap<String, List<WorkoutSlot>> workoutSlots;
    private HashMap<WorkoutType, List<WorkoutSlot>> workoutTypeSlots;

    public Center(String id, String name) {
        this.id = id;
        this.name = name;
        this.workoutSlots = new HashMap<>();
        this.workoutTypeSlots = new HashMap<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, List<WorkoutSlot>> getWorkoutSlots() {
        return workoutSlots;
    }

    public HashMap<WorkoutType, List<WorkoutSlot>> getWorkoutTypeSlots() {
        return workoutTypeSlots;
    }
}
