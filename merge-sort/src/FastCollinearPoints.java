import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class FastCollinearPoints {
    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) > 0) a[k] = aux[j++];
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
    private static void merge(Comparator<Point> c, Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (c.compare(aux[i], aux[j]) < 0) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
    }
    private static void mergeSort(Comparator<Point> c, Point[] a, Point[] aux, int lo, int hi) {
//        if (lo >= hi) return;
//        int mid = lo + (hi - lo) / 2;
//        mergeSort(c, a, aux, lo, mid);
//        mergeSort(c, a, aux, mid+1, hi);
//        merge(c, a, aux, lo, mid, hi);
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(c, a, aux, lo, mid);
        mergeSort(c, a, aux, mid+1, hi);
        merge(c, a, aux, lo, mid, hi);
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
    public FastCollinearPoints(Point[] points_) {
        if (points_ == null) {
            throw new IllegalArgumentException();
        }
        Point[] points = new Point[points_.length];
        System.arraycopy(points_, 0, points, 0, points_.length);
        lineSegments = new LineSegment[1];

        // check for duplicates
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        Point[] aux = new Point[points.length];
        mergeSort(points, aux, 0, points.length - 1);

        Point[] pCopy = new Point[points.length];
        System.arraycopy(points, 0, pCopy, 0, points.length);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Comparator<Point> c = p.slopeOrder();
            mergeSort(pCopy, aux, 0, pCopy.length - 1);
            mergeSort(c, pCopy, aux, 0, pCopy.length - 1);
            int combo = 0;
            double slope = 0;
            boolean isFirst = true;
            boolean thisSlopeToSkip = false;
            int indexStartOfCombo = 0;
            // calculate point
            for (int j = 0; j < points.length; j++) {
                if (pCopy[j].compareTo(p) == 0)
                    continue;
                if (isFirst) {
                    if (pCopy[j].compareTo(p) > 0) {
                        thisSlopeToSkip = true;
                    }
                    isFirst = false;
                    slope = p.slopeTo(pCopy[j]);
                    indexStartOfCombo = j;
                    combo++;
                    continue;
                }
                combo++;
                if (pCopy[j].compareTo(p) > 0 && slope == p.slopeTo(pCopy[j])) {
                    thisSlopeToSkip = true;
                    combo = 1;
                }
                if (slope != p.slopeTo(pCopy[j]) || j + 1 == points.length) {
                    if (thisSlopeToSkip) {
                        combo = 1;
                        indexStartOfCombo = j;
                        thisSlopeToSkip = false;
                    }
                    if (combo >= 3) {
                        addToLineSegments(new LineSegment(p, pCopy[indexStartOfCombo]));
                    }
                    indexStartOfCombo = j;
                    combo = 1;
                }
                slope = p.slopeTo(pCopy[j]);
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
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 20000);
        StdDraw.setYscale(0, 20000);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.setPenRadius(0.005);
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
