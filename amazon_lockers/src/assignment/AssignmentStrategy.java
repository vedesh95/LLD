package assignment;

import locker.Locker;

import java.util.Map;
import locker.Size;

public interface AssignmentStrategy {
    String assign(Map<String, Locker> lockerMap, Item item);

    class Item {
        private String id;
        private Size size;

        public Item(String id, Size size) {
            this.id = id;
            this.size = size;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public Size getSize() { return size; }
        public void setSize(Size size) { this.size = size; }
    }
}
