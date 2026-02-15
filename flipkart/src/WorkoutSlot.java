import java.sql.Timestamp;
import java.util.*;
import java.sql.Date;

public class WorkoutSlot {
    private List<String> users;
    private WorkoutType workoutType;
    private String id;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer capacity;

    public WorkoutSlot(String id, WorkoutType workoutType, Timestamp startTime, Timestamp endTime, Integer capacity) {
        this.id = id;
        this.workoutType = workoutType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.users = new ArrayList<>();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }

    public Date getDate() {
        return date;
    }

    Boolean isSeatAvailable() {
        return users.size() < capacity;
    }

    public void addUser(String userEmail) {
        users.add(userEmail);
    }
}
