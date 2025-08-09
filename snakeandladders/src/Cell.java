public class Cell {
    private int id;
    GameObject gameObject;

    Cell(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public GameObject getObject() {
        return gameObject;
    }
    public void setObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
