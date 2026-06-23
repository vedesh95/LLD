package amazon_lockers;
// delivery boys can add and customers can pickup packages from lockers
// customers acces lockers with id and accesscode
// incorrect accesscode will not allow to pickup package
// system generates accesscode when package is added to locker
// lockers can be of different sizes
// assume package size is dropped to locker of same size

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

import static java.lang.Thread.sleep;

enum PackageSize {SMALL, MEDIUM, LARGE}
enum LockerStatus {AVAILABLE, OCCUPIED}
enum LockerSize {SMALL, MEDIUM, LARGE}
class LockerDTO{
    int lockerId;
    int accessCode;
    public LockerDTO(int lockerId,int accessCode){
        this.lockerId=lockerId;
        this.accessCode=accessCode;
    }
}

class Locker{
    private int id;
    private LockerSize size;
    private LockerStatus status;
    private int accessCode;

    public Locker(int accessCode, int id, LockerSize size, LockerStatus status) {
        this.accessCode = accessCode;
        this.id = id;
        this.size = size;
        this.status = status;
    }

    public int getAccessCode() { return accessCode; }
    public int getId() { return id; }
    public LockerSize getSize() { return size; }
    public LockerStatus getStatus() { return status; }
    public void setStatus(LockerStatus status) {this.status = status;}
    public void setAccessCode(int accessCode) {this.accessCode = accessCode;}
}
class Package{
    private int id;
    private PackageSize size;
    public Package(int id, PackageSize size){
        this.id = id;
        this.size = size;
    }
    public int getId() {return this.id;}
    public PackageSize getSize() {return this.size;}
}

interface DropStrategy{
    int getLockerId(PackageSize size);
}

class SimpleDropStrategy implements DropStrategy{
    Map<Integer,Locker>  lockers;
    public SimpleDropStrategy(Map<Integer,Locker>  lockers){this.lockers=lockers;}
    public int getLockerId(PackageSize size){
        for(Locker locker: lockers.values()){
            if (locker.getStatus()==LockerStatus.AVAILABLE && size.toString()==locker.getSize().toString()){
                return locker.getId();
            }
        }
        return -1;
    }
}

public class LockerSystem {
    Map<Integer,Locker>  lockers;
    DropStrategy dropStrategy;

    public LockerSystem(){
        this.lockers = new java.util.HashMap<>();
        this.dropStrategy = new SimpleDropStrategy(this.lockers);
    }

    public LockerDTO dropPackage(PackageSize packageSize){
        int lockerId = this.dropStrategy.getLockerId(packageSize);
        if(lockerId==-1) return null;
        lockers.get(lockerId).setStatus(LockerStatus.OCCUPIED);
        lockers.get(lockerId).setAccessCode(Timestamp.from(Instant.now()).getNanos());
        return new LockerDTO(lockerId,lockers.get(lockerId).getAccessCode());
    }

    public void collectPackage(int lockerId, int accessCode) throws Exception {
        if(lockers.get(lockerId)==null) throw new Exception("locker doesn't exist");
        Locker locker = lockers.get(lockerId);
        if(locker.getStatus()==LockerStatus.AVAILABLE) throw new Exception("incorrect locker specified");
        if(locker.getAccessCode()!=accessCode) throw new Exception("wrong access code");

        // simulate locker
        sleep(3000);
        locker.setStatus(LockerStatus.AVAILABLE);
    }

    public void initialize(){
        // add three lockers of each size
        this.lockers.put(1,new Locker(0,1,LockerSize.SMALL,LockerStatus.AVAILABLE));
        this.lockers.put(2,new Locker(0,2,LockerSize.SMALL,LockerStatus.AVAILABLE));
        this.lockers.put(3,new Locker(0,3, LockerSize.LARGE,LockerStatus.AVAILABLE));
        this.lockers.put(3,new Locker(0,4, LockerSize.MEDIUM,LockerStatus.AVAILABLE));
    }
}

class Test{
    public static void main(String[] args) {
        LockerSystem system = new LockerSystem();
        system.initialize();
        LockerDTO lockerDTO = system.dropPackage(PackageSize.SMALL);
        try {
            system.collectPackage(lockerDTO.lockerId, lockerDTO.accessCode+1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            system.collectPackage(lockerDTO.lockerId, lockerDTO.accessCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // add another small package and check if it is dropped in same locker
        LockerDTO lockerDTO2 = system.dropPackage(PackageSize.SMALL);
        System.out.println(lockerDTO2==null ? "no locker available" : "locker with id=" + lockerDTO2.lockerId +" assigned");

        LockerDTO lockerDTO3 = system.dropPackage(PackageSize.SMALL);
        System.out.println(lockerDTO3==null ? "no locker available" : "locker with id=" + lockerDTO3.lockerId +" assigned");

        LockerDTO lockerDTO4 = system.dropPackage(PackageSize.SMALL);
        System.out.println(lockerDTO4==null ? "no locker available" : "locker with id=" + lockerDTO4.lockerId +" assigned");

    }
}
