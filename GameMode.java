public interface GameMode {
    /** Initialize game state (shuffle if needed, set goals, rendering, etc.). */
    void start(Game g, int shuffleSteps);

    /** Apply one user command; return true if a legal move happened. */
    boolean step(Game g, String input);

    /** True if current game is over (delegates to board/goal as needed). */
    boolean isOver(Game g);

    /** String to display each turn (board + HUD). */
    String render(Game g);
}