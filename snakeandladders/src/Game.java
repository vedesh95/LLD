import java.util.*;

public class Game {

    private List<Player> players;
    private Map<Integer, Cell> cells;
    private GameStatus status;


    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    Game(List<GameObject> objects, List<Player> players){
        cells = new HashMap<>();
        for(int i=0; i<=100; i++) cells.put(i, new Cell(i));
        for(GameObject object : objects) {
            cells.get(object.getStartPosition()).setObject(object);
        }
        setPlayers(players);
    }

    public Player startGame() {
        // Game logic to start the game
        // This could include rolling dice, moving players, checking for snakes or ladders, etc.
        status = GameStatus.START;
        Player winner = null;
        while(!status.equals(GameStatus.END)){
            for(Player player : players){
                move(player);
                if(player.getPosition() == 100) {
                    status = GameStatus.END;
                    winner = player;
                    break;
                }
            }
        }
        return winner;
    }

    public int rollDice() {
        // Simulate rolling a dice
        return (int) (Math.random() * 6) + 1; // Returns a number between 1 and 6
    }

    public void movePlayer(Player player, int newPosition) {
        if(newPosition == 100) {
            player.setPosition(newPosition);
            return;
        } else if(newPosition > 100) return;
        player.setPosition(newPosition);
        GameObject object = cells.get(player.getPosition()).getObject();
        if(object != null) {
            movePlayer(player, object.getEndPosition());
        }
    }
    public void move(Player player) {
        int roll = rollDice();
        int currentPosition = player.getPosition();
        int newPosition = player.getPosition() + roll;
        movePlayer(player, newPosition);
        System.out.println(player.getName() + " rolled a " + roll + " and moved from " + currentPosition + " to " + player.getPosition());
    }
}
