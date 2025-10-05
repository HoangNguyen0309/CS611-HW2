import java.util.*;

public class DotsBoard extends Board {
    // Legacy/simple tracking (for rendering and counts)
    private final Set<String> claimedEdges = new HashSet<>();
    private final Set<Pos> claimedBoxes = new HashSet<>();
    private boolean renderDots = true;

    // Owned tracking (who claimed what)
    private final Map<String, EdgePiece> edgePieces = new HashMap<>();
    private final Map<Pos, BoxPiece>    boxPieces  = new HashMap<>();

    public DotsBoard(int rows, int cols, Tile[] tiles,
                     GoalStatergy goal, SolvabilityPolicy solvability) {
        super(rows, cols, tiles, goal, solvability);
    }

    // ===== dots-specific implementations =====

    @Override
    public void setDotsRendering(boolean on) { this.renderDots = on; }

    @Override
    public int totalBoxes() { return Math.max(0, (rows - 1) * (cols - 1)); }

    @Override
    public int boxesClaimed() { return claimedBoxes.size(); }

    @Override
    public boolean hasEdge(Pos a, Pos b) {
        checkBounds(a); checkBounds(b);
        return claimedEdges.contains(edgeKey(a, b));
    }

    @Override
    public boolean markEdge(Pos a, Pos b) {
        checkBounds(a); checkBounds(b);
        if (!isAdjacent(a, b)) return false;

        String key = edgeKey(a, b);
        if (claimedEdges.contains(key)) return false;

        claimedEdges.add(key);
        tileAt(a).addEdge(b);
        tileAt(b).addEdge(a);
        return true;
    }

    @Override
    public boolean claimEdge(Pos a, Pos b, int ownerId) {
        if (!markEdge(a, b)) return false;
        String key = edgeKey(a, b);
        edgePieces.putIfAbsent(key, new EdgePiece(a, b, ownerId));
        return true;
    }

    @Override
    public int checkBoxCompleted(Pos a, Pos b) {
        return checkBoxCompleted(a, b, -1);
    }

    @Override
    public int checkBoxCompleted(Pos a, Pos b, int ownerId) {
        int gained = 0;

        // Horizontal edge
        if (a.row == b.row) {
            int r = a.row;
            int cLeft = Math.min(a.col, b.col);
            if (r > 0 && boxClosedAt(r - 1, cLeft)) {
                if (claimBox(new Pos(r - 1, cLeft), ownerId)) gained++;
            }
            if (r < rows - 1 && boxClosedAt(r, cLeft)) {
                if (claimBox(new Pos(r, cLeft), ownerId)) gained++;
            }
        }
        // Vertical edge
        else if (a.col == b.col) {
            int c = a.col;
            int rTop = Math.min(a.row, b.row);
            if (c > 0 && boxClosedAt(rTop, c - 1)) {
                if (claimBox(new Pos(rTop, c - 1), ownerId)) gained++;
            }
            if (c < cols - 1 && boxClosedAt(rTop, c)) {
                if (claimBox(new Pos(rTop, c), ownerId)) gained++;
            }
        }
        return gained;
    }

    private boolean claimBox(Pos topLeft, int ownerId) {
        if (boxPieces.containsKey(topLeft)) return false;
        boxPieces.put(topLeft, new BoxPiece(topLeft, ownerId));
        claimedBoxes.add(topLeft);
        return true;
    }

    private boolean isAdjacent(Pos a, Pos b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col) == 1;
    }

    private String edgeKey(Pos a, Pos b) {
        boolean aFirst = (a.row < b.row) || (a.row == b.row && a.col <= b.col);
        Pos p = aFirst ? a : b, q = aFirst ? b : a;
        return p.row + "," + p.col + "|" + q.row + "," + q.col;
    }

    private boolean boxClosedAt(int r, int c) {
        if (r < 0 || c < 0 || r + 1 >= rows || c + 1 >= cols) return false;

        Pos tl = new Pos(r, c);
        Pos tr = new Pos(r, c + 1);
        Pos bl = new Pos(r + 1, c);
        Pos br = new Pos(r + 1, c + 1);

        return hasEdge(tl, tr) && hasEdge(bl, br) && hasEdge(tl, bl) && hasEdge(tr, br);
    }

    // Optional accessors
    public Collection<EdgePiece> getEdgePieces() { return edgePieces.values(); }
    public Collection<BoxPiece>  getBoxPieces()  { return boxPieces.values();  }
    public BoxPiece getBoxOwner(Pos tl)          { return boxPieces.get(tl);   }

    @Override
    public DotsBoard copy() {
        Tile[] copy = new Tile[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            copy[i] = new Tile(tiles[i].getValue(), tiles[i].getPos());
        }
        DotsBoard b = new DotsBoard(rows, cols, copy, goal, solvability);
        b.claimedEdges.addAll(this.claimedEdges);
        b.claimedBoxes.addAll(this.claimedBoxes);
        b.renderDots = this.renderDots;
        b.edgePieces.putAll(this.edgePieces);
        b.boxPieces.putAll(this.boxPieces);
        return b;
    }

    @Override
    public String toString() {
        if (!renderDots) {
            StringBuilder sb = new StringBuilder();
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    sb.append(String.format("%2s ", tiles[idx(r, c)].toString()));
                }
                sb.append('\n');
            }
            return sb.toString();
        }

        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            // Points + horizontal edges
            for (int c = 0; c < cols; c++) {
                sb.append("•");
                if (c < cols - 1) {
                    Pos a = new Pos(r, c);
                    Pos b = new Pos(r, c + 1);
                    sb.append(hasEdge(a, b) ? "──" : "  ");
                }
            }
            sb.append('\n');

            // Vertical edges and box interiors
            if (r < rows - 1) {
                for (int c = 0; c < cols; c++) {
                    Pos a = new Pos(r, c);
                    Pos b = new Pos(r + 1, c);
                    sb.append(hasEdge(a, b) ? "│" : " ");
                    if (c < cols - 1) {
                        Pos box = new Pos(r, c);
                        BoxPiece owner = boxPieces.get(box);
                        if (owner != null) {
                            char ch = (char) ('A' + Math.max(0, owner.ownerId()));
                            sb.append(ch).append(ch);
                        } else {
                            sb.append(claimedBoxes.contains(box) ? "##" : "  ");
                        }
                    }
                }
                sb.append('\n');
            }
        }

        if (!claimedEdges.isEmpty()) {
            sb.append('\n').append(formatClaimedEdges()).append('\n');
        }

        return sb.toString();
    }

    private String formatClaimedEdges() {
        if (claimedEdges.isEmpty()) return "Claimed edges (0): —";
        java.util.List<String> parts = new java.util.ArrayList<>();
        for (String key : claimedEdges) {
            String[] halves = key.split("\\|");
            String[] a = halves[0].split(",");
            String[] b = halves[1].split(",");
            int r1 = Integer.parseInt(a[0]) + 1, c1 = Integer.parseInt(a[1]) + 1;
            int r2 = Integer.parseInt(b[0]) + 1, c2 = Integer.parseInt(b[1]) + 1;
            parts.add("(" + r1 + "," + c1 + ")-(" + r2 + "," + c2 + ")");
        }
        java.util.Collections.sort(parts);
        return "Claimed edges (" + parts.size() + "): " + String.join(", ", parts);
    }
}
