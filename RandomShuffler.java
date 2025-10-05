import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomShuffler implements Shuffler {
    private final Random rng;

    public RandomShuffler() {
        this(new Random());
    }

    public RandomShuffler(Random rng) {
        this.rng = rng;
    }

    @Override
    public Board shuffle(Board b, int steps) {
        Board copy = b.copy();
        for (int i = 0; i < steps; i++) {
            List<Direction> moves = new ArrayList<Direction>();
            for (Direction d : Direction.values()) {
                if (copy.canSlide(d)) moves.add(d);
            }
            if (moves.isEmpty()) break;
            Direction pick = moves.get(rng.nextInt(moves.size()));
            copy.slide(pick);
        }

        // ✅ Enforce solvability contract
        if (!copy.isSolvable()) {
            // If something unexpected happens, fallback
            return b.copy();
        }

        return copy;
    }
}
