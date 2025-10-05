import java.util.Arrays;

public abstract class Board {
    protected final int rows;
    protected final int cols;
    protected final Tile[] tiles; // shared grid representation
    protected GoalStatergy goal;
    protected SolvabilityPolicy solvability;

    protected Board(int rows, int cols, Tile[] tiles,
                    GoalStatergy goal, SolvabilityPolicy solvability) {
        if (rows <= 0 || cols <= 0) throw new IllegalArgumentException("rows/cols must be > 0");
        if (tiles == null || tiles.length != rows * cols)
            throw new IllegalArgumentException("rows*cols must equal tiles.length");
        this.rows = rows;
        this.cols = cols;
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        this.goal = goal;
        this.solvability = solvability;
    }

    /** Back-compat factory: creates a solved sliding-puzzle board. */
    public static Board standard(int rows, int cols, int[] values) {
        return PuzzleBoard.standard(rows, cols, values);
    }

    // ===== shared API =====
    public int rows() { return rows; }
    public int cols() { return cols; }

    public Tile tileAt(Pos p) {
        checkBounds(p);
        return tiles[idx(p.row, p.col)];
    }

    public Tile[] tilesCopy() { return Arrays.copyOf(tiles, tiles.length); }

    public GoalStatergy goal() { return goal; }
    public void setGoal(GoalStatergy goal) { this.goal = goal; }

    public SolvabilityPolicy solvability() { return solvability; }
    public void setSolvability(SolvabilityPolicy s) { this.solvability = s; }

    public boolean isSolved() { return goal != null && goal.isGoal(this); }
    public boolean isSolvable() { return solvability == null || solvability.isSolvable(this); }

    protected int idx(int r, int c) { return r * cols + c; }
    protected void checkBounds(Pos p) {
        if (p.row < 0 || p.row >= rows || p.col < 0 || p.col >= cols)
            throw new IndexOutOfBoundsException("Out of bounds: " + p);
    }

    // ===== puzzle-specific (implemented by PuzzleBoard) =====
    public Pos blankPos() { throw new UnsupportedOperationException("blankPos: puzzle only"); }
    public boolean canSlide(Direction d) { throw new UnsupportedOperationException("canSlide: puzzle only"); }
    public Slide slide(Direction d) { throw new UnsupportedOperationException("slide: puzzle only"); }

    // ===== dots-specific (implemented by DotsBoard) =====
    public void setDotsRendering(boolean on) { throw new UnsupportedOperationException("dots-only"); }
    public int totalBoxes() { throw new UnsupportedOperationException("dots-only"); }
    public int boxesClaimed() { throw new UnsupportedOperationException("dots-only"); }
    public boolean hasEdge(Pos a, Pos b) { throw new UnsupportedOperationException("dots-only"); }
    public boolean markEdge(Pos a, Pos b) { throw new UnsupportedOperationException("dots-only"); }
    public boolean claimEdge(Pos a, Pos b, int ownerId) { throw new UnsupportedOperationException("dots-only"); }
    public int checkBoxCompleted(Pos a, Pos b) { throw new UnsupportedOperationException("dots-only"); }
    public int checkBoxCompleted(Pos a, Pos b, int ownerId) { throw new UnsupportedOperationException("dots-only"); }

    // ===== lifecycle =====
    public abstract Board copy();
    @Override public abstract String toString();
}
