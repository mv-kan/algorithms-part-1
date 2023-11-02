import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }
    private static void mergeSort(Point[] a, Point[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }
    private LineSegment[] lineSegments;
    private int n;
    private void addToLineSegments(LineSegment segment) {
        n++;
        if (lineSegments.length < n) {
            LineSegment[] copy = new LineSegment[lineSegments.length * 2];
            System.arraycopy(lineSegments, 0, copy, 0, lineSegments.length);
            lineSegments = copy;
        }
        lineSegments[n - 1] = segment;
    }
    private void shinkTheArr() {
        if (n != lineSegments.length) {
            LineSegment[] copy = new LineSegment[n];
            System.arraycopy(lineSegments, 0, copy, 0, n);
            lineSegments = copy;
        }
    }
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        lineSegments = new LineSegment[1];
        if (points == null) {
            throw new IllegalArgumentException();
        }
        // check for duplicates
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j]) {
                    throw new IllegalArgumentException();
                }
            }
        }

        Point[] aux = new Point[points.length];
        mergeSort(points, aux, 0, points.length - 1);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];

                        double pq = p.slopeTo(q);
                        double pr = p.slopeTo(r);
                        double ps = p.slopeTo(s);
                        if (pq == pr && pq == ps && p.compareTo(q) < 0 && p.compareTo(r) < 0 && p.compareTo(s) < 0) {
                            LineSegment lsPS = new LineSegment(p, s);
                            addToLineSegments(lsPS);
                        }
                    }
                }
            }
        }
        shinkTheArr();
    }
    // the number of line segments
    public int numberOfSegments() {
        return n;
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[lineSegments.length];
        System.arraycopy(lineSegments, 0, copy, 0, lineSegments.length);
        return copy;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
