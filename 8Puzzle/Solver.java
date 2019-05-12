import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private int moves;
    private Stack<Board> solution;

    public Solver(
            Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        Node searchNode;
        Node nextSearch;
        MinPQ<Node> minPq = new MinPQ<Node>();
        solution = new Stack<Board>();
        moves = 0;

        searchNode = new Node(null, initial, moves);
        minPq.insert(searchNode);
        solution.push(searchNode.board);

        solution.push(initial);
        while (!searchNode.board.isGoal()) {
            Iterable<Board> it = searchNode.board.neighbors();
            moves += 1;
            for (Board i : it) {
                Node nextNode = new Node(searchNode.board, i, moves);
                if (!nextNode.board.equals(nextNode.predecessor)) {
                    minPq.insert(nextNode);
                }
            }

            searchNode = minPq.delMin();
            solution.push(searchNode.board);
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
        return solution;
    }

    private class Node implements Comparable<Node> {
        private Board predecessor;
        private Board board;
        private int moves;

        public Node(Board before, Board now, int moveCount) {
            predecessor = before;
            board = now;
            moves = moveCount;
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

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        In in = new In("puzzle01.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Solver solver = new Solver(new Board(tiles));

        StdOut.println(solver.moves());

        Iterable<Board> it = solver.solution();
        for (Board i : it) {
            StdOut.println(i);
        }
    }

}
