public class StandardGoal implements GoalStatergy {
    @Override
    public boolean isGoal(Board b) {
        int r = b.rows();
        int c = b.cols();
        Tile[] tiles = b.tilesCopy();  // now Tile[]

        int expected = 1;
        for (int i = 0; i < r * c - 1; i++) {
            if (tiles[i].getValue() != expected++) return false;
        }
        // Last position should be blank (0)
        return tiles[r * c - 1].getValue() == 0;
    }
}
