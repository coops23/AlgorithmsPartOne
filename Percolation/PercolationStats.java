import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stdDev;
    private final double confidenceLo;
    private final double confidenceHi;
    private static final double CONFIDENCE_INTERVAL_CONSTANT = 1.96;

    public PercolationStats(int n,
                            int trials)    // perform trials independent experiments on an n-by-n grid
    {
        double[] samples = new double[trials];
        double total = 0;

        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int col = StdRandom.uniform(1, n + 1);
                int row = StdRandom.uniform(1, n + 1);

                per.open(row, col);
            }
            samples[i] = (double) per.numberOfOpenSites() / (n * n);
            total += samples[i];
        }

        mean = StdStats.mean(samples);
        stdDev = StdStats.stddev(samples);
        confidenceLo = mean - ((CONFIDENCE_INTERVAL_CONSTANT * stdDev) / Math.sqrt(trials));
        confidenceHi = mean + ((CONFIDENCE_INTERVAL_CONSTANT * stdDev) / Math.sqrt(trials));
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return mean;
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return stdDev;
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return confidenceLo;
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return confidenceHi;
    }

    public static void main(String[] args)        // test client (described below)
    {
        PercolationStats stats = new PercolationStats(200, 100);

        StdOut.println(stats.mean());
        StdOut.println(stats.stddev());
        StdOut.println("[" + stats.confidenceLo() + ":" + stats.confidenceHi() + "]");
    }
}
