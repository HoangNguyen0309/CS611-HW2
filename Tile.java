import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Collections;

public class Tile {
    private int value;   // 0 = blank (by convention)
    private Pos pos;

    // ---- Dots & Boxes: incident (undirected) edges from this point to neighbor positions ----
    private final Set<Pos> incidentEdges = new HashSet<>();

    public Tile(int value, Pos pos) {
        if (pos == null) throw new IllegalArgumentException("pos cannot be null");
        if (value < 0) throw new IllegalArgumentException("value must be >= 0");
        this.value = value;
        this.pos = pos;
    }

    // -------- Sliding puzzle API (unchanged) --------
    public int getValue() { return value; }

    public void setValue(int v) {
        if (v < 0) throw new IllegalArgumentException("value must be >= 0");
        this.value = v;
    }

    public Pos getPos() { return pos; }

    public void setPos(Pos p) {
        if (p == null) throw new IllegalArgumentException("pos cannot be null");
        this.pos = p;
    }

    @Override
    public String toString() {
        return value == 0 ? "_" : Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        // Keep original equality semantics for the puzzle
        return value == tile.value && Objects.equals(pos, tile.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, pos);
    }

    // -------- Dots & Boxes helpers (new) --------

    /** Return true if an edge from this point to the given neighbor position already exists. */
    public boolean isConnected(Pos neighbor) {
        if (neighbor == null) return false;
        return incidentEdges.contains(neighbor);
    }

    /**
     * Add an edge from this point to the given neighbor position.
     * Returns true if it was newly added (not already present).
     * NOTE: Board should also add the reciprocal edge on the neighbor's Tile to keep it undirected.
     */
    public boolean addEdge(Pos neighbor) {
        if (neighbor == null) return false;
        return incidentEdges.add(neighbor);
    }

    /** Remove an existing edge from this point to the given neighbor position. */
    public boolean removeEdge(Pos neighbor) {
        if (neighbor == null) return false;
        return incidentEdges.remove(neighbor);
    }

    /** Unmodifiable view of adjacent positions connected to this point. */
    public Set<Pos> neighbors() {
        return Collections.unmodifiableSet(incidentEdges);
    }

    /** Degree (number of incident edges) of this point. */
    public int degree() {
        return incidentEdges.size();
    }
}
