import java.util.*;

public class ConnectedGoal implements GoalStatergy {

    @Override
    public boolean isGoal(Board b) {
        int rows = b.rows();
        int cols = b.cols();
        int totalPossible = totalPossibleEdges(rows, cols);

        // Count claimed edges once each (check only right & down neighbors)
        int claimed = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Pos u = new Pos(r, c);
                // right neighbor
                if (c + 1 < cols && b.hasEdge(u, new Pos(r, c + 1))) claimed++;
                // down neighbor
                if (r + 1 < rows && b.hasEdge(u, new Pos(r + 1, c))) claimed++;
            }
        }

        return claimed == totalPossible;
    }

    /** Total possible undirected unit edges in an rows×cols point grid. */
    private static int totalPossibleEdges(int rows, int cols) {
        int horizontal = rows * (cols - 1);
        int vertical   = (rows - 1) * cols;
        return Math.max(0, horizontal + vertical);
    }
}
