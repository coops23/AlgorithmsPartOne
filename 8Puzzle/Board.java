import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] _blocks;
    private final int n;
    private int moves;
    private List<Board> neighbors;

    public Board(
            int[][] blocks)           // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    {
        n = blocks.length;
        boolean openPositionFound = false;
        neighbors = new ArrayList<Board>();
        _blocks = new int[n][n];
        moves = 0;

        if (n <= 0) {
            throw new IllegalArgumentException("blocks are empty!");
        }

        int openRow = 0;
        int openCol = 0;
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < n; y++) {
                _blocks[i][y] = blocks[i][y];
                if (blocks[i][y] == 0) {
                    openPositionFound = true;
                    openRow = i;
                    openCol = y;
                }
            }
        }

        if (!openPositionFound) {
            throw new IllegalArgumentException("No open spot!");
        }

        if (openRow > 0 && openRow < n - 1 && openCol > 0 && openCol < n - 1) {
            
        }
        else if (openRow == 0 && openCol == 0) {

        }
        else if (openRow == 0 && openCol == n - 1) {

        }
        else if (openRow == n - 1 && openCol == 0) {

        }
        else if (openRow == n - 1 && openCol == n - 1) {

        }
        else if ()
    }

    public int dimension()                 // board dimension n
    {
        return n;
    }

    public int hamming()                   // number of blocks out of place
    {
        int count = moves;
        int iter = 1;

        for (int i = 0; i < n; i++) {
            for (int y = 0; y < n; y++) {
                if (_blocks[i][y] != iter) {
                    count++;
                }
                iter++;
            }
        }

        return count;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int count = moves;
        int iter = 1;

        for (int i = 0; i < n; i++) {
            for (int y = 0; y < n; y++) {
                if (_blocks[i][y] != iter) {
                    //say 1 was in position i = 2 and y = 2
                    int desiredRow = _blocks[i][y] / n;
                    int desiredColumn = _blocks[i][y] - (desiredRow * n) - 1;
                    int deltaRow = java.lang.Math.abs(desiredRow - i);
                    int deltaColumn = java.lang.Math.abs(desiredColumn - y);
                    int delta = deltaColumn + deltaRow;

                    count += delta;
                }
                iter++;
            }
        }

        return count;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        int iter = 1;
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < n; y++) {
                if (_blocks[i][y] != iter) {
                    if (iter != n * n) {
                        return false;
                    }
                }
                iter++;
            }
        }

        return true;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int y = 0; y < n; y++) {
                tiles[i][y] = _blocks[i][y];
            }
        }

        int pos0 = StdRandom.uniform(0, n * n - 1);
        int pos1 = StdRandom.uniform(0, n * n - 1);

        while (pos1 == pos0) {
            pos1 = StdRandom.uniform(0, n * n - 1);
        }

        int i0 = pos0 / n;
        int j0 = pos0 - (i0 * n);
        int i1 = pos1 / n;
        int j1 = pos1 - (i1 * n);

        int temp = tiles[i0][j0];
        tiles[i0][j0] = tiles[i1][j1];
        tiles[i1][j1] = temp;

        return new Board(tiles);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        return neighbors;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        String message = "";

        for (int i = 0; i < n; i++) {
            String row = "";
            for (int y = 0; y < n; y++) {
                row += String.valueOf(_blocks[i][y]) + " ";
            }
            row += "\n";

            message += row;
        }

        return message;
    }

    private int[][] swap(int[][] tiles, int rowA, int colA, int rowB, int colB) {
        int temp = tiles[rowA][colA];
        tiles[rowA][colA] = tiles[rowB][colB];
        tiles[rowB][colB] = temp;

        return tiles;
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In("puzzle2x2-00.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board board = new Board(tiles);
        Board twin = board.twin();

        StdOut.println(board.toString());
        StdOut.println(board.isGoal());
        StdOut.println(twin.toString());
        StdOut.println(twin.isGoal());
    }
}
