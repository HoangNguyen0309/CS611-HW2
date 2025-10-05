// TilePiece.java
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class TilePiece implements GamePiece {
    private final int value;     // 0 allowed
    private final Pos pos;

    public TilePiece(int value, Pos pos) {
        if (pos == null) throw new IllegalArgumentException("pos required");
        this.value = value;
        this.pos = pos;
    }

    public int value() { return value; }
    public Pos pos() { return pos; }

    @Override public int ownerId() { return -1; }
    @Override public String kind() { return "TILE"; }
    @Override public List<Pos> positions() { return Collections.singletonList(pos); }

    @Override public String toString() {
        return "TilePiece{v=" + value + " @(" + (pos.row+1) + "," + (pos.col+1) + ")}";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TilePiece)) return false;
        TilePiece that = (TilePiece) o;
        return value == that.value && Objects.equals(pos, that.pos);
    }
    @Override public int hashCode() { return Objects.hash(value, pos); }
}
