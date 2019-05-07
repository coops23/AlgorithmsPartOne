import edu.princeton.cs.algs4.In;

public class Solver {
    private int moves;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        moves = 0;
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return true;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return moves;
    }

    /*public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {

    }*/

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        In in = new In("puzzle2x2-00.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board initialBoard = new Board(tiles);
        Solver solver = new Solver(initialBoard);


    }

}