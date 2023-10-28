import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final boolean[] grid;
    private final int n;
    private int nOfOpen;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int num) {
        if (num <= 0)  {
            throw new IllegalArgumentException();
        }
        // + 2 for begin of percolation and end of it
        uf = new WeightedQuickUnionUF(num * num + 2);
        grid = new boolean[num * num];
        n = num;
        nOfOpen = 0;
    }
    private void checkArgs(int row, int col) {
        if (row > n || row <= 0) {
            throw new IllegalArgumentException();
        }
        if (col > n || col <= 0) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)  {
        checkArgs(row, col);
        row -= 1;
        col -= 1;
        // open this site
        int thisSite = row*n + col;
        if (grid[thisSite]) {
            return;
        }
        grid[thisSite] = true;
        nOfOpen++;
        // begin of grid
        int topSite;
        if (row == 0) {
            // n * n is last - 1 element
            topSite = n*n;
            uf.union(topSite, thisSite);
        } else if (row - 1 >= 0 && grid[(row-1)*n + col]){
            topSite = (row-1)*n + col;
            uf.union(topSite, thisSite);
        }

        int bottomSite;
        if (row == n - 1) {
            // n * n + 1 is last element
            bottomSite = n*n+1;
            uf.union(bottomSite, thisSite);
        } else if (row + 1 < n && grid[(row+1)*n + col]) {
            bottomSite = (row+1)*n + col;
            uf.union(bottomSite, thisSite);
        }

        int rightSite = row*n + col + 1;
        if (col + 1 < n && grid[rightSite]) {
            uf.union(rightSite, thisSite);
        }

        int leftSite = row*n + col-1;
        if (col - 1 >= 0 && grid[leftSite]) {
            uf.union(leftSite, thisSite);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArgs(row, col);
        row -= 1;
        col -= 1;
        return grid[row*n + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArgs(row, col);
        row -= 1;
        col -= 1;

        int thisSite = row*n + col;
        int topmostSite = n*n;
        thisSite = uf.find(thisSite);
        int tt = uf.find(topmostSite);
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (grid[i*n + j]) {
//                    System.out.print("1  ");
//                } else {
//                    System.out.print("0  ");
//                }
//            }
//            System.out.println();
//        }
        return thisSite == tt;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        int topmostSite = n*n;
        int bottommostSite = n*n + 1;
        bottommostSite = uf.find(bottommostSite);
        topmostSite = uf.find(topmostSite);
        return topmostSite == bottommostSite;
    }
    private static void test(Percolation p, int row, int col) {
        System.out.println("isOpen: " + p.isOpen(row, col));
        System.out.println("percolates: " + p.percolates());
        System.out.println("numberOfOpenSites: " + p.numberOfOpenSites());
        System.out.println("isFull: " + p.isFull(row, col));
        System.out.println("row col: " + row + "/" + col);
    }
    public static void main(String[] args) {
        int n = 10000;
        Percolation p = new Percolation(n);
        for (int i = 0; i < n ;i++ ) {
            for (int j = 0; j < n; j++) {
                p.open(i+1, j+1);
            }
        }
//        for (int i = 0; i < n; i++) {
//            p.open(1, i + 1);
//        }

        for (int i = 0; i < n ;i++ ) {
            for (int j = 0; j < n; j++) {
                if (!p.isFull(i+1, j+1)) {
                    test(p, i+1, j+1);
                }
            }
        }
    }
}
