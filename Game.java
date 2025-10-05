import java.util.Objects;

public class Game {
    private Board board;               // reassignable
    private final Shuffler shuffler;   // used by puzzle mode
    private GameMode mode;             // strategy

    // --- Players & turn tracking ---
    private Player[] players = new Player[2];
    private int currentPlayerIdx = 0;

    public Game(Board initial, Shuffler shuffler) {
        if (initial == null) throw new IllegalArgumentException("initial board is null");
        if (shuffler == null) throw new IllegalArgumentException("shuffler is null");
        this.board = initial;
        this.shuffler = shuffler;
    }

    // --- Players API ---
    /** Attach two players; P1 starts. */
    public void setPlayers(Player p1, Player p2) {
        this.players[0] = Objects.requireNonNull(p1, "p1");
        this.players[1] = Objects.requireNonNull(p2, "p2");
        this.currentPlayerIdx = 0;
    }

    /** Returns the players array (length 2). */
    public Player[] getPlayers() { return players; }

    /** Current player or null if players not set. */
    public Player currentPlayer() {
        return (players == null) ? null : players[currentPlayerIdx];
    }

    /** Index of current player: 0 or 1. */
    public int currentPlayerIdx() { return currentPlayerIdx; }

    /** Flip turn: 0 -> 1, 1 -> 0. */
    public void nextPlayer() { currentPlayerIdx = 1 - currentPlayerIdx; }

    /** Reset both players' scores (if present). */
    public void resetScores() {
        if (players != null) {
            if (players[0] != null) players[0].resetScore();
            if (players[1] != null) players[1].resetScore();
        }
    }

    // --- Strategy binding ---
    public void setMode(GameMode mode) { this.mode = mode; }
    public GameMode getMode() { return mode; }

    // --- Public API for both games ---
    public void start(int shuffleSteps) {
        if (mode == null) throw new IllegalStateException("GameMode not set");
        mode.start(this, shuffleSteps);
    }

    public boolean stepCommand(String input) {
        if (mode == null) throw new IllegalStateException("GameMode not set");
        return mode.step(this, input);
    }

    public boolean isOver() {
        if (mode == null) throw new IllegalStateException("GameMode not set");
        return mode.isOver(this);
    }

    public String render() {
        if (mode == null) throw new IllegalStateException("GameMode not set");
        return mode.render(this);
    }

    // --- Backward-compatible puzzle methods (used by PuzzleMode) ---
    Slide stepInternal(Direction d) { // old Main/Game.step(d) behavior
        Slide move = board.slide(d);
        if (move != null) {
            System.out.println(move);
            System.out.println(board);
        }
        return move;
    }

    void startInternal(int shuffleSteps) { // old Game.start(shuffle)
        this.board = shuffler.shuffle(board, shuffleSteps);
        System.out.println("Starting board:\n" + board);
    }

    // --- Getters ---
    public Board board() { return board; }

    public void setBoard(Board b) {
        if (b == null) throw new IllegalArgumentException("board is null");
        this.board = b;
    }

    public Shuffler shuffler() { return shuffler; }
}
