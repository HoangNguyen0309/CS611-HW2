import java.util.Objects;

/**
 * Player identity + simple mutable stats.
 * Game/Mode decides how score is used (boxes claimed, etc.).
 */
public final class Player {
    private final int id;
    private final String name;

    private int score;
    private int wins;
    private int losses;
    private int ties;

    public Player(int id, String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("name required");
        this.id = id;
        this.name = name.trim();
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public int getScore() { return score; }
    public void addScore(int delta) { this.score += delta; }
    public void resetScore() { this.score = 0; }

    public void recordWin()  { wins++; }
    public void recordLoss() { losses++; }
    public void recordTie()  { ties++; }

    public int getWins()   { return wins; }
    public int getLosses() { return losses; }
    public int getTies()   { return ties; }

    @Override public String toString() {
        return "Player{id=" + id + ", name='" + name + "', score=" + score +
                ", W-L-T=" + wins + "-" + losses + "-" + ties + "}";
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player that = (Player) o;
        return id == that.id;
    }

    @Override public int hashCode() { return Objects.hash(id); }
}
