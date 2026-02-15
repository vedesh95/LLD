import java.util.List;

public class User {
    private String email;
    private String name;
    private String location;
    private List<String> slotIds;

    public User(String email, String name, String location) {
        this.email = email;
        this.name = name;
        this.location = location;
    }

    public static User createUser(String email, String name, String location) {
        return new User(email, name, location);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(List<String> slotIds) {
        this.slotIds = slotIds;
    }
}
