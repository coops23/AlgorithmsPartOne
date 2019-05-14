import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private int moves;
    private Stack<Board> solution;
    private final boolean isSolvable;

    public Solver(
            Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node searchNode, twinSearchNode;
        MinPQ<Node> minPq = new MinPQ<Node>();
        MinPQ<Node> twinMinPq = new MinPQ<Node>();
        Stack<Node> validNodes = new Stack<Node>();
        solution = new Stack<Board>();
        moves = 0;
        int twinMoves = 0;

        searchNode = new Node(null, initial, 0);
        validNodes.push(searchNode);
        twinSearchNode = new Node(null, initial.twin(), 0);
        while (!searchNode.board.isGoal() && !twinSearchNode.board.isGoal()) {
            Iterable<Board> it = searchNode.board.neighbors();
            for (Board i : it) {
                if (searchNode.predecessor == null) {
                    Node nextNode = new Node(searchNode, i, moves + 1);
                    minPq.insert(nextNode);
                }
                else if (!i.equals(searchNode.predecessor.board)) {
                    Node nextNode = new Node(searchNode, i, moves + 1);
                    minPq.insert(nextNode);
                }
            }

            searchNode = minPq.delMin();
            validNodes.push(searchNode);
            moves = searchNode.moves;

            Iterable<Board> twinIt = twinSearchNode.board.neighbors();
            for (Board i : twinIt) {
                if (twinSearchNode.predecessor == null) {
                    Node nextNode = new Node(twinSearchNode, i, twinMoves + 1);
                    twinMinPq.insert(nextNode);
                }
                else if (!i.equals(twinSearchNode.predecessor.board)) {
                    Node nextNode = new Node(twinSearchNode, i, twinMoves + 1);
                    twinMinPq.insert(nextNode);
                }
            }

            twinSearchNode = twinMinPq.delMin();
            twinMoves = twinSearchNode.moves;
        }

        if (twinSearchNode.board.isGoal() && !searchNode.board.isGoal()) {
            isSolvable = false;
            return;
        }
        else {
            isSolvable = true;
        }

        while (searchNode != null) {
            solution.push(searchNode.board);
            searchNode = searchNode.predecessor;
        }

    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return isSolvable;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (isSolvable)
            return moves;
        else
            return -1;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (isSolvable()) {
            return solution;
        }
        else {
            return null;
        }
    }

    private class Node implements Comparable<Node> {
        private Node predecessor;
        private Board board;
        private int moves;
        private int manhatten;

        public Node(Node before, Board now, int moveCount) {
            predecessor = before;
            board = now;
            moves = moveCount;
            manhatten = now.manhattan() + moveCount;
        }

        public int getManhatten() {
            return manhatten;
        }

        public int getMoves() {
            return moves;
        }

        public int compare(Node a, Node b) {
            return Integer.compare(a.getManhatten(), b.getManhatten());
        }

        public int compareTo(Node that) {
            return compare(this, that);
        }

        public String toString() {
            String message = "Move: " + moves + " Manhatten: " + board.manhattan() + " Priority: "
                    + manhatten + " Hamming: " + board.hamming() + "\n";

            message += board.toString();

            return message;
        }

        public Comparator<Node> manhattenComparitor = new Comparator<Node>() {
            public int compare(Node a, Node b) {
                return a.compare(a, b);
            }

        };
    }

    public static void main(String[] args) {

        String file = "puzzle4x4-09.txt";
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
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

}
