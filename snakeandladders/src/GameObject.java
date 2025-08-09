public class GameObject {
    private int startPosition;
    private int endPosition;
    private ObjectType type;

    public GameObject(int startPosition, int endPosition, ObjectType type) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.type = type;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }
}
