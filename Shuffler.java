public interface Shuffler {
    /**
     * Shuffle a board into a new configuration.
     * Must guarantee the returned board is solvable.
     *
     * @param b the starting board (usually solved)
     * @param steps an optional complexity parameter (used by some strategies)
     * @return a shuffled, solvable Board
     */
    Board shuffle(Board b, int steps);
}

