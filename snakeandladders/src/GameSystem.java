import java.util.List;

public class GameSystem {

    private Game game;
    private static GameSystem instance;
    private GameSystem() {}

    /**
     * Singleton pattern to ensure only one instance of GameSystem exists.
     */
    public synchronized static GameSystem getInstance() {
        if (instance == null) {
            instance = new GameSystem();
        }
        return instance;
    }


    public Game createGame(List<GameObject> objects, List<Player> players) {
        this.game = new Game(objects, players);
        return game;
    }
    public void startGame() {
        Player player = game.startGame();
        System.out.println(player.getName() + " wins the game");
    }

}
