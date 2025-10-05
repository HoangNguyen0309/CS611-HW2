import java.util.Arrays;

public class PuzzleBoard extends Board {
    private int blankIndex;

    public PuzzleBoard(int rows, int cols, Tile[] tiles,
                       GoalStatergy goal, SolvabilityPolicy solvability) {
        super(rows, cols, tiles, goal, solvability);
        this.blankIndex = findBlankIndex();
    }

    /** Factory for a standard solved puzzle (keeps old behavior). */
    public static PuzzleBoard standard(int rows, int cols, int[] values) {
        if (values == null || values.length != rows * cols)
            throw new IllegalArgumentException("values length must be rows*cols");
        Tile[] ts = new Tile[values.length];
        for (int i = 0; i < values.length; i++) {
            ts[i] = new Tile(values[i], new Pos(i / cols, i % cols));
        }
        return new PuzzleBoard(rows, cols, ts, new StandardGoal(), new ParitySolvability());
    }

    // ===== puzzle-specific implementations =====

    @Override
    public Pos blankPos() { return tiles[blankIndex].getPos(); }

    @Override
    public boolean canSlide(Direction d) {
        Pos blank = blankPos();
        switch (d) {
            case UP:    return blank.row + 1 < rows;
            case DOWN:  return blank.row - 1 >= 0;
            case LEFT:  return blank.col + 1 < cols;
            case RIGHT: return blank.col - 1 >= 0;
            default:    return false;
        }
    }

    @Override
    public Slide slide(Direction d) {
        if (!canSlide(d)) return null;

        Pos blank = blankPos();
        Pos t;
        switch (d) {
            case UP:    t = new Pos(blank.row + 1, blank.col); break;
            case DOWN:  t = new Pos(blank.row - 1, blank.col); break;
            case LEFT:  t = new Pos(blank.row, blank.col + 1); break;
            case RIGHT: t = new Pos(blank.row, blank.col - 1); break;
            default:    return null;
        }

        int ti = idx(t.row, t.col);
        Tile movingTile = tiles[ti];

        // Swap values
        tiles[blankIndex].setValue(movingTile.getValue());
        movingTile.setValue(0);

        Pos oldBlank = blank;
        blankIndex = ti;

        return new Slide(d, t, oldBlank, tiles[blankIndex].getValue());
    }

    private int findBlankIndex() {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getValue() == 0) return i;
        }
        throw new IllegalArgumentException("No blank (0) tile found");
    }

    @Override
    public PuzzleBoard copy() {
        Tile[] copy = new Tile[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            copy[i] = new Tile(tiles[i].getValue(), tiles[i].getPos());
        }
        return new PuzzleBoard(rows, cols, copy, goal, solvability);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                sb.append(String.format("%2s ", tiles[idx(r, c)].toString()));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
