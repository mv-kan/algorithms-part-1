import edu.princeton.cs.algs4.StdRandom;

// https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
public class PercolationStats {
    private double mean;
    private double stddev;
    private final double confidHi;
    private final double confidLo;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        double[] xs = new double[trials];
        for (int i = 0; i < trials; i++) {
            int timesUntilThreshold = 0;
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(0, n) + 1;
                int col = StdRandom.uniformInt(0, n) + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    timesUntilThreshold++;
                }
            }
            double x = (double)timesUntilThreshold / (n*n);
            xs[i] = x;
        }
        // calc mean
        for (int i = 0; i < trials; i++ ) {
            mean += xs[i];
        }
        mean /= trials;

        // calc stddev
        for (int i = 0; i < trials; i++) {
            stddev += Math.pow((xs[i] - mean), 2);
        }
        stddev /= trials - 1;
        stddev = Math.sqrt(stddev);

        // calc lo
        confidLo = mean - (1.96*stddev / Math.sqrt(trials));
        // calc hi
        confidHi = mean + (1.96*stddev / Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, T);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = { " + ps.confidenceLo() + ", " + ps.confidenceHi() + " }");
    }
}
