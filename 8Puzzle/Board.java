import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] tiles;
    private final int n;
    private int moves;
    private Stack<Board> neighbors;
    private final int openRow;
    private final int openCol;

    public Board(
            int[][] blocks)           // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    {
        n = blocks.length;
        boolean openPositionFound = false;
        neighbors = new Stack<Board>();
        tiles = new int[n][n];
        moves = 0;

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
                if (tiles[i][y] != iter) {
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
                if (tiles[i][y] != iter) {
                    //say 1 was in position i = 2 and y = 2
                    int desiredRow = tiles[i][y] / n;
                    int desiredColumn = tiles[i][y] - (desiredRow * n) - 1;
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
                if (tiles[i][y] != iter) {
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
        int[][] tempTiles = new int[n][n];
        cpy(tempTiles);

        int pos0 = StdRandom.uniform(0, (n * n) - 1);
        int pos1 = StdRandom.uniform(0, (n * n) - 1);

        while (pos1 == pos0) {
            pos1 = StdRandom.uniform(0, n * n - 1);
        }

        swap(tempTiles, row(pos0), col(pos0), row(pos1), col(pos1));

        return new Board(tempTiles);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        final Board that = (Board) y;
        for(int i = 0; i < n ; i++)
        {
            for(int j = 0; j < n ; j++)
            {
                if(that.tiles[i][j] != this.tiles[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        int[][] tempTiles = new int[n][n];
        //swap upper
        if(openRow > 0)
        {
            cpy(tempTiles);
            swap(tempTiles, openRow, openCol, openRow - 1, openCol);
            neighbors.push(new Board(tempTiles));
        }

        //swap lower
        if(openRow < n - 1)
        {
            cpy(tempTiles);
            swap(tempTiles, openRow, openCol, openRow + 1, openCol);
            neighbors.push(new Board(tempTiles));
        }

        //swap left
        if(openCol > 0)
        {
            cpy(tempTiles);
            swap(tempTiles, openRow, openCol, openRow, openCol - 1);
            neighbors.push(new Board(tempTiles));
        }

        //swap right
        if(openCol < n - 1)
        {
            cpy(tempTiles);
            swap(tempTiles, openRow, openCol, openRow, openCol + 1);
            neighbors.push(new Board(tempTiles));
        }

        return neighbors;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        String message = "";

        for (int i = 0; i < n; i++) {
            String row = "";
            for (int y = 0; y < n; y++) {
                row += String.valueOf(tiles[i][y]) + " ";
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

    private void cpy(int[][] temp)
    {
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

    private int pos(int row, int col) {
        return (row * n) + col;
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

        StdOut.println("Now printing out neighbors");
        Iterable<Board> it = board.neighbors();
        for(Board i : it)
        {
            StdOut.println(i);
            StdOut.println(i.equals(board));
        }

        StdOut.println(board.equals(board));
        StdOut.println(board.equals(null));
        Board board2 = new Board(tiles);
        StdOut.println(board2.equals(board));
    }
}
