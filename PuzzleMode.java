public class PuzzleMode implements GameMode {
    @Override
    public void start(Game g, int shuffleSteps) {
        // ensure a puzzle-style goal if needed (keep your existing one)
        if (!(g.board().goal() instanceof StandardGoal)) {
            g.board().setGoal(new StandardGoal());
        }
        g.startInternal(shuffleSteps); // use Game’s internal shuffle (see updated Game below)
    }

    @Override
    public boolean step(Game g, String input) {
        Direction d = parseDirection(input);
        if (d == null) {
            System.out.println("Use: up/down/left/right or quit");
            return false;
        }
        return g.stepInternal(d) != null; // calls Board.slide + prints board
    }

    @Override
    public boolean isOver(Game g) {
        return g.board().isSolved();
    }

    @Override
    public String render(Game g) {
        return g.board().toString();
    }

    private Direction parseDirection(String s) {
        s = s.toLowerCase();
        switch (s) {
            case "up":    return Direction.UP;
            case "down":  return Direction.DOWN;
            case "left":  return Direction.LEFT;
            case "right": return Direction.RIGHT;
            default:      return null;
        }
    }
}
