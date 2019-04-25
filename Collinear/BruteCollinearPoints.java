/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {
    private int segmentCount;
    private List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        lineSegments = new ArrayList<LineSegment>();
        List<Point> pointList = new ArrayList<Point>();
        for (int i = 0; i < points.length; i++) {
            if (points[i] != null) {
                pointList.add(points[i]);
            }
            else {
                throw new java.lang.IllegalArgumentException();
            }
        }
        Collections.sort(pointList);

        if(pointList.size() > 1) {
            Point prev = pointList.get(0);
            for (int i = 1; i < pointList.size(); i++) {
                Point p = pointList.get(i);
                if (p.compareTo(prev) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }

                prev = p;
            }
        }

        for (int p0 = 0; p0 < pointList.size(); p0++) {
            for (int p1 = p0 + 1; p1 < pointList.size(); p1++) {
                for (int p2 = p1 + 1; p2 < pointList.size(); p2++) {
                    for (int p3 = p2 + 1; p3 < pointList.size(); p3++) {
                        Point origin = pointList.get(p0);
                        double pq = origin.slopeTo(pointList.get(p1));
                        double pr = origin.slopeTo(pointList.get(p2));
                        double ps = origin.slopeTo(pointList.get(p3));

                        if ((pq == pr) && (pr == ps)) {
                            lineSegments.add(new LineSegment(origin, pointList.get(p3)));
                            segmentCount++;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        LineSegment[] returnValue = new LineSegment[lineSegments.size()];

        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = lineSegments.get(i);
        }

        return returnValue;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("input8.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
