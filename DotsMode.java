import java.util.*;

public class DotsMode implements GameMode {
    private int current = 0; // which player’s turn (0 or 1)

    @Override
    public void start(Game g, int shuffleStepsIgnored) {
        g.board().setGoal(new ConnectedGoal());
        try { g.board().setDotsRendering(true); } catch (Throwable ignored) {}

        // Ensure players exist; if not, create defaults
        Player[] ps = g.getPlayers();
        if (ps == null || ps[0] == null || ps[1] == null) {
            Player p1 = new Player(0, "Player 1");
            Player p2 = new Player(1, "Player 2");
            g.setPlayers(p1, p2);
        } else {
            g.resetScores(); // fresh match
        }
        current = 0; // P1 starts
        System.out.println("Starting board:\n" + g.board());
        System.out.println(hud(g));
    }

    @Override
    public boolean step(Game g, String input) {
        // Expected input: "a b" where a,b are 1-based point IDs (adjacent)
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            System.out.println("Use: <a> <b> (two adjacent point IDs), or 'quit'");
            return false;
        }

        int rows = g.board().rows(), cols = g.board().cols();
        Pos a, b;
        try {
            int id1 = Integer.parseInt(parts[0]);
            int id2 = Integer.parseInt(parts[1]);
            if (!inIdRange(id1, rows, cols) || !inIdRange(id2, rows, cols)) {
                System.out.println("IDs must be in [1, " + (rows * cols) + "].");
                return false;
            }
            a = idToPos(id1, rows, cols);
            b = idToPos(id2, rows, cols);
        } catch (Exception e) {
            System.out.println("Invalid numbers.");
            return false;
        }

        if (Math.abs(a.row - b.row) + Math.abs(a.col - b.col) != 1) {
            System.out.println("Points must be adjacent (no diagonals).");
            return false;
        }
        if (g.board().hasEdge(a, b)) {
            System.out.println("That edge is already claimed.");
            return false;
        }

        // Owned edge claim
        if (!g.board().claimEdge(a, b, current)) {
            System.out.println("Could not add edge.");
            return false;
        }

        int before = safeBoxes(g);
        int gained = g.board().checkBoxCompleted(a, b, current);
        int after  = safeBoxes(g);
        if (gained != (after - before)) gained = Math.max(0, after - before);

        Player me = g.getPlayers()[current];
        if (gained > 0) {
            me.addScore(gained);
            System.out.println(me.getName() + " closed " + gained + " box" + (gained > 1 ? "es" : "") + " and goes again!");
            // same player keeps the turn
        } else {
            current = 1 - current;
            g.nextPlayer();
        }

        System.out.println(g.board());
        System.out.println(hud(g));
        return true;
    }

    @Override
    public boolean isOver(Game g) {
        return g.board().isSolved(); // goal decides win condition (e.g., ConnectedGoal)
    }

    @Override
    public String render(Game g) {
        return hud(g) + "\n" + g.board().toString();
    }

    // ---------- helpers ----------
    private String hud(Game g) {
        Player[] ps = g.getPlayers();
        Player p1 = ps[0], p2 = ps[1];
        int totalBoxes = (g.board().rows() - 1) * (g.board().cols() - 1);
        int claimed = safeBoxes(g);
        String turnName = ps[current].getName();
        return "Scores — " + p1.getName() + ":" + p1.getScore()
                + "  " + p2.getName() + ":" + p2.getScore()
                + "   Boxes: " + claimed + "/" + totalBoxes
                + "   Turn: " + turnName;
    }

    private static boolean inIdRange(int id, int rows, int cols) {
        return id >= 1 && id <= rows * cols;
    }

    private static Pos idToPos(int id1Based, int rows, int cols) {
        int idx = id1Based - 1;
        return new Pos(idx / cols, idx % cols);
    }

    private int safeBoxes(Game g) {
        try { return g.board().boxesClaimed(); } catch (Throwable t) { return 0; }
    }
}
