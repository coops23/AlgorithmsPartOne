import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] nodes;
    private int openSites;
    private final int len;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufBackWash;

    public Percolation(int n)// create n-by-n grid, with all sites blocked
    {
        openSites = 0;
        len = n;

        if (len <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid grid size");
        }

        uf = new WeightedQuickUnionUF((len * len) + 3);
        ufBackWash = new WeightedQuickUnionUF((len*len) + 2);

        nodes = new boolean[len][len];
        for (int col = 0; col < len; col++) {
            for (int row = 0; row < len; row++) {
                nodes[col][row] = false;
            }
        }
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (row <= 0 || row > len || col <= 0 || col > len) {
            throw new java.lang.IllegalArgumentException("Invalid row or col parameter");
        }

        if (isOpen(row, col))
            return;

        int p = (col - 1) + ((row - 1) * len) + 1;
        int q;

        q = (col - 1) + ((row - 2) * len) + 1;
        if (row == 1) {
            q = (len * len) + 1;
            uf.union(p, q);
            ufBackWash.union(p,q);
        }
        else {
            if (isOpen(row - 1, col)) {
                uf.union(p, q);
                ufBackWash.union(p,q);
            }
        }

        q = (col - 1) + ((row * len) + 1);
        if (row == len) {
            q = (len * len) + 2;
            uf.union(p, q);
        }
        else {
            if (isOpen(row + 1, col))
            {
                uf.union(p, q);
                ufBackWash.union(p,q);
            }
        }

        q = (col - 2) + ((row - 1) * len) + 1;
        if (col != 1)
            if (isOpen(row, col - 1))
            {
                uf.union(p, q);
                ufBackWash.union(p,q);
            }

        q = (col) + ((row - 1) * len) + 1;
        if (col != len)
            if (isOpen(row, col + 1))
            {
                uf.union(p, q);
                ufBackWash.union(p,q);
            }

        nodes[row - 1][col - 1] = true;
        openSites++;
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row <= 0 || row > len || col <= 0 || col > len) {
            throw new java.lang.IllegalArgumentException("Invalid row or col parameter");
        }

        return (nodes[row - 1][col - 1]);
    }

    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        if (row <= 0 || row > len || col <= 0 || col > len) {
            throw new java.lang.IllegalArgumentException("Invalid row or col parameter");
        }

        if (isOpen(row, col)) {
            int p = (col - 1) + ((row - 1) * len) + 1;
            return ufBackWash.connected((len * len) + 1, p);
        }
        else {
            return false;
        }
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return openSites;
    }

    public boolean percolates()            // does the system percolate?
    {
        return uf.connected((len * len) + 1, (len * len) + 2);
    }

    public static void main(String[] args)   // test client (optional)
    {

    }
}
