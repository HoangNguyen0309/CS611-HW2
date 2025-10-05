public class ParitySolvability implements SolvabilityPolicy {
    @Override
    public boolean isSolvable(Board b) {
        Tile[] ts = b.tilesCopy();       // <-- Tile[]
        int width = b.cols();
        int inversions = 0;

        for (int i = 0; i < ts.length; i++) {
            int vi = ts[i].getValue();
            if (vi == 0) continue;
            for (int j = i + 1; j < ts.length; j++) {
                int vj = ts[j].getValue();
                if (vj != 0 && vi > vj) inversions++;
            }
        }

        if (width % 2 == 1) {
            return inversions % 2 == 0;  // odd width: even inversions
        } else {
            Pos blank = b.blankPos();
            int rowFromBottom = b.rows() - blank.row; // 1-based from bottom
            boolean blankOnEvenFromBottom = (rowFromBottom % 2 == 0);
            return blankOnEvenFromBottom ? (inversions % 2 == 1)
                    : (inversions % 2 == 0);
        }
    }
}
