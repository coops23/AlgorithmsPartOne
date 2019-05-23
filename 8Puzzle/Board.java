import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] tiles;
    private final int n;
    private final int openRow;
    private final int openCol;
    private int swapPos0;
    private int swapPos1;

    public Board(int[][] blocks) {
        n = blocks.length;
        boolean openPositionFound = false;

        tiles = new int[n][n];

        if (n <= 0) {
            throw new IllegalArgumentException("blocks are empty!");
        }

        int x = 0, j = 0;
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < n; y++) {
                tiles[i][y] = blocks[i][y];
                if (blocks[i][y] == 0) {
                    openPositionFound = true;
                    x = i;
                    j = y;
                }
            }
        }

        if (!openPositionFound) {
            throw new IllegalArgumentException("No open spot!");
        }

        openRow = x;
        openCol = j;

        swapPos0 = 0;
        int value = 0;
        while (value == 0) {
            swapPos0 = StdRandom.uniform(0, (n * n));
            value = tiles[row(swapPos0)][col(swapPos0)];
        }

        swapPos1 = 0;
        value = 0;
        while (swapPos1 == swapPos0 || value == 0) {
            swapPos1 = StdRandom.uniform(0, (n * n));
            value = tiles[row(swapPos1)][col(swapPos1)];
        }
    }

    public int dimension()                 // board dimension n
    {
        return n;
    }

    public int hamming()                   // number of blocks out of place
    {
        int hamming = 0;

        int pos = 1;
        while (pos < (n * n)) {
            int i = row(pos - 1);
            int y = col(pos - 1);
            int value = tiles[i][y];

            if (value != pos) {
                hamming++;
            }

            pos++;
        }

        return hamming;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manhatten = 0;

        int pos = 1;
        while (pos <= (n * n)) {
            int i = row(pos - 1);
            int y = col(pos - 1);
            int value = tiles[i][y];

            if (value != 0) {
                manhatten += java.lang.Math.abs(row(value - 1) - i);
                manhatten += java.lang.Math.abs(col(value - 1) - y);
            }

            pos++;
        }

        return manhatten;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        int pos = 1;
        while (pos < (n * n)) {
            int i = row(pos - 1);
            int y = col(pos - 1);

            if (tiles[i][y] != pos) {
                return false;
            }

            pos++;
        }

        return true;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] twinTiles = new int[n][n];
        Board twin;

        int value = 0;
        int pos0 = 0;
        while(value == 0)
        {
        int pos0 = StdRandom.uniform(0, (n * n) - 1);
            value = tempTiles[row(pos0)][col(pos0)];
        }


        value = 0;
        while (pos1 == pos0) {
            pos1 = StdRandom.uniform(0, n * n - 1);
            value = tempTiles[row(pos1)][col(pos1)];
        swap(twinTiles, row(swapPos0), col(swapPos0), row(swapPos1), col(swapPos1));
        twin = new Board(tempTiles);
        return new Board(tempTiles);
        return twin;
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;

        final Board that = (Board) y;

        {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> neighbors = new Stack<Board>();

        int[][] tempTiles = new int[n][n];
        //swap upper
        if (openRow > 0) {
            cpy(tempTiles);
            swap(tempTiles, openRow, openCol, openRow - 1, openCol);
            neighbors.push(new Board(tempTiles));
        }

        //swap lower
        if (openRow < n - 1) {
            cpy(tempTiles);
            swap(tempTiles, openRow, openCol, openRow + 1, openCol);
            neighbors.push(new Board(tempTiles));
        }

        //swap left
        if (openCol > 0) {
            cpy(tempTiles);
            swap(tempTiles, openRow, openCol, openRow, openCol - 1);
            neighbors.push(new Board(tempTiles));
        }

        //swap right
        if (openCol < n - 1) {
            cpy(tempTiles);
            swap(tempTiles, openRow, openCol, openRow, openCol + 1);
            neighbors.push(new Board(tempTiles));
        }

        return neighbors;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        String message = n + "\n";

        for (int i = 0; i < n; i++) {
            String row = " ";
            for (int y = 0; y < n; y++) {
                row += String.valueOf(tiles[i][y]) + "  ";
            }
            row += "\n";

            message += row;
        }

        return message;
    }

    private void swap(int[][] swappedTiles, int rowA, int colA, int rowB, int colB) {
        int temp = swappedTiles[rowA][colA];
        swappedTiles[rowA][colA] = swappedTiles[rowB][colB];
        swappedTiles[rowB][colB] = temp;
    }

    private void cpy(int[][] temp) {
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < n; y++) {
                temp[i][y] = tiles[i][y];
            }
        }
    }

    private int row(int pos) {
        return pos / n;
    }

    private int col(int pos) {
        return pos - (row(pos) * n);
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In("puzzle2x2-unsolvable1.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board board = new Board(tiles);
        Board twin = board.twin();

        StdOut.println(board.manhattan());
        StdOut.println(board.hamming());
        StdOut.println(board);
        Board twin = board.twin();
        StdOut.println(twin);
        StdOut.println(board);
    }
}
