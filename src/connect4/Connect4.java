package connect4;
// 7*6 board
// Two players take turns dropping colored discs
// The pieces fall straight down, occupying the lowest available space within the column
// The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs

// entites
/*
 * board
 * player
 * disc
 * game
 * */

import java.util.Objects;

enum DiscColor {RED,ORANGE}
enum Move {INVALID, VALID, WINNER_MOVE, DRAW_MOVE}
class Disc {
    private DiscColor color;
    public Disc(DiscColor color) { this.color = color; }
    public DiscColor getColor() { return color; }
}

class Player{
    private String name;
    private DiscColor color;
    public String getName() {return this.name;}
    public DiscColor getColor() { return color; }

    public Player(DiscColor color, String name) {
        this.color = color;
        this.name = name;
    }
}

class Board{
    private Disc [][] board;
    private int r;
    private int c;

    public Board(){
        r=6;c=7;
        board = new Disc[r][c];
    }

    public int dropDisc(Disc disc,int c){
        if(c<0 || c>this.c) return -1;
        for(int i=r-1;i>=0;i--){
            if(board[i][c]==null){
                board[i][c]=disc;
                return i;
            }
        }
        return -1;
    }
    public boolean checkWin(int i,int j,DiscColor color){
        int[][] dirs ={{-1,0},{0,1},{1,0},{0,-1}};
        for(int[] dir:dirs){
            if(traverse(i,j,dir,color)) return true;
        }
        return false;
    }
    public Boolean traverse(int i, int j, int[] dir,DiscColor color){
        int count=0;
        while(i>=0 && j>=0 && i<r && j<c && board[i][j]!=null && Objects.equals(board[i][j].getColor(), color)){
            count++;
            i=i+dir[0];
            j=j+dir[1];
            if(count>=4) return true;
        }
        return false;
    }

    public int maxMoves(){return r*c;}
}

class Game{
    private Board board;
    Player player1;
    Player player2;
    Player currentPlayer;
    int currentMoves;

    public Game(Player p1, Player p2){
        this.board = new Board();
        player1=p1;
        player2=p2;
        currentPlayer = p1;
        currentMoves = 0;
    }

    public Move makeMove(int c){
        Disc disc = new Disc(currentPlayer.getColor());
        int r= board.dropDisc(disc, c);
        if(r==-1) return Move.INVALID;
        if(board.checkWin(r,c,currentPlayer.getColor())) return Move.WINNER_MOVE;
        currentPlayer = currentPlayer==player1 ? player2 : player1;
        currentMoves++;
        if(currentMoves==board.maxMoves()) return Move.DRAW_MOVE;
        return Move.VALID;
    }
}

class Connect4 {
    public static void main(String[] args){
        Player p1 = new Player(DiscColor.RED,"snakeandladders.Player 1");
        Player p2 = new Player(DiscColor.ORANGE,"snakeandladders.Player 2");
        Game game = new Game(p1,p2);
        // simulate game till win or draw
        int[] moves = {0,0,1,1,2,2,4,4,3,4};
        for(int move:moves) {
            Move result = game.makeMove(move);
            if (result == Move.INVALID) System.out.println("Invalid move");
            else if (result == Move.WINNER_MOVE) {
                System.out.println(game.currentPlayer.getName() + " wins!");
                break;
            } else if (result == Move.DRAW_MOVE) {
                System.out.println("snakeandladders.Game is a draw!");
                break;
            }
        }
    }
}




