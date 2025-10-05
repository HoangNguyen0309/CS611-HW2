import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===================================");
            System.out.println(" [1] Sliding Puzzle");
            System.out.println(" [2] Dots & Boxes");
            System.out.println(" [q] Quit");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim().toLowerCase();

            if (ch.equals("q")) break;

            switch (ch) {
                case "1": {
                    // Ask size, build puzzle board
                    int rows = askInt(sc, "Enter number of rows: ", 2, 50);
                    int cols = askInt(sc, "Enter number of cols: ", 2, 50);
                    int[] vals = new int[rows * cols];
                    for (int i = 0; i < vals.length - 1; i++) vals[i] = i + 1;
                    vals[vals.length - 1] = 0;

                    Board initial = Board.standard(rows, cols, vals);
                    Game game = new Game(initial, new RandomShuffler());
                    game.setMode(new PuzzleMode());
                    int steps = Math.max(20, rows * cols * 10);
                    game.start(steps);

                    System.out.println("Type: up, down, left, right. Type quit to exit.\n");
                    while (!game.isOver()) {
                        System.out.println(game.render());
                        System.out.print("Move: ");
                        String cmd = sc.nextLine().trim().toLowerCase();
                        if (cmd.equals("quit")) break;
                        game.stepCommand(cmd);
                    }
                    break;
                }
                case "2": {
                    // Ask size & players, build dots board
                    System.out.print("Player 1 name: ");
                    String p1 = sc.nextLine().trim();
                    System.out.print("Player 2 name: ");
                    String p2 = sc.nextLine().trim();

                    int rows = askInt(sc, "Rows of points (>=2): ", 2, 30);
                    int cols = askInt(sc, "Cols of points (>=2): ", 2, 30);

                    Tile[] tiles = new Tile[rows * cols];
                    int k = 0;
                    for (int r = 0; r < rows; r++)
                        for (int c = 0; c < cols; c++)
                            tiles[k++] = new Tile(k, new Pos(r, c));

                    Board b = new DotsBoard(rows, cols, tiles, new ConnectedGoal(), new ParitySolvability());
                    Game game = new Game(b, new RandomShuffler());
                    game.setMode(new DotsMode());
                    game.start(0);

                    System.out.println("Enter edge as: a b   (two adjacent point IDs). Type 'quit' to exit.\n");
                    while (!game.isOver()) {
                        System.out.println(game.render());
                        System.out.print("Move: ");
                        String cmd = sc.nextLine().trim();
                        if (cmd.equalsIgnoreCase("quit")) break;
                        game.stepCommand(cmd);
                    }
                    break;
                }
                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.out.println("Goodbye!");
        sc.close();
    }

    private static int askInt(Scanner sc, String prompt, int lo, int hi) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v < lo || v > hi) throw new NumberFormatException();
                return v;
            } catch (Exception e) {
                System.out.println("Enter an integer in [" + lo + ", " + hi + "].");
            }
        }
    }
}
