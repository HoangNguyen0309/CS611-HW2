// BoxPiece.java
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class BoxPiece implements GamePiece {
    private final Pos topLeft;   // 1x1 box anchor
    private final int ownerId;

    public BoxPiece(Pos topLeft, int ownerId) {
        if (topLeft == null) throw new IllegalArgumentException("topLeft required");
        this.topLeft = topLeft; this.ownerId = ownerId;
    }

    public Pos topLeft() { return topLeft; }

    @Override public int ownerId() { return ownerId; }
    @Override public String kind() { return "BOX"; }
    @Override public List<Pos> positions() { return Collections.singletonList(topLeft); }

    @Override public String toString() {
        return "BoxPiece{tl=" + (topLeft.row+1) + "," + (topLeft.col+1) + " @P" + (ownerId+1) + "}";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoxPiece)) return false;
        BoxPiece that = (BoxPiece) o;
        return ownerId == that.ownerId && Objects.equals(topLeft, that.topLeft);
    }
    @Override public int hashCode() { return Objects.hash(topLeft, ownerId); }
}
