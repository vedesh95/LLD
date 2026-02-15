package locker;

public class Locker {
    private Size size;
    private String id;
    private State state;
    private String accessCode;
    
    public Locker(String id, Size size){
        this.id = id; 
        this.size = size; 
        state = State.EMPTY;
    }
    
    public void collect(String code){
        if(state == State.EMPTY) throw new RuntimeException("wrong locker selected.");
        if(!checkAccessCode(code)) throw new RuntimeException("invalid access code.");
        // sleep for 30 secs
        this.state = State.EMPTY;
        this.accessCode = null;
    }
    
    public void drop() {
        if(state == State.OCCUPIED) throw new RuntimeException("wrong locker selected.");
        this.accessCode = generateAccessCode();
        this.state = State.OCCUPIED;
    }
    
    String generateAccessCode() {
        return id + "-" + System.currentTimeMillis();
    }
    
    boolean checkAccessCode(String code) { return this.accessCode.equals(code); }

    public Size getSize() { return size; }
    public String getId() { return id; }
    public State getState() { return state; }
    public String getAccessCode() { return accessCode; }

}
