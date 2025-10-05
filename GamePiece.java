// GamePiece.java
import java.util.List;

public interface GamePiece {
    /** -1 for neutral (e.g., puzzle tile). Otherwise player id (0/1/...). */
    int ownerId();

    /** A short kind string: "TILE", "EDGE", "BOX" (or "KING", etc. later). */
    String kind();

    /** One or more board positions this piece occupies/anchors (edge has 2). */
    List<Pos> positions();
}
