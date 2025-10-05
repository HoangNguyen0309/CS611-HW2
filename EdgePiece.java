// EdgePiece.java
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class EdgePiece implements GamePiece {
    private final Pos a;           // endpoints (adjacent)
    private final Pos b;
    private final int ownerId;     // 0/1/.. who claimed it

    public EdgePiece(Pos a, Pos b, int ownerId) {
        if (a == null || b == null) throw new IllegalArgumentException("endpoints required");
        this.a = a; this.b = b; this.ownerId = ownerId;
    }

    public Pos a() { return a; }
    public Pos b() { return b; }

    /** Canonical undirected key "r1,c1|r2,c2" (sorted) for maps/sets. */
    public String key() {
        boolean aFirst = (a.row < b.row) || (a.row == b.row && a.col <= b.col);
        Pos p = aFirst ? a : b, q = aFirst ? b : a;
        return p.row + "," + p.col + "|" + q.row + "," + q.col;
    }

    @Override public int ownerId() { return ownerId; }
    @Override public String kind() { return "EDGE"; }
    @Override public List<Pos> positions() { return Arrays.asList(a, b); }

    @Override public String toString() {
        return "EdgePiece{" + (a.row+1)+","+(a.col+1) + "-" + (b.row+1)+","+(b.col+1) + " @P" + (ownerId+1) + "}";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgePiece)) return false;
        EdgePiece that = (EdgePiece) o;
        return ownerId == that.ownerId && this.key().equals(that.key());
    }
    @Override public int hashCode() { return Objects.hash(key(), ownerId); }
}
