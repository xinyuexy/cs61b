package hw2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class PercolationStats {
    private int N;
    private int T;
    private List<Double> threshold;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("arguments cannot be zero");
        }

        this.N = N;
        this.T = T;
        this.threshold = this.runSimulation();
    }

    private List<Double> runSimulation() {
        List<Double> thresh = new ArrayList<>();

        for (int i=0; i<T; i++) {
            double n = runSinglePass();
            thresh.add(n);
        }

        return thresh;
    }

    private double runSinglePass() {
        Percolation p = new Percolation(N);

        while (!p.percolates()) {
            // get a blocked site
            int row = StdRandom.uniform(0, N);
            int col = StdRandom.uniform(0, N);
            if (!p.isOpen(row, col)) {
                p.open(row, col);
            }
        }

        double thresh = (double) p.numberOfOpenSites() / (N * N);
        return thresh;
    }

    public double mean() {
        double mu = 0;

        for (int i=0; i<T; i++) {
            mu += threshold.get(i);
        }

        mu /= T;
        return mu;
    }

    public double stddev() {
        double mu = this.mean();
        double sigmasq = 0;

        for (int i=0; i<T; i++) {
            double term = threshold.get(i) - mu;
            term = Math.pow(term, 2);
            sigmasq += term;
        }

        sigmasq /= T - 1;
        return sigmasq;
    }

    public double confidenceLow() {
        double mu = this.mean();
        double sigma = Math.sqrt(this.stddev());

        double lowEndPoint = mu - ((1.96 * sigma) / Math.sqrt(this.T));
        return lowEndPoint;
    }

    public double confidenceHigh() {
        double mu = this.mean();
        double sigma = Math.sqrt(this.stddev());

        double highEndPoint = mu + ((1.96 * sigma) / Math.sqrt(this.T));
        return highEndPoint;
    }

    public static void main(String[] args) {
        Stopwatch timer = new Stopwatch();
        PercolationStats ps = new PercolationStats(20, 50);
        double time = timer.elapsedTime();
        StdOut.printf("mean: %.3f,  stddev: %.3f\n", ps.mean(), ps.stddev());
        StdOut.printf("%.2f seconds\n", time);
    }
}                       
