import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("inp.txt");
        GameSystem gameSystem = GameSystem.getInstance();
        try {
            List<GameObject> gameObjects = new ArrayList<>();
            List<Player> playerList = new ArrayList<>();
            initialiseGameObjects(gameObjects, playerList);
            gameSystem.createGame(gameObjects, playerList);
            gameSystem.startGame();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void initialiseGameObjects(List<GameObject> gameObjects, List<Player> playerList) {
        Scanner scanner = new Scanner(System.in);

        int snakes = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < snakes; i++) {
            String s = scanner.nextLine();
            List<String> l = Arrays.asList(s.split(" "));
            int start = Integer.parseInt(l.get(0));
            int end = Integer.parseInt(l.get(1));
            gameObjects.add(new GameObject(start, end, ObjectType.SNAKE));
        }

        int ladders = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < ladders; i++) {
            String s = scanner.nextLine();
            List<String> l = Arrays.asList(s.split(" "));
            int start = Integer.parseInt(l.get(0));
            int end = Integer.parseInt(l.get(1));
            gameObjects.add(new GameObject(start, end, ObjectType.LADDER));
        }

        int players = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < players; i++) {
            String name = scanner.nextLine();
            playerList.add(new Player(name, 0));
        }
    }
}
