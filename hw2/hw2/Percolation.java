package hw2;

/**
 * 参考:[http://www.cnblogs.com/anne-vista/p/4841732.html]
 *  [https://github.com/varadgunjal/cs61b/blob/master/hw2/hw2/Percolation.java]
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    //0 for blocked site, 1 for open site
    private boolean[][] isOpen;
    //track number of open sites
    private int numberOfOpenSites;
    //uf用于判断percolates(), ufNoBottom判断isFull()
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufNoBottom;    //解决backwash问题
    //虚拟结点
    private int virtualTop;
    private int virtualBottom;

    /**
     * create N-by-N grid, with all sites initially blocked
     * @param N number of grid
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Invalid input: "+N);
        }
        this.n = N;
        this.numberOfOpenSites = 0;
        this.isOpen = new boolean[N][N];     // all are initialized to false by default

        int numSets = (N * N) + 2;  // + 2 for virtual top & bottom
        uf = new WeightedQuickUnionUF(numSets);
        ufNoBottom = new WeightedQuickUnionUF(numSets-1);   // only virtual top, no bottom

        // Make the last 2 elements  the virtual top and bottom
        virtualTop = numSets - 2;
        virtualBottom = numSets - 1;
    }

    /**
     * open the site (row, col) if it is not open already
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
       // if (isOpen[row][col]) return;
        isOpen[row][col] = true;
        int index1D = indexXYTo1D(row, col);

        if (row == 0) {     //top connected to virtualTop
            uf.union(index1D, virtualTop);
            ufNoBottom.union(index1D, virtualTop);
        }

        if (row == n - 1) {     //last row connected to bottom
            uf.union(index1D, virtualBottom);
        }

        if (row > 0 && isOpen[row-1][col]) {    // 如果格子不在第一排，则与上面的格子连通
            uf.union(indexXYTo1D(row-1, col), index1D);
            ufNoBottom.union(indexXYTo1D(row-1, col), index1D);
        }
        if (row < n-1 && isOpen[row+1][col]) {  //down
            uf.union(indexXYTo1D(row+1, col), index1D);
            ufNoBottom.union(indexXYTo1D(row+1, col), index1D);
        }
        if (col > 0 && isOpen[row][col-1]) {    //left
            uf.union(indexXYTo1D(row, col - 1), index1D);
            ufNoBottom.union(indexXYTo1D(row, col - 1), index1D);
        }
        if (col < n-1 && isOpen[row][col+1]) {  //right
            uf.union(indexXYTo1D(row, col + 1), index1D);
            ufNoBottom.union(indexXYTo1D(row, col + 1), index1D);
        }

        numberOfOpenSites++;
    }

    /** is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        return isOpen[row][col];
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= this.n ||
                col < 0 || col >= this.n) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        int index1D = indexXYTo1D(row, col);
        //防止回流
        return uf.connected(index1D, virtualTop) && ufNoBottom.connected(index1D, virtualTop);
    }

    /** number of open sites */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return uf.connected(virtualBottom, virtualTop);
    }

    private int indexXYTo1D(int x, int y) {
        return (x * n) + y;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(0, 3);
        p.open(1, 3);
        p.open(2,3);
        p.open(3,3);
        p.open(4,3);
        if (p.percolates()) {
            System.out.println("percolates");
        }
    }
}                       
