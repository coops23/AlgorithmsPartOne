import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private int moves;
    private LinkedStack<Board> solution;

    public Solver(
            Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        Node searchNode;
        MinPQ<Node> minPq = new MinPQ<Node>();

        solution = new LinkedStack<Board>();
        moves = 0;
        Board temp;

        searchNode = new Node(null, initial, moves);
        temp = searchNode.board;
        solution.push(temp);

        while (!searchNode.board.isGoal()) {


            Iterable<Board> it = searchNode.board.neighbors();

            for (Board i : it) {
                if (!i.equals(searchNode.predecessor)) {
                    Node nextNode = new Node(searchNode.board, i, moves + 1);
                    minPq.insert(nextNode);
                }
            }

            searchNode = minPq.delMin();
            temp = searchNode.board;
            solution.push(temp);
            moves++;
        }
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return true;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return moves;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (isSolvable()) { return solution; }
        else { return null; }
    }

    private class Node implements Comparable<Node> {
        private Board predecessor;
        private Board board;
        private int moves;
        private int manhatten;

        public Node(Board before, Board now, int moveCount) {
            predecessor = before;
            board = now;
            moves = moveCount;
            manhatten = now.manhattan();
        }

        public int compare(Node a, Node b) {
            return Integer.compare(a.board.manhattan() + a.moves, b.board.manhattan() + b.moves);
        }

        public int compareTo(Node that) {
            return compare(this, that);
        }

        public Comparator<Node> manhattenComparitor
                = new Comparator<Node>() {

            public int compare(Node a, Node b) {
                return a.compare(a, b);
            }

        };
    }

    public static void main(String[] args) {

        String file = "puzzle07.txt";
        In in = new In(file);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves() + " for " + file);
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
