package locker;

public class LockerDetails {
    public String lockerId;
    public String accessCode;

    public LockerDetails(Locker locker){
        this.lockerId = locker.getId();
        this.accessCode = locker.getAccessCode();
    }

}
