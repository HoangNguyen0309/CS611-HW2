public class Slide extends Move {
    private final Pos from;       // position of tile that moved
    private final Pos to;         // blank position after move
    private final int tileValue;  // value of tile moved

    public Slide(Direction direction, Pos from, Pos to, int tileValue) {
        super(direction);
        this.from = from;
        this.to = to;
        this.tileValue = tileValue;
    }

    public Pos getFrom() { return from; }
    public Pos getTo() { return to; }
    public int getTileValue() { return tileValue; }

    @Override
    public String toString() {
        return "Slide tile " + tileValue + " " + getDirection() +
                " from " + from + " to " + to;
    }
}
